package com.janoos.badi.perishablegoods;

/**
 * Created by bweru on 22/07/2018.
 */

public class Item {
    private String name, expire , gname;
    Double price;
    public Item(){

    }

    public Item(String n, String e, Double p, String gname){
        this.name = n;
        this.expire = e;
        this.price = p;
        this.gname = gname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.name = gname;
    }
}
