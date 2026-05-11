package org.example.models;

import com.google.gson.annotations.SerializedName;

public class Forecast {
    private int timepoint;
    private int temp2m;
    @SerializedName("wind10m")
    private Wind wind;

    public void setWind(Wind wind) {
        this.wind = wind;
    }
    public Forecast(int timepoint, int temp2m, Wind wind){
        this.timepoint = timepoint;
        this.temp2m = temp2m;
        this.wind = wind;
    }
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

    @Override
    public String toString(){
        return String.format("At hour %d: %d °C with %d speed winds from the %s.", timepoint, temp2m, wind.getSpeed(), wind.getDirection());
    }
}
