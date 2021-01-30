package com.seg2105.servicenovigrad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerWelcomePage extends AppCompatActivity {
    private final int normalVibrateValue = 10;
    private final int lowVibrateValue = normalVibrateValue / 2;
    FirebaseDatabase firebase;
    DatabaseReference dbReference;
    private String customerUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_page);

        customerUsername = getIntent().getStringExtra("EXTRA_USERNAME");

        // Initializing variables
        Button servicesButton = (Button) findViewById(R.id.servicesButton);
        Button pendingRequestsButton = (Button) findViewById(R.id.pendingRequestsButton);
        Button previousRequestsButton = (Button) findViewById(R.id.previousRequestsButton);
        Button logoutButton = (Button) findViewById(R.id.logoutButton);

        // OnClickListeners for the buttons
        servicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servicesClick();
            }
        });

        pendingRequestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pendingRequestsClick();
            }
        });

        previousRequestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousRequestsClick();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutClick();
            }
        });
    }

    // Takes the customer to the services requests page
    public void servicesClick() {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(normalVibrateValue);

        Intent intent = new Intent(CustomerWelcomePage.this, CustomerRequestServicesPage.class);
        intent.putExtra("EXTRA_USERNAME", customerUsername);
        startActivity(intent);
    }

    // Takes the customer to the pending requests page
    public void pendingRequestsClick() {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(normalVibrateValue);

        Intent intent = new Intent(CustomerWelcomePage.this, CustomerPendingRequestPage.class);
        intent.putExtra("EXTRA_USERNAME", customerUsername);
        startActivity(intent);
    }

    // Takes the customer to the previous requests page
    public void previousRequestsClick() {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(normalVibrateValue);

        Intent intent = new Intent(CustomerWelcomePage.this, CustomerPreviousRequestPage.class);
        intent.putExtra("EXTRA_USERNAME", customerUsername);
        startActivity(intent);
    }

    // Logs out the customer
    public void logoutClick() {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(normalVibrateValue);

        firebase = FirebaseDatabase.getInstance();
        dbReference = firebase.getReference("user_information");

        Intent intent = new Intent(CustomerWelcomePage.this, MainActivity.class);
        startActivity(intent);

        // Makes it so that the user can't go back to the employee panel once they're logged out
        finish();

        dbReference.child(customerUsername).child("loginStatus").setValue("false");

        Toast toast;
        Toast.makeText(CustomerWelcomePage.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
    }
}
