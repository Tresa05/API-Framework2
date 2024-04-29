package com.APITests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class UniversityApiTest {

	@BeforeMethod
	public void setUp() {
		// Set base URI for RestAssured
		baseURI = "http://127.0.0.1:4010/";
		basePath = "university";

	}

	@Test
	public void GetUniversityInfo() {
		given().header("api_key", "f3c84cbb-1f9a-4b87-bb5b-2d1691b24e1e")
				.header("content", "application/json").param("universityName", "University of Toronto").when().get()
				.then().log().all().statusCode(200);

	}

	@Test 
	public void createNewUniversity() {
		given().header("api_key", "f3c84cbb-1f9a-4b87-bb5b-2d1691b24e1e")
				.contentType("application/json")
				.body("{\r\n" + "    \"Unive rsityID\": 266,\r\n"
						+ "    \"UniversityName\": \"University of Ottawa\",\r\n"
						+ "    \"UniversityLocation\": \"Ottawa, Ontario, Canada\",\r\n"
						+ "    \"UniversityFounded\": 1975,\r\n" + "    \"KeyPeople\": [\r\n" + "        {\r\n"
						+ "            \"name\": \"Grace Smith\",\r\n" + "            \"age\": 45,\r\n"
						+ "            \"title\": \"President\"\r\n" + "        }\r\n" + "    ]\r\n" + "}")
				.when().post().then().log().all().statusCode(201);
	}

	@Test
	public void deleteUniversity() {
		given().header("api_key", "f3c84cbb-1f9a-4b87-bb5b-2d1691b24e1e")
				.param("universityName", "University of Toronto").when().delete().then().log().all().statusCode(204);
	}

	@Test // nw
	public void getUniversityInfoWithID() {
		given().header("api_key", "f3c84cbb-1f9a-4b87-bb5b-2d1691b24e1e")
				.header("content", "application/json").pathParam("universityID", 978).when().get().then()
				.log().all().statusCode(200);
	}

	@Test // nw
	public void updateUniversityInfo() {
		given().header("api_key", "f3c84cbb-1f9a-4b87-bb5b-2d1691b24e1e")
				.header("content", "application/json").pathParam("universityID", 978)
				.body("{ \"UniversityName\": \"New UniversityName\" }").when().put("/university/{universityID}").then()
				.statusCode(201);
	}

	@Test
	public void getAllUniversities() {
		given().basePath("universities").header("api_key", "f3c84cbb-1f9a-4b87-bb5b-2d1691b24e1e")
				.header("contentType", "application/json").when().get().then().log().all().statusCode(200);
	}

	@Test
	public void testGetUniversityInfoUnauthorized() {
		given().param("universityName", "University of Toronto").when().delete().then().log()
				.all().statusCode(401);
	}

	@Test
	public void testMissingParameter() {
		given().header("api_key", "f3c84cbb-1f9a-4b87-bb5b-2d1691b24e1e").when().delete().then()
				.log().all().statusCode(422);
	}

	@Test
	public void testAuthorisationError() {
		given().header("api_key", "mkknlmdhjryjo").when().get("/university/abcd").then().log().all().statusCode(404);
	}

	@Test
	public void testUnencodedParameter() {
		given().header("api_key", "f3c84cbb-1f9a-4b87-bb5b-2d1691b24e1e")
				.param("universityName", "University of Toronto").when().get().then().log().all().statusCode(200);
	}

	@Test
	public void testUrlpathError() {
		given().header("api_key", "f3c84cbb-1f9a-4b87-bb5b-2d1691b24e1e").when().get("/university/abcd").then().log()
				.all().statusCode(404);
	}

}
