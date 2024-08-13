import org.testng.Assert;
import org.testng.annotations.*;

import POJO_Classes.CreateOrder_POJO;
import POJO_Classes.CreateProd_Resp;
import POJO_Classes.LoginInfo_POJO_Class;
import POJO_Classes.Login_Res_POJO_Class;
import POJO_Classes.Order;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class TestSuite {
    // Variables to store token, user ID, product ID, and order ID
    private String token;
    private String userID;
    private String ProductID;
    private String orderID;
    RequestSpecification Auth_Spec;

    // Method to handle login functionality
    @BeforeTest
    public void TC_Login_Func_00() {
        // Create a login info POJO and set user credentials
        LoginInfo_POJO_Class user1 = new LoginInfo_POJO_Class();
        user1.setUserEmail("selenuim@yahoo.com");
        user1.setUserPassword("Hello123@");

        // Build the base request specification
        RequestSpecification BaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/api/ecom")
                .setContentType(ContentType.JSON).build();

        // Send login request and deserialize response into POJO
        Login_Res_POJO_Class logInRes = given().spec(BaseReq).body(user1).when().post("auth/login")
                .then().log().body().assertThat().statusCode(200).extract().response().as(Login_Res_POJO_Class.class);

        // Assert login success and store token and user ID
        Assert.assertEquals(logInRes.getMessage(), "Login Successfully");
        token = logInRes.getToken();
        userID = logInRes.getUserId();
        Assert.assertEquals(logInRes.getMessage(), "Login Successfully");
        System.out.println("Token :" + token);
        System.out.println("ID :" + userID);
    }

    // Method to handle product creation functionality
    @Test
    public void TC_CreateProduct_Func_01() {
        // Build the authenticated request specification
        Auth_Spec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/api/ecom")
                .addHeader("Authorization", token).build();

        // Create product request with parameters and image
        RequestSpecification CP_SpecForm = given().log().body().spec(Auth_Spec)
                .param("productName", "Iphone 122 Pro Max")
                .param("productAddedBy", userID)
                .param("productCategory", "fashion")
                .param("productSubCategory", "shirts")
                .param("productPrice", 10010)
                .param("productDescription", "Made in Egypt")
                .param("productFor", "women")
                .multiPart("productImage", new File("E:\\Downloads\\8bf37546-a051-4175-bc9d-29d875d5f5c9.jpg"));

        // Send product creation request and deserialize response into POJO
        CreateProd_Resp CTRes = CP_SpecForm.when().post("product/add-product")
                .then().log().body().assertThat().statusCode(201).extract().response().as(CreateProd_Resp.class);

        // Store product ID and assert product creation success
        ProductID = CTRes.getProductId();
        Assert.assertEquals(CTRes.getMessage(), "Product Added Successfully");
    }

    // Method to handle order creation functionality
    @Test(dependsOnMethods = {"TC_CreateProduct_Func_01"})
    public void TC_CreateOrder_Func_02() {
        // Create order POJO and set order details
        CreateOrder_POJO CTInstance = new CreateOrder_POJO();
        Order order1 = new Order();
        order1.setCountry("India");
        order1.setProductOrderedId(ProductID);

        // Add order to the list and set it in the order instance
        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        CTInstance.setOrders(orders);

        // Send order creation request and extract response as string
        String CreateOrderResponse = given().spec(Auth_Spec).header("Content-Type", "application/json").body(CTInstance)
                .when().post("https://rahulshettyacademy.com/api/ecom/order/create-order")
                .then().log().body().assertThat().statusCode(201).extract().response().asString();

        // Parse response to get order ID and assert order creation success
        JsonPath jsRes = new JsonPath(CreateOrderResponse);
        orderID = jsRes.getString("productOrderId[0]");
        Assert.assertEquals(jsRes.getString("message"), "Order Placed Successfully");
        System.out.println(orderID);
    }

    // Method to handle order deletion functionality
    @Test(dependsOnMethods = {"TC_CreateOrder_Func_02"})
    public void TC_DeleteOrder_Func_03() {
        // Send order deletion request and extract response as string
        String deleteMessage = given().spec(Auth_Spec).when().delete("product/delete-product/" + orderID)
                .then().log().body().assertThat().statusCode(200).extract().response().asString();

        // Parse response and assert order deletion success
        JsonPath jsDel = new JsonPath(deleteMessage);
        Assert.assertEquals(jsDel.getString("message"), "Product Deleted Successfully");
    }
}
