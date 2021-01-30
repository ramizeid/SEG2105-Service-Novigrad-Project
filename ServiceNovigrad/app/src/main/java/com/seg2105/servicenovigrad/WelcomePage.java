package com.seg2105.servicenovigrad;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class WelcomePage extends Login {

    FirebaseDatabase firebase;
    DatabaseReference reference;
    User userInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        //initialize firebase database
        firebase = FirebaseDatabase.getInstance();
        reference = firebase.getReference("user_information");

        //DB listener
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot){

                //retrieving username from login bundle
                Bundle user = getIntent().getExtras();
                String username = (String) user.getCharSequence("userN");

                //retrieving first name and branch from database , using username
                String firstNameDB = snapshot.child(username).child("firstname").getValue().toString();
                String lastname = snapshot.child(username).child("lastname").getValue().toString();
                String email = snapshot.child(username).child("email").getValue().toString();
                String usernamedb = snapshot.child(username).child("username").getValue().toString();
                String password = snapshot.child(username).child("password").getValue().toString();
                String roleDB = snapshot.child(username).child("branch").getValue().toString();
                String numberEmployee = snapshot.child(username).child("numberEmployee").getValue().toString();


                /**
                 * IN these three if statements we are taking from the existing databse and we are instantiating the users accordingly
                 * to their roles. Their classes will be useful later on in the project since each one consists of different methods and functionality
                 * */
                if (roleDB.equals("customer")) {
                    roleDB = "customer";
                    userInstance = new Customer(firstNameDB,lastname, email,usernamedb,password, roleDB, numberEmployee, true);
                } else if (roleDB.equals("admin")) {
                    roleDB = "admin";
                    userInstance = new Administrator(firstNameDB,lastname, email,usernamedb,password, roleDB, numberEmployee, true);
                } else {
                    roleDB = "employee";
                    userInstance = new BranchEmployee(firstNameDB,lastname, email,usernamedb,password, roleDB, numberEmployee, true);
                }

                //Displays the welcome message and logged in role
                displayName(userInstance.getFirstname(), userInstance.getBranch());
                reference.removeEventListener(this);
            }


            @SuppressLint("SetTextI18n")
            public void displayName(String firstName, String role){
                TextView displayedName = (TextView)findViewById(R.id.welcomeNameText);
                displayedName.setText(displayedName.getText()+" "+ firstName);
                TextView displayedRole = (TextView)findViewById(R.id.signedRoleText );

                if (userInstance.getBranch().equals("customer")) {
                    displayedRole.setText(displayedRole.getText() + " a " + role);
                } else {
                    displayedRole.setText(displayedRole.getText() + " an " + role);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        finish();
    }
}