package com.janoos.badi.perishablegoods;

public class User {
    private String uname, fname, lname;
    private String passwd;
    private int type;

    //Use the empty constructor
    public User() {
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUname() {
        return uname;
    }

    public String getPasswd() {
        return passwd;
    }

    public int getType() {
        return type;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
