package com.vumee.ios_source;

import android.app.Application;

public class GlobalClass extends Application {
    private String urlcolleccted;
    private String custname;
    private String venuename;
    private String custmob;
    private String RepID;



    public String getUrlcolleccted() {
        return this.urlcolleccted;
    }

    public void setUrlcolleccted(final String urlcolleccted) {
        this.urlcolleccted = urlcolleccted;
    }

    public String getCustname() {
        return this.custname;
    }

    public void setCustname(final String custname) {
        this.custname = custname;
    }

    public String getVenuename() {
        return this.venuename;
    }

    public void setVenuename(final String venuename) {
        this.venuename = venuename;
    }

    public String getCustmob() {
        return this.custmob;
    }

    public void setCustmob(final String custmob) {
        this.custmob = custmob;
    }

    public String getRepID() {
        return this.RepID;
    }

    public void setRepID(final String RepID) {
        this.RepID = RepID;
    }







}
