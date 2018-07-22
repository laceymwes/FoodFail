package com.mweslacey.city.foodfail;

import android.app.Application;
import com.facebook.stetho.Stetho;

/**
 * Allows data and network inspection of Food Fail application during run-time.
 */
public class FoodFailApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
  }
}
