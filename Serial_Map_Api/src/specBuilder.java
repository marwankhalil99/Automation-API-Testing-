
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;


import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import POJO.AddApiBody;
import POJO.Loc;
public class specBuilder {
		@Test
	public void TC_API_05()
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
			addBody.setWebsite("http://aaaa0aaaaaaaaaaaaa");
			
			RequestSpecification req =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
					.addQueryParam("key", "qaclick123")
					.setContentType(ContentType.JSON).build();
					 
					 
			ResponseSpecification resspec =new ResponseSpecBuilder().expectStatusCode(200).
					expectContentType(ContentType.JSON).build();
			RequestSpecification res=given().spec(req)
			.body(addBody);

			res.when().post("/maps/api/place/add/json").
			then().log().body().spec(resspec);

	}
}


