Feature: Get shopping baskets
  Get a shopping basket for the appropriate round

  Scenario: Unregistered teams can't get batches
    Given my team is unregistered
    When I ask for a batch
    Then my batch request is rejected

  Scenario: In round 0 you get a single basket with a single item
    Given my team is in round 0
    When I ask for a batch
    Then I successfully receive a batch
    And the batch contains 1 basket
    And the basket contains 1 item


  Scenario: In round 1 you get a multiple baskets
    Given my team is in round 1
    When I ask for a batch
    Then I successfully receive a batch
    And the batch contains multiple baskets
