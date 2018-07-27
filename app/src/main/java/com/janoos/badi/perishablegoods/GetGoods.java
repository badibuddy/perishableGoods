package com.janoos.badi.perishablegoods;

import java.util.Date;

public class GetGoods {

    String goodsName;
    String date,TxtSellerName;
    Double price;

    public String getGoodsName() {
        return goodsName;
    }

    public String getDate() {
        return date;
    }

    public Double getPrice() {
        return price;
    }

    public String getTxtSellerName() {
        return TxtSellerName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setTxtSellerName(String txtSellerName) {
        TxtSellerName = txtSellerName;
    }
}
