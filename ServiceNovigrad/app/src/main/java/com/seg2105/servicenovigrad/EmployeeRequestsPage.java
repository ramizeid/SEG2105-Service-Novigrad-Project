package com.seg2105.servicenovigrad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class EmployeeRequestsPage extends AppCompatActivity {
    String employeeUsername;
    ListView requestsListView;
    ArrayList<String> requests;
    FirebaseDatabase firebase;
    DatabaseReference requestsRef;
    DatabaseReference servicesRef;
    DatabaseReference refUser;
    RequestsList requestsAdapter;
    ListView servicesListView;
    BranchEmployee employee = new BranchEmployee();

    TextView firstNameTextView;
    TextView lastNameTextView;
    String serviceUsername;
    String serviceAddress;
    String serviceDateOfBirth;
    String serviceName;
    boolean serviceProofOfPhoto;
    boolean serviceProofOfResidence;
    boolean serviceProofOfStatus;
    boolean serviceProofOfLicense;

    private final int normalVibrateValue = 10;
    private final int lowVibrateValue = normalVibrateValue / 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_requests_page);

        employeeUsername = getIntent().getStringExtra("EXTRA_USERNAME");
        requestsListView = (ListView) findViewById(R.id.requestsListView);
        firebase = FirebaseDatabase.getInstance();
        servicesRef = firebase.getReference("ServiceRequests");
        requestsRef = firebase.getReference("Requests");
        refUser = firebase.getReference("user_information");
        requests = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        requestsRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                requestsAdapter = new RequestsList(EmployeeRequestsPage.this, requests);
                requestsListView.setAdapter(requestsAdapter);

                /**
                 * IMPORTANT NOTE!
                 * Arraylist Requests should contain objects of types requests for the next deliverable
                 */
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if (postSnapshot.child("branchName").getValue().toString().equals(employeeUsername + " Branch")) {
                        String requestNumber = postSnapshot.getKey();

                        requests.add("#" + requestNumber);
                    }
                }

                requestsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vb.vibrate(lowVibrateValue);

                        String requestNumber = String.valueOf(requests.get(i));

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            if (postSnapshot.getKey().equals(requestNumber.split("#")[1])) {
                                serviceUsername = postSnapshot.child("customerUsername").getValue().toString();
                                serviceAddress = postSnapshot.child("address").getValue().toString();
                                serviceDateOfBirth = postSnapshot.child("dateOfBirth").getValue().toString();
                                serviceName = postSnapshot.child("serviceName").getValue().toString();
                                serviceProofOfPhoto = Boolean.parseBoolean(postSnapshot.child("proofOfPhotoAttached").getValue().toString());
                                serviceProofOfResidence = Boolean.parseBoolean(postSnapshot.child("proofOfResidenceAttached").getValue().toString());
                                serviceProofOfStatus = Boolean.parseBoolean(postSnapshot.child("proofOfStatusAttached").getValue().toString());
                                serviceProofOfLicense = Boolean.parseBoolean(postSnapshot.child("proofOfLicenseAttached").getValue().toString());
                            }
                        }

                        listViewClick(requestNumber);
                    }
                });

                requestsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String requestNumber = String.valueOf(requests.get(i));
                        approveOrDeclineRequest(requestNumber);
                        return true;
                    }
                });

                requestsRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    /**
     *
     * This method is responsible for approving or rejecting incoming requests that can be found on the branch
     * employee profile
     * @param requestNumber
     */
    public void approveOrDeclineRequest(final String requestNumber) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.employee_approve_decline_request_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        final Button approveRequestButton = (Button) dialogView.findViewById(R.id.approveRequestButton);
        final Button declineRequestButton = (Button) dialogView.findViewById(R.id.declineRequestButton);

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        approveRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                String actualRequestNumber = requestNumber.split("#")[1];

                employee.approveRequest(actualRequestNumber);
                requestsAdapter.notifyDataSetChanged();

                Toast.makeText(EmployeeRequestsPage.this, "Successfully approved request #" + actualRequestNumber,
                        Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });

        declineRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                String actualRequestNumber = requestNumber.split("#")[1];

                employee.declineRequest(actualRequestNumber);
                requestsAdapter.notifyDataSetChanged();

                Toast.makeText(EmployeeRequestsPage.this, "Successfully rejected request #" + actualRequestNumber,
                        Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void listViewClick(final String requestNumber) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.employee_request_detail_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EmployeeRequestsPage.this);
        dialogBuilder.setView(dialogView);

        firstNameTextView = (TextView) dialogView.findViewById(R.id.firstName);
        lastNameTextView = (TextView) dialogView.findViewById(R.id.lastName);
        final TextView serviceDescriptionTitle = (TextView) dialogView.findViewById(R.id.serviceNameDescription);
        final TextView requestIdDescription = (TextView) dialogView.findViewById(R.id.requestIdDescription);
        final TextView addressDescription = (TextView) dialogView.findViewById(R.id.addressDescription);
        final TextView dateOfBirthDescription = (TextView) dialogView.findViewById(R.id.dateOfBirthDescription);
        final TextView proofOfPhotoDescription = (TextView) dialogView.findViewById(R.id.proofOfPhotoDescription);
        final TextView proofOfResidenceDescription = (TextView) dialogView.findViewById(R.id.proofOfResidenceDescription);
        final TextView proofOfStatusDescription = (TextView) dialogView.findViewById(R.id.proofOfStatusDescription);
        final TextView proofOfLicenseDescription = (TextView) dialogView.findViewById(R.id.proofOfLicenseDescription);
        final TextView recommendedActionDescription = (TextView) dialogView.findViewById(R.id.recommendedActionDescription);

        final boolean[] addressRequired = new boolean[1];
        final boolean[] dateOfBirthRequired = new boolean[1];
        final boolean[] proofOfPhotoRequired = new boolean[1];
        final boolean[] proofOfResidenceRequired = new boolean[1];
        final boolean[] proofOfStatusRequired = new boolean[1];
        final boolean[] proofOfLicenseRequired = new boolean[1];

        servicesRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if (postSnapshot.getKey().equals(serviceName)) {
                        addressRequired[0] = Boolean.parseBoolean(postSnapshot.child("address").getValue().toString());
                        dateOfBirthRequired[0] = Boolean.parseBoolean(postSnapshot.child("dateOfBirth").getValue().toString());
                        proofOfPhotoRequired[0] = Boolean.parseBoolean(postSnapshot.child("proofOfPhoto").getValue().toString());
                        proofOfResidenceRequired[0] = Boolean.parseBoolean(postSnapshot.child("proofOfResidence").getValue().toString());
                        proofOfStatusRequired[0] = Boolean.parseBoolean(postSnapshot.child("proofOfStatus").getValue().toString());
                        proofOfLicenseRequired[0] = Boolean.parseBoolean(postSnapshot.child("typeOfLicense").getValue().toString());

                        break;
                    }
                }

                int requiredCount = 0;
                int givenCount = 0;

                serviceDescriptionTitle.setText(serviceName + " Request");
                requestIdDescription.setText(requestNumber);

                fetchFullName(serviceUsername);

                if (addressRequired[0]) {
                    requiredCount++;

                    if (!serviceAddress.equals("")) {
                        addressDescription.setTextColor(Color.rgb(0, 137, 61));
                        addressDescription.setText(serviceAddress);

                        givenCount ++;
                    } else{
                        addressDescription.setTextColor(Color.RED);
                        addressDescription.setText("Address is NOT given");
                    }
                } else {
                    addressDescription.setText("Address is NOT required");
                }

                if (dateOfBirthRequired[0]) {
                    requiredCount++;

                    if (!serviceDateOfBirth.equals("")) {
                        dateOfBirthDescription.setTextColor(Color.rgb(0, 137, 61));
                        dateOfBirthDescription.setText(serviceDateOfBirth);

                        givenCount ++;
                    } else{
                        dateOfBirthDescription.setTextColor(Color.RED);
                        dateOfBirthDescription.setText("Date of birth is NOT given");
                    }
                } else {
                    dateOfBirthDescription.setText("Date of birth is NOT required");
                }

                if (proofOfPhotoRequired[0]) {
                    requiredCount++;

                    if (serviceProofOfPhoto) {
                        proofOfPhotoDescription.setTextColor(Color.rgb(0, 137, 61));
                        proofOfPhotoDescription.setText("Proof of photo is given");

                        givenCount ++;
                    } else{
                        proofOfPhotoDescription.setTextColor(Color.RED);
                        proofOfPhotoDescription.setText("Proof of photo is NOT given");
                    }
                } else {
                    proofOfPhotoDescription.setText("Proof of photo is NOT required");
                }

                if (proofOfResidenceRequired[0]) {
                    requiredCount++;

                    if (serviceProofOfResidence) {
                        proofOfResidenceDescription.setTextColor(Color.rgb(0, 137, 61));
                        proofOfResidenceDescription.setText("Proof of residence is given");

                        givenCount ++;
                    } else{
                        proofOfResidenceDescription.setTextColor(Color.RED);
                        proofOfResidenceDescription.setText("Proof of residence is NOT given");
                    }
                } else {
                    proofOfResidenceDescription.setText("Proof of residence is NOT required");
                }

                if (proofOfStatusRequired[0]) {
                    requiredCount++;

                    if (serviceProofOfStatus) {
                        proofOfStatusDescription.setTextColor(Color.rgb(0, 137, 61));
                        proofOfStatusDescription.setText("Proof of status is given");

                        givenCount ++;
                    } else{
                        proofOfStatusDescription.setTextColor(Color.RED);
                        proofOfStatusDescription.setText("Proof of status is NOT given");
                    }
                } else {
                    proofOfStatusDescription.setText("Proof of status is NOT required");
                }

                if (proofOfLicenseRequired[0]) {
                    requiredCount++;

                    if (serviceProofOfLicense) {
                        proofOfLicenseDescription.setTextColor(Color.rgb(0, 137, 61));
                        proofOfLicenseDescription.setText("Proof of license is given");

                        givenCount ++;
                    } else{
                        proofOfLicenseDescription.setTextColor(Color.RED);
                        proofOfLicenseDescription.setText("Proof of license is NOT given");
                    }
                } else {
                    proofOfLicenseDescription.setText("Proof of license is NOT required");
                }

                if (requiredCount == givenCount) {
                    recommendedActionDescription.setTextColor(Color.rgb(0, 137, 61));
                    recommendedActionDescription.setText("Approve request");
                } else {
                    recommendedActionDescription.setTextColor(Color.RED);
                    recommendedActionDescription.setText("Reject request");
                }

                servicesRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    public void fetchFullName(final String username){
        refUser.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String firstName = snapshot.child(username).child("firstname").getValue().toString();
                String lastName = snapshot.child(username).child("lastname").getValue().toString();

                firstNameTextView.setText(firstName);
                lastNameTextView.setText(lastName);

                refUser.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}

