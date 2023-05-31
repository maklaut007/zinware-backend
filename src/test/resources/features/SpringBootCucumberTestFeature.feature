Feature: Rest API functionalities
  Scenario: User able to get Categories
    Given A list of Categories are available
    When I search for products from category
    Then A list of products is displayed
    When I search for product by id
    Then A product information is displayed

