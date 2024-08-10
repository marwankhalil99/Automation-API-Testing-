import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;


public class TestAuth {
	private String accessToken;
@Test
	public void getToken()
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String OAuthResponse = given().formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials").formParam("scope","trust")
		.when().post("oauthapi/oauth2/resourceOwner/token")
		.then().log().body().assertThat().statusCode(200).body("scope", equalTo("create"))
		.extract().response().asString();
		
		
		JsonPath JsonOAuthRes = new JsonPath(OAuthResponse);
		accessToken = JsonOAuthRes.get("access_token");
		System.out.println("Access Token Is :");
		System.out.println(accessToken);
		System.out.println("*******************************************");
	}
@Test
	public void testToken()
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		 given().queryParam("access_token", accessToken)
		.when().get("oauthapi/getCourseDetails")
		.then().log().body().assertThat().statusCode(401);
	}

}
