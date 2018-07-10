package com.mweslacey.city.foodfail.service;

import com.mweslacey.city.foodfail.model.entity.Inspection;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoCodeService {
/*
https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=YOUR_API_KEY
 */

  @GET("/maps/api/geocode/json")
  Call<Inspection> get(@Query("address") String address, @Query("key") String apiKey);
}
