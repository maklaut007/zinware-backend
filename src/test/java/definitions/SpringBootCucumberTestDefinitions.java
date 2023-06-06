package definitions;

import com.example.zinware.ZinwareApplication;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ZinwareApplication.class)
public class SpringBootCucumberTestDefinitions {
    private static final String BASE_URL = "http://localhost:";

    private String authToken;

    @LocalServerPort
    String port;

    private static Response response;

    @Given("A list of Categories are available")
    public void aListOfCategoriesAreAvailable() {
        try{
            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/api/categories/", HttpMethod.GET, null, String.class);
            List<Map<String, String>> properties = JsonPath
                    .from(String.valueOf(response
                            .getBody()))
                    .getList("$");
            Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
            Assert.assertTrue(properties.size() > 0);

        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }
    @When("User search for products from category")
    public void userSearchForProductsFromCategory() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        response = request.get(BASE_URL + port + "/api/categories/1/products/");
    }

    @Then("A list of products is displayed")
    public void aListOfProductsIsDisplayed() {
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.body());
        Assert.assertTrue(response.body().asString().contains("name"));
        Assert.assertTrue(response.body().as(List.class).size()> 1);
        Assert.assertTrue(response.body().asString().contains("description"));
    }
    @When("User search for product by id")
    public void userSearchForProductById() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        response = request.get(BASE_URL + port + "/api/categories/1/products/1/");
        Assert.assertNotNull(response.body());
    }

    @Then("A product information is displayed")
    public void aProductInformationIsDisplayed() {
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.body());
        Assert.assertTrue(response.body().asString().contains("name"));
        Assert.assertTrue(response.body().asString().contains("description"));
        Assert.assertTrue(response.body().asString().contains("price"));
        Assert.assertTrue(response.body().asString().contains("image"));
    }

    @Given("A user is successfully registered")
    public void aUserIsSuccessfullyRegistered() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("password", "123456");
            requestBody.put("email", "email@mail.com");
            requestBody.put("username", "user");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);
            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/auth/register/", HttpMethod.POST, request, String.class);
            Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @When("A user logs in")
    public void aUserLogsIn() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("password", "123456");
        requestBody.put("email", "email@mail.com");
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).post(BASE_URL + port + "/auth/login/");
    }

    @Then("JWT key is displayed")
    public void jwtKeyIsDisplayed() {
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.body());
    }

    @Given("A user is successfully logged in")
    public void aUserIsSuccessfullyLoggedIn() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("password", "123456");
            requestBody.put("email", "test@mail.com");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);
            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/auth/login/", HttpMethod.POST, request, String.class);
            Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
            // get JWT token from response body
            JSONObject responseBody = new JSONObject(response.getBody());
            authToken = responseBody.getString("message");

        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @When("A user open cart")
    public void aUserOpenCart() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + authToken).header("Accept", "application/json");
        JSONObject requestBody = new JSONObject();
        response = request.body(requestBody.toString()).get(BASE_URL + port + "/api/cart/");
    }

    @Then("A list of items in cart is displayed")
    public void aListOfItemsInCartIsDisplayed() {
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.body());
        Assert.assertTrue(response.body().asString().contains("cartItems"));
    }

    @When("User adds item to cart")
    public void userAddsItemToCart() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + authToken);
        JSONObject requestBody = new JSONObject();
        requestBody.put("productId", 1);
        requestBody.put("quantity", 2);
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).post(BASE_URL + port + "/api/cart/");
    }
    @Then("Item successfully added to cart")
    public void itemSuccessfullyAddedToCart() {
        Assert.assertEquals(201, response.getStatusCode());
        Assert.assertNotNull(response.body());
        Assert.assertTrue(response.body().asString().contains("id"));
    }

    @When("A user increase number of products in cart")
    public void aUserIncreaseNumberOfProductsInCart() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + authToken);
        JSONObject requestBody = new JSONObject();
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).put(BASE_URL + port + "/api/cart/1/increase-quantity/");
    }

    @Then("product quantity is increased by one")
    public void productQuantityIsIncreasedByOne() {
        Assert.assertEquals(200, response.getStatusCode());
    }


    @When("A user changes number of products in cart")
    public void aUserChangesNumberOfProductsInCart() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + authToken);
        JSONObject requestBody = new JSONObject();
        request.header("Content-Type", "application/json");
        requestBody.put("quantity", 2);
        response = request.body(requestBody.toString()).put(BASE_URL + port + "/api/cart/1/");

    }
    @Then("New number of products in displayed")
    public void newNumberOfProductsInDisplayed() {
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.body());
        Assert.assertTrue(response.body().asString().contains("quantity"));
    }
    @When("A user delete item from cart")
    public void aUserDeleteItemFromCart() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + authToken);
        JSONObject requestBody = new JSONObject();
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).delete(BASE_URL + port + "/api/cart/1/");
    }
    @Then("Cart without item is displayed")
    public void cartWithoutItemIsDisplayed() {
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.body());
        Assert.assertTrue(response.body().asString().contains("cartItems"));
    }

    @Given("A user is not signed in")
    public void aUserIsNotSignedIn() {
        // User JWT is not valid
        authToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QG1haWwuY29tIiwiaWF0IjoxNjg1OTEwNDk5LCJleHAiOjE2ODU5OTY4OTl9.M_rZBC9DdV7uiDnbOitjsg8yi0NNatXNtuL62rseY54";
    }


    @When("User tries to open the cart")
    public void userTriesToOpenTheCart() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + authToken).header("Accept", "application/json");
        JSONObject requestBody = new JSONObject();
        response = request.body(requestBody.toString()).get(BASE_URL + port + "/api/cart/");
    }

    @When("User tries to add an item to the cart")
    public void userTriesToAddAnItemToTheCart() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + authToken).header("Accept", "application/json");
        JSONObject requestBody = new JSONObject();
        requestBody.put("productId", 1);
        requestBody.put("quantity", 2);
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).post(BASE_URL + port + "/api/cart/");
    }

    @When("User tries to change the number of products in the cart")
    public void userTriesToChangeTheNumberOfProductsInTheCart() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + authToken);
        JSONObject requestBody = new JSONObject();
        request.header("Content-Type", "application/json");
        requestBody.put("quantity", 2);
        response = request.body(requestBody.toString()).put(BASE_URL + port + "/api/cart/1/");
    }

    @When("User tries to delete an item from the cart")
    public void userTriesToDeleteAnItemFromTheCart() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + authToken);
        JSONObject requestBody = new JSONObject();
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).delete(BASE_URL + port + "/api/cart/1/");
    }

    @Then("An authentication error is displayed")
    public void anAuthenticationErrorIsDisplayed() {
        Assert.assertEquals(403, response.getStatusCode());
        Assert.assertNotNull(response.body());
    }


}
