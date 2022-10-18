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

    ;
    Button btnDate, btnNextSecond;
    TextInputEditText etAddress, etMobile, etDate;
    TextInputLayout tilAddress, tilMobile, tilDate;
    String age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);

        etDate = (TextInputEditText) findViewById(R.id.etDate);
        etAddress = (TextInputEditText) findViewById(R.id.etEmailAddress);
        etMobile = (TextInputEditText) findViewById(R.id.etMobile);

        tilDate = (TextInputLayout) findViewById(R.id.tilDate);
        tilAddress = (TextInputLayout) findViewById(R.id.tilEmailAddress);
        tilMobile = (TextInputLayout) findViewById(R.id.tilPasswordd);

        btnDate = (Button) findViewById(R.id.btnDate);

        //Calendar (Date of Birth)
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(Register_second.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month+1;
                        String date = month+"/"+dayOfMonth+"/"+year;
                        etDate.setText(date);

                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        age = Integer.toString(calculateAge(c.getTimeInMillis()));
//                        int a = Integer.parseInt(age.toString());
//                        if (a < 18){
//                            tilDate.setError("18 below is not allowed");
//                            requestFocus(etDate);
//                        } else {
//                            tilDate.setErrorEnabled(false);
//                        }

                    }
                }, year, month, day);
                dialog.show();
            }
        });
        //End Calendar

        btnNextSecond = (Button) findViewById(R.id.btnNextSecond);
        btnNextSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent third = new Intent(Register_second.this, Register.class);
            }
        });

    }

    public void nextForm2(){
//        String firstName = getIntent().getStringExtra("firstName");
//        String lastName = getIntent().getStringExtra("lastName");
//        String address = etAddress.getText().toString().trim();
//        String mobile = etMobile.getText().toString().trim();
//        String dob = etDate.getText().toString().trim();
        Intent third = new Intent(Register_second.this, Register_third.class);
//        third.putExtra("firstName", firstName);
//        third.putExtra("lastName", lastName);
//        third.putExtra("address", address);
//        third.putExtra("mobile", mobile);
//        third.putExtra("dob", dob);
//        third.putExtra("age", age);
        startActivity(third);
        Toast.makeText(this, "New Form", Toast.LENGTH_SHORT).show();
    }

    private boolean validateAddress(){
        String address = etAddress.getText().toString().trim();
        if (address.isEmpty()){
            tilAddress.setError("Field can't be empty!");
            requestFocus(etAddress);
            return false;
        } else {
            if(address.length() <= 5){
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
            if(mobile.length() < 10){
                tilMobile.setError("Mobile number is too short!");
                requestFocus(etMobile);
                return false;
            } else {
                tilMobile.setErrorEnabled(false);
            }
        }
        return true;
    }
    private boolean validateDate(){
        String date = etDate.getText().toString().trim();
        if (date.isEmpty()){
            tilDate.setError("Field can't be empty!");
            requestFocus(etDate);
            return false;
        } else {
            tilDate.setErrorEnabled(false);
        }
        return true;
    }

    public int calculateAge(long date){
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);

        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
            age--;
        }

        return age;
    }

    public void requestFocus(View view){
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}