package net.almaak.missingpathvariable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MissingPathVariableApplicationTests {

    private static final String GOOD_URL = "http://localhost:8091/items/4aefdf5d-b46d-4212-9b35-a6d85d0ece39/item";
    private static final String BAD_URL = "http://localhost:8091/items/null/item";

    @Value("${server.port}")
    private int serverPort;

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testShouldSuccessfullyCreateResource() {
        final HttpEntity<String> request = new HttpEntity<>("");
        final ResponseEntity<String> response = restTemplate
                .exchange(GOOD_URL, HttpMethod.POST, request, String.class);

        Assertions.assertAll(() -> Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode()));
    }

    @Test
    public void testShouldThrowMissingPathVariableException() {
        final HttpEntity<String> request = new HttpEntity<>("");
        final ResponseEntity<String> response = restTemplate
                .exchange(BAD_URL, HttpMethod.POST, request, String.class);

        Assertions.assertAll(() -> Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()));
    }

    @Test
    public void testShouldCheckForPort() {
        Assertions.assertAll(() -> Assertions.assertEquals(8091, serverPort));
    }

}
