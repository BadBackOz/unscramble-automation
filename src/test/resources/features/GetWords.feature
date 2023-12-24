@Regression @GetWords
Feature: Scenarios for GetWords API

  @Smoke
  Scenario Outline: GetWords API invoked with invalid character '<scrambledCharacters>'
    When getWords API is invoked with scrambled characters '<scrambledCharacters>'
    Then response Status Code should be 400
    And response body should contain '{"message":"Invalid Request: Only Alphabetic characters and \'*\' allowed."}'
    Examples:
      | scrambledCharacters |
      | 1                   |
      | !                   |
      | @                   |
      | $                   |
      | %                   |
      | ^                   |
      | &                   |
      | (                   |
      | )                   |
      | _                   |
      | +                   |
      | =                   |
      | ;                   |

  @Smoke @Debug
  Scenario: GetWords API invoked with valid characters
    When getWords API is invoked with scrambled characters 'e**t'
    Then response Status Code should be 200
    And response body should have jsonPath array 'fourLetterWords' containing value 'Test'
    And all words at jsonPath 'oneLetterWords' should contain 1 characters
    And all words at jsonPath 'twoLetterWords' should contain 2 characters
    And all words at jsonPath 'threeLetterWords' should contain 3 characters
    And all words at jsonPath 'fourLetterWords' should contain 4 characters
    And response body should not contain jsonPath 'fiveLetterWords'