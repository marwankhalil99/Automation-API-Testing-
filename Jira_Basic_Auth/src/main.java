import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

import file.help;
public class main {
	@Test
	public static void main(String[] args) {
		// TODO Auto-generated method stub
RestAssured.baseURI = "https://marwanshamso2022.atlassian.net";
	String ResponseAddIssue = given().header("Content-Type","application/json").header("Authorization",help.Auth())
	.body(help.addIssueBody("MK", "Bug", "Create Issue Using Rest Assured"))
	.when().post("rest/api/3/issue")
	.then().log().body().assertThat().statusCode(201).extract().response().asString();
			
	JsonPath jsonAddIssue = new JsonPath(ResponseAddIssue);
	}

}
