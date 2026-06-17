package com.api.framework.tests;

import com.api.framework.base.BaseTest;
import com.api.framework.models.User;
import com.api.framework.utils.ConfigReader;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativeUserTests extends BaseTest {

    @Test(description = "Invalid token")
    public void testUnauthorizedAccess(){
        Response response = requestSpec.baseUri(ConfigReader.getProperty("base.url"))
                .header("Authorization", "Bearer invalid_token_123")
                .header("Content-Type", "application/json")
                .when()
                .get("/users");

        Assert.assertEquals(response.getStatusCode(),401);
        Assert.assertTrue(response.getBody().asString().contains("Authentication failed"));
    }

    @Test(description = "User register using already registered email")
    public void testCreateDuplicateUser(){

        User duplicateUser = new User("Duplicate User","qa_test_user@mail.com","male","active");

        Response response = requestSpec
                .body(duplicateUser)
                .when().post("/users");

        Assert.assertEquals(response.getStatusCode(),422);
        Assert.assertTrue(response.getBody().asString().contains("has already been taken"));
    }

    @Test(description = "found invalid ID")
    public void testGetNonExistingUser(){
        Response response = requestSpec
                .when()
                .get("/users/0000000");

        Assert.assertEquals(response.getStatusCode(),404);
        Assert.assertTrue(response.getBody().asString().contains("Resource not found"));
    }
}
