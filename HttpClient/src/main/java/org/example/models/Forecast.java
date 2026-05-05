package org.example.models;

import com.google.gson.annotations.SerializedName;

public class Forecast {
    private int timepoint;
    private int temp2m;
    @SerializedName("wind10m")
    private Wind wind;

    public Forecast(){}
    public int getTimepoint() {
        return timepoint;
    }

    public void setTimepoint(int timepoint) {
        this.timepoint = timepoint;
    }

    public int getTemp2m() {
        return temp2m;
    }

    public void setTemp2m(int temp2m) {
        this.temp2m = temp2m;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
