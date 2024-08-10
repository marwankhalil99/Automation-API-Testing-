import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import file.AddPlacePayload;

public class ApiMan {

	public static void main(String[] args) {
		
		/*RestAssured.baseURI = "https://rahulshettyacademy.com";
		String AddResponse = given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(AddPlacePayload.AddPlaceBody()).when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).header("server","Apache/2.4.52 (Ubuntu)")
		.body("scope",equalTo("APP")).body("status", equalTo("OK"))
		.extract().response().asString();
		
		JsonPath JsonAddRes = new JsonPath(AddResponse);
		String Added_place_id = JsonAddRes.getString("place_id");
		String AddStatus = JsonAddRes.getString("status");
		Assert.assertEquals(AddStatus, "OK");
		System.out.println("Test Case 1 Done");*/
		JsonPath MockAddPlace = new JsonPath(AddPlacePayload.MockUpAddPlaceResponse());
		//Print Number of Courses
		int CrsSize = MockAddPlace.getInt("courses.size()");
		System.out.println("Number Of Courses : "+CrsSize);
		//print Purches amount
		int PurchaseAmount = MockAddPlace.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount : "+PurchaseAmount);
		//Print Title of the first Course
		String titleFirstCrs = MockAddPlace.getString("courses[0].title");
		System.out.println("Course[0] : "+titleFirstCrs);	
		//Print All course title and their prices
		System.out.println("***************");
		int CrsPrice;
		String CrsName;
		for (int i = 0 ; i < CrsSize ; i++)
		{
			CrsName = MockAddPlace.getString("courses["+i+"].title");
			CrsPrice = MockAddPlace.getInt("courses["+i+"].price");
			System.out.println("Course["+i+"] : "+CrsName);
			//System.out.println("Price : "+CrsPrice);
			System.out.println("Price : "+MockAddPlace.getInt("courses["+i+"].price"));
		}
		System.out.println("***************");
		//print Number of Copy sold by PRA
		for (int i = 0 ; i < CrsSize ; i++)
		{
			CrsName = MockAddPlace.getString("courses["+i+"].title");
			if(CrsName.equals("RPA"))
			{
				int SoldCopy = MockAddPlace.getInt("courses["+i+"].copies");
				System.out.println("Number of Sold books by RPA : "+SoldCopy);
				break;
			}
		}
		System.out.println("***************");
		int sum = 0;
		for (int i = 0 ; i < CrsSize ; i++)
		{
			sum += ((MockAddPlace.getInt("courses["+i+"].price"))
					*(MockAddPlace.getInt("courses["+i+"].copies")));
		}
		if(sum == PurchaseAmount)
		{
			System.out.println("Verified ");
		}
		else
		{
			System.out.println("Error ");
		}
		
	}

}
