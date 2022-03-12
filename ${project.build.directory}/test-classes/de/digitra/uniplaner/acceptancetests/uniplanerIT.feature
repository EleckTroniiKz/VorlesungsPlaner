Feature: Uniplaner functionalities
  This feature contains a list of functionalities related to the uniplaner application

  Scenario: get all lecture dates
    Given a lecture date in the system
    When the user requests the list of lecture dates
    Then the result list contains one lecture date

