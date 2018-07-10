package com.mweslacey.city.foodfail.model.entity;

import static android.arch.persistence.room.ForeignKey.CASCADE;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.ColorLong;
import android.support.annotation.NonNull;
import com.google.gson.annotations.Expose;

@Entity(tableName = "inspections",   foreignKeys = @ForeignKey(entity = Facility.class,
/* if a facility is removed all inspection*/  parentColumns = "facility_key",
/* inspection records for that facility   */  childColumns = "facility_id",
/* should be removed.                     */  onDelete = CASCADE))

public class Inspection {

  public Inspection() {

  }

  @NonNull
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "inspection_id")
  private long inspectionID;

  @ColumnInfo(index = true, name = "facility_id") // allows db to locate records much faster
  private int facilityID;

  @ColumnInfo(name = "inspection_date")
  private String inspectionDate;

  @ColumnInfo(name = "result_code")
  private int resultCode;

  @ColumnInfo(name = "result_desc")
  private String resultDesc;

  @ColumnInfo(name = "violation_code")
  private String violationCode;

  @ColumnInfo(name = "violation_desc")
  private String violationDesc;

  @ColumnInfo(name = "inspection_memo")
  private String inspectionMemo;

  @Ignore
  @Expose(deserialize = true, serialize = false)
  private Long longitude;

  @Ignore
  @Expose(deserialize = true, serialize = false)
  private Long latitude;

  public Long getLongitude() {
    return longitude;
  }

  public void setLongitude(Long longitude) {
    this.longitude = longitude;
  }

  public Long getLatitude() {
    return latitude;
  }

  public void setLatitude(Long latitude) {
    this.latitude = latitude;
  }

  @NonNull
  public long getInspectionID() {
    return inspectionID;
  }

  public void setInspectionID(@NonNull long inspectionID) {
    this.inspectionID = inspectionID;
  }

  public int getFacilityID() {
    return facilityID;
  }

  public void setFacilityID(int facilityID) {
    this.facilityID = facilityID;
  }

  public String getInspectionDate() {
    return inspectionDate;
  }

  public void setInspectionDate(String inspectionDate) {
    this.inspectionDate = inspectionDate;
  }

  public int getResultCode() {
    return resultCode;
  }

  public void setResultCode(int resultCode) {
    this.resultCode = resultCode;
  }

  public String getViolationCode() {
    return violationCode;
  }

  public void setViolationCode(String violationCode) {
    this.violationCode = violationCode;
  }

  public String getViolationDesc() {
    return violationDesc;
  }

  public void setViolationDesc(String violationDesc) {
    this.violationDesc = violationDesc;
  }

  public String getInspectionMemo() {
    return inspectionMemo;
  }

  public void setInspectionMemo(String inspectionMemo) {
    this.inspectionMemo = inspectionMemo;
  }

  public String getResultDesc() {
    return resultDesc;
  }

  public void setResultDesc(String resultDesc) {
    this.resultDesc = resultDesc;
  }
}
