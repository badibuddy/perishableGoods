package com.janoos.badi.perishablegoods;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class ListStagedActivity extends Activity {


    public String TxtSeller, TxtGoodName, TxtExpierDate, TxtCost, VwTxtGoodName, VwTxtSeller, VwTxtExpDate, VwTxtPrice;

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
                    TxtSeller = goodsData.child("uname_s").getValue().toString();




                    Log.d("gname as per db: ", TxtGoodName);
                    Log.d("cost as per db: ", TxtCost);
                    Log.d("exp as per db: ", TxtExpierDate);

                    HashMap<String, String> goodsListed = new HashMap<>();
                    goodsListed.put("goodname", TxtGoodName);
                    goodsListed.put("sellername", TxtSeller);
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

    }


    private void populateList() {

        ListAdapter adapter = new SimpleAdapter(this, stagedList, R.layout.list_staged,
                new String[]{"goodname", "sellername", "goodexpiry", "goodcost"},
                new int[]{R.id.Vw_gname,R.id.Vw_seller_name, R.id.Vw_expire, R.id.Vw_cost});
        mListView.setAdapter(adapter);
        mListView.setLongClickable(true);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view,
                                           final int position, final long id) {

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ListStagedActivity.this);
                    dialogBuilder.setTitle("Remove Items!");
                    dialogBuilder.setMessage("Do you want to add the item to goods sold?");
                    dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Toast.makeText(getApplicationContext(), "Adding "+ TxtGoodName
                                    , Toast.LENGTH_SHORT).show();

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Query queryref = databaseReference.child("staging").orderByChild("gname").equalTo(VwTxtGoodName).getRef();

                            queryref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot listedGoodsData : dataSnapshot.getChildren()) {
                                        TxtGoodName = listedGoodsData.child("gname").getValue().toString();
                                        Log.d("testing if : ", TxtGoodName);
                                        listedGoodsData.getRef().removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            Toast.makeText(getApplicationContext(), "Removed "+ TxtGoodName
                                    , Toast.LENGTH_SHORT).show();
                        }
                    });
                dialogBuilder.create().show();

                return true;
            }
        });



    }

    private void save() {
        initialize();

        HashMap<String, String> goodsMap = new HashMap<String, String>();
        goodsMap.put("gname", VwTxtGoodName);
        goodsMap.put("expdate", VwTxtExpDate);
        goodsMap.put("price", VwTxtPrice);
        goodsMap.put("uname_s", VwTxtSeller);

        databaseReference.push().child("goods").setValue(goodsMap);

        Log.e("Adding user to DB", String.valueOf(goodsMap));
    }

    public void initialize() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("staging");
    }


}
