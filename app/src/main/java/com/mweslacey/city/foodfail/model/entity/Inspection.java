package com.mweslacey.city.foodfail.model.entity;

import static android.arch.persistence.room.ForeignKey.CASCADE;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(foreignKeys = @ForeignKey(            entity = Facility.class,
/* if a facility is removed all inspection*/  parentColumns = "facilityID",
/* inspection records for that facility   */  childColumns = "facilityID",
/* should be removed.                     */  onDelete = CASCADE))

public class Inspection {

@NonNull
@PrimaryKey(autoGenerate = true)
private long inspectionID;

private int facilityID;

private long inspectionDate;

private String inspectionType;

private int actionCode;

private int resultCode;

private String violationCode;

private String violationDesc;

private String inspectionMemo;


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

  public long getInspectionDate() {
    return inspectionDate;
  }

  public void setInspectionDate(long inspectionDate) {
    this.inspectionDate = inspectionDate;
  }

  public String getInspectionType() {
    return inspectionType;
  }

  public void setInspectionType(String inspectionType) {
    this.inspectionType = inspectionType;
  }

  public int getActionCode() {
    return actionCode;
  }

  public void setActionCode(int actionCode) {
    this.actionCode = actionCode;
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


}
