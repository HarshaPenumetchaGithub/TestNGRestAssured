package com.hyd.tests;

import static org.testng.Assert.*;
import org.testng.annotations.*;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

public class Base {

	String Userid1 = "";
	String Userid2 = "";

	@Test(priority = 1)
	public void getUser() {
		Response response = get("https://reqres.in/api/users/2");
		System.out.println(response.getStatusCode());
		assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority = 2)
	public void getUsers() {
		given().get("https://reqres.in/api/users?page=2").then().statusCode(200);
	}

	@Test(priority = 3)
	public void createUser() {
		// Approach 1
		JSONObject request1 = new JSONObject();
		request1.put("name", "Joshua");
		request1.put("job", "Technical Product Manager");
		Response response1 = given().body(request1).when().post("https://reqres.in/api/users");

		assertEquals(response1.getStatusCode(), 201);
		Userid1 = response1.jsonPath().get("id");
		System.out.println(Userid1);

		// Approach 2
		RequestSpecification request2 = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "Suheb");
		requestParams.put("job", "Technical Delivery Manager");

		request2.body(requestParams);
		Response response2 = request2.post("https://reqres.in/api/users");

		assertEquals(response2.getStatusCode(), 201);
		Userid2 = response2.jsonPath().get("id");
		System.out.println(Userid2);
	}

	@Test(priority = 4)
	public void updateUser() {
		// Approach 1
		JSONObject request1 = new JSONObject();
		request1.put("name", "Joshua");
		request1.put("job", "Product Manager");
		Response response1 = given().body(request1).when().patch("https://reqres.in/api/users/" + Userid1);

		assertEquals(response1.getStatusCode(), 200);

		// Approach 2
		RequestSpecification request2 = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "Suheb");
		requestParams.put("job", "Delivery Manager");

		request2.body(requestParams);
		Response response2 = request2.patch("https://reqres.in/api/users/" + Userid2);

		assertEquals(response2.getStatusCode(), 200);
	}

	@Test(priority = 5)
	public void deleteUser() {
		// Approach 1
		Response response1 = given().when().delete("https://reqres.in/api/users/" + Userid1);

		assertEquals(response1.getStatusCode(), 204);

		// Approach 2
		Response response2 = delete("https://reqres.in/api/users/" + Userid2);

		assertEquals(response2.getStatusCode(), 204);
	}
}