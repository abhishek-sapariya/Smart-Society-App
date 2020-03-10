package com.example.darshi.homescreen;

public class Report {
    private String resident,status,paidon;

    public Report()
    {

    }
    public Report(String resident,String status, String paidon)
    {
        this.resident = resident;
        this.paidon = paidon;
        this.status = status;

    }

    public String getResident() {
        return resident;
    }

    public void setResident(String resident) {
        this.resident = resident;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaidon() {
        return paidon;
    }

    public void setPaidon(String paidon) {
        this.paidon = paidon;
    }
}
