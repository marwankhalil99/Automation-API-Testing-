package file;

public class help {
	public static String Auth()
	{
		return "Basic Token";
	}
	public static String addIssueBody(String key , String IssueType , String IssueSummary)
	{
		return "{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \""+key+"\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \""+IssueSummary+"\",\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \""+IssueType+"\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}";
	}
}
