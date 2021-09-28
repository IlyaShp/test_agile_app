package com.jpatests;

import com.retrofit.Client;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

import static com.restassured.RestAssuredEndpoint.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

public class JpaAccountTest {
	private static EntityManagerFactory entityManagerFactory;
	private RequestSpecification request;

	@BeforeAll
	public static void setUpJpa() {
		entityManagerFactory = Persistence.createEntityManagerFactory("dbo");
	}

	@AfterAll
	public void setUpRestAssured() {
		request = given()
				.baseUri(BASE_URL)
				.port(PORT)
				.basePath(DBO_API)
				.header("X-API-VERSION", 1)
				.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
	}

	@Test
	public void shouldGetAccountByIdWhenExists() throws SQLException {
		final EntityManager em = entityManagerFactory.createEntityManager();

		final String newLogin = "login" + new Random().nextInt();
		final Client client = new Client(newLogin, "secret", "salt", LocalDateTime.now(), true);

		em.getTransaction().begin();
		em.persist(client);
		em.getTransaction().commit();

		request.
				when().
				get(CLIENT_ID, client.getId()).
				statusCode(is(SC_OK)).
				body("id", equalTo(client.getId()),
						"login", equalTo(client.getLogin()));

		em.getTransaction().begin();
		final Client clientSaved = em.find(Client.class, client.getId());
		em.remove(clientSaved);
		em.getTransaction().commit();

		em.close();
	}
}
