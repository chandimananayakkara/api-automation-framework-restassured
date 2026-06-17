package com.api.framework.tests;

import com.api.framework.base.BaseTest;
import com.api.framework.models.User;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserTests extends BaseTest {

    @Test
    public void testCreateUser(){
        String email = "apiuser"+System.currentTimeMillis()+"@mail.com";
        User newUser = new User("QA Student", email, "male","active");

        Response response = requestSpec.body(newUser).when().post("/users");

        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),201);
        Assert.assertEquals(response.jsonPath().getString("name"),"QA Student");
    }
}
