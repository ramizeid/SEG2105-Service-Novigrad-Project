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

public class EmployeeWelcomePage extends AppCompatActivity {
    private final int normalVibrateValue = 10;
    private final int lowVibrateValue = normalVibrateValue/2;
    FirebaseDatabase firebase;
    DatabaseReference dbReference;
    private String employeeUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);

        employeeUsername = getIntent().getStringExtra("EXTRA_USERNAME");

        // Initializing variables
        Button servicesButton = (Button) findViewById(R.id.servicesButton);
        Button branchProfileButton = (Button) findViewById(R.id.profileButton);
        Button branchHoursButton = (Button) findViewById(R.id.branchHoursButton);
        Button requestsButton = (Button) findViewById(R.id.requestsButton);
        Button logoutButton = (Button) findViewById(R.id.empLogoutButton);

        // OnClickListeners for the buttons
        servicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servicesClick();
            }
        });

        branchProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileClick();
            }
        });

        branchHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hoursClick();
            }
        });

        requestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestsClick();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutClick();
            }
        });
    }

    // Takes the employee to the branch services page
    public void servicesClick() {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(normalVibrateValue);

        Intent intent = new Intent(EmployeeWelcomePage.this, EmployeeServicesPage.class);
        intent.putExtra("EXTRA_USERNAME", employeeUsername);
        startActivity(intent);
    }

    // Takes the employee to the branch profile page
    public void profileClick() {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(normalVibrateValue);

        Intent intent = new Intent(EmployeeWelcomePage.this, EmployeeBranchProfilePage.class);
        intent.putExtra("EXTRA_USERNAME", employeeUsername);
        startActivity(intent);
    }

    // Takes the employee to the branch hours page
    public void hoursClick() {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(normalVibrateValue);

        Intent intent = new Intent(EmployeeWelcomePage.this, EmployeeBranchHoursPage.class);
        intent.putExtra("EXTRA_USERNAME", employeeUsername);
        startActivity(intent);
    }

    // Takes the employee to the branch profile page
    public void requestsClick() {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(normalVibrateValue);

        Intent intent = new Intent(EmployeeWelcomePage.this, EmployeeRequestsPage.class);
        intent.putExtra("EXTRA_USERNAME", employeeUsername);
        startActivity(intent);
    }

    // Logs out the employee
    public void logoutClick() {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(normalVibrateValue);

        firebase = FirebaseDatabase.getInstance();
        dbReference = firebase.getReference("user_information");

        Intent intent = new Intent(EmployeeWelcomePage.this, MainActivity.class);
        startActivity(intent);

        // Makes it so that the user can't go back to the employee panel once they're logged out
        finish();

        dbReference.child(employeeUsername).child("loginStatus").setValue("false");

        Toast toast;
        Toast.makeText(EmployeeWelcomePage.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
    }
}
