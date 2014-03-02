Feature: Get requirements
  Get requirements for the appropriate round

  Scenario: Unregistered teams can't get requirements
    Given my team is unregistered
    When I ask for requirements
    Then I receive an ERROR response

  Scenario: Registered teams can get requirements
    Given my team is in round 0
    When I ask for requirements
    Then I receive an OK response
    And some requirements