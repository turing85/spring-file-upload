Feature: Upload file

  Scenario: I can upload a file
    When I upload a file with a size of 1024 bytes
    Then I get the expected response

  Scenario: I get a bad request
    When I upload no file
    Then I get a bad request response