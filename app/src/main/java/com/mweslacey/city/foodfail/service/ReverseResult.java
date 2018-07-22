package com.mweslacey.city.foodfail.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mweslacey.city.foodfail.service.ReverseResult.Results.AddressComponents;
import java.util.List;

/**
 * Java object defining return type for {@link GeoCodeService#getPostal(String)}.
 * Defines class and field mapping for de-serialization of JSON response object.
 */
public class ReverseResult {

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

  /**
   * Get postal code from Results.
   * @return int postal code
   */
  public int getPostalCode() {
    if (results.size() > 0) {
      List<AddressComponents> components = results.get(0).getAddressComponents();
      return Integer.parseInt(components.get(components.size() - 1).longName);
    } else {
      return -1;
    }
  }

  public class Results {

    @Expose
    @SerializedName("address_components")
    private List<AddressComponents> addressComponents;

    public List<AddressComponents> getAddressComponents() {
      return addressComponents;
    }

    public void setAddressComponents(List<AddressComponents> addressComponents) {
      this.addressComponents = addressComponents;
    }

    @Expose
    @SerializedName("geometry")
    private Geometry geometry;

    public Geometry getGeometry() {
      return geometry;
    }

    public void setGeometry(Geometry geometry) {
      this.geometry = geometry;
    }

    public class AddressComponents {

      public String getLongName() {
        return longName;
      }

      public String getShortName() {
        return shortName;
      }

      @Expose
      @SerializedName("long_name")
      private String longName;

      @Expose
      @SerializedName("short_name")
      private String shortName;

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





