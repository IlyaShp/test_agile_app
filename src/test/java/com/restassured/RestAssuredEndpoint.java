package com.restassured;

public interface RestAssuredEndpoint {
	String BASE_URL = "http://localhost";
	int PORT = 8080;
	String DBO_API = "/dbo/api/";
	String CLIENT_ID = "client/{id}";
}
