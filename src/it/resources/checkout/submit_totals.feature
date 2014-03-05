Feature: Post answers for a batch

  Team needs to be registered
  Results don't need to be in any particular order
  If all answers are correct you get a 200
  If any answers are incorrect (or missing) you get 400 and a list of incorrect responses
  If we've finished all rounds, then error code

  @single
  Scenario: Correct response success
    Given I register a team
    And we have a simple batch
    And we have a simple price list
    When I submit the simple totals
    Then I receive a CREATED response

  Scenario: Correct response is successful and moves to next round
    Given my team is in round 0
    When I submit the correct totals
    Then I receive a CREATED response
    And my team is now in round 1

  Scenario: Incorrect response is rejected
    Given my team is in round 0
    When I submit incorrect totals
    Then I receive an ERROR response
    And my team is still in round 0
    And I know the basket ID of the incorrect total

  Scenario: Empty response is rejected
    Given my team is in round 0
    When I submit empty totals
    Then I receive an ERROR response
    And my team is still in round 0
    And I know the basket ID of the missing basket

  Scenario: Unexpected response is rejected
    Given my team is in round 0
    When I submit totals for an unexpected basket
    Then I receive an ERROR response
    And my team is still in round 0
    And I know the basket ID of the unexpected basket

