package com.example.authorizationservicerest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class AuthorizationServiceRestApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final GenericContainer<?> devApp = new GenericContainer<>("devapp:latest")
            .withExposedPorts(8080);
    private static final GenericContainer<?> prodApp = new GenericContainer<>("prodapp:latest")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    void contextLoads() {

        String urlDev = String.format("http://localhost:%d/authorize?user=%s&password=%s",
                devApp.getMappedPort(8080), "admin", "123456789");

        String urlProd = String.format("http://localhost:%d/authorize?user=%s&password=%s",
                prodApp.getMappedPort(8081), "a", "123456789");

        ResponseEntity<String> entityFromFirst = restTemplate.getForEntity(urlDev, String.class);
        ResponseEntity<String> entityFromSecond = restTemplate.getForEntity(urlProd, String.class);

        System.out.println("First - body: " + entityFromFirst.getBody());
        System.out.println("First - status code: " + entityFromFirst.getStatusCode());
        assertEquals(HttpStatus.OK, entityFromFirst.getStatusCode());
        System.out.println("Second - body: " + entityFromSecond.getBody());
        System.out.println("Second - status code: " + entityFromSecond.getStatusCode());
        assertEquals(HttpStatus.UNAUTHORIZED, entityFromSecond.getStatusCode());
    }
}
