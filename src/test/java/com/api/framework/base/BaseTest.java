package com.api.framework.base;

import com.api.framework.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setUp() {

        String baseUrl = ConfigReader.getProperty("base.url");
        String token = ConfigReader.getProperty("api.token");
        requestSpec = RestAssured.given().baseUri(baseUrl).header("Authorization", "Bearer " + token).header("Content-Type", "application/json").log().all();
    }
}
