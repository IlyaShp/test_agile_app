package com.restassured;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.restassured.RestAssuredEndpoint.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class RestAssuredTest {
	private RequestSpecification request;

	@BeforeEach
	public void setUpRestAssured() {
		request = given()
				.baseUri(BASE_URL)
				.port(PORT)
				.basePath(DBO_API)
				.header("X-API-VERSION", 1)
				.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
	}

	@Test
	public void getClientById() {
		request.
				when().
				get(CLIENT_ID, 1).
				then().
				statusCode(SC_OK).
				body("id", IsEqual.equalTo(1),
						"secret", IsEqual.equalTo("c99ef573720e30031034d24e82721350dfa6af9957d267c2acc0be98813bb3e4"));
	}

	@Test
	public void deleteClientById() {
		request.
				when().
				delete(CLIENT_ID, 3).
				then().
				statusCode(SC_OK);
	}

}
