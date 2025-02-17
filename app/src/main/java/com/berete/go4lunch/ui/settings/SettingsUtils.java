package com.berete.go4lunch.ui.settings;

import com.berete.go4lunch.domain.restaurants.models.Place;
import com.berete.go4lunch.domain.restaurants.models.Prediction;

public interface SettingsUtils {
  static String toFormattedWorkplaceSummary(Prediction workplacePrediction) {
    return workplacePrediction.getBestMatch() + "\n" + workplacePrediction.getRelatedText();
  }

  static String toFormattedWorkplaceSummary(Place workplace) {
    return workplace.getName() + "\n" + workplace.getAddress();
  }
}
