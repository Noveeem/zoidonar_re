package com.example.zoidonar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    Button btnNext;
    TextInputEditText etFN, etLN;
    TextInputLayout tilFN, tilLN;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //TextInputEditText
        etFN = (TextInputEditText) findViewById(R.id.etFN);
        etLN = (TextInputEditText) findViewById(R.id.etLN);

        //TextInputLayout
        tilFN = (TextInputLayout) findViewById(R.id.tilFN);
        tilLN = (TextInputLayout) findViewById(R.id.tilLN);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() !=null)
        {
            finish();
            return;
        }

        btnNext = (Button) findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateFN() | !validateLN())
                {
                    return;
                }
                nextForm();
            }
        });


    }

    public void nextForm(){
        String firstName = etFN.getText().toString().trim();
        String lastName = etLN.getText().toString().trim();
        Intent second = new Intent(Register.this, Register_second.class);
        second.putExtra("firstName", firstName);
        second.putExtra("lastName", lastName);
        startActivity(second);
    }

    //Validate FirstName
    private boolean validateFN(){
        String firstName = etFN.getText().toString().trim();

        if (firstName.isEmpty()) {
            tilFN.setError("Field can't be empty!");
            requestFocus(etLN);
            return false;
        } else {
            if (firstName.length() <= 2){
                tilFN.setError("First name is too short!");
                requestFocus(etLN);
                return false;
            } else {
                tilFN.setErrorEnabled(false);
            }
        }
        return true;
    }

    //Validate LastName
    private boolean validateLN(){
        String lastName = etLN.getText().toString().trim();

        if (lastName.isEmpty()) {
            tilLN.setError("Field can't be empty!");
            requestFocus(etLN);
            return false;
        } else {
            if (lastName.length() <= 2){
                tilLN.setError("Last name is too short!");
                requestFocus(etLN);
                return false;
            } else {
                tilLN.setErrorEnabled(false);
            }
        }
        return true;
    }

    public void requestFocus(View view){
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}