Feature: Rest API functionalities
  Scenario: User able to get Categories
    Given A list of Categories are available
    When I search for products from category
    Then A list of products is displayed
    When I search for product by id
    Then A product information is displayed
  Scenario: User able to Register and Login
    Given A user is successfully registered
    When A user logs in
    Then JWT key is displayed
