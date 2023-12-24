package com.ohz.runner;

import com.ohz.util.CustomHTMLReport;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;

@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"com/ohz/steps"},
        tags = "@Smoke",
        monochrome = true,
        plugin = {"pretty", "html:target/cucumber-reports/TestResult.html",
                    "json:target/cucumber-reports/TestResult.json"}
)
public class CucumberTestNGRunner extends AbstractTestNGCucumberTests {

    @BeforeSuite
    public void beforeSuite(){
        //setProperties();
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("afterMethod");
    }

    @AfterSuite
    public void afterSuite() throws IOException {
        CustomHTMLReport.generateHTMLReport();
    }

    @Test(groups = "cucumber", description = "Cucumber Scenarios", dataProvider = "scenarios")
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper){
        super.runScenario(pickleWrapper, featureWrapper);
    }

    //TODO - Enable running of tests by executing this runner class directly instead of via Gradle command
    /*@Override
    @DataProvider(name="scenarios", parallel = true)
    public Object[][] scenarios(){
        return super.scenarios();
    }

    private void setProperties() {
        String environment = System.getProperty("env");
        if(null == environment){
            Properties properties = new Properties();
            try(FileInputStream fis = new FileInputStream("src/main/resources/uat.properties")){
                properties.load(fis);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.setProperties(properties);
        }
    }*/

}
