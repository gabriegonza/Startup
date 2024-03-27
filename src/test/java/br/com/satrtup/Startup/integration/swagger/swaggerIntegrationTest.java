package br.com.satrtup.Startup.integration.swagger;

import br.com.satrtup.Startup.integration.config.TestConfig;
import br.com.satrtup.Startup.integration.testContainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class swaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void swaggerUiPage(){
        var content =
                given()
                    .basePath("/swagger-ui/index.html")
                    .port(TestConfig.SERVER_PORT)
                    .when()
                        .get()
                    .then()
                        .statusCode(200)
                    .extract()
                        .body()
                            .asString();

        assertTrue(content.contains("Swagger UI"));
    }
}
