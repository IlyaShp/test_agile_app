package com.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public class RetrofitTest {

	@Test
	public void getClient() throws IOException {
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		httpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
		Retrofit retrofit = new Retrofit.Builder()
				.addConverterFactory(JacksonConverterFactory.create())
				.baseUrl("http://localhost:8080/dbo/api/")
				.client(httpClient.build())
				.build();
		ClientService service = retrofit.create(ClientService.class);
		service.getClients().execute().body().forEach(System.out::println);
	}

	@Test
	public void postClient() throws IOException {
		Retrofit retrofit = new Retrofit.Builder()
				.addConverterFactory(JacksonConverterFactory.create())
				.baseUrl("http://localhost:8080/dbo/api/")
				.build();
		ClientService service = retrofit.create(ClientService.class);
		service.createClient(new Client("2@1.com", "somesalt", "fsdu8dfsu9s8a")).execute();
	}

}
