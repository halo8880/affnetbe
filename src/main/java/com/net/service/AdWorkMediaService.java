package com.net.service;

import com.net.dto.adwork.AdWorkMediaRequestDTO;
import com.net.dto.adwork.AdWorkMediaResponseDTO;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class AdWorkMediaService {
	public List<AdWorkMediaResponseDTO> getOffers() throws IOException {

		AdWorkMediaRequestDTO requestDTO = new AdWorkMediaRequestDTO();
		requestDTO.setCampDetails(true);
		requestDTO.setMaxCampaigns("20");

		String fooResourceUrl = "https://www.adworkmedia.com/";

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(fooResourceUrl)
				.client(createHttpClient())
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		AdWorkMediaRetrofit caller = retrofit.create(AdWorkMediaRetrofit.class);
		Call<List<AdWorkMediaResponseDTO>> resCall = caller.getAllOffers(requestDTO.getMap());
		Response<List<AdWorkMediaResponseDTO>> res = resCall.execute();
		return res.body();
	}

	private OkHttpClient createHttpClient() {
		return new OkHttpClient.Builder()
				.connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
				.build();
	}
}
