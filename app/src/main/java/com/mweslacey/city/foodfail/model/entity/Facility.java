package com.mweslacey.city.foodfail.model.entity;

import static android.arch.persistence.room.ColumnInfo.NOCASE;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "facilities")
public class Facility {

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

  @NonNull
  public int getFacilityKey() {
    return facilityKey;
  }

  public void setFacilityKey(@NonNull int facilityKey) {
    this.facilityKey = facilityKey;
  }

  public String getFacilityName() {
    return facilityName;
  }

  public void setFacilityName(String facilityName) {
    this.facilityName = facilityName;
  }

  public String getStreetNumber() {
    return streetNumber;
  }

  public void setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
  }

  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public int getZip() {
    return zip;
  }

  public void setZip(int zip) {
    this.zip = zip;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }
}
