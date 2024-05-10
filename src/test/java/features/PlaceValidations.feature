Feature: Validating Place APIs

  @AddPlace
  Scenario Outline: Verify if place is being added using AddPlaceAPI <Name>
    Given Add Place Payload with "<Name>"
    When user calls "AddPlaceAPI" with "POST" HTTP Request
    Then the API Call is with status code 200
    And "status" in response body is "OK"
    Then verify if place added "<Name>" in call "GetPlaceAPI"

    Examples:
      | Name           |
      | Juan Dela Cruz |
      | Lebron James   |
      | Kobe Bryant    |

  @DeletePlace
  Scenario Outline: Verify if DeletePlaceAPI is working
    Given Delete Place Payload
    When user calls "DeletePlaceAPI" with "POST" HTTP Request
    Then the API Call is with status code 200

    Examples:
      | Name           |
      | Juan Dela Cruz |





