package com.mweslacey.city.foodfail.model.entity;

import static android.arch.persistence.room.ForeignKey.CASCADE;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Inspections entity/table definition for database.
 */
@Entity(tableName = "inspections", foreignKeys = @ForeignKey(entity = Facility.class,
/* if a facility is removed all inspection*/  parentColumns = "facility_key",
/* inspection records for that facility   */  childColumns = "facility_id",
/* should be removed.                     */  onDelete = CASCADE))
public class Inspection {

  /**
   * Empty constructor utilized by Room.
   */
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

  /**
   * Get inspection ID.
   * @return long inspection ID
   */
  @NonNull
  public long getInspectionID() {
    return inspectionID;
  }

  /**
   * Set inspection ID.
   * @param inspectionID
   */
  public void setInspectionID(@NonNull long inspectionID) {
    this.inspectionID = inspectionID;
  }

  /**
   * Get facility ID.
   * @return int facility ID
   */
  public int getFacilityID() {
    return facilityID;
  }

  /**
   * Set facility ID.
   * @param facilityID
   */
  public void setFacilityID(int facilityID) {
    this.facilityID = facilityID;
  }

  /**
   * Get inspection date.
   * @return String inspection date
   */
  public String getInspectionDate() {
    return inspectionDate;
  }

  /**
   * Set inspection date.
   * @param inspectionDate
   */
  public void setInspectionDate(String inspectionDate) {
    this.inspectionDate = inspectionDate;
  }

  /**
   * Get inspection result code.
   * @return int result code.
   */
  public int getResultCode() {
    return resultCode;
  }

  /**
   * Set inspection result code.
   * @param resultCode
   */
  public void setResultCode(int resultCode) {
    this.resultCode = resultCode;
  }

  /**
   * Get violation code.
   * @return String violation code
   */
  public String getViolationCode() {
    return violationCode;
  }

  /**
   * Set violation code.
   * @param violationCode
   */
  public void setViolationCode(String violationCode) {
    this.violationCode = violationCode;
  }

  /**
   * Get violation code.
   * @return String violation code.
   */
  public String getViolationDesc() {
    return violationDesc;
  }

  /**
   * Set violation code.
   * @param violationDesc
   */
  public void setViolationDesc(String violationDesc) {
    this.violationDesc = violationDesc;
  }

  /**
   * Get inspection memorandum.
   * @return String inspection memorandum
   */
  public String getInspectionMemo() {
    return inspectionMemo;
  }

  /**
   * Set inspection memorandum.
   * @param inspectionMemo
   */
  public void setInspectionMemo(String inspectionMemo) {
    this.inspectionMemo = inspectionMemo;
  }

  /**
   * Get inspection result description.
   * @return String result description
   */
  public String getResultDesc() {
    return resultDesc;
  }

  /**
   * Set inspection result description.
   * @param resultDesc
   */
  public void setResultDesc(String resultDesc) {
    this.resultDesc = resultDesc;
  }
}
