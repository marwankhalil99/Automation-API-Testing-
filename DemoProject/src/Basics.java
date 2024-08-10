import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import file.payload;
public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Validate if add place API is working as expected
		
		//given -> All input Details (Q-Parameter , Header , body)
		//when -> Submit the API (Resource .. Method)
		//then -> Validate the response
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json\r\n")
		.body(payload.AddPlace()).when().post("maps/api/place/add/json?key=qaclick123")
		.then()/*.log().all()*/.assertThat().statusCode(200)
		.body("scope", equalTo("APP")).header("server","Apache/2.4.52 (Ubuntu)")
		.extract().response().asString();
		
		System.out.println(response);
		JsonPath js = new JsonPath(response); // for parsing Json
		String place_id = js.getString("place_id"); 	//if it has parent "location"."lan"
		String newAddress = "Damitta";
		//Update Place using Parsing
		
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json\r\n")
		.body("{\r\n"
				+ "\"place_id\":\""+place_id+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));
		
		//Get place
	
		
		String getResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id",place_id)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).body("address",equalTo(newAddress))
		.extract().response().asString();
		
		JsonPath getJs= new JsonPath(getResponse);
		
		String getAddress = getJs.getString("address");
		
		Assert.assertEquals(getAddress,newAddress );
		
	}
}
