package com.mweslacey.city.foodfail.model;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;

public class Geometry {

  @Expose(deserialize = true, serialize = false)
  private Location location;

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }
}
