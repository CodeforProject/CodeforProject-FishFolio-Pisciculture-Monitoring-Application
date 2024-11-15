package com.example.fishfolio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;

public class SettingsFragment extends Fragment {

    Button btnPrivacyPolicy, btnAboutUs, btnTermsConditions, btnFAQ, btnLogOut;
    LinearLayout btnEditProfile;
    SwitchCompat changeModeSwitch;
    boolean isNightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        btnEditProfile = rootView.findViewById(R.id.btnEditProfile);
        btnAboutUs = rootView.findViewById(R.id.btnAboutUs);
        btnPrivacyPolicy = rootView.findViewById(R.id.btnPrivacyPolicy);
        btnTermsConditions = rootView.findViewById(R.id.btnTermsConditions);
        btnFAQ = rootView.findViewById(R.id.btnFAQ);
        btnLogOut = rootView.findViewById(R.id.btnLogOut);
        changeModeSwitch = rootView.findViewById(R.id.changeModeSwitch);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });

        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AboutUsActivity.class));
            }
        });

        btnPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PrivacyPolicyActivity.class));
            }
        });

        btnTermsConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TermsConditionsActivity.class));
            }
        });

        btnFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FaqActivity.class));
            }
        });

        sharedPreferences = requireContext().getSharedPreferences(getString(R.string.app_name),Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        isNightMode = sharedPreferences.getBoolean("nightMode",false);

        if(isNightMode){
            changeModeSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        changeModeSwitch.setOnClickListener(v -> {
            myThemes();
        });

        return rootView;

    }

    private void myThemes() {
        if(isNightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor.putBoolean("nightMode",false);
            setRetainInstance(true);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.putBoolean("nightMode",true);
            setRetainInstance(true);
        }
        editor.apply();

    }
}