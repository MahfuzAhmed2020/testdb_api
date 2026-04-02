package com.example.testdb_api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserApiTest {

    @BeforeClass
    public void setup() {
        // Base URI for all tests
        RestAssured.baseURI = "http://localhost:8080";
    }

    // Test GET /users
    @Test
    public void testGetAllUsers() {
        given()
        .when()
            .get("/users")
        .then()
            .statusCode(200)               // Assert status 200 OK
            .body("size()", greaterThan(0)) // Ensure there are some users
            .body("[0].name", notNullValue()); // First user's name is not null
    }

    // Test GET /users/{id}
    @Test
    public void testGetUserById() {
        int userId = 1;

        given()
            .pathParam("id", userId)
        .when()
            .get("/users/{id}")
        .then()
            .statusCode(200)
            .body("id", equalTo(userId))
            .body("name", notNullValue())
            .body("email", containsString("@"));
    }

    // Test POST /users
    @Test
    public void testCreateUser() {
        String newUserJson = "{ \"name\": \"Charlie\", \"email\": \"charlie@example.com\", \"address\": \"NY\" }";

        Response response =
        given()
            .header("Content-Type", "application/json")
            .body(newUserJson)
        .when()
            .post("/users")
        .then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("name", equalTo("Charlie"))
            .extract().response();

        System.out.println("Created User ID: " + response.path("id"));
    }

    // Test PATCH /users/{id}
    @Test
    public void testPatchUser() {
        int userId = 1;
        String updateJson = "{ \"name\": \"UpdatedName\" }";

        given()
            .header("Content-Type", "application/json")
            .pathParam("id", userId)
            .body(updateJson)
        .when()
            .patch("/users/{id}")
        .then()
            .statusCode(200)
            .body("changedFields.name.new", equalTo("UpdatedName"));
    }

    // Test DELETE /users/{id}
    @Test
    public void testDeleteUser() {
        int userId = 2;

        given()
            .pathParam("id", userId)
        .when()
            .delete("/users/{id}")
        .then()
            .statusCode(200)
            .body("message", equalTo("User deleted successfully"));
    }
}