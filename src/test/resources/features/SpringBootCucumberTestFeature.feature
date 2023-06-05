Feature: Rest API functionalities
  Scenario: User able to get Categories
    Given A list of Categories are available
    When User search for products from category
    Then A list of products is displayed
    When User search for product by id
    Then A product information is displayed
  Scenario: User able to Register and Login
    Given A user is successfully registered
    When A user logs in
    Then JWT key is displayed
  Scenario: User can get, add and remove from cart
    Given A user is successfully logged in
    When A user open cart
    Then A list of items in cart is displayed
    When User adds item to cart
    Then Item successfully added to cart
    When A user changes number of products in cart
    Then New number of products in displayed
    When I delete item from cart
    Then Cart without item is displayed
  Scenario: User tries to access protected endpoints without signing in
    Given A user is not signed in
    When User tries to search for products from category
    Then An authentication error is displayed
    When User tries to search for product by id
    Then An authentication error is displayed
    When User tries to open the cart
    Then An authentication error is displayed
    When User tries to add an item to the cart
    Then An authentication error is displayed
    When User tries to change the number of products in the cart
    Then An authentication error is displayed
    When User tries to delete an item from the cart
    Then An authentication error is displayed
