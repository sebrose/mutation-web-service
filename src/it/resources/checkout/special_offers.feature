Feature: Special offers

  Scenario: Unregistered team can't get special offers
    Given my team is unregistered
    When I ask for special offers
    Then I receive an ERROR response

  Scenario: In round 0 you get no special offers
    Given my team is in round 0
    When I ask for special offers
    Then I receive an OK response
    And there are 0 special offers

  Scenario: In round 4 you get a single special offer
    Given my team is in round 4
    When I ask for special offers
    Then I receive an OK response
    And there is 1 special offer
