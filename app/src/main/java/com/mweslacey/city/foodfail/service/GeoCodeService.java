package com.mweslacey.city.foodfail.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoCodeService {
/*
https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+
Mountain+View,+CA&key=YOUR_API_KEY

https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&
location_type=ROOFTOP&result_type=street_address&key=YOUR_API_KEY
 */
  @GET("/maps/api/geocode/json")
  Call<Result> getLatLng(@Query("address") String address, @Query("key") String apiKey);

  @GET("/maps/api/geocode/json")
  Call<ReverseResult> getPostal(@Query("latlng") String latlng);

}
