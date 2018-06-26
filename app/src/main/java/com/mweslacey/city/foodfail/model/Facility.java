package com.mweslacey.city.foodfail.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Facility {

@NonNull
@PrimaryKey
private int facility_key;

private String facility_name;

private int street_number;

private String street_name;

private int street_zip;

private String site_state;

}
