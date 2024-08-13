import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import GetCoursePojoRes.Api;
import GetCoursePojoRes.PojoClassResponse;
import GetCoursePojoRes.WebAutomation;
import GetTokenRes.TokenRes;


public class test {
	private String accessToken;
	
	@BeforeClass
	public void TestToken()
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		TokenRes tokenResponse = given().formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type","client_credentials")
		.formParam("scope","create")
		.when().post("oauthapi/oauth2/resourceOwner/token")
		.then().log().body().assertThat().statusCode(200)
		.extract().response().as(TokenRes.class);
		accessToken = tokenResponse.getAccess_token();
		System.out.println("Token :" + accessToken);
	}
	@Test
	public void TestGetCourse()
	{
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		PojoClassResponse GetcourseRes = given().queryParam("access_token",accessToken)
		.when().get("oauthapi/getCourseDetails")
		.then().log().body().assertThat().statusCode(401)
		.extract().response().as(PojoClassResponse.class);
		Assert.assertEquals(GetcourseRes.getInstructor(), "RahulShetty");
		Assert.assertEquals(GetcourseRes.getUrl(), "rahulshettycademy.com");
		Assert.assertEquals(GetcourseRes.getServices(), "projectSupport");
		Assert.assertEquals(GetcourseRes.getExpertise(), "Automation");
		Assert.assertEquals(GetcourseRes.getCourses().getApi().get(0).getCourseTitle() ,"Rest Assured Automation using Java");
		
		List<Api> apiCourses = GetcourseRes.getCourses().getApi();
		
		for(int i = 0; i<apiCourses.size(); i++)
		{
			if(apiCourses.get(i).getCourseTitle().equals("SoapUI Webservices testing"))
			{
				System.out.println("Price = "+ apiCourses.get(i).getPrice());
			}
		}
		List<WebAutomation> webCourses = GetcourseRes.getCourses().getWebAutomation();
		for(int i = 0; i<webCourses.size(); i++)
		{
			System.out.println("Name = "+ webCourses.get(i).getCourseTitle());
			System.out.println("Price = "+ webCourses.get(i).getPrice());
		}

	}
}
