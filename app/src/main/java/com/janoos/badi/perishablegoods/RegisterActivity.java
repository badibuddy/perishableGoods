package com.janoos.badi.perishablegoods;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private Button SaveButtn, CancelButtn;
    private EditText nTXTUserName, nTxtPasswd, nTxtPasswd2,nTXTFirstName, nTXTLastName;
    private RadioButton buyerType, sellerType;
    private ProgressBar SignInProgress;
    private String TxtPassword, TxtPassword2, TxtUserName, userTypeValue, TxtFirstname, TxtLastname;
    private Integer TxtUserType;
    DatabaseReference databaseReference;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
        nTXTUserName = findViewById(R.id.txtUserName);
        nTXTFirstName = findViewById(R.id.txtFirstName);
        nTXTLastName = findViewById(R.id.txtLastName);
        nTxtPasswd = findViewById(R.id.txtRegPass);
        nTxtPasswd2 = findViewById(R.id.txtRegPassConfirm);
        SaveButtn = findViewById(R.id.bttnReg);
        CancelButtn = findViewById(R.id.bttnCancelReg);
        SaveButtn.setOnClickListener(this);
        CancelButtn.setOnClickListener(this);
        buyerType = findViewById(R.id.radio_customer);
        sellerType = findViewById(R.id.radio_seller);
        buyerType.setOnClickListener(this);
        sellerType.setOnClickListener(this);

    }
    public void initialize() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("users");
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bttnReg:
                regUser(view);
                break;
            case R.id.bttnCancelReg:
                clearData();
                break;
            case R.id.radio_customer:
                buyerType.setChecked(true);
                sellerType.setChecked(false);
                TxtUserType = 2;
                break;
            case R.id.radio_seller:
                sellerType.setChecked(true);
                buyerType.setChecked(false);
                TxtUserType = 1;
                break;
            default:
                TxtUserType = 3;
                break;

        }
    }
    public void clearData() {
        nTXTUserName.setText("");
        nTxtPasswd.setText("");
        nTxtPasswd2.setText("");
        nTXTFirstName.setText("");
        nTXTLastName.setText("");

        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }
    public void regUser(View view) {

        TxtPassword = nTxtPasswd.getText().toString().trim();
        TxtUserName = nTXTUserName.getText().toString().trim();
        TxtFirstname = nTXTFirstName.getText().toString().trim();
        TxtPassword2 = nTxtPasswd2.getText().toString().trim();
        TxtLastname = nTXTLastName.getText().toString().trim();


        Log.i("Passed user type", String.valueOf(TxtUserType));
        if (TxtUserName.isEmpty()) {
            nTXTUserName.setError("Username is required");
        } else if (TxtFirstname.isEmpty()){
            nTXTFirstName.setError("First name is required");
        }else if (TxtLastname.isEmpty()){
            nTXTLastName.setError("Last name is required");
        }else if (TxtPassword.isEmpty()){
            nTxtPasswd.setError("Password is required");
        }else if (TxtPassword.length() < 6) {
            nTxtPasswd.setError ("Password must be at least 6 characters");
        }else if (!TxtPassword.equals(TxtPassword2)) {
            nTxtPasswd.setError("Passwords provided do not match. Try again");
        } else {
            try {
                final String uname = TxtUserName;
                final String passwd = TxtPassword;

                User myUser = new User();
                myUser.setUname(uname);
                myUser.setPasswd(passwd);
                myUser.setFname(TxtFirstname);
                myUser.setLname(TxtLastname);
                myUser.setType(TxtUserType);
                databaseReference.push().setValue(myUser);
                Log.e("Adding user to DB", String.valueOf(myUser));
                Toast.makeText(RegisterActivity.this, "Please login to proceed.", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);

            } catch (Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                }
            }

        }
}
