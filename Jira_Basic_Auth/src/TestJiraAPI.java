import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.annotations.Test;

import file.help;
public class TestJiraAPI {
	@Test
	public static void TestAddIssue() {
	RestAssured.baseURI = "https://marwanshamso2022.atlassian.net";
	String ResponseAddIssue = given().header("Content-Type","application/json")
			.header("Authorization",help.Auth())
			.body(help.addIssueBody("MK", "Bug", "Create Issue Using Rest Assured"))
			.when().post("rest/api/3/issue")
			.then().log().body().assertThat().statusCode(201).extract().response().asString();
			
	JsonPath jsonAddIssue = new JsonPath(ResponseAddIssue);
	String IssueID = jsonAddIssue.get("id");
	
	//Add Attachment;
	String ResponseAddAttach = given().header("X-Atlassian-Token","no-check")
			.header("Authorization",help.Auth()).pathParam("key", IssueID)
			.multiPart("file",new File("E:\\447443809_3943601419253664_2585760587877699016_n.jpg"))
			.when().post("rest/api/3/issue/{key}/attachments")
			.then().log().body().assertThat().statusCode(200).extract().response().asString();
	}

}
