package com.seg2105.servicenovigrad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.ArrayList;

public class AdminServicesPage extends AppCompatActivity {
    FirebaseDatabase firebase;
    DatabaseReference ref;
    ListView servicesListView;
    ArrayList<Service> services;
    private final int normalVibrateValue = 10;
    private final int lowVibrateValue = normalVibrateValue / 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_services_page);

        firebase = FirebaseDatabase.getInstance();
        ref = firebase.getReference("ServiceRequests");

        servicesListView = (ListView) findViewById(R.id.servicesListView);
        services = new ArrayList<>();
        Button addServiceButton = (Button) findViewById(R.id.addServiceButton);

        addServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);
                serviceSubmission();
            }
        });

        servicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);

                Service service = services.get(i);
                listViewClick(service.getServiceName());
            }
        });

        servicesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                serviceUpdateOrDelete(service.getServiceName());
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Service service = postSnapshot.getValue(Service.class);
                    services.add(service);
                }

                ServiceList servicesAdapter = new ServiceList(AdminServicesPage.this, services);
                servicesListView.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void serviceSubmission() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.administrator_create_service_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        final Administrator admin = new Administrator();
        final Button serviceCreationButton = (Button) dialogView.findViewById(R.id.serviceCreationButton);
        final EditText editServiceNamePlainText = (EditText) dialogView.findViewById(R.id.editServiceNamePlainText);
        final EditText editServicePricePlainText = (EditText) dialogView.findViewById(R.id.editPriceText);
        final CheckBox dateOfBirthCheckBox = (CheckBox) dialogView.findViewById(R.id.dateOfBirthCheckBox);
        final CheckBox addressCheckBox = (CheckBox) dialogView.findViewById(R.id.addressCheckBox);
        final CheckBox typeOfLicenseCheckBox = (CheckBox) dialogView.findViewById(R.id.typeOfLicenseCheckBox);
        final CheckBox proofOfResidenceCheckBox = (CheckBox) dialogView.findViewById(R.id.proofOfResidenceCheckBox);
        final CheckBox proofOfStatusCheckBox = (CheckBox) dialogView.findViewById(R.id.proofOfStatusCheckBox);
        final CheckBox photoProofCheckBox = (CheckBox) dialogView.findViewById(R.id.photoProofCheckBox);

        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        serviceCreationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final String serviceName = editServiceNamePlainText.getText().toString().trim();
            final String servicePrice = editServicePricePlainText.getText().toString().trim();
            double price = 0;
            boolean isValid = true;

            Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vb.vibrate(lowVibrateValue);

            if (serviceName.isEmpty()) {
                editServiceNamePlainText.setError("Service name is required");
                isValid = false;
            }

            if (servicePrice.isEmpty()) {
                editServicePricePlainText.setError("Service price is required");
                isValid = false;
            } else {
                try {
                    price = Double.parseDouble(servicePrice);
                    if (price < 0){
                        editServicePricePlainText.setError("Service price can't be negative");
                        isValid = false;
                    }

                } catch (NumberFormatException e) {
                    editServicePricePlainText.setError("Service price is not a number");
                    isValid = false;
                }
            }

            if (!dateOfBirthCheckBox.isChecked() && !addressCheckBox.isChecked() &&
                    !typeOfLicenseCheckBox.isChecked() && !proofOfResidenceCheckBox.isChecked() &&
                    !proofOfStatusCheckBox.isChecked() && !photoProofCheckBox.isChecked()) {
                dateOfBirthCheckBox.setError("At least 3 requirements must be checked");
                addressCheckBox.setError("At least 3 requirements must be checked");
                typeOfLicenseCheckBox.setError("At least 3 requirements must be checked");
                proofOfResidenceCheckBox.setError("At least 3 requirements must be checked");
                proofOfStatusCheckBox.setError("At least 3 requirements must be checked");
                photoProofCheckBox.setError("At least 3 requirements must be checked");

                isValid = false;
            }

            if (isValid) {
                admin.addService(serviceName, dateOfBirthCheckBox, addressCheckBox, typeOfLicenseCheckBox,
                        proofOfResidenceCheckBox, proofOfStatusCheckBox, photoProofCheckBox, price);
                dialog.dismiss();

                Toast toast;
                Toast.makeText(AdminServicesPage.this, "Successfully created service " + serviceName,
                        Toast.LENGTH_SHORT).show();
            }
            }
        });
    }

    public void serviceUpdateOrDelete(final String serviceName) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.administrator_update_service_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        final Administrator admin = new Administrator();
        final Button updateServiceButton = (Button) dialogView.findViewById(R.id.updateServiceButton);
        final Button deleteServiceButton = (Button) dialogView.findViewById(R.id.deleteServiceButton);
        final EditText editServiceNamePlainText = (EditText) dialogView.findViewById(R.id.editServiceNamePlainText);
        final EditText editServicePricePlainText = (EditText) dialogView.findViewById(R.id.editPriceText);
        final CheckBox dateOfBirthCheckBox = (CheckBox) dialogView.findViewById(R.id.dateOfBirthCheckBox);
        final CheckBox addressCheckBox = (CheckBox) dialogView.findViewById(R.id.addressCheckBox);
        final CheckBox typeOfLicenseCheckBox = (CheckBox) dialogView.findViewById(R.id.typeOfLicenseCheckBox);
        final CheckBox proofOfResidenceCheckBox = (CheckBox) dialogView.findViewById(R.id.proofOfResidenceCheckBox);
        final CheckBox proofOfStatusCheckBox = (CheckBox) dialogView.findViewById(R.id.proofOfStatusCheckBox);
        final CheckBox photoProofCheckBox = (CheckBox) dialogView.findViewById(R.id.photoProofCheckBox);

        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();

        ref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String currentServiceName = dataSnapshot.child(serviceName).child("serviceName").getValue().toString();
                editServiceNamePlainText.setText(currentServiceName);

                try {
                    String currentServicePrice = dataSnapshot.child(serviceName).child("price").getValue().toString();
                    editServicePricePlainText.setText(currentServicePrice);
                } catch (NullPointerException e){}

                boolean addressRequired = Boolean.parseBoolean(dataSnapshot.child(currentServiceName).child("address").getValue().toString());
                boolean dateOfBirthRequired = Boolean.parseBoolean(dataSnapshot.child(currentServiceName).child("dateOfBirth").getValue().toString());
                boolean proofOfPhotoRequired = Boolean.parseBoolean(dataSnapshot.child(currentServiceName).child("proofOfPhoto").getValue().toString());
                boolean proofOfResidenceRequired = Boolean.parseBoolean(dataSnapshot.child(currentServiceName).child("proofOfResidence").getValue().toString());
                boolean proofOfStatusRequired = Boolean.parseBoolean(dataSnapshot.child(currentServiceName).child("proofOfStatus").getValue().toString());
                boolean typeOfLicenseRequired = Boolean.parseBoolean(dataSnapshot.child(currentServiceName).child("typeOfLicense").getValue().toString());

                if (addressRequired) {
                    addressCheckBox.setChecked(true);
                }

                if (dateOfBirthRequired) {
                    dateOfBirthCheckBox.setChecked(true);
                }

                if (proofOfPhotoRequired) {
                    photoProofCheckBox.setChecked(true);
                }

                if (proofOfResidenceRequired) {
                    proofOfResidenceCheckBox.setChecked(true);
                }

                if (proofOfStatusRequired) {
                    proofOfStatusCheckBox.setChecked(true);
                }

                if (typeOfLicenseRequired) {
                    typeOfLicenseCheckBox.setChecked(true);
                }

                ref.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        dialog.show();

        updateServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newServiceName = editServiceNamePlainText.getText().toString().trim();
                final String newServicePrice = editServicePricePlainText.getText().toString().trim();
                double newPrice = 0;
                boolean isValid = true;

                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);

                if (newServiceName.isEmpty()) {
                    editServiceNamePlainText.setError("Service name is required");
                    isValid = false;
                }

                if (newServicePrice.isEmpty()) {
                    editServicePricePlainText.setError("Service price is required");
                    isValid = false;
                } else {
                    try {
                        newPrice = Double.parseDouble(newServicePrice);
                        if (newPrice < 0){
                            editServicePricePlainText.setError("Service price can't be negative");
                            isValid = false;
                        }

                    } catch (NumberFormatException e) {
                        editServicePricePlainText.setError("Service price is not a number");
                        isValid = false;
                    }
                }

                if (isValid) {
                    admin.updateService(serviceName, newServiceName, dateOfBirthCheckBox, addressCheckBox, typeOfLicenseCheckBox,
                            proofOfResidenceCheckBox, proofOfStatusCheckBox, photoProofCheckBox, newPrice);
                    dialog.dismiss();

                    Toast toast;
                    Toast.makeText(AdminServicesPage.this, "Successfully updated service " + serviceName,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);

                admin.deleteService(serviceName);
                dialog.dismiss();

                Toast toast;
                Toast.makeText(AdminServicesPage.this, "Successfully deleted service " + serviceName,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void listViewClick(final String serviceName) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.administrator_service_details_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        final TextView serviceDescriptionTitle = (TextView) dialogView.findViewById(R.id.serviceDescriptionTitle);
        final TextView addressRequiredDescription = (TextView) dialogView.findViewById(R.id.addressRequiredDescription);
        final TextView dateOfBirthRequiredDescription = (TextView) dialogView.findViewById(R.id.dateOfBirthRequiredDescription);
        final TextView proofOfPhotoRequiredDescription = (TextView) dialogView.findViewById(R.id.proofOfPhotoRequiredDescription);
        final TextView proofOfResidenceRequiredDescription = (TextView) dialogView.findViewById(R.id.proofOfResidenceRequiredDescription);
        final TextView proofOfStatusRequiredDescription = (TextView) dialogView.findViewById(R.id.proofOfStatusRequiredDescription);
        final TextView typeOfLicenseRequiredDescription = (TextView) dialogView.findViewById(R.id.typeOfLicenseRequiredDescription);
        final TextView servicePrice = (TextView) dialogView.findViewById(R.id.servicePrice);

        serviceDescriptionTitle.setText(serviceName + " Description");

        ref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean addressRequired = Boolean.parseBoolean(dataSnapshot.child(serviceName).child("address").getValue().toString());
                boolean dateOfBirthRequired = Boolean.parseBoolean(dataSnapshot.child(serviceName).child("dateOfBirth").getValue().toString());
                boolean proofOfPhotoRequired = Boolean.parseBoolean(dataSnapshot.child(serviceName).child("proofOfPhoto").getValue().toString());
                boolean proofOfResidenceRequired = Boolean.parseBoolean(dataSnapshot.child(serviceName).child("proofOfResidence").getValue().toString());
                boolean proofOfStatusRequired = Boolean.parseBoolean(dataSnapshot.child(serviceName).child("proofOfStatus").getValue().toString());
                boolean typeOfLicenseRequired = Boolean.parseBoolean(dataSnapshot.child(serviceName).child("typeOfLicense").getValue().toString());
                String servicePriceValue = dataSnapshot.child(serviceName).child("price").getValue().toString();

                if (addressRequired) {
                    addressRequiredDescription.setText("Address is required");
                } else {
                    addressRequiredDescription.setText("Address is NOT required");
                }

                if (dateOfBirthRequired) {
                    dateOfBirthRequiredDescription.setText("Date of birth is required");
                } else {
                    dateOfBirthRequiredDescription.setText("Date of birth is NOT required");
                }

                if (proofOfPhotoRequired) {
                    proofOfPhotoRequiredDescription.setText("Photo proof is required");
                } else {
                    proofOfPhotoRequiredDescription.setText("Photo proof is NOT required");
                }

                if (proofOfResidenceRequired) {
                    proofOfResidenceRequiredDescription.setText("Proof of residence is required");
                } else {
                    proofOfResidenceRequiredDescription.setText("Proof of residence is NOT required");
                }

                if (proofOfStatusRequired) {
                    proofOfStatusRequiredDescription.setText("Proof of status is required");
                } else {
                    proofOfStatusRequiredDescription.setText("Proof of status is NOT required");
                }

                if (typeOfLicenseRequired) {
                    typeOfLicenseRequiredDescription.setText("Type of license is required");
                } else {
                    typeOfLicenseRequiredDescription.setText("Type of license is NOT required");
                }

                servicePrice.setText("Service price: $" + servicePriceValue);

                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
