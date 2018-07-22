package com.mweslacey.city.foodfail.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Java object defining return type for {@link GeoCodeService#getLatLng(String, String)}.
 * Defines class and field mapping for de-serialization of JSON response object.
 */
public class Result {

  @Expose
  @SerializedName("results")
  private List<Results> results;

  /**
   * Get List of Results de-serialized by Gson and initialized by Retrofit.
   * @return List Results
   */
  public List<Results> getResults() {
    return results;
  }

  /**
   * Utilized by Retrofit to initialize List of Results
   * @param results
   */
  public void setResults(List<Results> results) {
    this.results = results;
  }

  /**
   * Get latitude and longitude coordinates from Results.
   * @return double[] coordinates
   */
  public double[] getGeometryCoordinates() {
    double[] coordinates = {
        results.get(0).getGeometry().getLocation().getLatitude(),
        results.get(0).getGeometry().getLocation().getLongitude()
    };
    return coordinates;
  }

  private class Results {

    @Expose
    @SerializedName("geometry")
    private Geometry geometry;

    public Geometry getGeometry() {
      return geometry;
    }

    public void setGeometry(Geometry geometry) {
      this.geometry = geometry;
    }

    private class Geometry {

      @Expose
      private Location location;

      public Location getLocation() {
        return location;
      }

      public void setLocation(Location location) {
        this.location = location;
      }

      private class Location {

        @Expose
        @SerializedName("lat")
        private double latitude;
        @Expose
        @SerializedName("lng")
        private double longitude;

        public double getLatitude() {
          return latitude;
        }

        public void setLatitude(double latitude) {
          this.latitude = latitude;
        }

        public double getLongitude() {
          return longitude;
        }

        public void setLongitude(double longitude) {
          this.longitude = longitude;
        }
      }
    }
  }
}





