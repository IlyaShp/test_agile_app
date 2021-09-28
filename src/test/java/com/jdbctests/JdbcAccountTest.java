package com.jdbctests;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.Instant;

import static com.restassured.RestAssuredEndpoint.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assume.assumeTrue;

public class JdbcAccountTest {
	private RequestSpecification request;
	private Connection connection;

	@BeforeEach
	public void setUpDbConnection() throws SQLException {
		connection = DriverManager.getConnection("jdbc:derby://localhost/dbo-db");
	}

	@AfterEach
	public void choseDbConnection() throws SQLException {
		connection.close();
	}

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
	public void shouldGetSomeClientsWhenPreparedDb() throws SQLException {

		int newClientId;
		try (final PreparedStatement newClient = connection.prepareStatement(
				"INSERT INTO CLIENT(LOGIN, SECRET, SALT, " +
						"CREATED, ENABLED" +
						") VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS))
		{
			newClient.setString(1, "account@ru.ru");
			newClient.setString(2, "kh425kh523jon42kjnl2bjb2lj452ll2n5l24");
			newClient.setString(3, "some-salt");
			newClient.setTimestamp(4, Timestamp.from(Instant.now()));
			newClient.setBoolean(5, true);

			assumeTrue(newClient.executeUpdate() == 1);

			try (final ResultSet generatedKeys = newClient.getGeneratedKeys()) {
				assumeTrue(generatedKeys.next());
				newClientId = generatedKeys.getInt(1);
			}
		}

		int clientsCount;
		try (final PreparedStatement countClients = connection.prepareStatement("SELECT COUNT(*) FROM CLIENT");
			 final ResultSet resultSet = countClients.executeQuery())
		{
			assumeTrue(resultSet.next());
			clientsCount = resultSet.getInt(1);
		}

		try {
			request.when().get("client").
					then().statusCode(200).body(
							"size()", is(clientsCount),
							"id", hasItem(newClientId)
					);
		} finally {
			try (final PreparedStatement deleteClient =
						 connection.prepareStatement(("DELETE FROM CLIENT WHERE ID=?"))) {
				deleteClient.setInt(1, newClientId);
				assumeTrue(deleteClient.executeUpdate() == 1);
			}
		}
	}

	@Test
	public void shouldPost() throws SQLException {
		try {
			request.when().contentType(ContentType.JSON).
					body("{\n" +
							"  \"login\": \"00@email.com\",\n" +
							"  \"salt\": \"some-salt\",\n" +
							"  \"secret\": \"4k43g4k3g343g4k433g3k34g34hhgkh343h\"\n" +
							"}").
					post("client").
					then().statusCode(SC_CREATED);
		} finally {
			try(final PreparedStatement deleteClient = connection.prepareStatement("DELETE FROM CLIENT WHERE LOGIN=?")) {
				deleteClient.setString(1, "00@email.com");
				assumeTrue(deleteClient.executeUpdate() == 1);
			}
		}
	}

}