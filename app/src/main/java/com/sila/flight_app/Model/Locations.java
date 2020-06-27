package com.sila.flight_app.Model;

public class Locations {
    private String latitude;
    private String longitude;
    private String time;

    public Locations(String latitude, String longitude, String time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Locations [latitude=" + latitude + ", longitude=" + longitude + "]";
    }
}
