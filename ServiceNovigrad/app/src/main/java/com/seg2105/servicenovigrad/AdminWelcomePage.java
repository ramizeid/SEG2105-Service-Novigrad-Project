package com.seg2105.servicenovigrad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AdminWelcomePage extends AppCompatActivity {
    private final int normalVibrateValue = 10;
    private final int lowVibrateValue = normalVibrateValue/2;
    FirebaseDatabase firebase;
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_page);

        // Initializing variables
        Button servicesButton = (Button) findViewById(R.id.servicesButton);
        Button accountsButton = (Button) findViewById(R.id.accountsButton);
        Button logoutButton = (Button) findViewById(R.id.logoutButton);

        // OnClickListeners for the buttons
        servicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servicesClick();
            }
        });

        accountsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountsClick();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutClick();
            }
        });
    }

    // Takes the admin user to the services page
    public void servicesClick() {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(normalVibrateValue);

        Intent intent = new Intent(AdminWelcomePage.this, AdminServicesPage.class);
        startActivity(intent);
    }

    // Takes the admin user to the accounts page
    public void accountsClick() {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(normalVibrateValue);

        Intent intent = new Intent(AdminWelcomePage.this, AdminAccountsPage.class);
        startActivity(intent);
    }

    // Logs out the admin user
    public void logoutClick() {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(normalVibrateValue);

        firebase = FirebaseDatabase.getInstance();
        dbReference = firebase.getReference("user_information");

        Intent intent = new Intent(AdminWelcomePage.this, MainActivity.class);
        startActivity(intent);

        // Makes it so that the user can't go back to the admin panel once they're logged out
        finish();

        dbReference.child("admin").child("loginStatus").setValue("false");

        Toast toast;
        Toast.makeText(AdminWelcomePage.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
    }





}
