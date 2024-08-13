import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import POJO.AddApiBody;
import POJO.Loc;
public class TestApi {
	@Test
	public void TC_API_00()
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		AddApiBody addBody = new AddApiBody();
		Loc location = new Loc();
		location.setLat(40.256464);
		location.setLng(88.256464);
		List<String> a = new ArrayList<String>() ;
		a.add("A7a");
		a.add("555");
		addBody.setAccuracy(50);
		addBody.setAddress("Domiat Elgedida");
		addBody.setLanguage("Arabic");
		addBody.setLocation(location);
		addBody.setName("Marwan Khalil");
		addBody.setPhone_number("666116516");
		addBody.setTypes(a);
		addBody.setWebsite("http://aaaaaaaaaaaaaaaa");
		
		String addAPI_response = given().queryParam("key","qaclick123").body(addBody)
		.when().post("/maps/api/place/add/json")
		.then().log().body().assertThat().statusCode(200).body("status",equalTo("OK"))
		.extract().response().asString();
		
	}
	
	@Test
	public void TC_API_02()
		{
			
			
			AddApiBody addBody = new AddApiBody();
			Loc location = new Loc();
			location.setLat(400.256464);
			location.setLng(808.256464);
			List<String> a = new ArrayList<String>();
			a.add("A70a");
			a.add("5550");
			addBody.setAccuracy(500);
			addBody.setAddress("Domiat Elgedida0");
			addBody.setLanguage("Arabic0");
			addBody.setLocation(location);
			addBody.setName("Marwan Khalil");
			addBody.setPhone_number("6661016516");
			addBody.setTypes(a);
			addBody.setWebsite("http://aaaa0aaaaaaaaaaaa");
			
			
			RequestSpecification request = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
					.addQueryParam("key","qaclick123").build();
					
			ResponseSpecification response = new ResponseSpecBuilder().expectStatusCode(200).build();
					
			String responseStr = given().spec(request).body(addBody)
			.when().post("/maps/api/place/add/json")
			.then().log().body().spec(response).extract().response().asString();
			System.out.println(responseStr);
	}
	
}
