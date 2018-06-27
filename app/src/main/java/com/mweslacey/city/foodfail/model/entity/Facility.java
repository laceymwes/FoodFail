package com.mweslacey.city.foodfail.model.entity;

import static android.arch.persistence.room.ColumnInfo.NOCASE;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.ColumnInfo.Collate;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


public class Facility {

@NonNull
@PrimaryKey
private Integer facilityKey;

@ColumnInfo (collate = NOCASE) // users will be able to search by facility name
private String facilityName;

private int streetNumber;

private String streetName;

private int zip;

private String state;

  @NonNull
  public Integer getFacilityKey() {
    return facilityKey;
  }

  public void setFacilityKey(@NonNull Integer facilityKey) {
    this.facilityKey = facilityKey;
  }

  public String getFacilityName() {
    return facilityName;
  }

  public void setFacilityName(String facilityName) {
    this.facilityName = facilityName;
  }

  public int getStreetNumber() {
    return streetNumber;
  }

  public void setStreetNumber(int streetNumber) {
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
