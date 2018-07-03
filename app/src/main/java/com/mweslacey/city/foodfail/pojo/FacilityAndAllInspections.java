package com.mweslacey.city.foodfail.pojo;

import android.arch.persistence.room.Embedded;
import com.mweslacey.city.foodfail.model.entity.Facility;
import com.mweslacey.city.foodfail.model.entity.Inspection;
import java.util.List;

public class FacilityAndAllInspections {


  @Embedded
  public Facility facility;

  @Embedded
  public List<Inspection> inspections;

}
