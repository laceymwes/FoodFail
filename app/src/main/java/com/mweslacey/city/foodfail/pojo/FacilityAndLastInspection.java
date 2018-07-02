package com.mweslacey.city.foodfail.pojo;

import android.arch.persistence.room.Embedded;
import com.mweslacey.city.foodfail.model.entity.Facility;
import com.mweslacey.city.foodfail.model.entity.Inspection;

public class FacilityAndLastInspection {

  @Embedded
  public Facility facility;

  @Embedded
  public Inspection inspection;

}
