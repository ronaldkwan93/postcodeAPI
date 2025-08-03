package com.postcodeapi.postcodeapi.postcode;

import com.postcodeapi.postcodeapi.Postcode.PostCodeRepository;
import com.postcodeapi.postcodeapi.Postcode.Postcode;
import com.postcodeapi.postcodeapi.Suburb.Suburb;
import com.postcodeapi.postcodeapi.Suburb.SuburbRepository;
import com.postcodeapi.postcodeapi.User.User;
import com.postcodeapi.postcodeapi.jwt.LoginRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PostCodeEndToEndTest {

    @LocalServerPort
    private int port;

    private ArrayList<Postcode> postcodes = new ArrayList<>();

    @Autowired
    private SuburbRepository suburbRepository;


    @Autowired
    private PostCodeRepository postCodeRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;

        this.postCodeRepository.deleteAll();
        this.postcodes.clear();

        Postcode postcode1 = new Postcode();
        postcode1.setCode("2000");
        
        // Create and save the suburb first
        Suburb suburb1 = new Suburb();
        suburb1.setSuburb("Sydney");
        suburbRepository.save(suburb1);  // Save suburb first
        
        Set<Suburb> suburbs = new HashSet<>();
        suburbs.add(suburb1);
        
        postcode1.setAssignedSuburbs(suburbs);
        
        this.postcodes.add(postcode1);
        this.postCodeRepository.saveAll(this.postcodes);
    }


    @Test
    public void getSuburbs_byPostCodeInDB_ReturnsSuburbs () {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("testpassword");

        User testUser = new User();
        testUser.setEmail("testuser");
        testUser.setPassword("testpassword");
        testUser.setName("Test User");

        RestAssured.given()
            .contentType("application/json")
            .body(testUser)
            .when()
            .post("/register")
            .then()
            .statusCode(200);

        String token = RestAssured.given()
            .contentType("application/json")
            .body(loginRequest)
            .when()
            .post("/signin")
            .then()
            .statusCode(200)
            .extract()
            .path("jwtToken");

        RestAssured.given()
            .log().all()
            .header("Authorization", "Bearer " + token)
            .when()
            .get("/postcode/findSuburbs?postcode=2000")
            .then()
            .log().all()
            .statusCode(200);
}

    @Test
    public void getSuburbs_byPostCodeNotInDB_ReturnsEmptyList () {

    }
}