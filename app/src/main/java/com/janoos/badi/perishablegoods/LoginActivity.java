package com.janoos.badi.perishablegoods;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText nTxtUserSignIn, nTxtPassSignIn;
    private TextView nVwNewSignin, nVwForgotSignIn;
    private Button bttnSignIn;
    private ProgressBar SignInProgress;
    public String TxtPassword, TxtUserName, TxtSellerName, DBUsername, DBPassword;
    private boolean doubleBackToExitPressedOnce;
    private Bundle bundleFK;
    private Intent intentFK;
    DatabaseReference databaseReference;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
        nTxtUserSignIn = findViewById(R.id.txtUserSignIn);
        nTxtPassSignIn = findViewById(R.id.txtPassSignIn);
        nVwNewSignin = findViewById(R.id.txtNewSignIn);
        nVwForgotSignIn = findViewById(R.id.txtForgotSignIn);
        bttnSignIn = findViewById(R.id.bttnSignIn);
        SignInProgress = findViewById(R.id.signInBar);
        nVwForgotSignIn.setOnClickListener(this);
        nVwNewSignin.setOnClickListener(this);
        bttnSignIn.setOnClickListener(this);
    }

    public void initialize() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("users");
    }

    @Override

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            //finish();
            //System.exit(1);
            return;
        }

        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bttnSignIn:
                process();
                break;
            case R.id.txtNewSignIn:
                sendToReg();
                break;
            case R.id.txtForgotSignIn:
                break;
        }
    }

    private void sendToReg() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    private void process() {
        View view = null;
        Boolean cancel = false;


        if (TextUtils.isEmpty(nTxtUserSignIn.getText().toString())) {
            nTxtUserSignIn.setError("This field must be filled");
            view = nTxtUserSignIn;
            cancel = true;
        }

        if (TextUtils.isEmpty(nTxtPassSignIn.getText().toString())) {
            nTxtPassSignIn.setError("This field must be filled");
            view = nTxtPassSignIn;
            cancel = true;

        } else if (nTxtPassSignIn.getText().toString().length() < 6) {
            nTxtPassSignIn.setError("Password must be at least 6 characters");
            view = nTxtPassSignIn;
            cancel = true;
        }

//        SignInProgress.setVisibility(View.VISIBLE);

        if (cancel) {
            view.requestFocus();
        } else {
            VerifyCredentials(nTxtUserSignIn.getText().toString(), nTxtPassSignIn.getText().toString());
        }
    }

    protected void VerifyCredentials(final String TxtUserName, final String TxtPassword) {
//        final List<User> userList = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userData : dataSnapshot.getChildren()) {

//                    String stage = dataSnapshot.child("staging").child(key).child("gname").getValue(String.class);
                    DBUsername = userData.child("uname").getValue().toString();
                    DBPassword = userData.child("passwd").getValue().toString();

                    User c = userData.getValue(User.class);
//                    Log.d("Staged goods are: ", stage);
                    Log.d("username as per db: ", DBUsername);
                    Log.d("password as per db: ", DBPassword);

                    Log.d("User Details:: ", "User Name: " + c.getUname() + " " + "Password: " + c.getPasswd()
                            + " " + "UserType:" + c.getType());
                    String txtUsername = c.getUname();
                    String passvalue = c.getPasswd();
                    String TypeValue = String.valueOf(c.getType());


                    if ( TxtPassword.equals(passvalue) && TypeValue.equals("2")) {
                        Toast.makeText(LoginActivity.this, "Welcome: " + TxtUserName, Toast.LENGTH_SHORT).show();
                            Intent buyerIntent = new Intent(LoginActivity.this, BuyerActivity.class);
                            startActivity(buyerIntent);
                    } else if (TxtPassword.equals(passvalue) && TypeValue.equals("1")) {
                            Intent sellerIntent = new Intent(LoginActivity.this, SellerActivity.class);
                            Bundle bundleAdmin = new Bundle();
                            bundleAdmin.putString("username", txtUsername);
                            sellerIntent.putExtras(bundleAdmin);
                            startActivity(sellerIntent);
                     } else if (TxtPassword.equals(passvalue) && TypeValue.equals("3")) {
                            Intent sellerIntent = new Intent(LoginActivity.this, ListStagedActivity.class);
                            Bundle bundleAdmin = new Bundle();
                            bundleAdmin.putString("username", txtUsername);
                            sellerIntent.putExtras(bundleAdmin);
                            startActivity(sellerIntent);
                     } else {
                            Toast.makeText(LoginActivity.this, "Invalid credentials,try again.", Toast.LENGTH_SHORT).show();

                    }

                }




            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}

