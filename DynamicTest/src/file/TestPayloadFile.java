package file;

import java.io.IOException;

import java.nio.file.Files;

import java.nio.file.Paths;



import org.testng.annotations.Test;


import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;

import io.restassured.response.Response;
public class TestPayloadFile {
	
	@Test
	public void add_Book() throws IOException  
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String addBookResponse = given().header("Content-Type","application/json")
		.body(new String (Files.readAllBytes(Paths.get("E:\\ITI_Testing\\External_Resources\\Rahul Shetty_API\\file.json"))))
		.when().post("Library/Addbook.php")
		.then().log().body().assertThat().statusCode(200).extract().response().asString(); 
		
	}
}
