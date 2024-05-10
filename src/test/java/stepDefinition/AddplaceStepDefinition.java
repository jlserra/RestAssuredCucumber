package stepDefinition;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utilities.APIResources;
import utilities.TestData;
import utilities.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class AddplaceStepDefinition extends Utils {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    static String placeId;
    static String name = "";
    Response response;

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("Add Place Payload with {string}")
    public void add_place_payload(String name) throws IOException {
        TestData testData = new TestData();
        requestSpecification = given().spec(requestSpecification()).body(testData.requestAddPlace(name));
    }

    @When("user calls {string} with {string} HTTP Request")
    public void user_calls_with_post_http_request(String string, String method) {

        APIResources resource = APIResources.valueOf(string);
        String endpoint = resource.getResource();

        if(method.equalsIgnoreCase("POST"))
        response = requestSpecification
                .when()
                .post(endpoint)
                .then()
                .spec(responseSpecification200())
                .extract().response();
        else if(method.equalsIgnoreCase("GET"))
        response = requestSpecification
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpecification200())
                .extract().response();

    }

    @Then("the API Call is with status code {int}")
    public void the_api_call_is_with_status_code(Integer int1) {

        assertEquals(String.valueOf(response.getStatusCode()), String.valueOf(int1));
    }

    @Then("{string} in response body is {string}")
    public void status_in_response_body_is_ok(String key, String value) {
        JsonPath js = new JsonPath(response.getBody().asString());
        assertEquals(js.get(key), value);
    }

    @Then("verify if place added {string} in call {string}")
    public void verify_if_place_added_in_call(String expectedName, String string) throws IOException {

        APIResources resource = APIResources.valueOf(string);
        String endpoint = resource.getResource();

        JsonPath js = new JsonPath(response.getBody().asString());
        placeId = js.get("place_id");

        requestSpecification = given().spec(requestSpecification())
                .queryParam("key", "qaclick123")
                .queryParam("place_id", placeId);

        response = requestSpecification.get(endpoint)
                .then()
                .spec(responseSpecification200())
                .extract().response();

        js = new JsonPath(response.asString());
        name = js.get("name");
        System.out.println(name + " - " + expectedName);
        assertEquals(name,expectedName);

    }

    @Given("Delete Place Payload")
    public void delete_place_payload() throws IOException {
        requestSpecification = given().spec(requestSpecification()).body(TestData.placeId(placeId));
    }
}
