package com.mweslacey.city.foodfail.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Inspection {

@NonNull
@PrimaryKey(autoGenerate = true)
private long inspection_id;

private int facility_key;

private long inspection_date;

private String inspection_type;

private int action_code;

private int result_code;

private String violation_code;

private String violation_desc;

private String inspection_memo;

}
