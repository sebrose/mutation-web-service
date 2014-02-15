Feature: Team Registration
  Teams need to register a unique name
  Minimum length 1 char, max length 255
  Any characters allowed, stripped at front and back
  [Any use of name from a new IP address is blocked]

  Scenario: Register a simple name
    Given my chosen team name is "Ace-Ventura_5"
    When I register
    Then my choice of name should be confirmed

  Scenario: Register an invalid name
    Given my chosen team name is "Ace Ventura!"
    When I register
    Then my choice of name should be rejected

  Scenario: Team name cannot be registered more than once
    Given team "Ace" is already registered
    And my chosen team name is "Ace"
    When I register
    Then my choice of name should be rejected

  Scenario: Register with a really long name
    Given I choose a really long name
    When I register
    Then my choice of name should be rejected
    
