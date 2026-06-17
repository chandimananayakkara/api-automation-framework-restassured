package com.api.framework.tests;

import com.api.framework.base.BaseTest;
import com.api.framework.models.User;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserTests extends BaseTest {

   private int userId;

   @Test(priority = 1, description = "Create a new user")
    public void testCreateUser(){
       String email = "apiuser" + System.currentTimeMillis()+"@mail.com";
       User newUser = new User("QA Student", email, "male","active");

       Response response = requestSpec.body(newUser).when().post("/users");

       Assert.assertEquals(response.getStatusCode(),201);

       userId = response.jsonPath().getInt("id");
       System.out.println("Created User ID: "+userId);
   }

   @Test(priority = 2, dependsOnMethods = "testCreateUser", description = "View details of created user")
    public void testGetUser(){
       Response response = requestSpec.when().get("/users" + userId);

       Assert.assertEquals(response.getStatusCode(),200);
       Assert.assertEquals(response.jsonPath().getString("name"),"QA Student");
   }

   @Test(priority = 3, dependsOnMethods = "testGetUser", description = "Change name of user")
    public void testUpdateUser(){
       User updatedUser = new User("Updated QA Student",null,"male","active");

       Response response = requestSpec.body(updatedUser).when().patch("/users/"+userId);

       Assert.assertEquals(response.getStatusCode(),200);
       Assert.assertEquals(response.jsonPath().getString("name"),"Updated QA Student");
   }

   @Test(priority = 4, dependsOnMethods = "testUpdateUser", description = "Delete a user")
    public void testDeleteUser(){
       Response response = requestSpec.when().delete("/users/"+userId);

       Assert.assertEquals(response.getStatusCode(),204);
   }

   @Test(priority = 5, dependsOnMethods = "testDeleteUser", description = "Verify the user is successfully deleted")
    public void testVerifyDeletion(){
       Response response = requestSpec.when().get("/users/"+userId);
   }
}
