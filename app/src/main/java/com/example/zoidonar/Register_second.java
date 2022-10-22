package com.example.zoidonar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class Register_second extends AppCompatActivity {

    TextInputLayout tilAddress, tilMobile, tilDob;
    TextInputEditText etAddress, etMobile, etDob;

    Button btnThirdNext;
    Button btnDate;

    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);


        //TIL
        tilAddress = (TextInputLayout) findViewById(R.id.tilEmail);
        tilMobile = (TextInputLayout) findViewById(R.id.tilMobile);
        tilDob = (TextInputLayout) findViewById(R.id.tilDob);

        //et
        etAddress = (TextInputEditText) findViewById(R.id.etAddress);
        etMobile = (TextInputEditText) findViewById(R.id.etMobile);
        etDob = (TextInputEditText) findViewById(R.id.etDob);

        //Buttons
        btnThirdNext = (Button) findViewById(R.id.btnCreate);
        btnDate = (Button) findViewById(R.id.btnDate);


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(Register_second.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = month+"/"+dayOfMonth+"/"+year;
                        etDob.setText(date);

                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, day);
                        age = calculateAge(c.getTimeInMillis());
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        btnThirdNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateAddress() | !validateMobile() | !validateDate()){ return;}
                thirdForm();
            }
        });

    }

    public void thirdForm(){
        Intent i = getIntent();
        String firstName = i.getStringExtra("firstName");
        String lastName = i.getStringExtra("lastName");
        String address = etAddress.getText().toString();
        String mobile = etMobile.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        int userAge = age;
        Intent third = new Intent(Register_second.this, Register_third.class);
        third.putExtra("firstName", firstName);
        third.putExtra("lastName", lastName);
        third.putExtra("Address", address);
        third.putExtra("Mobile", mobile);
        third.putExtra("Dob", dob);
        third.putExtra("Age", userAge);
        startActivity(third);
    }

    private boolean validateAddress(){
        String address = etAddress.getText().toString().trim();

        if (address.isEmpty()){
            tilAddress.setError("Field can't be empty!");
            requestFocus(etAddress);
            return false;
        } else {
            if (address.length() < 5)
            {
                tilAddress.setError("Address is too short!");
                requestFocus(etAddress);
                return false;
            } else {
                tilAddress.setErrorEnabled(false);
            }
        }
        return true;
    }

    private boolean validateMobile(){
        String mobile = etMobile.getText().toString().trim();

        if (mobile.isEmpty()){
            tilMobile.setError("Field can't be empty!");
            requestFocus(etMobile);
            return false;
        } else {
            if (mobile.length() < 10){
                tilMobile.setError("Mobile is too short!");
                requestFocus(etMobile);
                return false;
            } else {
                tilMobile.setErrorEnabled(false);
            }
        }
        return true;
    }

    private boolean validateDate(){
        String date = etDob.getText().toString().trim();

        if (date.isEmpty()){
            tilDob.setError("Field can't be empty!");
            requestFocus(etDob);
            return false;
        } else {
            if (age < 18) {
                tilDob.setError("Below 18 is not allowed!");
                requestFocus(etDob);
                return false;
            } else {
                tilDob.setErrorEnabled(false);
            }
        }
        return true;
    }

    int calculateAge(long date){
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);

        Calendar today = Calendar.getInstance();

        int a = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if(today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH))
        {
            a--;
        }
        return a;
    }

    public void requestFocus(View view){
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}