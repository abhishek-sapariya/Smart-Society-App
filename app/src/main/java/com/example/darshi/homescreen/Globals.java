package com.example.darshi.homescreen;

import android.content.SharedPreferences;

import java.util.ArrayList;

public class Globals {

    private static Globals instance;
    private String sname,hm_blockno,hm_flatno,hm_resname,hm_members,hm_usertype,reportnamex,lastcreatedreport,amt,exp;
    private  String global_receipt_title,global_addline1,global_addline2,global_addline3,pincode;
    ArrayList<String> votedon = new ArrayList<>();


    private Globals() {
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getGlobal_receipt_title() {
        return global_receipt_title;
    }

    public void setGlobal_receipt_title(String global_receipt_title) {
        this.global_receipt_title = global_receipt_title;
    }

    public String getGlobal_addline1() {
        return global_addline1;
    }

    public void setGlobal_addline1(String global_addline1) {
        this.global_addline1 = global_addline1;
    }

    public String getGlobal_addline2() {
        return global_addline2;
    }

    public void setGlobal_addline2(String global_addline2) {
        this.global_addline2 = global_addline2;
    }

    public String getGlobal_addline3() {
        return global_addline3;
    }

    public void setGlobal_addline3(String global_addline3) {
        this.global_addline3 = global_addline3;
    }

    public ArrayList<String> getVotedon() {
        return votedon;
    }

    public void setVotedon(ArrayList<String> votedon) {
        this.votedon = votedon;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getHm_blockno() {
        return hm_blockno;
    }

    public void setHm_blockno(String hm_blockno) {
        this.hm_blockno = hm_blockno;
    }

    public String getHm_flatno() {
        return hm_flatno;
    }

    public void setHm_flatno(String hm_flatno) {
        this.hm_flatno = hm_flatno;
    }

    public String getHm_resname() {
        return hm_resname;
    }

    public void setHm_resname(String hm_resname) {
        this.hm_resname = hm_resname;
    }

    public String getHm_members() {
        return hm_members;
    }

    public void setHm_members(String hm_members) {
        this.hm_members = hm_members;
    }

    public String getHm_usertype() {
        return hm_usertype;
    }

    public void setHm_usertype(String hm_usertype) {
        this.hm_usertype = hm_usertype;
    }

    public String getReportnamex() {
        return reportnamex;
    }

    public void setReportnamex(String reportnamex) {
        this.reportnamex = reportnamex;
    }

    public String getLastcreatedreport() {
        return lastcreatedreport;
    }

    public void setLastcreatedreport(String lastcreatedreport) {
        this.lastcreatedreport = lastcreatedreport;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public static synchronized Globals getInstance() {
        if (instance == null) {
            instance = new Globals();
        }
        return instance;
    }

}
