package com.seg2105.servicenovigrad;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class AdminAccountsPage extends AppCompatActivity {
    ListView accountsListView;
    ArrayList<User> userList;
    FirebaseDatabase firebase;
    DatabaseReference ref;
    private final int normalVibrateValue = 10;
    private final int lowVibrateValue = normalVibrateValue / 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_accounts_page);

        accountsListView = (ListView) findViewById(R.id.accountsListView);
        userList = new ArrayList<>();
        firebase = FirebaseDatabase.getInstance();
        ref = firebase.getReference("user_information");


        /**
         * When holding on a users name it will prompt a dialog for asking if you would like to delete user
         */
        accountsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                User users = userList.get(i);
                deleteAccounts(users);
                return true;
            }
        });

        accountsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);

                User user = userList.get(i);
                listViewClick(user.getUsername());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    userList.add(user);
                }

                AccountList accountList = new AccountList(AdminAccountsPage.this, userList);
                accountsListView.setAdapter(accountList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void listViewClick(final String username) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.administrator_account_details_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AdminAccountsPage.this);
        dialogBuilder.setView(dialogView);

        final TextView accountDescriptionTitle = (TextView) dialogView.findViewById(R.id.accountDescriptionTitle);
        final TextView usernameDescription = (TextView) dialogView.findViewById(R.id.usernameDescription);
        final TextView firstNameDescription = (TextView) dialogView.findViewById(R.id.firstNameDescription);
        final TextView lastNameDescription = (TextView) dialogView.findViewById(R.id.lastNameDescription);
        final TextView emailDescription = (TextView) dialogView.findViewById(R.id.emailDescription);
        final TextView accountTypeDescription = (TextView) dialogView.findViewById(R.id.accountTypeDescription);
        final TextView loginStatusDescription = (TextView) dialogView.findViewById(R.id.loginStatusDescription);

        accountDescriptionTitle.setText(username + "'s Account Description");

        ref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String firstName = dataSnapshot.child(username).child("firstname").getValue().toString();
                String lastName = dataSnapshot.child(username).child("lastname").getValue().toString();
                String email = dataSnapshot.child(username).child("email").getValue().toString();
                String accountType = dataSnapshot.child(username).child("branch").getValue().toString();
                String employeeNumber = dataSnapshot.child(username).child("numberEmployee").getValue().toString();
                boolean loginStatus = Boolean.parseBoolean(dataSnapshot.child(username).child("loginStatus").getValue().toString());

                if (accountType.toLowerCase().equals("admin")) {
                    accountType = "Admin Account";
                } else if (accountType.toLowerCase().equals("customer")) {
                    accountType = "Customer Account";
                } else if (accountType.toLowerCase().equals("employee")) {
                    accountType = "Branch Employee Account \n" + "Employee Number: " + employeeNumber;
                }

                usernameDescription.setText(username);
                firstNameDescription.setText(firstName);
                lastNameDescription.setText(lastName);
                emailDescription.setText(email);
                accountTypeDescription.setText(accountType);

                if (loginStatus) {
                    loginStatusDescription.setText("Online");
                } else {
                    loginStatusDescription.setText("Offline");
                }

                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    public void deleteAccounts(final User user) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.administrator_delete_accounts_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        final Button deleteAccountsButton = (Button) dialogView.findViewById(R.id.approveRequestButton);
        final Administrator admin = new Administrator();

        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();


        /**
         * Account deletion is done here
         */
        deleteAccountsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);

                if (user.getBranch().toLowerCase().equals("admin")) {
                    Toast.makeText(AdminAccountsPage.this, "Error: Admin accounts cannot be deleted!", Toast.LENGTH_SHORT).show();
                } else if (user.getBranch().toLowerCase().equals("employee")) {
                    admin.deleteSelectedAccount(user.getUsername().toLowerCase());
                    admin.deleteSelectedBranch(user.getUsername().toLowerCase() + " Branch");
                    dialog.dismiss();

                    Toast.makeText(AdminAccountsPage.this, "Successfully deleted account", Toast.LENGTH_SHORT).show();
                } else {
                    admin.deleteSelectedAccount(user.getUsername().toLowerCase());
                    dialog.dismiss();

                    Toast.makeText(AdminAccountsPage.this, "Successfully deleted account", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
