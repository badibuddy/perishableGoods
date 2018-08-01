package com.janoos.badi.perishablegoods;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class ListStagedActivity extends AppCompatActivity {


    public String TxtSeller, TxtGoodName, TxtExpierDate, TxtCost;
    DatabaseReference databaseReference;
    ListView mListView;
    FirebaseDatabase database;
    ArrayList<HashMap<String, String>> stagedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_staged);
        mListView = findViewById(R.id.staging_list);
        stagedList = new ArrayList<>();
        initialize();

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot goodsData : dataSnapshot.getChildren()) {
                    TxtGoodName = goodsData.child("gname").getValue().toString();
                    TxtCost = goodsData.child("price").getValue().toString();
                    TxtExpierDate = goodsData.child("expdate").getValue().toString();

                    Log.d("gname as per db: ", TxtGoodName);
                    Log.d("cost as per db: ", TxtCost);
                    Log.d("exp as per db: ", TxtExpierDate);

                    HashMap<String, String> goodsListed = new HashMap<>();
                    goodsListed.put("goodname", TxtGoodName);
                    goodsListed.put("goodcost", TxtCost);
                    goodsListed.put("goodexpiry", TxtExpierDate);


                    stagedList.add(goodsListed);
                }
                populateList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
         });


        /*databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot userData : dataSnapshot.getChildren()) {
                    Log.d("gname as per db: ", String.valueOf(userData.child("gname")));
                    TxtGoodName = userData.child("gname").getValue().toString();
                    TxtCost = userData.child("price").getValue().toString();
                    TxtExpierDate = userData.child("expdate").getValue().toString();

                    Log.d("gname as per db: ", TxtGoodName);
                    Log.d("cost as per db: ", TxtCost);
                    Log.d("exp as per db: ", TxtExpierDate);

                    mListgoods.add(TxtGoodName);
                    mListgoods.add(TxtExpierDate);
                    mListgoods.add(TxtCost);


                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                
            }
        });*/


    }

    private void populateList() {


        ListAdapter adapter = new SimpleAdapter(this, stagedList,R.layout.list_staged,
                new String[]{"goodname", "goodexpiry", "goodcost"},
                new int[]{R.id.Vw_gname, R.id.Vw_expire, R.id.Vw_cost});
        mListView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();

    }

    public void initialize() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("staging");
    }
}
