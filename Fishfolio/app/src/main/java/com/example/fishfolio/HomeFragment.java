package com.example.fishfolio;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class HomeFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    TextView tvHelloUsername, tvTemperatueValue, tvTurbidityValue, tvPhValue, tvOxygenValue;
    private LinearLayout myLayout;

//    private  static  final String CHANNEL_ID = "Warning Channel";
//    private  static  final int TEMP_NOTIFICATION_ID = 100;
//    private  static  final int TURBO_NOTIFICATION_ID = 101;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        //Asking for notification permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Check if the permission is already granted
            if (ContextCompat.checkSelfPermission(requireActivity(),
                    android.Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED) {
                // Request the permission
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }



        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        tvHelloUsername = rootView.findViewById(R.id.tvHelloUsername);
        tvTemperatueValue = rootView.findViewById(R.id.tvTemperatureValue);
        tvTurbidityValue = rootView.findViewById(R.id.tvTurbidityValue);
        tvPhValue = rootView.findViewById(R.id.tvPhValue);
        tvOxygenValue = rootView.findViewById(R.id.tvOxygenValue);

        myLayout = rootView.findViewById(R.id.tempratureBox);

        myLayout.setOnClickListener((v) -> {startActivity(new Intent(getContext(), TemperatureActivity.class));});

        database.getReference("Users").child(user.getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Users currentUser = task.getResult().getValue(Users.class);
                if (currentUser != null) {
                    tvHelloUsername.setText("Hello, "+currentUser.getName());
                }
            }
        });

        database.getReference().child("IoT Data").child(user.getUid()).child("temp")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        String tempVal = snapshot.getValue(String.class);
//                        int tempIntVal = Integer.parseInt(tempVal);
//                        if(tempIntVal > 30)
//                            showNotification(R.drawable.temperature,"Temperature is fluctuating.","Temperature Warning!!!",TEMP_NOTIFICATION_ID);
                        tvTemperatueValue.setText(snapshot.getValue(String.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });

        database.getReference().child("IoT Data").child(user.getUid()).child("turbo")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        String turboVal = snapshot.getValue(String.class);
//                        int turboIntVal = Integer.parseInt(turboVal);
//                        if(turboIntVal > 900)
//                            showNotification(R.drawable.turbidity,"Turbidity is fluctuating.","Turbidity Warning!!!",TURBO_NOTIFICATION_ID);
                        tvTurbidityValue.setText(snapshot.getValue(String.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });

        database.getReference().child("IoT Data").child(user.getUid()).child("oxy")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        tvOxygenValue.setText(snapshot.getValue(String.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });

        database.getReference().child("IoT Data").child(user.getUid()).child("ph")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        tvPhValue.setText(snapshot.getValue(String.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });

        return rootView; // Return the inflated view
    }

//    private void showNotification(int drawableResId, String contentText, String subText, int NOTIFICATION_ID) {
//
//        Drawable Drawable = ResourcesCompat.getDrawable(getResources(),drawableResId,null);
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) Drawable;
//        Bitmap largeIcon = bitmapDrawable.getBitmap();
//
//        NotificationManager notificationManager = (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification notification;
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            notification = new Notification.Builder(requireContext())
//                    .setLargeIcon(largeIcon)
//                    .setSmallIcon(R.drawable.app_icon)
//                    .setContentText(contentText)
//                    .setSubText(subText)
//                    .setChannelId(CHANNEL_ID)
//                    .build();
//
//            notificationManager.createNotificationChannel(
//                    new NotificationChannel(CHANNEL_ID,"New Channel",NotificationManager.IMPORTANCE_HIGH));
//
//        }else {
//            notification = new Notification.Builder(requireContext())
//                    .setLargeIcon(largeIcon)
//                    .setSmallIcon(R.drawable.app_icon)
//                    .setContentText(contentText)
//                    .setSubText(subText)
//                    .build();
//        }
//
//        notificationManager.notify(NOTIFICATION_ID,notification);
//    }
}
