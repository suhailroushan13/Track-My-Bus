package com.w8india.w8;

public class BusTimeParse {


    private String stop;
    private int time;
    private String location;
    public BusTimeParse(String stop, int time, String location){
        this.stop = stop;
        this.time = time;
        this.location = location;
    }
    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
