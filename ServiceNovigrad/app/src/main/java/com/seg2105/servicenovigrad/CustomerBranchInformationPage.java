package com.seg2105.servicenovigrad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomerBranchInformationPage extends AppCompatActivity {
    FirebaseDatabase firebase;
    DatabaseReference ref;
    DatabaseReference servicesRef;
    private final int normalVibrateValue = 10;
    private final int lowVibrateValue = normalVibrateValue / 2;
    String employeeUsername;

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
    protected String address;
    private String customerUsername;
    private String branchName;

    TextView branchNameTextView = null;
    TextView branchAddressTextView = null;
    RatingBar ratingBar = null;
    TextView mondayHours = null;
    TextView tuesdayHours = null;
    TextView wednesdayHours = null;
    TextView thursdayHours = null;
    TextView fridayHours = null;
    TextView saturdayHours = null;
    TextView sundayHours = null;

    public CustomerBranchInformationPage(){}
    public String getAddress(){
        return address;
    }

    public String getCustomerName(){
        return customerUsername;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_branch_information);

        customerUsername = getIntent().getStringExtra("EXTRA_USERNAME");
        branchName = getIntent().getStringExtra("EXTRA_BRANCH_NAME");

        firebase = FirebaseDatabase.getInstance();
        ref = firebase.getReference("Branches");

        Button sendRequestButton = (Button) findViewById(R.id.sendRequestButton);
        branchNameTextView = (TextView) findViewById(R.id.customerBranchNameInformationText);
        branchAddressTextView = (TextView) findViewById(R.id.customerBranchAddressInformationText);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        mondayHours = (TextView) findViewById(R.id.customerMondayHours);
        tuesdayHours = (TextView) findViewById(R.id.customerTuesdayHours);
        wednesdayHours = (TextView) findViewById(R.id.customerWednesdayHours);
        thursdayHours = (TextView) findViewById(R.id.customerThursdayHours);
        fridayHours = (TextView) findViewById(R.id.customerFridayHours);
        saturdayHours = (TextView) findViewById(R.id.customerSaturdayHours);
        sundayHours = (TextView) findViewById(R.id.customerSundayHours);

        ref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentBranchName = dataSnapshot.child(branchName).child("branchName").getValue().toString();
                currentBranchPhoneNumber = dataSnapshot.child(branchName).child("branchPhoneNumber").getValue().toString();
                currentBranchAddress = dataSnapshot.child(branchName).child("branchAddress").getValue().toString();
                currentBranchServices = dataSnapshot.child(branchName).child("branchServices").getValue().toString();
                currentMondayOpen = Boolean.parseBoolean(dataSnapshot.child(branchName).child("mondayOpen").getValue().toString());
                currentTuesdayOpen = Boolean.parseBoolean(dataSnapshot.child(branchName).child("tuesdayOpen").getValue().toString());
                currentWednesdayOpen = Boolean.parseBoolean(dataSnapshot.child(branchName).child("wednesdayOpen").getValue().toString());
                currentThursdayOpen = Boolean.parseBoolean(dataSnapshot.child(branchName).child("thursdayOpen").getValue().toString());
                currentFridayOpen = Boolean.parseBoolean(dataSnapshot.child(branchName).child("fridayOpen").getValue().toString());
                currentSaturdayOpen = Boolean.parseBoolean(dataSnapshot.child(branchName).child("saturdayOpen").getValue().toString());
                currentSundayOpen = Boolean.parseBoolean(dataSnapshot.child(branchName).child("sundayOpen").getValue().toString());
                currentMondayWorkingHours = dataSnapshot.child(branchName).child("mondayWorkingHours").getValue().toString();
                currentTuesdayWorkingHours = dataSnapshot.child(branchName).child("tuesdayWorkingHours").getValue().toString();
                currentWednesdayWorkingHours = dataSnapshot.child(branchName).child("wednesdayWorkingHours").getValue().toString();
                currentThursdayWorkingHours = dataSnapshot.child(branchName).child("thursdayWorkingHours").getValue().toString();
                currentFridayWorkingHours = dataSnapshot.child(branchName).child("fridayWorkingHours").getValue().toString();
                currentSaturdayWorkingHours = dataSnapshot.child(branchName).child("saturdayWorkingHours").getValue().toString();
                currentSundayWorkingHours = dataSnapshot.child(branchName).child("sundayWorkingHours").getValue().toString();
                currentBranchRating = dataSnapshot.child(branchName).child("branchRating").getValue().toString();
                currentBranchRatingVoteCount = dataSnapshot.child(branchName).child("branchRatingVoteCount").getValue().toString();

                address = currentBranchAddress;

                branchNameTextView.setText(currentBranchName);
                branchAddressTextView.setText(currentBranchAddress);
                ratingBar.setRating(Float.parseFloat(currentBranchRating));

                if (currentMondayOpen) {
                    mondayHours.setTextColor(Color.rgb(0, 137, 61));

                    if (currentMondayWorkingHours.equals("N/A")) {
                        mondayHours.setText("Undetermined");
                    } else {
                        mondayHours.setText(currentMondayWorkingHours);
                    }
                } else {
                    mondayHours.setTextColor(Color.RED);
                    mondayHours.setText("Closed");
                }

                if (currentTuesdayOpen) {
                    tuesdayHours.setTextColor(Color.rgb(0, 137, 61));

                    if (currentTuesdayWorkingHours.equals("N/A")) {
                        tuesdayHours.setText("Undetermined");
                    } else {
                        tuesdayHours.setText(currentTuesdayWorkingHours);
                    }
                } else {
                    tuesdayHours.setTextColor(Color.RED);
                    tuesdayHours.setText("Closed");
                }

                if (currentWednesdayOpen) {
                    wednesdayHours.setTextColor(Color.rgb(0, 137, 61));

                    if (currentWednesdayWorkingHours.equals("N/A")) {
                        wednesdayHours.setText("Undetermined");
                    } else {
                        wednesdayHours.setText(currentWednesdayWorkingHours);
                    }
                } else {
                    wednesdayHours.setTextColor(Color.RED);
                    wednesdayHours.setText("Closed");
                }

                if (currentThursdayOpen) {
                    thursdayHours.setTextColor(Color.rgb(0, 137, 61));

                    if (currentThursdayWorkingHours.equals("N/A")) {
                        thursdayHours.setText("Undetermined");
                    } else {
                        thursdayHours.setText(currentThursdayWorkingHours);
                    }
                } else {
                    thursdayHours.setTextColor(Color.RED);
                    thursdayHours.setText("Closed");
                }

                if (currentFridayOpen) {
                    fridayHours.setTextColor(Color.rgb(0, 137, 61));

                    if (currentFridayWorkingHours.equals("N/A")) {
                        fridayHours.setText("Undetermined");
                    } else {
                        fridayHours.setText(currentFridayWorkingHours);
                    }
                } else {
                    fridayHours.setTextColor(Color.RED);
                    fridayHours.setText("Closed");
                }

                if (currentSaturdayOpen) {
                    saturdayHours.setTextColor(Color.rgb(0, 137, 61));

                    if (currentSaturdayWorkingHours.equals("N/A")) {
                        saturdayHours.setText("Undetermined");
                    } else {
                        saturdayHours.setText(currentSaturdayWorkingHours);
                    }
                } else {
                    saturdayHours.setTextColor(Color.RED);
                    saturdayHours.setText("Closed");
                }

                if (currentSundayOpen) {
                    sundayHours.setTextColor(Color.rgb(0, 137, 61));

                    if (currentSundayWorkingHours.equals("N/A")) {
                        sundayHours.setText("Undetermined");
                    } else {
                        sundayHours.setText(currentSundayWorkingHours);
                    }
                } else {
                    sundayHours.setTextColor(Color.RED);
                    sundayHours.setText("Closed");
                }

                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // OnClickListeners
        sendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                submitRequest();
            }
        });
    }

    public void submitRequest() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_customer_request_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);
        servicesRef = firebase.getReference("ServiceRequests");
        final Customer customer = new Customer();

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        final Spinner servicesSpinner = (Spinner) dialogView.findViewById(R.id.servicesSpinner);
        final EditText editTextDateOfBirthRequest = (EditText) dialogView.findViewById(R.id.editTextDateOfBirthRequest);
        final EditText editTextAddress = (EditText) dialogView.findViewById(R.id.editTextAddress);
        final CheckBox attachPhotoCheckBox = (CheckBox) dialogView.findViewById(R.id.attachPhotoCheckBox);
        final CheckBox attachProofOfResidenceCheckBox = (CheckBox) dialogView.findViewById(R.id.attachProofOfResidenceCheckBox);
        final CheckBox attachProofOfLicenseCheckBox = (CheckBox) dialogView.findViewById(R.id.attachProofOfLicenseCheckBox);
        final CheckBox attachProofOfStatusCheckBox = (CheckBox) dialogView.findViewById(R.id.attachProofOfStatusCheckBox);
        final Button submitRequestButton = (Button) dialogView.findViewById(R.id.submitRequestButton);

        final TextView servicePriceTrueFalse = (TextView) dialogView.findViewById(R.id.servicePriceTrueFalse);
        final TextView addressTrueFalse = (TextView) dialogView.findViewById(R.id.addressTrueFalse);
        final TextView dateOfBirthTrueFalse = (TextView) dialogView.findViewById(R.id.dateOfBirthTrueFalse);
        final TextView proofOfPhotoTrueFalse = (TextView) dialogView.findViewById(R.id.proofOfPhotoTrueFalse);
        final TextView proofOfResidenceTrueFalse = (TextView) dialogView.findViewById(R.id.proofOfResidenceTrueFalse);
        final TextView proofOfLicenseTrueFalse = (TextView) dialogView.findViewById(R.id.proofOfLicenseTrueFalse);
        final TextView proofOfStatusTrueFalse = (TextView) dialogView.findViewById(R.id.proofOfStatusTrueFalse);

        final String[] serviceName = new String[1];
        final boolean[] addressRequired = new boolean[1];
        final boolean[] dateOfBirthRequired = new boolean[1];
        final boolean[] proofOfPhotoRequired = new boolean[1];
        final boolean[] proofOfResidenceRequired = new boolean[1];
        final boolean[] proofOfStatusRequired = new boolean[1];
        final boolean[] proofOfLicenseRequired = new boolean[1];
        final double[] servicePrice = new double[1];

        // Service Information
        ArrayList<String> servicesArrayList;

        if (!currentBranchServices.equals("N/A")) {
            servicesArrayList = new ArrayList<String>(Arrays.asList(currentBranchServices.split(",")));

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CustomerBranchInformationPage.this, android.R.layout.simple_spinner_item, servicesArrayList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            servicesSpinner.setAdapter(spinnerAdapter);
        }

        servicesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);

                serviceName[0] = servicesSpinner.getSelectedItem().toString().trim();

                servicesRef.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        addressRequired[0] = Boolean.parseBoolean(dataSnapshot.child(serviceName[0]).child("address").getValue().toString());
                        dateOfBirthRequired[0] = Boolean.parseBoolean(dataSnapshot.child(serviceName[0]).child("dateOfBirth").getValue().toString());
                        proofOfPhotoRequired[0] = Boolean.parseBoolean(dataSnapshot.child(serviceName[0]).child("proofOfPhoto").getValue().toString());
                        proofOfResidenceRequired[0] = Boolean.parseBoolean(dataSnapshot.child(serviceName[0]).child("proofOfResidence").getValue().toString());
                        proofOfStatusRequired[0] = Boolean.parseBoolean(dataSnapshot.child(serviceName[0]).child("proofOfStatus").getValue().toString());
                        proofOfLicenseRequired[0] = Boolean.parseBoolean(dataSnapshot.child(serviceName[0]).child("typeOfLicense").getValue().toString());
                        servicePrice[0] = Double.parseDouble(dataSnapshot.child(serviceName[0]).child("price").getValue().toString());

                        if (addressRequired[0]) {
                            addressTrueFalse.setTextColor(Color.rgb(0, 137, 61));
                            addressTrueFalse.setText("Required");
                        } else {
                            addressTrueFalse.setTextColor(Color.RED);
                            addressTrueFalse.setText("Not required");
                        }

                        if (dateOfBirthRequired[0]) {
                            dateOfBirthTrueFalse.setTextColor(Color.rgb(0, 137, 61));
                            dateOfBirthTrueFalse.setText("Required");
                        } else {
                            dateOfBirthTrueFalse.setTextColor(Color.RED);
                            dateOfBirthTrueFalse.setText("Not required");
                        }

                        if (proofOfPhotoRequired[0]) {
                            proofOfPhotoTrueFalse.setTextColor(Color.rgb(0, 137, 61));
                            proofOfPhotoTrueFalse.setText("Required");
                        } else {
                            proofOfPhotoTrueFalse.setTextColor(Color.RED);
                            proofOfPhotoTrueFalse.setText("Not required");
                        }

                        if (proofOfResidenceRequired[0]) {
                            proofOfResidenceTrueFalse.setTextColor(Color.rgb(0, 137, 61));
                            proofOfResidenceTrueFalse.setText("Required");
                        } else {
                            proofOfResidenceTrueFalse.setTextColor(Color.RED);
                            proofOfResidenceTrueFalse.setText("Not required");
                        }

                        if (proofOfStatusRequired[0]) {
                            proofOfStatusTrueFalse.setTextColor(Color.rgb(0, 137, 61));
                            proofOfStatusTrueFalse.setText("Required");
                        } else {
                            proofOfStatusTrueFalse.setTextColor(Color.RED);
                            proofOfStatusTrueFalse.setText("Not required");
                        }

                        if (proofOfLicenseRequired[0]) {
                            proofOfLicenseTrueFalse.setTextColor(Color.rgb(0, 137, 61));
                            proofOfLicenseTrueFalse.setText("Required");
                        } else {
                            proofOfLicenseTrueFalse.setTextColor(Color.RED);
                            proofOfLicenseTrueFalse.setText("Not required");
                        }

                        servicePriceTrueFalse.setText("$" + servicePrice[0]);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                addressTrueFalse.setText("N/A");
                dateOfBirthTrueFalse.setText("N/A");
                proofOfPhotoTrueFalse.setText("N/A");
                proofOfResidenceTrueFalse.setText("N/A");
                proofOfStatusTrueFalse.setText("N/A");
                proofOfLicenseTrueFalse.setText("N/A");
                servicePriceTrueFalse.setText("N/A");
            }
        });

        if (currentBranchServices.equals("N/A")) {
            addressTrueFalse.setTextColor(Color.RED);
            addressTrueFalse.setText("N/A");

            dateOfBirthTrueFalse.setTextColor(Color.RED);
            dateOfBirthTrueFalse.setText("N/A");

            proofOfPhotoTrueFalse.setTextColor(Color.RED);
            proofOfPhotoTrueFalse.setText("N/A");

            proofOfResidenceTrueFalse.setTextColor(Color.RED);
            proofOfResidenceTrueFalse.setText("N/A");

            proofOfStatusTrueFalse.setTextColor(Color.RED);
            proofOfStatusTrueFalse.setText("N/A");

            proofOfLicenseTrueFalse.setTextColor(Color.RED);
            proofOfLicenseTrueFalse.setText("N/A");

            servicePriceTrueFalse.setTextColor(Color.RED);
            servicePriceTrueFalse.setText("N/A");
        }

        // Submitting Request
        submitRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                if (editTextDateOfBirthRequest.getText().toString().length() == 0 && editTextAddress.getText().toString().length() == 0 & !attachPhotoCheckBox.isChecked() && !attachProofOfResidenceCheckBox.isChecked()
                        && !attachProofOfLicenseCheckBox.isChecked() && !attachProofOfStatusCheckBox.isChecked() && !currentBranchServices.equals("N/A")) {
                    submitRequestButton.setError("At least one field is required");
                } else {
                    Request request = new Request(customerUsername, branchName, serviceName[0], editTextDateOfBirthRequest.getText().toString(), editTextAddress.getText().toString(),
                            attachPhotoCheckBox.isChecked(), attachProofOfResidenceCheckBox.isChecked(), attachProofOfLicenseCheckBox.isChecked(), attachProofOfStatusCheckBox.isChecked());

                    customer.submitServiceRequest(request);
                    Toast.makeText(CustomerBranchInformationPage.this, "Successfully submitted your request", Toast.LENGTH_SHORT)
                            .show();
                    dialog.dismiss();

                }
            }
        });
    }
}
