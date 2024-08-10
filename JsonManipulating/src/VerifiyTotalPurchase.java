import org.testng.Assert;
import org.testng.annotations.Test;

import file.AddPlacePayload;
import io.restassured.path.json.JsonPath;

public class VerifiyTotalPurchase {

	@Test
	public void ValidateSumPurches()
	{
		JsonPath jsMock = new JsonPath(AddPlacePayload.MockUpAddPlaceResponse());
		int size = jsMock.getInt("courses.size()");
		int sum = 0;
		for (int i = 0 ; i < size ; i++)
		{
			sum += ((jsMock.getInt("courses["+i+"].price"))
					*(jsMock.getInt("courses["+i+"].copies")));
		}
		int TotalPurches = jsMock.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(TotalPurches,sum);
	}

}
