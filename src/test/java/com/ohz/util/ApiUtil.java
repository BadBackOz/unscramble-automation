package com.ohz.util;

import com.ohz.common.Configuration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

public class ApiUtil {

    private final String baseURL = System.getProperty("env.url");

    private Response response;

    public void getWordsAPICall(String scrambledCharacters) {
        try {
            String uri = baseURL.concat("/getWords/%s".formatted(scrambledCharacters));
            Configuration.getScenario().log("API Invoked: %s".formatted(uri));
            response = RestAssured.when().get(uri).then().extract().response();
            Configuration.getScenario().log("Response: %s".formatted(getResponseBody()));
        } catch (Exception e) {
            Configuration.getScenario().log("Exception Thrown: %s".formatted(e));
            Assert.fail();
        }
    }

    public String getResponseBody() {
        return response.getBody().asString();
    }

    public Response getResponse() {
        return response;
    }

    public void assertStatusCode(int expectedStatusCode) {
        Configuration.getScenario().log("Actual Status Code: %s".formatted(response.getStatusCode()));

        Assert.assertEquals(response.statusCode(), expectedStatusCode);
    }

    public void assertResponseBodyContainsString(String expectedValue) {
        String message = "Expected Response Body to contain: %s".formatted(expectedValue) + System.lineSeparator()
                + "Actual Response Body: %s".formatted(getResponseBody());

        Configuration.getScenario().log(message);
        Assert.assertTrue(getResponseBody().contains(expectedValue));
    }

    public void assertJsonPathListContainsValue(String jsonPath, String expectedValue) {
        List<Object> jsonPathValue = response.jsonPath().getList(jsonPath);
        Configuration.getScenario().log("JSON Object List Values: %s".formatted(jsonPathValue));
        Assert.assertTrue(jsonPathValue.contains(expectedValue));
    }
}
