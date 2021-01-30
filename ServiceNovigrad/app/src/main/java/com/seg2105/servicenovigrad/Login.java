package com.seg2105.servicenovigrad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Login extends AppCompatActivity {
    private int normalVibrateValue = 10;
    private int lowVibrateValue = normalVibrateValue / 2;
    FirebaseDatabase firebase;
    DatabaseReference referencePassword;
    Button btnLogin;
    EditText usernameTextBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*************************** Variables **********************************/
        final TextView forgotPassword = (TextView) findViewById(R.id.forgotPasswordTextView);
        // TextView newAccount = (TextView) findViewById(R.id.newlyRegisteredAccount);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        usernameTextBox = (EditText) findViewById(R.id.usernameTextBox);
        final EditText passwordTextBox = (EditText) findViewById(R.id.passwordTextBox);
        /************************************************************************/


        /**
         *
         * This is performing validation operation when user press the login button, we need to be able to cross reference with
         * the firebase database
         */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                firebase = FirebaseDatabase.getInstance();
                referencePassword = firebase.getReference("user_information");

                referencePassword.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        ArrayList<Object> arr = new ArrayList<>();
                        String username = (usernameTextBox.getText().toString()).length() > 0
                                ? usernameTextBox.getText().toString().trim().toLowerCase()
                                : "";
                        String password = (passwordTextBox.getText().toString()).length() > 0
                                ? passwordTextBox.getText().toString().trim()
                                : "";

                        if (username == "" || password == "") {
                            usernameTextBox.setError("Username is invalid");
                            passwordTextBox.setError("Password is invalid");
                        } else {
                            arr.add(snapshot.child(username).getValue());
                            arr.add(snapshot.child(username).child(password).getValue());

                            // checks if user are existing in the database it will give null reference if
                            // not found
                            if (arr.get(0) == null && arr.get(1) == null) {
                                usernameTextBox.setError("Username is invalid");
                                passwordTextBox.setError("Password is invalid");
                            } else {

                                String passworddb = snapshot.child(username).child("password").getValue().toString();
                                String usernamedb = snapshot.child(username).child("username").getValue().toString();

                                // hash password if it is not admin account
                                if (!username.equals("admin")) {
                                    password = hashPassword(password, usernamedb);
                                }

                                if (username.equals(usernamedb) && password.equals(passworddb)) {
                                    Intent intent;
                                    String role = snapshot.child(usernamedb).child("branch").getValue().toString();

                                    if (role.equals("admin")) {
                                        intent = new Intent(Login.this, AdminWelcomePage.class);
                                        intent.putExtra("EXTRA_USERNAME", username);
                                    } else if (role.equals("employee")) {
                                        intent = new Intent(Login.this, EmployeeWelcomePage.class);
                                        intent.putExtra("EXTRA_USERNAME", username);
                                    } else {
                                        intent = new Intent(Login.this, CustomerWelcomePage.class);
                                        intent.putExtra("EXTRA_USERNAME", username);
                                    }

                                    Bundle userBundle = new Bundle();
                                    userBundle.putString("userN", username);
                                    intent.putExtras(userBundle);
                                    startActivity(intent);

                                    referencePassword.child(usernamedb).child("loginStatus").setValue("true");

                                    Toast toast;
                                    Toast.makeText(Login.this, "Successfully logged in as " + username,
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    passwordTextBox.setError("Username could not be found or password invalid");
                                }

                                referencePassword.removeEventListener(this);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

    }


    /**
     * This method is responsible to hash password of the user in our firebase database
     *
     * @param password
     * @param salt
     * @return
     */
    private static String hashPassword(String password, String salt) {
        StringBuilder hashedPassword = new StringBuilder();
        StringBuilder hashedSalt = new StringBuilder();
        StringBuilder finalHashedPassword = new StringBuilder();

        try {
            // Convert password & salt to bytes
            byte[] passwordBytes = password.getBytes();
            byte[] saltBytes = salt.getBytes();

            // Start of the hashing process of both, the password & salt
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passwordHashStart = md.digest(passwordBytes);
            byte[] saltHashStart = md.digest(saltBytes);

            // Converting the byte arrays into signed versions
            BigInteger hashSigned = new BigInteger(1, passwordHashStart);
            BigInteger saltSigned = new BigInteger(1, saltHashStart);

            // Converting the signed versions to actual hashes
            hashedPassword = new StringBuilder(hashSigned.toString(16));
            hashedSalt = new StringBuilder(saltSigned.toString(16));

            // Making sure that each hash is 32 characters in length
            while (hashedPassword.length() < 32) {
                hashedPassword.insert(0, "0");
            }

            while (hashedSalt.length() < 32) {
                hashedSalt.insert(0, "0");
            }

            /*
             * / Combining the password & salt's hashes by converting them into numbers.
             * This is an extra layer of security which completely hides any visible pattern
             * in the hashes. /
             */
            for (int i = 0; i < 32; i++) {
                int hashedPasswordCharValue = hashedPassword.charAt(i);
                int hashedSaltCharValue = hashedSalt.charAt(i);
                int hashedSum = hashedPasswordCharValue + hashedSaltCharValue;

                finalHashedPassword.append(hashedSum);
            }
        } catch (Exception e) {
            System.out.println("The following error has occurred while hashing the password: " + e);
        }

        return finalHashedPassword.toString();
    }

}
