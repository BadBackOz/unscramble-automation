package com.ohz.util;

import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

public class ApiUtil {

    private final String baseURL = System.getProperty("env.url");

    private Response response;

    public void getWordsAPICall(Scenario scenario, String scrambledCharacters) {
        try {
            String uri = baseURL.concat("/getWords/%s".formatted(scrambledCharacters));
            scenario.log("API Invoked: %s".formatted(uri));
            response = RestAssured.when().get(uri).then().extract().response();
            scenario.log("Response: %s".formatted(getResponseBody()));
        } catch (Exception e) {
            scenario.log("Exception Thrown: %s".formatted(e));
            Assert.fail();
        }
    }

    public String getResponseBody() {
        return response.getBody().asString();
    }

    public Response getResponse() {
        return response;
    }

    public void assertStatusCode(Scenario scenario, int expectedStatusCode) {
        scenario.log("Actual Status Code: %s".formatted(response.getStatusCode()));

        Assert.assertEquals(response.statusCode(), expectedStatusCode);
    }

    public void assertResponseBodyContainsString(Scenario scenario, String expectedValue) {
        String message = "Expected Response Body to contain: %s".formatted(expectedValue) + System.lineSeparator()
                + "Actual Response Body: %s".formatted(getResponseBody());

        scenario.log(message);
        Assert.assertTrue(getResponseBody().contains(expectedValue));
    }

    public void assertJsonPathListContainsValue(Scenario scenario, String jsonPath, String expectedValue) {
        List<Object> jsonPathValue = response.jsonPath().getList(jsonPath);
        scenario.log("JSON Object List Values: %s".formatted(jsonPathValue));
        Assert.assertTrue(jsonPathValue.contains(expectedValue));
    }
}
