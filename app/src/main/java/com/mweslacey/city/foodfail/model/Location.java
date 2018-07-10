package com.mweslacey.city.foodfail.model;

import com.google.gson.annotations.Expose;

public class Location {

  @Expose(deserialize = true, serialize = false)
  private double lat;

  @Expose(deserialize = true, serialize = false)
  private double lng;

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }
}
