package com.example.zoidonar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity  {

    BottomNavigationView bottomNavigationView;
    ProfileFragment profileFragment = new ProfileFragment();
    HomeFragment homeFragment = new HomeFragment();
    MedicalForm medicalFragment = new MedicalForm();
    FloatingActionButton iconForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser == null) {
            Intent relog = new Intent(this, Login.class);
            startActivity(relog);
            finish();
            return;
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.Framecontainer, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.iconHome:
                        getSupportFragmentManager().beginTransaction().replace(R.id.Framecontainer, homeFragment).commit();
                        return true;
                    case R.id.iconProfile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.Framecontainer, profileFragment).commit();
                        return true;
                }
                return false;
            }
        });

        iconForm = (FloatingActionButton) findViewById(R.id.iconForm);
        iconForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(MainActivity.this, MedicalForm.class);
               startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }
}