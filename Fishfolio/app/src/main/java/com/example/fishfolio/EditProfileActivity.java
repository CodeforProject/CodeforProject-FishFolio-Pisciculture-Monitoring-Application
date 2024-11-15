package com.example.fishfolio;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseUser user;
    EditText etFullName;
    CheckBox chMale,chFemale;
    String gender;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        etFullName = findViewById(R.id.etFullname);
        chMale = findViewById(R.id.chMale);
        chFemale = findViewById(R.id.chFemale);
        btnUpdate = findViewById(R.id.btnUpdate);

        chMale.setOnClickListener((v) -> onCheckboxClicked(chMale));
        chFemale.setOnClickListener((v) -> onCheckboxClicked(chFemale));
        btnUpdate.setOnClickListener((v -> updateProfile()));

    }

    private void updateProfile() {
        String name = String.valueOf(etFullName.getText());
        boolean isValidated = validateData(name);
        if(!isValidated)
            return;

        Users currUser = new Users(name,gender,user.getEmail());
        if(user != null) {
            database.getReference().child("Users").child(user.getUid()).setValue(currUser);
            Toast.makeText(EditProfileActivity.this, "Data Uploaded Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditProfileActivity.this,MainActivity.class));
            finish();
        } else {
            Toast.makeText(EditProfileActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    boolean validateData(String name){
        if (TextUtils.isEmpty(name)) {
            etFullName.setError("Invalid Name");
            return false;
        }
        if (!chMale.isChecked() && !chFemale.isChecked()) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void onCheckboxClicked(View checkedView) {
        boolean checked = ((CheckBox) checkedView).isChecked();

        // Check which checkbox was clicked
        if (checkedView.getId() == R.id.chMale) {
            if (checked) {
                chFemale.setChecked(false);// Uncheck the other checkbox
                gender = chMale.getText().toString();
            }
        } else if (checkedView.getId() == R.id.chFemale) {
            if (checked) {
                chMale.setChecked(false); // Uncheck the other checkbox
                gender = chFemale.getText().toString();
            }
        }
    }
}