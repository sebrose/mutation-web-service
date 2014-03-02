Feature: Scoring
  Teams start with nothing
  They get points for every successful PUT - registration & correct batch totals
  The number of points available for a round's batch total decreases each time a team is successful
  They lose points for every unsuccessful PUT

  Scenario: Unregistered team has no score
    Given my team is unregistered
    When I retrieve my score
    Then I receive an ERROR response

  Scenario: Registering gives me a score
    Given I register a team
    When I retrieve my score
    Then my score is 10 points

  @single
  Scenario: Successful batch submission returns CREATED
    Given my team is in round 0
    And I submit the correct totals
    Then I receive a CREATED response
    

  Scenario: Successful batch submission gets points
    Given my team is in round 0
    And I submit the correct totals
    When I retrieve my score
    Then my score is 50 points

  Scenario: Unsuccessful batch submission loses a point
    Given my team is in round 0
    And I submit incorrect totals
    When I retrieve my score
    Then my score is -1 points

  Scenario: Points awarded for successful batch submission decreases
    Given my team is in round 0
    And another team has already submitted correct round 0 totals
    And I submit the correct totals
    When I retrieve my score
    Then my score is 45 points

  Scenario: Round scores are preserved on restart
    Given my team is in round 0
    And another team has already submitted correct round 0 totals
    When the server is restarted
    And I submit the correct totals
    When I retrieve my score
    Then my score is 45 points


