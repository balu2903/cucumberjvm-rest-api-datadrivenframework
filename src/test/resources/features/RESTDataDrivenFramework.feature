Feature: Rest API Tests with json
  I want to execute REST API Tests for GET, POST and PUT  Verbs
  Background:
    Given a server named as "localhost"
    And a http port with number "8888"
    And a server context named as "Context"
    And a valid token "oathToken"
  Scenario: Successful Get
    When I click endpoint
    Then I see all the landlords
  Scenario Outline: Successful Post
    Given I have landlord with below details "<firstName>" and "<lastName>" and "<trustedFlag>"
    When I hit the endpoint
    Then I see landlords are added to system
    Examples:
      |firstName|lastName|trustedFlag|
      |Reddy1|Palem1     |false       |
      |Reddy2|Palem2     |true        |
  Scenario: Successful Put
    When I click endpoint to update firstName "ReddyUpdate3" and lastName "PalemUpdate3" with the unique id "wdnv0Fwl"
    Then I see the values are updated for the unique id "wdnv0Fwl"