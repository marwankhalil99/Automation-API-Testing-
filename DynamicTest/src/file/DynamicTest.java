package file;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicTest {
	@Test(dataProvider = "AddBooksData")
	public void addBook(String isbn , String aisle)
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String addBookResponse = given().header("Content-Type","application/json")
				.body(Payload.AddBookJson(isbn,aisle))
		.when().post("Library/Addbook.php")
		.then().log().body().assertThat().statusCode(200).extract().response().asString(); 
		
	}

	//delete book
	@Test(dataProvider = "AddBooksData")
	public void deleteBook(String isbn , String aisle)
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String deleteBookResponse = given().header("Content-Type","application/json").body(Payload.deleteBookJson(isbn, aisle))
		.when().post("Library/DeleteBook.php").
		then().log().body().assertThat().statusCode(200).extract().response().asString();
		JsonPath JsonDeleteBook = new JsonPath (deleteBookResponse);
		String ID = JsonDeleteBook.get("ID"); //get as string
		
	}
	@DataProvider(name="AddBooksData")
	public Object[][] getData()
	{
		return new Object[][] {{"maro0","123"},{"maro1","1234"},{"maro2","1235"}};
		
	}
}
