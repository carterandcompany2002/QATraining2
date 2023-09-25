package restAssured;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.json.JSONArray;
import org.json.JSONObject;

public class TestRestAssuredAPI {

    private String token ="fb30ac2bfec4d2bb37f8de97fdf243de436bd60c0f7560b8924f39d745caa145";
    private String baseURL = "https://gorest.co.in/public/v2";
    private int userID = 5178532;

    @BeforeTest
    public void setUp(){
        RestAssured.baseURI = baseURL;
    }
    //Smoke test.  For deeper dive compare response to Excel spreadsheet data
    @Test
    public void testGetRequest(){
        Response response = RestAssured.given()
                .when()
                .get("/users");

        int statusCode = response.getStatusCode();
        System.out.println("status: "+ statusCode);

        Assert.assertEquals(statusCode,200);

        String responseBody = response.getBody().asString();
        System.out.println("respone: " + responseBody);

        JSONArray jsonArray = new JSONArray(responseBody);
        for (int i = 0; i < jsonArray.length();i++){
            JSONObject user = jsonArray.getJSONObject(i);
            Assert.assertTrue(user.has("id"));
            Assert.assertTrue(user.has("name"));
            Assert.assertTrue(user.has("email"));
            Assert.assertTrue(user.has("gender"));
            Assert.assertTrue(user.has("status"));
        }
    }
    @Test (enabled = true)
    public void testPostRequest(){

        RequestSpecification request = RestAssured.given()
                .header("Authorization","Bearer " + token)
                .header("Content-Type","application/json");

        String requestBody = "{"
                + "\"name\": \"Brian Patrick Kim\","
                + "\"email\": \"BPK@example.com\","
                + "\"gender\": \"male\","
                + "\"status\": \"active\""
                + "}";

        Response response = request.body(requestBody).post("/users");

        int statusCode = response.getStatusCode();
        System.out.println("status: " + statusCode);
        Assert.assertEquals(statusCode,201);

        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);

        int newUserId = response.jsonPath().getInt("data.id");
        System.out.println("New User with Id: " + newUserId);

    }

    @Test(enabled = false)
    public void testPutRequest(){
        RequestSpecification request = RestAssured.given()
                .header("Authorization","Bearer " + token)
                .header("Content-Type","application/json");

        String requestBody = "{"
                + "\"name\": \"Kim Too\","
                + "\"email\": \"kim2@example.com\","
                + "\"gender\": \"female\","
                + "\"status\": \"active\""
                +"}";

        Response response = request.body(requestBody).put("/users/"+ userID);

        int statusCode = response.getStatusCode();
        System.out.println("status: " + statusCode);
        Assert.assertEquals(statusCode, 200);

        String responseBody = response.getBody().toString();
        System.out.println("response: " + responseBody);
    }

    @Test (enabled = false)
    public void testDeleteRequest(){

        Response response = RestAssured.given()
                .header("Authorization","Bearer " + token)
                .when()
                .delete("/users/" + userID);

        int statusCode = response.getStatusCode();
        System.out.println("Response Status: " + statusCode);

    }
}