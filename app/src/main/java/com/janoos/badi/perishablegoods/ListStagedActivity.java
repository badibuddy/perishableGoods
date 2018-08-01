package com.janoos.badi.perishablegoods;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class ListStagedActivity extends AppCompatActivity {


    private ArrayList<Item> listItems;
    public static final String FIRST_COLUMN = "First";
    public static final String SECOND_COLUMN = "Second";
    public static final String THIRD_COLUMN = "Third";
    public static final String FOURTH_COLUMN = "Fourth";
    public String TxtSeller, TxtGoodName, TxtExpierDate, TxtCost;
    DatabaseReference databaseReference;
    ListView mListView;
    FirebaseDatabase database;
    ArrayList<String> mListgoods = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_staged);
        initialize();
        populateList();


        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot userData : dataSnapshot.getChildren()) {

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
        });


    }

    private void populateList() {

        mListView = findViewById(R.id.listView1);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.id., mListgoods);
        mListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

    }

    public void initialize() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("staging");
    }
}
