package com.sila.flight_app.Model;

import java.util.ArrayList;
import java.util.List;

public class UserFlights {

    private String startDate;
    private String endDate;
    private String ffrom;
    private String tto;
    private String flihtsId;
    private String flyName;
    private String firstPilot;
    private String secondPilot;
    private String flyTime;
    private List<Locations> userFlightsList = null;

    public UserFlights(String fligtsId, String ffrom, String tto, String startDate, String endDate, String flyName, String firstPilot, String secondPilot, String flyTime, ArrayList<Locations> myList_1) {
        this.flihtsId = fligtsId;
        this.ffrom = ffrom;
        this.tto = tto;
        this.startDate = startDate;
        this.endDate = endDate;
        this.flyName = flyName;
        this.firstPilot = firstPilot;
        this.secondPilot = secondPilot;
        this.flyTime = flyTime;
        this.userFlightsList = myList_1;
    }


    public UserFlights() {
    }

    public String getFlihtsId() {
        return flihtsId;
    }

    public void setFlihtsId(String flihtsId) {
        this.flihtsId = flihtsId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFfrom() {
        return ffrom;
    }

    public void setFfrom(String ffrom) {
        this.ffrom = ffrom;
    }

    public String getTto() {
        return tto;
    }

    public void setTto(String tto) {
        this.tto = tto;
    }

    public String getFlyName() {
        return flyName;
    }

    public void setFlyName(String flyName) {
        this.flyName = flyName;
    }

    public String getFirstPilot() {
        return firstPilot;
    }

    public void setFirstPilot(String firstPilot) {
        this.firstPilot = firstPilot;
    }

    public String getSecondPilot() {
        return secondPilot;
    }

    public void setSecondPilot(String secondPilot) {
        this.secondPilot = secondPilot;
    }

    public String getFlyTime() {
        return flyTime;
    }

    public void setFlyTime(String flyTime) {
        this.flyTime = flyTime;
    }

    public List<Locations> getUserFlightsList() {
        return userFlightsList;
    }

    public void setUserFlightsList(List<Locations> userFlightsList) {
        this.userFlightsList = userFlightsList;
    }
}
