Feature: Post answers for a batch

  Team needs to be registered
  Results don't need to be in any particular order
  If all answers are correct you get a 200
  If any answers are incorrect (or missing) you get 400 and a list of incorrect responses
  If we've finished all rounds, then error code

  Scenario: Correct response advances round
    Given a just registered team with simple data
    When I submit the simple totals
    And my team is now in round 1

  Scenario: Correct response gets CREATED response
    Given a just registered team with simple data
    When I submit the simple totals
    Then I receive a CREATED response

  Scenario: Incorrect response is rejected
    Given a just registered team with simple data
    When I submit incorrect totals
    Then I receive an ERROR response
    And my team is still in round 0
    And I know the basket ID of the incorrect total

  Scenario: Empty response is rejected
    Given a just registered team with simple data
    When I submit empty totals
    Then I receive an ERROR response
    And my team is still in round 0
    And I know the basket ID of the missing basket

  Scenario: Unexpected response is rejected
    Given a just registered team with simple data
    When I submit totals for an unexpected basket
    Then I receive an ERROR response
    And my team is still in round 0
    And I know the basket ID of the unexpected basket

  Scenario: BOGOF offer is respected
    Given a just registered team with BOGOF data
    When I submit the BOGOF totals
    Then I receive a CREATED response

  Scenario: Ten Percent off offer is respected
    Given a just registered team with Ten Percent offer data
    When I submit the Ten Percent totals
    Then I receive a CREATED response
