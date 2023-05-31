package definitions;

import com.example.zinware.ZinwareApplication;
import io.cucumber.java.en.Given;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/api/", HttpMethod.GET, null, String.class);
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
}
