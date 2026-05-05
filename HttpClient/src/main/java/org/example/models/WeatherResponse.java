package org.example.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherResponse {
    private String product;
    @SerializedName("dataseries")
    List <Forecast> forecasts = new ArrayList<>();

    public WeatherResponse(String product, List<Forecast> forecasts) {
        this.product = product;
        this.forecasts = forecasts;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
