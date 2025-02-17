package com.berete.go4lunch.data_sources.restaurants.remote_source;

import com.berete.go4lunch.data_sources.restaurants.dto.AutocompleteHttpResponse;
import com.berete.go4lunch.data_sources.restaurants.dto.NearbySearchHttpResponse;
import com.berete.go4lunch.data_sources.restaurants.dto.PlaceDetailsHttpResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface PlaceHttpClient {
    @GET("nearbysearch/json")
    Call<NearbySearchHttpResponse> getPlaces(@QueryMap Map<String, String> params);

    @GET("autocomplete/json")
    Call<AutocompleteHttpResponse> getPredictions(@QueryMap Map<String, String> params);

    @GET("details/json")
    Call<PlaceDetailsHttpResponse> getPlaceDetails(@QueryMap Map<String, String> params);
}
