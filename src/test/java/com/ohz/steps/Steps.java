package com.ohz.steps;

import com.ohz.util.ApiUtil;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;

public class Steps {

    private final static String GET_WORDS_ENDPOINT = "/getWords";

    ApiUtil apiUtil;

    public Steps(){
        apiUtil = new ApiUtil();
    }

    Scenario scenario;

    @BeforeStep
    public void beforeStep(Scenario scenario){
        this.scenario = scenario;
    }
    @When("getWords API is invoked with scrambled characters {string}")
    public void invokeGetWordsApiWithChars(String scrambledCharacters){
        apiUtil.getWordsAPICall(scenario, scrambledCharacters);
    }

    @Then("response Status Code should be {int}")
    public void responseStatusCodeShouldBe(int expectedStatusCode) {
        apiUtil.assertStatusCode(scenario, expectedStatusCode);
    }

    @Then("response body should contain {string}")
    public void responseBodyShouldContain(String expectedString){
        apiUtil.assertResponseBodyContainsString(scenario, expectedString);
    }

    @Then("response body should have jsonPath array {string} containing value {string}")
    public void jsonPathShouldContainValue(String jsonPath, String expectedValue){
        apiUtil.assertJsonPathListContainsValue(scenario, jsonPath, expectedValue);
    }

    @And("all words at jsonPath {string} should contain {int} characters")
    public void allWordsAtJsonPathShouldContainCharacters(String jsonPath, int characterCount) {
        List<String> jsonPathValueList = apiUtil.getResponse().jsonPath().getList(jsonPath);
        boolean isFourCharacters= false;

        for(String word : jsonPathValueList){
            isFourCharacters = word.length() == characterCount;
            if(!isFourCharacters){
                scenario.log("Word with incorrect character count in object: %s".formatted(word));
                break;
            }
        }

        Assert.assertTrue(isFourCharacters);
    }

    @And("response body should not contain jsonPath {string}")
    public void responseBodyShouldNotContainJsonPath(String jsonPath) {
        Object jsonPathValue = apiUtil.getResponse().jsonPath().get(jsonPath);

        Assert.assertNull(jsonPathValue);
    }
}
