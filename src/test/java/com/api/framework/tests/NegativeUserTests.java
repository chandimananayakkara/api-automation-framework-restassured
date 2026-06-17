package com.api.framework.tests;

import com.api.framework.base.BaseTest;
import com.api.framework.models.User;
import com.api.framework.utils.ConfigReader;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class NegativeUserTests extends BaseTest {

    @Test(description = "Invalid token")
    public void testUnauthorizedAccess(){
        Response response = given()
                .baseUri(ConfigReader.getProperty("base.url"))
                .header("Authorization", "invalidToken1234")
                .header("Content-Type", "application/json")
                .when()
                .get("/users");

        Assert.assertEquals(response.getStatusCode(),401);

        Assert.assertTrue(response.getBody().asString().contains("Invalid token"));
    }

    @Test(description = "User register using already registered email")
    public void testCreateDuplicateUser(){

        User duplicateUser = new User("Duplicate User","sharda_vm_mishra@osinski-padberg.test","male","active");

        Response response = requestSpec
                .body(duplicateUser)
                .when().post("/users");

        Assert.assertEquals(response.getStatusCode(),422);
        Assert.assertTrue(response.getBody().asString().contains("has already been taken"),"User used already registered email");
    }

    @Test(description = "found invalid ID")
    public void testGetNonExistingUser(){
        Response response = requestSpec
                .when()
                .get("/users/0000000");

        Assert.assertEquals(response.getStatusCode(),404);
        Assert.assertTrue(response.getBody().asString().contains("Resource not found"),"User is not register user");
    }
}
