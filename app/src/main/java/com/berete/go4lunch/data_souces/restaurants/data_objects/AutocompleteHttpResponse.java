package com.berete.go4lunch.data_souces.restaurants.data_objects;

import com.berete.go4lunch.domain.restaurants.models.Prediction;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class AutocompleteHttpResponse {

  @SerializedName("predictions")
  @Expose
  public List<PredictionDataObject> predictionDataObjects = null;

  @SerializedName("status")
  @Expose
  public String status;

  public Prediction[] getPredictions() {
    if(!status.equals("OK")) return new Prediction[0];
    return predictionDataObjects.stream()
        .map(PredictionDataObject::toPrediction)
        .filter(Objects::nonNull)
        .toArray(Prediction[]::new);
  }
}

class PredictionDataObject {
  @SerializedName("description")
  @Expose
  public String description;

  @SerializedName("distance_meters")
  @Expose
  public Float distance_meters;

  @SerializedName("matched_substrings")
  @Expose
  public List<Matched_substring> matched_substrings = null;

  @SerializedName("place_id")
  @Expose
  public String place_id;

  @SerializedName("reference")
  @Expose
  public String reference;

  @SerializedName("structured_formatting")
  @Expose
  public Structured_formatting structured_formatting;

  @SerializedName("terms")
  @Expose
  public List<Term> terms = null;

  @SerializedName("types")
  @Expose
  public List<String> types = null;

  public Prediction toPrediction() {
    if (types.contains("restaurant")
        || types.contains("food")
        || types.contains("meal_takeaway")
        || types.contains("meal_delivery")) {
      return new Prediction(
          structured_formatting.main_text,
          structured_formatting.secondary_text,
          place_id,
          distance_meters);
    }
    return null;
  }
}

class Main_text_matched_substring {
  @SerializedName("length")
  @Expose
  public Integer length;

  @SerializedName("offset")
  @Expose
  public Integer offset;
}

class Matched_substring {
  @SerializedName("length")
  @Expose
  public Integer length;

  @SerializedName("offset")
  @Expose
  public Integer offset;
}

class Structured_formatting {
  @SerializedName("main_text")
  @Expose
  public String main_text;

  @SerializedName("main_text_matched_substrings")
  @Expose
  public List<Main_text_matched_substring> main_text_matched_substrings = null;

  @SerializedName("secondary_text")
  @Expose
  public String secondary_text;
}

class Term {
  @SerializedName("offset")
  @Expose
  public Integer offset;

  @SerializedName("value")
  @Expose
  public String value;
}
