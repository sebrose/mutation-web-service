Feature: Get Price Lists

  Scenario: Unregistered team can't get price lists
    Given my team is unregistered
    When I ask for a price list
    Then I receive an ERROR response

  Scenario: In round 0 you get a price list with a single item
    Given my team is in round 0
    When I ask for a price list
    Then I receive an OK response
    And the price list contains 1 item
    And the price is 25c


  Scenario: In round 1 you get a price list with two items
    Given my team is in round 1
    When I ask for a price list
    Then I receive an OK response
    And the price list contains 2 items
