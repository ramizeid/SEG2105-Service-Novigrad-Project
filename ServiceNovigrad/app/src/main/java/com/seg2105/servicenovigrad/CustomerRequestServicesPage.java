package com.seg2105.servicenovigrad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
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
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomerRequestServicesPage extends AppCompatActivity {
    private final int normalVibrateValue = 10;
    private final int lowVibrateValue = normalVibrateValue / 2;
    private FirebaseDatabase firebase;
    private DatabaseReference ref;
    private DatabaseReference serviceref;
    private String customerUsername;
    private EditText customerBranchNameTextView;
    private EditText customerAddressTextView;
    private Button extraCriteriaButton;
    private Button filterSearchButton;


    private ArrayList<Service> customerserivces;
    private final ArrayList<Object> extraCriteriaFilters = new ArrayList<>();
    private final ArrayList<Branch> branches = new ArrayList<>();
    private ListView branchesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_services_page);

        for (int i = 0; i < 10; i++) {
            if (i < 7) {
                extraCriteriaFilters.add(false);
            } else {
                extraCriteriaFilters.add("");
            }
        }

        customerUsername = getIntent().getStringExtra("EXTRA_USERNAME");
        customerBranchNameTextView = findViewById(R.id.customerBranchNameTextView);
        customerAddressTextView = findViewById(R.id.customerAddressTextView);

        extraCriteriaButton = findViewById(R.id.extraCriteriaButton);
        filterSearchButton = findViewById(R.id.filterSearchButton);

        firebase = FirebaseDatabase.getInstance();
        ref = firebase.getReference("Branches");
        serviceref = firebase.getReference("ServiceRequests");
        customerserivces = new ArrayList<>();
        branchesListView = findViewById(R.id.branchesListView);

        branchesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);

                Branch branch = branches.get(i);
                listViewClick(branch.getBranchName());
            }
        });

        extraCriteriaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);

                extraCriteria();
            }
        });

        filterSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                branches.clear();
                branchesListView.setAdapter(null);
                verifySearch();
            }
        });

    }

    public void listViewClick(final String branchName) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if (postSnapshot.child("branchName").getValue().toString().equals(branchName)) {
                        Intent intent = new Intent(CustomerRequestServicesPage.this, CustomerBranchInformationPage.class);
                        intent.putExtra("EXTRA_USERNAME", customerUsername);
                        String branchname = postSnapshot.getKey();
                        intent.putExtra("EXTRA_BRANCH_NAME", branchname);

                        startActivity(intent);
                        ref.removeEventListener(this);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void verifySearch() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String branchName = customerBranchNameTextView.getText().toString().toLowerCase().trim().length() > 0 ? customerBranchNameTextView.getText().toString().toLowerCase().trim() : "";
                String branchAddress = customerAddressTextView.getText().toString().toLowerCase().trim().length() > 0 ? customerAddressTextView.getText().toString().toLowerCase().trim() : "";
                extraCriteriaFilters.set(8, branchName);
                extraCriteriaFilters.set(9, branchAddress);

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Branch branch = postSnapshot.getValue(Branch.class);
                    boolean defaultCriteria[] = {extraCriteriaFilters.get(0).equals(true), extraCriteriaFilters.get(1).equals(true),
                                                extraCriteriaFilters.get(2).equals(true), extraCriteriaFilters.get(3).equals(true),
                                                extraCriteriaFilters.get(4).equals(true), extraCriteriaFilters.get(5).equals(true),
                                                extraCriteriaFilters.get(6).equals(true), !extraCriteriaFilters.get(7).equals(""),
                                                !extraCriteriaFilters.get(8).equals(""), !extraCriteriaFilters.get(9).equals("")};
                    boolean branchCriteria[] = {false, false, false, false, false, false, false, false, false, false};

                    for (int i = 0; i < extraCriteriaFilters.size(); i++) {
                        switch (i) {
                            case 0:
                                if (extraCriteriaFilters.get(0).equals(true)) {
                                    boolean monday = Boolean.parseBoolean(postSnapshot.child("mondayOpen").getValue().toString());
                                    if (!monday) {
                                        branchCriteria[i] = false;
                                        break;
                                    } else {
                                        branchCriteria[i] = true;
                                        break;
                                    }
                                } else {
                                    branchCriteria[i] = false;
                                    break;
                                }

                            case 1:
                                if (extraCriteriaFilters.get(1).equals(true)) {
                                    boolean tuesday = Boolean.parseBoolean(postSnapshot.child("tuesdayOpen").getValue().toString());
                                    if (!tuesday) {
                                        branchCriteria[i] = false;
                                        break;
                                    } else {
                                        branchCriteria[i] = true;
                                        break;
                                    }
                                } else {
                                    branchCriteria[i] = false;
                                    break;
                                }

                            case 2:
                                System.out.println(extraCriteriaFilters.get(2).equals(true));
                                if (extraCriteriaFilters.get(2).equals(true)) {
                                    boolean wednesday = Boolean.parseBoolean(postSnapshot.child("wednesdayOpen").getValue().toString());
                                    if (!wednesday) {
                                        branchCriteria[i] = false;
                                        break;
                                    } else {
                                        branchCriteria[i] = true;
                                        break;
                                    }
                                } else {
                                    branchCriteria[i] = false;
                                    break;
                                }

                            case 3:
                                if (extraCriteriaFilters.get(3).equals(true)) {
                                    boolean thursday = Boolean.parseBoolean(postSnapshot.child("thursdayOpen").getValue().toString());
                                    if (!thursday) {
                                        branchCriteria[i] = false;
                                        break;
                                    } else {
                                        branchCriteria[i] = true;
                                        break;
                                    }
                                } else {
                                    branchCriteria[i] = false;
                                    break;
                                }

                            case 4:
                                if (extraCriteriaFilters.get(4).equals(true)) {
                                    boolean friday = Boolean.parseBoolean(postSnapshot.child("fridayOpen").getValue().toString());

                                    if (!friday) {
                                        branchCriteria[i] = false;
                                        break;
                                    } else {
                                        branchCriteria[i] = true;
                                        break;
                                    }
                                } else {
                                    branchCriteria[i] = false;
                                    break;
                                }

                            case 5:
                                if (extraCriteriaFilters.get(5).equals(true)) {
                                    boolean saturday = Boolean.parseBoolean(postSnapshot.child("saturdayOpen").getValue().toString());
                                    if (!saturday) {
                                        branchCriteria[i] = false;
                                        break;
                                    } else {
                                        branchCriteria[i] = true;
                                        break;
                                    }
                                } else {
                                    branchCriteria[i] = false;
                                    break;
                                }

                            case 6:
                                if (extraCriteriaFilters.get(6).equals(true)) {
                                    boolean sunday = Boolean.parseBoolean(postSnapshot.child("sundayOpen").getValue().toString());
                                    if (!sunday) {
                                        branchCriteria[i] = false;
                                        break;
                                    } else {
                                        branchCriteria[i] = true;
                                        break;
                                    }
                                } else {
                                    branchCriteria[i] = false;
                                    break;
                                }

                            case 7:
                                if (!extraCriteriaFilters.get(7).equals("")) {
                                    if (!postSnapshot.child("branchServices").getValue().toString().toLowerCase().contains(extraCriteriaFilters.get(7).toString().toLowerCase())) {
                                        branchCriteria[i] = false;
                                        break;
                                    } else {
                                        branchCriteria[i] = true;
                                        break;
                                    }
                                } else {
                                    branchCriteria[i] = false;
                                    break;
                                }

                            case 8:
                                if (!extraCriteriaFilters.get(8).equals("")) {
                                    if (!postSnapshot.child("branchName").getValue().toString().toLowerCase().equals(extraCriteriaFilters.get(8).toString().toLowerCase())) {
                                        branchCriteria[i] = false;
                                        break;
                                    } else {
                                        branchCriteria[i] = true;
                                        break;
                                    }
                                } else {
                                    branchCriteria[i] = false;
                                    break;
                                }

                            case 9:
                                if (!extraCriteriaFilters.get(9).equals("")) {
                                    if (!postSnapshot.child("branchAddress").getValue().toString().toLowerCase().equals(extraCriteriaFilters.get(9).toString().toLowerCase())) {
                                        branchCriteria[i] = false;
                                        break;
                                    } else {
                                        branchCriteria[i] = true;
                                        break;
                                    }
                                } else {
                                    branchCriteria[i] = false;
                                    break;
                                }
                        }

                    }

                    if (Arrays.equals(defaultCriteria, branchCriteria)) {
                        branches.add(branch);
                    }
                }
                BranchList servicesAdapter = new BranchList(CustomerRequestServicesPage.this, branches);
                branchesListView.setAdapter(servicesAdapter);
                ref.removeEventListener(this);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void extraCriteria() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.customer_extra_criteria_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        final ListView servicesListView = dialogView.findViewById(R.id.servicesListView);
        final Button confirmButton = dialogView.findViewById(R.id.confirmButton);
        final CheckBox mondayCheckBox = dialogView.findViewById(R.id.mondayCheckBox);
        final CheckBox tuesdayCheckBox = dialogView.findViewById(R.id.tuesdayCheckBox);
        final CheckBox wednesdayCheckBox = dialogView.findViewById(R.id.wednesdayCheckBox);
        final CheckBox thursdayCheckBox = dialogView.findViewById(R.id.thursdayCheckBox);
        final CheckBox fridayCheckBox = dialogView.findViewById(R.id.fridayCheckBox);
        final CheckBox saturdayCheckBox = dialogView.findViewById(R.id.saturdayCheckBox);
        final CheckBox sundayCheckBox = dialogView.findViewById(R.id.sundayCheckBox);
        final EditText serviceSearchBox = dialogView.findViewById(R.id.serviceSearchBox);
        final ArrayList<CheckBox> arr = new ArrayList<>();

        arr.add(mondayCheckBox);
        arr.add(tuesdayCheckBox);
        arr.add(wednesdayCheckBox);
        arr.add(thursdayCheckBox);
        arr.add(fridayCheckBox);
        arr.add(saturdayCheckBox);
        arr.add(sundayCheckBox);

        dialogBuilder.setView(dialogView);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        for (int i = 0; i < extraCriteriaFilters.size(); i++) {
            if (i < 8) {
                switch(i) {
                    case 0:
                        if (extraCriteriaFilters.get(i).equals(true)) {
                            mondayCheckBox.setChecked(true);
                        }

                        break;
                    case 1:
                        if (extraCriteriaFilters.get(i).equals(true)) {
                            tuesdayCheckBox.setChecked(true);
                        }

                        break;
                    case 2:
                        if (extraCriteriaFilters.get(i).equals(true)) {
                            wednesdayCheckBox.setChecked(true);
                        }

                        break;
                    case 3:
                        if (extraCriteriaFilters.get(i).equals(true)) {
                            thursdayCheckBox.setChecked(true);
                        }

                        break;
                    case 4:
                        if (extraCriteriaFilters.get(i).equals(true)) {
                            fridayCheckBox.setChecked(true);
                        }

                        break;
                    case 5:
                        if (extraCriteriaFilters.get(i).equals(true)) {
                            saturdayCheckBox.setChecked(true);
                        }

                        break;
                    case 6:
                        if (extraCriteriaFilters.get(i).equals(true)) {
                            sundayCheckBox.setChecked(true);
                        }

                        break;
                    case 7:
                        serviceSearchBox.setText(extraCriteriaFilters.get(i).toString());
                        break;
                }
            }
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                String serviceName = serviceSearchBox.getText().toString().trim();

                for (int i = 0; i < arr.size(); i++) {
                    CheckBox box = arr.get(i);
                    extraCriteriaFilters.set(i, box.isChecked());
                }

                // last index of arraylist is going to be the service name
                extraCriteriaFilters.set(7, serviceName);
                dialog.dismiss();
                Toast toast;
                Toast.makeText(CustomerRequestServicesPage.this, "Successfully added criteria ",
                        Toast.LENGTH_SHORT).show();
            }
        });

        serviceref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customerserivces.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Service service = postSnapshot.getValue(Service.class);
                    customerserivces.add(service);
                }

                ServiceList servicesAdapter = new ServiceList(CustomerRequestServicesPage.this, customerserivces);
                servicesListView.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                branches.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Branch branch = postSnapshot.getValue(Branch.class);
                    branches.add(branch);
                }

                BranchList servicesAdapter = new BranchList(CustomerRequestServicesPage.this, branches);
                branchesListView.setAdapter(servicesAdapter);
                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}
