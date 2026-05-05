package org.example.models;

public class Wind {
    private String direction;
    private int speed;

    public Wind () {}
    public Wind (String direction, int speed) {
        this.direction = direction;
        this.speed = speed;
    }
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
