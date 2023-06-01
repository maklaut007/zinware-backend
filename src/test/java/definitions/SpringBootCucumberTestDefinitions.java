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

    @When("I search for products from category")
    public void iSearchForProductsFromCategory() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        response = request.get(BASE_URL + port + "/api/categories/1/products/");
        Assert.assertNotNull(response.body());
    }

    @Then("A list of products is displayed")
    public void aListOfProductsIsDisplayed() {
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.body());
        Assert.assertTrue(response.body().asString().contains("name"));
        Assert.assertTrue(response.body().as(List.class).size()> 1);
    }

    @When("I search for product by id")
    public void iSearchForProductById() {
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
    public void aUserLogsIn() {
        
    }

    @Then("JWT key is displayed")
    public void jwtKeyIsDisplayed() {
    }
}
