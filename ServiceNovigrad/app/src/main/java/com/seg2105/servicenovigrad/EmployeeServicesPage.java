package com.seg2105.servicenovigrad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.Arrays;

public class EmployeeServicesPage extends AppCompatActivity {
    String employeeUsername;
    String branchName;

    FirebaseDatabase firebase;
    DatabaseReference branchesRef;
    DatabaseReference servicesRef;

    ListView branchServicesListView;
    ArrayList<String> services;
    ArrayList<Service> availableServices;

    private final int normalVibrateValue = 10;
    private final int lowVibrateValue = normalVibrateValue / 2;
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

    BranchServiceList servicesAdapter;
    BranchEmployee employee = new BranchEmployee();

    public EmployeeServicesPage(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_services_page);

        firebase = FirebaseDatabase.getInstance();
        branchesRef = firebase.getReference("Branches");
        servicesRef = firebase.getReference("ServiceRequests");

        employeeUsername = getIntent().getStringExtra("EXTRA_USERNAME");
        branchName = employeeUsername + " Branch";

        branchServicesListView = (ListView) findViewById(R.id.servicesAtBranchListView);
        services = new ArrayList<>();
        availableServices = new ArrayList<>();

        Button addServiceButton = (Button) findViewById(R.id.addServiceButton);

        addServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                addService();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        branchesRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentBranchName = dataSnapshot.child(employeeUsername + " Branch").child("branchName").getValue().toString();
                currentBranchPhoneNumber = dataSnapshot.child(employeeUsername + " Branch").child("branchPhoneNumber").getValue().toString();
                currentBranchAddress = dataSnapshot.child(employeeUsername + " Branch").child("branchAddress").getValue().toString();
                currentBranchServices = dataSnapshot.child(employeeUsername + " Branch").child("branchServices").getValue().toString();
                currentMondayOpen = Boolean.parseBoolean(dataSnapshot.child(employeeUsername + " Branch").child("mondayOpen").getValue().toString());
                currentTuesdayOpen = Boolean.parseBoolean(dataSnapshot.child(employeeUsername + " Branch").child("tuesdayOpen").getValue().toString());
                currentWednesdayOpen = Boolean.parseBoolean(dataSnapshot.child(employeeUsername + " Branch").child("wednesdayOpen").getValue().toString());
                currentThursdayOpen = Boolean.parseBoolean(dataSnapshot.child(employeeUsername + " Branch").child("thursdayOpen").getValue().toString());
                currentFridayOpen = Boolean.parseBoolean(dataSnapshot.child(employeeUsername + " Branch").child("fridayOpen").getValue().toString());
                currentSaturdayOpen = Boolean.parseBoolean(dataSnapshot.child(employeeUsername + " Branch").child("saturdayOpen").getValue().toString());
                currentSundayOpen = Boolean.parseBoolean(dataSnapshot.child(employeeUsername + " Branch").child("sundayOpen").getValue().toString());
                currentMondayWorkingHours = dataSnapshot.child(employeeUsername + " Branch").child("mondayWorkingHours").getValue().toString();
                currentTuesdayWorkingHours = dataSnapshot.child(employeeUsername + " Branch").child("tuesdayWorkingHours").getValue().toString();
                currentWednesdayWorkingHours = dataSnapshot.child(employeeUsername + " Branch").child("wednesdayWorkingHours").getValue().toString();
                currentThursdayWorkingHours = dataSnapshot.child(employeeUsername + " Branch").child("thursdayWorkingHours").getValue().toString();
                currentFridayWorkingHours = dataSnapshot.child(employeeUsername + " Branch").child("fridayWorkingHours").getValue().toString();
                currentSaturdayWorkingHours = dataSnapshot.child(employeeUsername + " Branch").child("saturdayWorkingHours").getValue().toString();
                currentSundayWorkingHours = dataSnapshot.child(employeeUsername + " Branch").child("sundayWorkingHours").getValue().toString();
                currentBranchRating = dataSnapshot.child(employeeUsername + " Branch").child("branchRating").getValue().toString();
                currentBranchRatingVoteCount = dataSnapshot.child(employeeUsername + " Branch").child("branchRatingVoteCount").getValue().toString();

                if (!currentBranchServices.equals("N/A")) {
                    getBranchServices();
                }

                servicesAdapter = new BranchServiceList(EmployeeServicesPage.this, services);
                branchServicesListView.setAdapter(servicesAdapter);

                branchServicesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String serviceName = services.get(i);
                        deleteService(serviceName);
                        return true;
                    }
                });

                branchesRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void getBranchServices() {
        if (!currentBranchServices.equals("N/A")) {
            String[] branchServices = currentBranchServices.split(", ");

            services.addAll(Arrays.asList(branchServices));
        }
    }

    public void deleteService(final String serviceName) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.employee_delete_branch_service_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        final Button deleteAccountsButton = (Button) dialogView.findViewById(R.id.approveRequestButton);

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        deleteAccountsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                String newBranchServices = null;

                services.remove(serviceName);

                if (services.isEmpty()) {
                    newBranchServices = "N/A";
                } else {
                    for (int i = 0; i < services.size(); i++) {
                        String serviceName = services.get(i);

                        if (i == 0) {
                            newBranchServices = serviceName;
                        } else {
                            newBranchServices = newBranchServices + ", " + serviceName;
                        }
                    }
                }

                servicesAdapter.notifyDataSetChanged();
                employee.updateServices(employeeUsername, currentBranchName, currentBranchAddress, currentBranchPhoneNumber, newBranchServices,
                        currentMondayOpen, currentTuesdayOpen, currentWednesdayOpen, currentThursdayOpen, currentFridayOpen, currentSaturdayOpen,
                        currentSundayOpen, currentMondayWorkingHours, currentTuesdayWorkingHours, currentWednesdayWorkingHours, currentThursdayWorkingHours,
                        currentFridayWorkingHours, currentSaturdayWorkingHours, currentSundayWorkingHours, currentBranchRating, currentBranchRatingVoteCount);

                Toast toast;
                Toast.makeText(EmployeeServicesPage.this, "Successfully deleted service " + serviceName,
                        Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
    }

    public void addService() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.employee_select_services_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        final ListView servicesListView = (ListView) dialogView.findViewById(R.id.servicesListView);
        final Button updateSelectionButton = (Button) dialogView.findViewById(R.id.updateSelectionButton);

        servicesRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                availableServices.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Service service = postSnapshot.getValue(Service.class);
                    availableServices.add(service);
                }

                final ServiceList availableServicesAdapter = new ServiceList(EmployeeServicesPage.this, availableServices);
                servicesListView.setAdapter(availableServicesAdapter);

                servicesListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

                @SuppressLint("UseCompatLoadingForDrawables") final Drawable employeeUnselectedServiceBorder = getResources().getDrawable(R.drawable.employee_unselected_service_border);
                @SuppressLint("UseCompatLoadingForDrawables") final Drawable employeeSelectedServiceBorder = getResources().getDrawable(R.drawable.employee_selected_service_border);
                @SuppressLint("UseCompatLoadingForDrawables") final Drawable employeeFirstSelectedServiceBorder = getResources().getDrawable(R.drawable.employee_first_selected_service_border);
                @SuppressLint("UseCompatLoadingForDrawables") final Drawable employeeFirstUnselectedServiceBorder = getResources().getDrawable(R.drawable.employee_first_unselected_service_border);

                ArrayList<Service> servicesToBeRemoved = new ArrayList<>();

                for (int i = 0; i < servicesListView.getCount(); i++) {
                    Service serviceItem = availableServicesAdapter.getItem(i);
                    String serviceName = serviceItem.getServiceName();

                    if (services.contains(serviceName)) {
                        servicesToBeRemoved.add(serviceItem);
                    }
                }

                for (Service service : servicesToBeRemoved) {
                    availableServicesAdapter.remove(service);
                }

                updateSelectionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vb.vibrate(normalVibrateValue);

                        String newBranchServices = null;

                        if (services.isEmpty()) {
                            updateSelectionButton.setError("Services must be selected!");
                        } else {
                            for (int i = 0; i < services.size(); i++) {
                                String serviceName = services.get(i);

                                if (i == 0) {
                                    newBranchServices = serviceName;
                                } else {
                                    newBranchServices = newBranchServices + ", " + serviceName;
                                }
                            }

                            servicesAdapter.notifyDataSetChanged();
                            employee.updateServices(employeeUsername, currentBranchName, currentBranchAddress, currentBranchPhoneNumber, newBranchServices,
                                    currentMondayOpen, currentTuesdayOpen, currentWednesdayOpen, currentThursdayOpen, currentFridayOpen, currentSaturdayOpen,
                                    currentSundayOpen, currentMondayWorkingHours, currentTuesdayWorkingHours, currentWednesdayWorkingHours, currentThursdayWorkingHours,
                                    currentFridayWorkingHours, currentSaturdayWorkingHours, currentSundayWorkingHours, currentBranchRating, currentBranchRatingVoteCount);

                            Toast toast;
                            Toast.makeText(EmployeeServicesPage.this, "Successfully updated the branch's services list",
                                    Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                        }
                    }
                });

                servicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        view.setSelected(!view.isSelected());

                        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vb.vibrate(lowVibrateValue);

                        Service serviceItem = availableServicesAdapter.getItem(position);
                        String serviceName = serviceItem.getServiceName();

                        if (position == 0) {
                            if (servicesListView.isItemChecked(position) && !services.contains(serviceName)) {
                                view.setBackground(employeeFirstSelectedServiceBorder);

                                services.add(serviceName);
                            } else {
                                view.setBackground(employeeFirstUnselectedServiceBorder);

                                services.remove(serviceName);
                            }
                        } else {
                            if (servicesListView.isItemChecked(position) && !services.contains(serviceName)) {
                                view.setBackground(employeeSelectedServiceBorder);

                                services.add(serviceName);
                            } else {
                                view.setBackground(employeeUnselectedServiceBorder);

                                services.remove(serviceName);
                            }
                        }
                    }
                });

                servicesRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}