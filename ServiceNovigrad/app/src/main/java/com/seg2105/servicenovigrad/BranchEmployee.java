package com.seg2105.servicenovigrad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class BranchEmployee extends User {
    public BranchEmployee() {}

    public BranchEmployee(String firstname, String lastname, String email, String username, String password, String branch, String numberEmployee, boolean isLoggedIn) {
        super(firstname,lastname, email, username, password, branch, numberEmployee, isLoggedIn);
    }

    public void updateBranch(String employeeUsername, String newBranchName, String branchAddress, String branchPhoneNumber, String branchServices,
                             boolean mondayOpen, boolean tuesdayOpen, boolean wednesdayOpen, boolean thursdayOpen, boolean fridayOpen,
                             boolean saturdayOpen, boolean sundayOpen, String mondayWorkingHours, String tuesdayWorkingHours,
                             String wednesdayWorkingHours, String thursdayWorkingHours, String fridayWorkingHours,
                             String saturdayWorkingHours, String sundayWorkingHours, String branchRating, String branchRatingVoteCount) {
        DatabaseReference dR;

        dR = FirebaseDatabase.getInstance().getReference("Branches").child(employeeUsername + " Branch");
        Branch branchRequest = new Branch(newBranchName, branchAddress, branchPhoneNumber, branchServices, mondayOpen,
                tuesdayOpen, wednesdayOpen, thursdayOpen, fridayOpen, saturdayOpen, sundayOpen, mondayWorkingHours,
                tuesdayWorkingHours, wednesdayWorkingHours, thursdayWorkingHours, fridayWorkingHours,
                saturdayWorkingHours, sundayWorkingHours, branchRating, branchRatingVoteCount);
        dR.removeValue();
        dR.getParent().child(employeeUsername + " Branch").setValue(branchRequest);
    }

    public void updateWorkingHours(String employeeUsername, String branchName, String branchAddress, String branchPhoneNumber, String branchServices,
                             boolean mondayOpen, boolean tuesdayOpen, boolean wednesdayOpen, boolean thursdayOpen, boolean fridayOpen,
                             boolean saturdayOpen, boolean sundayOpen, String mondayWorkingHours, String tuesdayWorkingHours,
                             String wednesdayWorkingHours, String thursdayWorkingHours, String fridayWorkingHours,
                             String saturdayWorkingHours, String sundayWorkingHours, String branchRating, String branchRatingVoteCount) {
        DatabaseReference dR;

        dR = FirebaseDatabase.getInstance().getReference("Branches").child(employeeUsername + " Branch");
        Branch branchRequest = new Branch(branchName, branchAddress, branchPhoneNumber, branchServices, mondayOpen,
                tuesdayOpen, wednesdayOpen, thursdayOpen, fridayOpen, saturdayOpen, sundayOpen, mondayWorkingHours,
                tuesdayWorkingHours, wednesdayWorkingHours, thursdayWorkingHours, fridayWorkingHours,
                saturdayWorkingHours, sundayWorkingHours, branchRating, branchRatingVoteCount);
        dR.removeValue();
        dR.getParent().child(employeeUsername + " Branch").setValue(branchRequest);
    }

    public void updateServices(String employeeUsername, String branchName, String branchAddress, String branchPhoneNumber, String branchServices,
                               boolean mondayOpen, boolean tuesdayOpen, boolean wednesdayOpen, boolean thursdayOpen, boolean fridayOpen,
                               boolean saturdayOpen, boolean sundayOpen, String mondayWorkingHours, String tuesdayWorkingHours,
                               String wednesdayWorkingHours, String thursdayWorkingHours, String fridayWorkingHours,
                               String saturdayWorkingHours, String sundayWorkingHours, String branchRating, String branchRatingVoteCount){
        DatabaseReference dR;

        dR = FirebaseDatabase.getInstance().getReference("Branches").child(employeeUsername + " Branch");
        Branch branchRequest = new Branch(branchName, branchAddress, branchPhoneNumber, branchServices, mondayOpen,
                tuesdayOpen, wednesdayOpen, thursdayOpen, fridayOpen, saturdayOpen, sundayOpen, mondayWorkingHours,
                tuesdayWorkingHours, wednesdayWorkingHours, thursdayWorkingHours, fridayWorkingHours,
                saturdayWorkingHours, sundayWorkingHours, branchRating, branchRatingVoteCount);
        dR.removeValue();
        dR.getParent().child(employeeUsername + " Branch").setValue(branchRequest);
    }

    public void approveRequest(final String requestNumber) {
        final DatabaseReference requestDR;
        final DatabaseReference approvedDR;

        requestDR = FirebaseDatabase.getInstance().getReference("Requests");
        approvedDR = FirebaseDatabase.getInstance().getReference("ApprovedRequests");

        final String[] serviceUsername = new String[1];
        final String[] serviceAddress = new String[1];
        final String[] serviceDateOfBirth = new String[1];
        final String[] serviceName = new String[1];
        final String[] serviceBranchName = new String[1];
        final boolean[] serviceProofOfPhoto = new boolean[1];
        final boolean[] serviceProofOfResidence = new boolean[1];
        final boolean[] serviceProofOfStatus = new boolean[1];
        final boolean[] serviceProofOfLicense = new boolean[1];

        final boolean[] serviceApproved = {false};

        requestDR.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if (postSnapshot.getKey().equals(requestNumber) && !serviceApproved[0]) {
                        serviceUsername[0] = postSnapshot.child("customerUsername").getValue().toString();
                        serviceAddress[0] = postSnapshot.child("address").getValue().toString();
                        serviceDateOfBirth[0] = postSnapshot.child("dateOfBirth").getValue().toString();
                        serviceName[0] = postSnapshot.child("serviceName").getValue().toString();
                        serviceProofOfPhoto[0] = Boolean.parseBoolean(postSnapshot.child("proofOfPhotoAttached").getValue().toString());
                        serviceProofOfResidence[0] = Boolean.parseBoolean(postSnapshot.child("proofOfResidenceAttached").getValue().toString());
                        serviceProofOfStatus[0] = Boolean.parseBoolean(postSnapshot.child("proofOfStatusAttached").getValue().toString());
                        serviceProofOfLicense[0] = Boolean.parseBoolean(postSnapshot.child("proofOfLicenseAttached").getValue().toString());
                        serviceBranchName[0] = postSnapshot.child("branchName").getValue().toString();

                        postSnapshot.getRef().removeValue();

                        serviceApproved[0] = true;

                        Request approvedRequest = new Request(serviceUsername[0], serviceBranchName[0], serviceName[0], serviceDateOfBirth[0],
                                serviceAddress[0], serviceProofOfPhoto[0], serviceProofOfResidence[0], serviceProofOfLicense[0], serviceProofOfStatus[0]);

                        approvedDR.child(String.valueOf(requestNumber)).setValue(approvedRequest);
                        break;
                    }
                }

                requestDR.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void declineRequest(final String requestNumber) {
        final DatabaseReference requestDR;
        final DatabaseReference rejectedDR;

        requestDR = FirebaseDatabase.getInstance().getReference("Requests");
        rejectedDR = FirebaseDatabase.getInstance().getReference("RejectedRequests");

        final String[] serviceUsername = new String[1];
        final String[] serviceAddress = new String[1];
        final String[] serviceDateOfBirth = new String[1];
        final String[] serviceName = new String[1];
        final String[] serviceBranchName = new String[1];
        final boolean[] serviceProofOfPhoto = new boolean[1];
        final boolean[] serviceProofOfResidence = new boolean[1];
        final boolean[] serviceProofOfStatus = new boolean[1];
        final boolean[] serviceProofOfLicense = new boolean[1];

        final boolean[] serviceApproved = {false};

        requestDR.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if (postSnapshot.getKey().equals(requestNumber) && !serviceApproved[0]) {
                        serviceUsername[0] = postSnapshot.child("customerUsername").getValue().toString();
                        serviceAddress[0] = postSnapshot.child("address").getValue().toString();
                        serviceDateOfBirth[0] = postSnapshot.child("dateOfBirth").getValue().toString();
                        serviceName[0] = postSnapshot.child("serviceName").getValue().toString();
                        serviceProofOfPhoto[0] = Boolean.parseBoolean(postSnapshot.child("proofOfPhotoAttached").getValue().toString());
                        serviceProofOfResidence[0] = Boolean.parseBoolean(postSnapshot.child("proofOfResidenceAttached").getValue().toString());
                        serviceProofOfStatus[0] = Boolean.parseBoolean(postSnapshot.child("proofOfStatusAttached").getValue().toString());
                        serviceProofOfLicense[0] = Boolean.parseBoolean(postSnapshot.child("proofOfLicenseAttached").getValue().toString());
                        serviceBranchName[0] = postSnapshot.child("branchName").getValue().toString();

                        postSnapshot.getRef().removeValue();

                        serviceApproved[0] = true;

                        Request approvedRequest = new Request(serviceUsername[0], serviceBranchName[0], serviceName[0], serviceDateOfBirth[0],
                                serviceAddress[0], serviceProofOfPhoto[0], serviceProofOfResidence[0], serviceProofOfLicense[0], serviceProofOfStatus[0]);

                        rejectedDR.child(String.valueOf(requestNumber)).setValue(approvedRequest);
                        break;
                    }
                }

                requestDR.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
