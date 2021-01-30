package com.seg2105.servicenovigrad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Rating;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
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
import java.math.*;

public class CustomerPreviousRequestPage extends AppCompatActivity {
    private final int normalVibrateValue = 10;
    private final int lowVibrateValue = normalVibrateValue / 2;
    FirebaseDatabase firebase;
    DatabaseReference requestsRef;
    DatabaseReference branchesRef;
    private String customerUsername;
    RequestsList requestsAdapter;
    ListView requestsListView;
    ArrayList<String> requests;
    String branchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_previous_requests);

        customerUsername = getIntent().getStringExtra("EXTRA_USERNAME");
        requestsListView = (ListView) findViewById(R.id.requestsListView);

        firebase = FirebaseDatabase.getInstance();
        requestsRef = firebase.getReference("ApprovedRequests");
        branchesRef = firebase.getReference("Branches");
        requests = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestsRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestsAdapter = new RequestsList(CustomerPreviousRequestPage.this, requests);
                requestsListView.setAdapter(requestsAdapter);

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if (postSnapshot.child("customerUsername").getValue().toString().equals(customerUsername)) {
                        String requestNumber = postSnapshot.getKey();
                        branchName = postSnapshot.child("branchName").getValue().toString();

                        requests.add("#" + requestNumber);
                    }
                }

                requestsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vb.vibrate(lowVibrateValue);

                        String requestNumber = String.valueOf(requests.get(i));
                        rateBranch(requestNumber);

                        return true;
                    }
                });

                requestsRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void rateBranch(final String requestNumber) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.customer_rate_branch_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        final Button submitReviewButton = (Button) dialogView.findViewById(R.id.submitReviewButton);
        final RatingBar ratingBar = (RatingBar) dialogView.findViewById(R.id.branchRatingBar);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        submitReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                final float userRating = ratingBar.getRating();
                final boolean[] snapshotFound = {false};

                branchesRef.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Customer customer = new Customer();

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            if (postSnapshot.getKey().equals(branchName) && !snapshotFound[0]) {
                                float currentBranchRating = Float.parseFloat(postSnapshot.child("branchRating").getValue().toString());
                                long currentBranchRatingVoteCount = Long.parseLong(postSnapshot.child("branchRatingVoteCount").getValue().toString());

                                float newBranchRating = Float.parseFloat(String.valueOf(Math.round((((currentBranchRating * currentBranchRatingVoteCount) + userRating)/(currentBranchRatingVoteCount + 1)) * 100)/100.0));
                                long newBranchRatingVoteCount = currentBranchRatingVoteCount + 1;

                                if (userRating == 0) {
                                    submitReviewButton.setError("Your rating must be greater than 0");
                                } else {
                                    customer.submitBranchRating(branchName, newBranchRating, newBranchRatingVoteCount);
                                    snapshotFound[0] = true;

                                    Toast.makeText(CustomerPreviousRequestPage.this, "Successfully rated the branch",
                                            Toast.LENGTH_SHORT).show();

                                    dialog.dismiss();
                                    break;
                                }
                            }
                        }

                        requestsRef.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        });
    }
}
