package com.retrofit;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Client
 * <p>
 * Entity with personalized information about client
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"login",
		"salt",
		"secret"
})
@Generated("jsonschema2pojo")
public class Client {
	private int id;

	public Client(String newLogin, String secret, String salt, LocalDateTime now, boolean b) {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Client() {
	}

	public Client(String s, String somesalt, String secret) {
	}

	/**
	 * Client login for auth
	 * (Required)
	 *
	 */
	@JsonProperty("login")
	@JsonPropertyDescription("Client login for auth")
	private String login;
	/**
	 * Client salt
	 * (Required)
	 *
	 */
	@JsonProperty("salt")
	@JsonPropertyDescription("Client salt")
	private String salt;
	/**
	 * Client secret
	 * (Required)
	 *
	 */
	@JsonProperty("secret")
	@JsonPropertyDescription("Client secret")
	private String secret;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * Client login for auth
	 * (Required)
	 *
	 */
	@JsonProperty("login")
	public String getLogin() {
		return login;
	}

	/**
	 * Client login for auth
	 * (Required)
	 *
	 */
	@JsonProperty("login")
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Client salt
	 * (Required)
	 *
	 */
	@JsonProperty("salt")
	public String getSalt() {
		return salt;
	}

	/**
	 * Client salt
	 * (Required)
	 *
	 */
	@JsonProperty("salt")
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * Client secret
	 * (Required)
	 *
	 */
	@JsonProperty("secret")
	public String getSecret() {
		return secret;
	}

	/**
	 * Client secret
	 * (Required)
	 *
	 */
	@JsonProperty("secret")
	public void setSecret(String secret) {
		this.secret = secret;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return "Client{" +
				"id=" + id +
				", login='" + login + '\'' +
				", salt='" + salt + '\'' +
				", secret='" + secret + '\'' +
				", additionalProperties=" + additionalProperties +
				'}';
	}
}
