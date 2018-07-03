package com.mweslacey.city.foodfail.model.pojo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import com.mweslacey.city.foodfail.model.entity.Facility;
import com.mweslacey.city.foodfail.model.entity.Inspection;
import java.util.List;

public class FacilityAndAllInspections {

  public FacilityAndAllInspections() {

  }

  @Embedded
  private Facility facility;

  @Relation(parentColumn = "facility_key", entityColumn = "facility_id")
  private List<Inspection> inspections;

  public Facility getFacility() {
    return facility;
  }

  public void setFacility(Facility facility) {
    this.facility = facility;
  }

  public List<Inspection> getInspections() {
    return inspections;
  }

  public void setInspections(
      List<Inspection> inspections) {
    this.inspections = inspections;
  }
}
