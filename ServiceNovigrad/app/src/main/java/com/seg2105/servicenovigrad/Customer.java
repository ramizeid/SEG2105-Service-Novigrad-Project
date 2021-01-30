package com.seg2105.servicenovigrad;

import android.annotation.SuppressLint;
import android.graphics.Color;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Customer extends User{
    DatabaseReference reference;
    FirebaseDatabase firebase;
    long requestId;
    Request globalRequest;

    public Customer() {
    }

    public Customer(String firstname, String lastname, String email, String username, String password, String branch, String numberEmployee, boolean isLoggedIn) {
        super(firstname,lastname, email, username, password, branch, numberEmployee, isLoggedIn);
    }
    
    public void submitServiceRequest(Request request){
        firebase = FirebaseDatabase.getInstance();
        reference = firebase.getReference("Requests");
        globalRequest = request;
        final Query lastQuery = reference.orderByKey().limitToLast(1);

        lastQuery.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long lastId = 0;

                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        lastId = Long.parseLong(snapshot.getKey());
                        break;
                    }

                    requestId = lastId + 1;
                } else {
                    requestId = 1;
                }

                reference.child(String.valueOf(requestId)).setValue(globalRequest);
                reference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void submitBranchRating(final String branchName, final float newBranchRating, final long newBranchRatingVoteCount) {
        firebase = FirebaseDatabase.getInstance();
        reference = firebase.getReference("Branches");
        final boolean[] snapshotFound = {false};

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if (postSnapshot.getKey().equals(branchName) && !snapshotFound[0]) {
                        reference.child(postSnapshot.getKey()).child("branchRating").setValue(String.valueOf(newBranchRating));
                        reference.child(postSnapshot.getKey()).child("branchRatingVoteCount").setValue(String.valueOf(newBranchRatingVoteCount));
                        snapshotFound[0] = true;

                        break;
                    }
                }

                reference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void cancelRequest(final String requestNumber) {
        firebase = FirebaseDatabase.getInstance();
        reference = firebase.getReference("Requests");

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if (postSnapshot.getKey().equals(requestNumber)) {
                        reference.child(postSnapshot.getKey()).removeValue();

                        break;
                    }
                }

                reference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
