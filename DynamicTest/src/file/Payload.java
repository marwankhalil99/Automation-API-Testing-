package file;

public class Payload {
	public static String AddBookJson(String isbn,String aisle  )
	{
		return "{\r\n"
				+ "\r\n"
				+ "\"name\":\"Learn Appium Automation with Java\",\r\n"
				+ "\"isbn\":\""+isbn+"\",\r\n"
				+ "\"aisle\":\""+aisle+"\",\r\n"
				+ "\"author\":\"John foe\"\r\n"
				+ "}\r\n"
				+ "";
	}
	public static String deleteBookJson(String isbn,String aisle  )
	{
		return "{\r\n"
				+ " \r\n"
				+ "\"ID\" : \""+isbn+aisle+"\"\r\n"
				+ " \r\n"
				+ "} \r\n"
				+ "";
	}
	
}
