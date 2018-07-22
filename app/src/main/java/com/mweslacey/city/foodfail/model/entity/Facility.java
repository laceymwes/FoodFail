package com.mweslacey.city.foodfail.model.entity;

import static android.arch.persistence.room.ColumnInfo.NOCASE;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Facilities entity/table definition for database.
 */
@Entity(tableName = "facilities")
public class Facility {

  /**
   *  Empty constructor utilized by Room.
   */
  public Facility() {

  }

  @NonNull
  @PrimaryKey
  @ColumnInfo(index = true, name = "facility_key")
  private int facilityKey;

  // Users will search for facility by name
  @ColumnInfo(collate = NOCASE, name = "facility_name", index = true)
  private String facilityName;

  @ColumnInfo(name = "street_number")
  private String streetNumber;

  @ColumnInfo(name = "street_name")
  private String streetName;

  private int zip;

  private String state;

  /**
   * Get unique facility key.
   * @return int facility key
   */
  @NonNull
  public int getFacilityKey() {
    return facilityKey;
  }

  /**
   * Set unique facility key.
   * @param facilityKey
   */
  public void setFacilityKey(@NonNull int facilityKey) {
    this.facilityKey = facilityKey;
  }

  /**
   * Get facility name.
   * @return String facility name
   */
  public String getFacilityName() {
    return facilityName;
  }

  /**
   * Set facility name.
   * @param facilityName
   */
  public void setFacilityName(String facilityName) {
    this.facilityName = facilityName;
  }

  /**
   * Get facility street number.
   * @return String street number
   */
  public String getStreetNumber() {
    return streetNumber;
  }

  /**
   * Set set street number.
   * @param streetNumber
   */
  public void setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
  }

  /**
   * Get street number.
   * @return String street name
   */
  public String getStreetName() {
    return streetName;
  }

  /**
   * Set street name.
   * @param streetName
   */
  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  /**
   * Get zip code.
   * @return int zip code
   */
  public int getZip() {
    return zip;
  }

  /**
   * Set zip code.
   * @param zip
   */
  public void setZip(int zip) {
    this.zip = zip;
  }

  /**
   * Get state.
   * @return String state
   */
  public String getState() {
    return state;
  }

  /**
   * Set state.
   * @param state
   */
  public void setState(String state) {
    this.state = state;
  }
}
