package ExtentReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class TestRestAssuredAPIExtent {

    private ExtentTest test;
    private ExtentReports extent;
    private RequestSpecification request;

    private String token = "2d3f043b90010b2ab13d8cac974888fa3296299b4762c87733ca4a07df5ed456";
    private String bsaseURI = "https://gorest.co.in/public/v2";

    private int userID = 5178397;

    private int newUserID;

    @BeforeMethod
    public void setUp() {
        extent = new ExtentReports();
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("extent-report.html");
        extent.attachReporter(htmlReporter);

        RestAssured.baseURI = bsaseURI;
        request = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

    }

    @Test(enabled = true)
    public void testGetRequest() {

        test = extent.createTest("testGetRequest", "simple Get Request for goRest API");

        Response response = RestAssured.given()
                .when()
                .get("/users");

        int statusCode = response.getStatusCode();
        test.log(Status.INFO, "response Status: " + statusCode);
        System.out.println("status: " + statusCode);

        Assert.assertEquals(statusCode, 200);

        String responseBody = response.getBody().asString();
        System.out.println("response: " + responseBody);

        JSONArray jsonArray = new JSONArray(responseBody);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject user = jsonArray.getJSONObject(i);
            Assert.assertTrue(user.has("id"));
            Assert.assertTrue(user.has("name"));
            Assert.assertTrue(user.has("email"));
            Assert.assertTrue(user.has("gender"));
            Assert.assertTrue(user.has("status"));

            test.log(Status.PASS, "Get request Test Passed");
        }
    }

    @Test(enabled = true)
    public void testPostRequest() {
        test = extent.createTest("testPostRequest", "simple Post Request for goRest API");

        RequestSpecification request = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

        String requestBody = "{"
                + "\"name\": \"PTest26\","
                + "\"email\": \"Ptest26@example.com\","
                + "\"gender\": \"female\","
                + "\"status\": \"active\""
                + "}";

        Response response = request.body(requestBody).post("/users");

        int statusCode = response.getStatusCode();
        test.log(Status.INFO, "response Status: " + statusCode);
        System.out.println("status: " + statusCode);
        Assert.assertEquals(statusCode, 201);

        String responseBody = response.getBody().asString();

        System.out.println("Response Body: " + responseBody);
        int newUserId = response.jsonPath().getInt("id");
        System.out.println("New User with Id: " + newUserId);
        test.log(Status.PASS, "Post request Test Passed");
        newUserID = newUserId;
        //testPutRequest();
//        try{
//
//        } catch(NullPointerException e){
//            e.printStackTrace();
//        }
    }

    @Test(dependsOnMethods = "testPostRequest", enabled = true)
    public void testPutRequest() {
        System.out.println("newUserID: " + newUserID);
        test = extent.createTest("testPutRequest", "simple Put Request for goRest API");

        RequestSpecification request = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

        String requestBody = "{"
                + "\"name\": \"Some diff Name\","
                + "\"email\": \"updateduser@example.com\","
                + "\"gender\": \"male\","
                + "\"status\": \"active\""
                + "}";

        Response response = request.body(requestBody).put("/users/" + newUserID);

        int statusCode = response.getStatusCode();
        test.log(Status.INFO, "response Status: " + statusCode);
        System.out.println("status: " + statusCode);
        Assert.assertEquals(statusCode, 200);

        String responseBody = response.getBody().asString();
        System.out.println("response: " + responseBody);

        test.log(Status.PASS, "Put request Test Passed");
    }

    @Test(dependsOnMethods = "testPutRequest", enabled = true)
    public void testDeleteRequest() {
        test = extent.createTest("testDeleteRequest", "simple Delete Request for goRest API");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/users/" + newUserID);

        int statusCode = response.getStatusCode();
        test.log(Status.INFO, "response Status: " + statusCode);
        System.out.println("Response Status: " + statusCode);

        test.log(Status.PASS, "Delete request Test Passed");

    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test Failed " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Passed " + result.getName());
        } else {
            test.log(Status.SKIP, "Test Skipped " + result.getName());
        }
    }

    @AfterClass
    public void flush() {
        extent.flush();
    }
}