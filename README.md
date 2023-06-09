# Zinware API

- [Overview](#overview)
- [Tools and Technologies](#tools-and-technologies)
- [Approach](#approach)
  - [ERD Diagram](#erd-diagram)
  - [User Stories](#user-stories)
  - [TDD](#test-driven-development)
- [API Endpoints](#api-endpoints)
- [Planning Documentation](#planing-documentation)
- [Resources and References](#resources-and-references)
- [Dependencies](#resources-and-references)
- [Credits](#credits)

## Overview
This project involves building a Backend API for an e-commerce website selling computer peripherals. Users can register, browse products like mice, keyboards, and headphones, and add items to their cart. The API ensures a checkout process and providing cart management for a user. 

Front-end part of a project is [here](https://github.com/maklaut007/zinware-frontend)
## Tools and Technologies
* Java
* IDE - IntelliJ
* Framework - Spring Boot  
* Database - Spring Data JPA
* Authentication - JWT
* Build Automation -  Maven
* Manual API testing - Postman
* Automated (BDD) testing - Cucumber Spring Integration

## Approach
### ERD Diagram
After deciding on the topic I created ERD diagram (latest version)

<img src="assets/erd.png" style="width: 40rem;">

### User Stories
Next step was to create user stories. I split them into multiple groups, each group representing one iteration/spring of my project. 
After completing each sprint I completed one sprint on front end part of my project.

Iteration One - Public Endpoints:
- As a user, I should be able to see categories for all products.
- As a user, I should be able to see all products from specific category.
- As a user, I should be able to see individual product.

Iteration Two - authorization:
- As a user, I should be able to register account.
- As a user, I should be able to log in into my account.

Iteration Three - Cart Interactions:
- As a user, I should be able to see products in my cart.
- As a user, I should be able to add individual product to my cart.
- As a user, I should be able to change quantity of a certain product in my cart.
- As a user, I should be able to delete product from my cart

Iteration Four - Checkout:
- As a user, I should be able to check out from my cart

### Test Driven Development
Coding was done using test driven development methodology. Test results:

<img src="assets/test-result.png" style="width: 40rem;">

## API Endpoints

| Request Type | URL                                               | Functionality                         | Access  | 
|--------------|---------------------------------------------------|---------------------------------------|---------|
| GET          | /api/categories/                                  | Get categories                        | Public  |
| GET          | /api/categories/{categoryId}/products/            | Products from categories              | Public  |
| GET          | /api/categories/{categoryId}/products/{productId} | Get specific product                  | Public  |
| POST         | /auth/login/                                      | User login               	            | Public  |
| POST         | /auth/register/                                   | User registration        	            | Public  |
| GET          | /api/cart/                                        | Products in cart 		                   | Private |
| POST         | /api/cart/                                        | Add product to cart	                  | Private |
| DELETE       | /api/cart/{itemId}                                | Delete product from cart	             | Private |
| UPDATE       | /api/cart/{itemId}                                | Update quantity of a product in cart	 | Private |
| POST         | /api/checkout/                                    | Submit checkout	                      | Private |

## Planing documentation
As a planning documentation I used Trello board:

<img src="assets/trello.png" style="hight: 30rem;">

## Resources and References

- [Stack Overflow](https://stackoverflow.com) - troubleshooting
- [Baeldung](https://www.baeldung.com) - information about Java
- [Trello](https://trello.com) - planning board
- [Draw.io](https://www.drawio.com) - ERD diagram builder
- [Midjorney AI](https://www.midjourney.com/home) - image generation 

## Credits

### Special Credit

- [Jeff Ou](https://github.com/pophero110)
- [Kevin Barrios](https://github.com/dayjyun)
- Groupmates in Room #9 and others

### Instructors

- [Suresh Sigera](https://github.com/sureshmelvinsigera/)
- [Leonardo Rodriguez](https://github.com/LRodriguez92)
- Dhrubo Chowdhury