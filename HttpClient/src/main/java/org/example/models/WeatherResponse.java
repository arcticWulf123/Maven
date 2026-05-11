package org.example.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherResponse {
    private String product;
    @SerializedName("dataseries")
    List <Forecast> forecasts = new ArrayList<>();

    public WeatherResponse(){}
    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public Forecast getForecastsString(int index) {
        return forecasts.get(index);
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

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
