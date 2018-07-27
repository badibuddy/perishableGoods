package com.janoos.badi.perishablegoods;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.view.View;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SellerActivity extends Activity implements View.OnClickListener {
    private Bundle bundleFK;
    private Button bttnSave;
    private Intent intentFK;
    private String TxtSellerName, TxTGoods, TxtDate, txtPrice;
    private EditText TxtPrice, TxtExpDate, TxtGoodsName;
    DatabaseReference databaseReference;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        intentFK = getIntent();
        bundleFK = intentFK.getExtras();

        TxtGoodsName = findViewById(R.id.txtGoodName);
        TxtPrice = findViewById(R.id.txtPrice);
        TxtExpDate = findViewById(R.id.txtExpDate);
        bttnSave = findViewById(R.id.bttnSave);
        bttnSave.setOnClickListener(this);
        TxtSellerName = bundleFK.getString("sellername");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bttnSave:
                save();
                break;
        }
    }


    private void save() {
        initialize();
        TxtSellerName = bundleFK.getString("sellername");
        TxTGoods = TxtGoodsName.getText().toString().trim();
        TxtDate = TxtExpDate.getText().toString().trim();
        txtPrice = TxtPrice.getText().toString();
        GetGoods goods = new GetGoods();

        HashMap<String, String> goodsMap = new HashMap<String, String>();
        goodsMap.put("gname", TxTGoods);
        goodsMap.put("expdate", TxtDate);
        goodsMap.put("price", txtPrice);
        goodsMap.put("selername", TxtSellerName);

        databaseReference.push().setValue(goodsMap);

        Log.e("Adding user to DB", String.valueOf(goodsMap));
    }

    public void initialize() {

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("staging");

    }


}
