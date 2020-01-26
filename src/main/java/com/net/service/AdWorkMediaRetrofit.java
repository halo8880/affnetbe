package com.net.service;

import com.net.dto.AdWorkMediaResponseDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import java.util.List;
import java.util.Map;

public interface AdWorkMediaRetrofit {
	@GET("api/index.php")
	Call<List<AdWorkMediaResponseDTO>> getAllOffers(@QueryMap Map<String, String> queries);
}
