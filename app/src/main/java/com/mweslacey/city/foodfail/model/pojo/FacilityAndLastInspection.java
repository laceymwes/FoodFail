package com.mweslacey.city.foodfail.model.pojo;

import android.arch.persistence.room.Embedded;
import com.mweslacey.city.foodfail.model.entity.Facility;
import com.mweslacey.city.foodfail.model.entity.Inspection;

public class FacilityAndLastInspection {

  public FacilityAndLastInspection() {

  }

  @Embedded
  private Facility facility;

  @Embedded
  private Inspection inspection;

  public Inspection getInspection() {
    return inspection;
  }

  public Facility getFacility() {
    return facility;
  }

  public void setFacility(Facility facility) {
    this.facility = facility;
  }

  public void setInspection(Inspection inspection) {
    this.inspection = inspection;
  }

  public String getName() {
    return facility.getFacilityName();
  }

  public String getAddress() {
    return facility.getStreetNumber() + " " + facility.getStreetName();
  }

  public String getLastInspection() {
    return inspection.getInspectionDate().replace("0:00", "") + " " + inspection.getResultDesc();
  }
}
