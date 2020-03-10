package com.example.darshi.homescreen;

public class ComplaintMaster {
    String complaint_description,complaint_uploaded_on;

    ComplaintMaster(){}

    public String getComplaint_description() {
        return complaint_description;
    }

    public void setComplaint_description(String complaint_description) {
        this.complaint_description = complaint_description;
    }

    public String getComplaint_uploaded_on() {
        return complaint_uploaded_on;
    }

    public void setComplaint_uploaded_on(String complaint_uploaded_on) {
        this.complaint_uploaded_on = complaint_uploaded_on;
    }
}
