package com.mweslacey.city.foodfail.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface utilized by Retrofit to make requests to the web-services consumed by the Food Fail
 * Application.
 */
public interface GeoCodeService {

  /*
  https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+
  Mountain+View,+CA&key=YOUR_API_KEY

  https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&
  location_type=ROOFTOP&result_type=street_address&key=YOUR_API_KEY
   */

  /**
   * Requests full address and geological coordinates from the Google Geocding API.
   * Lat and Lng extracted from JSON response for Google Map Camera update.
   * @param address Extracted from a {@link com.mweslacey.city.foodfail.model.entity.Facility} record.
   * @param apiKey Extracted from application properties file.
   * @return {@link Result}
   */
  @GET("/maps/api/geocode/json")
  Call<Result> getLatLng(@Query("address") String address, @Query("key") String apiKey);

  /**
   * Requests full address and geological coordinates from the Google Geocoding API.
   * Postal code extracted from JSON response for database query.
   * @param latlng Extracted from LocationManager response.
   * @return {@link ReverseResult}
   */
  @GET("/maps/api/geocode/json")
  Call<ReverseResult> getPostal(@Query("latlng") String latlng);

}
