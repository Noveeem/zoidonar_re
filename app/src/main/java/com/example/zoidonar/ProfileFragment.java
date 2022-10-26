package com.example.zoidonar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    TextView txtName, txtAddress, txtMobile, txtDob, txtEmail;
    Button btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        //TextView
        txtName = (TextView) v.findViewById(R.id.txtName);
        txtAddress = (TextView) v.findViewById(R.id.txtAddress);
        txtMobile = (TextView) v.findViewById(R.id.txtMobile);
        txtDob = (TextView) v.findViewById(R.id.txtDob);
        txtEmail = (TextView) v.findViewById(R.id.txtEmail);

        //Button
        btnLogout = (Button) v.findViewById(R.id.btnLogout);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("donors").child(currentUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null)
                {
                    txtName.setText(user.firstName+" "+user.lastName);
                    txtEmail.setText(user.Email);
                    txtAddress.setText(user.Address);
                    txtMobile.setText(user.Mobile);
                    txtDob.setText(user.Dob);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        return v;
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent i= new Intent(getActivity(), Login.class);
        startActivity(i);
        getActivity().finish();
    }
}