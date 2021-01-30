package com.seg2105.servicenovigrad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeBranchProfilePage extends AppCompatActivity {
    FirebaseDatabase firebase;
    DatabaseReference ref;
    private final int normalVibrateValue = 10;
    private final int lowVibrateValue = normalVibrateValue / 2;
    private String employeeUsername;

    EditText branchNameTextBox;
    EditText branchPhoneNumberTextBox;
    EditText branchAddressTextBox;

    String currentBranchName;
    String currentBranchPhoneNumber;
    String currentBranchAddress;
    String currentBranchServices;
    boolean currentMondayOpen;
    boolean currentTuesdayOpen;
    boolean currentWednesdayOpen;
    boolean currentThursdayOpen;
    boolean currentFridayOpen;
    boolean currentSaturdayOpen;
    boolean currentSundayOpen;
    String currentMondayWorkingHours;
    String currentTuesdayWorkingHours;
    String currentWednesdayWorkingHours;
    String currentThursdayWorkingHours;
    String currentFridayWorkingHours;
    String currentSaturdayWorkingHours;
    String currentSundayWorkingHours;
    String currentBranchRating;
    String currentBranchRatingVoteCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_branch_profile_page);

        firebase = FirebaseDatabase.getInstance();
        ref = firebase.getReference("Branches");

        branchNameTextBox = (EditText) findViewById(R.id.branchNameTextBox);
        branchPhoneNumberTextBox = (EditText) findViewById(R.id.branchPhoneNumberTextBox);
        branchAddressTextBox = (EditText) findViewById(R.id.branchAddressTextBox);
        Button saveButton = (Button) findViewById(R.id.saveBranchProfileButton);
        Button openMapButton = (Button) findViewById(R.id.openMapButton);

        employeeUsername = getIntent().getStringExtra("EXTRA_USERNAME");

        ref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentBranchName = dataSnapshot.child(employeeUsername + " Branch").child("branchName").getValue().toString();
                currentBranchPhoneNumber = dataSnapshot.child(employeeUsername + " Branch").child("branchPhoneNumber").getValue().toString();
                currentBranchAddress = dataSnapshot.child(employeeUsername + " Branch").child("branchAddress").getValue().toString();
                currentBranchServices = dataSnapshot.child(employeeUsername + " Branch").child("branchServices").getValue().toString();
                currentMondayOpen = Boolean.parseBoolean(dataSnapshot.child(employeeUsername + " Branch").child("mondayOpen").getValue().toString());
                currentTuesdayOpen = Boolean.parseBoolean(dataSnapshot.child(employeeUsername + " Branch").child("tuesdayOpen").getValue().toString());
                currentWednesdayOpen = Boolean.parseBoolean(dataSnapshot.child(employeeUsername + " Branch").child("wednesdayOpen").getValue().toString());
                currentThursdayOpen = Boolean.parseBoolean(dataSnapshot.child(employeeUsername + " Branch").child("thursdayOpen").getValue().toString());
                currentFridayOpen = Boolean.parseBoolean(dataSnapshot.child(employeeUsername + " Branch").child("fridayOpen").getValue().toString());
                currentSaturdayOpen = Boolean.parseBoolean(dataSnapshot.child(employeeUsername + " Branch").child("saturdayOpen").getValue().toString());
                currentSundayOpen = Boolean.parseBoolean(dataSnapshot.child(employeeUsername + " Branch").child("sundayOpen").getValue().toString());
                currentMondayWorkingHours = dataSnapshot.child(employeeUsername + " Branch").child("mondayWorkingHours").getValue().toString();
                currentTuesdayWorkingHours = dataSnapshot.child(employeeUsername + " Branch").child("tuesdayWorkingHours").getValue().toString();
                currentWednesdayWorkingHours = dataSnapshot.child(employeeUsername + " Branch").child("wednesdayWorkingHours").getValue().toString();
                currentThursdayWorkingHours = dataSnapshot.child(employeeUsername + " Branch").child("thursdayWorkingHours").getValue().toString();
                currentFridayWorkingHours = dataSnapshot.child(employeeUsername + " Branch").child("fridayWorkingHours").getValue().toString();
                currentSaturdayWorkingHours = dataSnapshot.child(employeeUsername + " Branch").child("saturdayWorkingHours").getValue().toString();
                currentSundayWorkingHours = dataSnapshot.child(employeeUsername + " Branch").child("sundayWorkingHours").getValue().toString();
                currentBranchRating = dataSnapshot.child(employeeUsername + " Branch").child("branchRating").getValue().toString();
                currentBranchRatingVoteCount = dataSnapshot.child(employeeUsername + " Branch").child("branchRatingVoteCount").getValue().toString();

                branchNameTextBox.setText(currentBranchName);
                branchPhoneNumberTextBox.setText(currentBranchPhoneNumber);
                branchAddressTextBox.setText(currentBranchAddress);

                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                saveProfile();
            }
        });

        openMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                openMap();
            }
        });
    }

    public void saveProfile() {
        final String newBranchName = branchNameTextBox.getText().toString().trim();
        final String newBranchPhoneNumber = branchPhoneNumberTextBox.getText().toString().trim();
        final String newBranchAddress = branchAddressTextBox.getText().toString().trim();
        final BranchEmployee employee = new BranchEmployee();

        boolean isValid = true;

        if (newBranchName.isEmpty()) {
            branchNameTextBox.setError("Branch name is required");
            isValid = false;
        }

        if (newBranchPhoneNumber.length() > 10) {
            branchPhoneNumberTextBox.setError("Phone number cannot be longer than 10 digits");
            isValid = false;
        } else if (newBranchPhoneNumber.isEmpty()) {
            branchPhoneNumberTextBox.setError("Branch phone number is required");
            isValid = false;
        }

        if (newBranchAddress.isEmpty()) {
            branchAddressTextBox.setError("Branch address is required");
            isValid = false;
        } else {
            String specialCharacters = "!\"#$%^&*()_+-=/*:;<>[]{}\\|~`";

            for (int i = 0; i < specialCharacters.length(); i++) {
                String specialCharacter = Character.toString(specialCharacters.charAt(i));

                if (newBranchAddress.contains(specialCharacter)) {
                    branchAddressTextBox.setError("Branch address cannot contain special characters");
                    isValid = false;
                }
            }
        }

        if (isValid) {
            employee.updateBranch(employeeUsername, newBranchName, newBranchAddress, newBranchPhoneNumber, currentBranchServices,
                    currentMondayOpen, currentTuesdayOpen, currentWednesdayOpen, currentThursdayOpen, currentFridayOpen, currentSaturdayOpen,
                    currentSundayOpen, currentMondayWorkingHours, currentTuesdayWorkingHours, currentWednesdayWorkingHours, currentThursdayWorkingHours,
                    currentFridayWorkingHours, currentSaturdayWorkingHours, currentSundayWorkingHours, currentBranchRating, currentBranchRatingVoteCount);

            Toast toast;
            Toast.makeText(EmployeeBranchProfilePage.this, "Successfully updated branch " + currentBranchName,
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(EmployeeBranchProfilePage.this, EmployeeWelcomePage.class);
            intent.putExtra("EXTRA_USERNAME", employeeUsername);

            startActivity(intent);
        }
    }

    public void openMap() {
        EditText teamAddress = (EditText) findViewById(R.id.branchAddressTextBox);

        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("http://maps.google.co.in/maps?q=" + teamAddress.getText());

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }
}
