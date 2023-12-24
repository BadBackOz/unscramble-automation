Tech Stack
 1. Java
 2. Rest Assured
 3. Cucumber
 4. Gherkin
 5. TestNG
 6. Gradle

Steps to Execute Tests
 1. Create Gradle Configuration.
 2. Set Run command to 'clean test -Denv="LOCAL" -Dcucumber.filter.tags="@Regression"'. (Scenarios executed can be controlled by updating 'cucumber.filter.tags' property with tag to execute, which are annotated above test scenarios.)
 3. Run configuration.

Environment specific properties files
 1. Located within resources folder.
 2. Property file loaded is dependent on 'env' property in Gradle configuration.

Test Results Reports
 1. Cucumber HTML and JSON reports saved to 'target\cucumber-reports' after each execution.
 2. Custom HTML test report saved to 'htmlreports' folder after each execution. (Generated from Cucumber JSON file)
