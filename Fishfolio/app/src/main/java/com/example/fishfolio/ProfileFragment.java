package com.example.fishfolio;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {

    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView tvFullName, tvEmail, tvGender, tvUserid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_profile, container, false);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        tvFullName = rootView.findViewById(R.id.tvFullName);
        tvGender = rootView.findViewById(R.id.tvGender);
        tvEmail = rootView.findViewById(R.id.tvEmail);
        tvUserid = rootView.findViewById(R.id.tvUserId);


        database.getReference().child("Users").child(user.getUid()).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Users currentUser = task.getResult().getValue(Users.class);
                if (currentUser != null){
                    tvFullName.setText(currentUser.getName());
                    tvEmail.setText(currentUser.geteMail());
                    tvGender.setText(currentUser.getGender());
                    tvUserid.setText(user.getUid());
                }
            }
        });



        return rootView;
    }
}