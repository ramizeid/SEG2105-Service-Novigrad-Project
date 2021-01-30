package com.seg2105.servicenovigrad;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EmployeeBranchHoursPage extends AppCompatActivity {
    FirebaseDatabase firebase;
    DatabaseReference ref;
    private final int normalVibrateValue = 10;
    private final int lowVibrateValue = normalVibrateValue / 2;
    String employeeUsername;

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

    TextView mondayHours = null;
    TextView tuesdayHours = null;
    TextView wednesdayHours = null;
    TextView thursdayHours = null;
    TextView fridayHours = null;
    TextView saturdayHours = null;
    TextView sundayHours = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_branch_hours_page);

        firebase = FirebaseDatabase.getInstance();
        ref = firebase.getReference("Branches");

        employeeUsername = getIntent().getStringExtra("EXTRA_USERNAME");

        // Initializing variables
        mondayHours = (TextView) findViewById(R.id.mondayHours);
        tuesdayHours = (TextView) findViewById(R.id.tuesdayHours);
        wednesdayHours = (TextView) findViewById(R.id.wednesdayHours);
        thursdayHours = (TextView) findViewById(R.id.thursdayHours);
        fridayHours = (TextView) findViewById(R.id.fridayHours);
        saturdayHours = (TextView) findViewById(R.id.saturdayHours);
        sundayHours = (TextView) findViewById(R.id.sundayHours);

        Button mondaySetHoursButton = (Button) findViewById(R.id.mondaySetHoursButton);
        Button tuesdaySetHoursButton = (Button) findViewById(R.id.tuesdaySetHoursButton);
        Button wednesdaySetHoursButton = (Button) findViewById(R.id.wednesdaySetHoursButton);
        Button thursdaySetHoursButton = (Button) findViewById(R.id.thursdaySetHoursButton);
        Button fridaySetHoursButton = (Button) findViewById(R.id.fridaySetHoursButton);
        Button saturdaySetHoursButton = (Button) findViewById(R.id.saturdaySetHoursButton);
        Button sundaySetHoursButton = (Button) findViewById(R.id.sundaySetHoursButton);

        final Switch mondayOpenSwitch = (Switch) findViewById(R.id.mondayOpenSwitch);
        final Switch tuesdayOpenSwitch = (Switch) findViewById(R.id.tuesdayOpenSwitch);
        final Switch wednesdayOpenSwitch = (Switch) findViewById(R.id.wednesdayOpenSwitch);
        final Switch thursdayOpenSwitch = (Switch) findViewById(R.id.thursdayOpenSwitch);
        final Switch fridayOpenSwitch = (Switch) findViewById(R.id.fridayOpenSwitch);
        final Switch saturdayOpenSwitch = (Switch) findViewById(R.id.saturdayOpenSwitch);
        final Switch sundayOpenSwitch = (Switch) findViewById(R.id.sundayOpenSwitch);

        ref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /**
                 * A NULL POINTER EXCEPTION OCCURS AFTER TRYING TO OPEN THE BRANCH PROFILE AFTER UPDATING
                 * IT TWICE, TO BE FIXED ASAP
                 */

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

                mondayHours.setText("Hours: " + currentMondayWorkingHours);
                tuesdayHours.setText("Hours: " + currentTuesdayWorkingHours);
                wednesdayHours.setText("Hours: " + currentWednesdayWorkingHours);
                thursdayHours.setText("Hours: " + currentThursdayWorkingHours);
                fridayHours.setText("Hours: " + currentFridayWorkingHours);
                saturdayHours.setText("Hours: " + currentSaturdayWorkingHours);
                sundayHours.setText("Hours: " + currentSundayWorkingHours);

                mondayOpenSwitch.setChecked(currentMondayOpen);
                tuesdayOpenSwitch.setChecked(currentTuesdayOpen);
                wednesdayOpenSwitch.setChecked(currentWednesdayOpen);
                thursdayOpenSwitch.setChecked(currentThursdayOpen);
                fridayOpenSwitch.setChecked(currentFridayOpen);
                saturdayOpenSwitch.setChecked(currentSaturdayOpen);
                sundayOpenSwitch.setChecked(currentSundayOpen);

                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        // OnClickListeners for the switches
        mondayOpenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);
                BranchEmployee employee = new BranchEmployee();

                currentMondayOpen = isChecked;

                employee.updateWorkingHours(employeeUsername, currentBranchName, currentBranchAddress, currentBranchPhoneNumber, currentBranchServices,
                        currentMondayOpen, currentTuesdayOpen, currentWednesdayOpen, currentThursdayOpen, currentFridayOpen, currentSaturdayOpen,
                        currentSundayOpen, currentMondayWorkingHours, currentTuesdayWorkingHours, currentWednesdayWorkingHours, currentThursdayWorkingHours,
                        currentFridayWorkingHours, currentSaturdayWorkingHours, currentSundayWorkingHours, currentBranchRating, currentBranchRatingVoteCount);
            }
        });

        tuesdayOpenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);
                BranchEmployee employee = new BranchEmployee();

                currentTuesdayOpen = isChecked;

                employee.updateWorkingHours(employeeUsername, currentBranchName, currentBranchAddress, currentBranchPhoneNumber, currentBranchServices,
                        currentMondayOpen, currentTuesdayOpen, currentWednesdayOpen, currentThursdayOpen, currentFridayOpen, currentSaturdayOpen,
                        currentSundayOpen, currentMondayWorkingHours, currentTuesdayWorkingHours, currentWednesdayWorkingHours, currentThursdayWorkingHours,
                        currentFridayWorkingHours, currentSaturdayWorkingHours, currentSundayWorkingHours, currentBranchRating, currentBranchRatingVoteCount);
            }
        });

        wednesdayOpenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);
                BranchEmployee employee = new BranchEmployee();

                currentWednesdayOpen = isChecked;

                employee.updateWorkingHours(employeeUsername, currentBranchName, currentBranchAddress, currentBranchPhoneNumber, currentBranchServices,
                        currentMondayOpen, currentTuesdayOpen, currentWednesdayOpen, currentThursdayOpen, currentFridayOpen, currentSaturdayOpen,
                        currentSundayOpen, currentMondayWorkingHours, currentTuesdayWorkingHours, currentWednesdayWorkingHours, currentThursdayWorkingHours,
                        currentFridayWorkingHours, currentSaturdayWorkingHours, currentSundayWorkingHours, currentBranchRating, currentBranchRatingVoteCount);
            }
        });

        thursdayOpenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);
                BranchEmployee employee = new BranchEmployee();

                currentThursdayOpen = isChecked;

                employee.updateWorkingHours(employeeUsername, currentBranchName, currentBranchAddress, currentBranchPhoneNumber, currentBranchServices,
                        currentMondayOpen, currentTuesdayOpen, currentWednesdayOpen, currentThursdayOpen, currentFridayOpen, currentSaturdayOpen,
                        currentSundayOpen, currentMondayWorkingHours, currentTuesdayWorkingHours, currentWednesdayWorkingHours, currentThursdayWorkingHours,
                        currentFridayWorkingHours, currentSaturdayWorkingHours, currentSundayWorkingHours, currentBranchRating, currentBranchRatingVoteCount);
            }
        });

        fridayOpenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);
                BranchEmployee employee = new BranchEmployee();

                currentFridayOpen = isChecked;

                employee.updateWorkingHours(employeeUsername, currentBranchName, currentBranchAddress, currentBranchPhoneNumber, currentBranchServices,
                        currentMondayOpen, currentTuesdayOpen, currentWednesdayOpen, currentThursdayOpen, currentFridayOpen, currentSaturdayOpen,
                        currentSundayOpen, currentMondayWorkingHours, currentTuesdayWorkingHours, currentWednesdayWorkingHours, currentThursdayWorkingHours,
                        currentFridayWorkingHours, currentSaturdayWorkingHours, currentSundayWorkingHours, currentBranchRating, currentBranchRatingVoteCount);
            }
        });

        saturdayOpenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);
                BranchEmployee employee = new BranchEmployee();

                currentSaturdayOpen = isChecked;

                employee.updateWorkingHours(employeeUsername, currentBranchName, currentBranchAddress, currentBranchPhoneNumber, currentBranchServices,
                        currentMondayOpen, currentTuesdayOpen, currentWednesdayOpen, currentThursdayOpen, currentFridayOpen, currentSaturdayOpen,
                        currentSundayOpen, currentMondayWorkingHours, currentTuesdayWorkingHours, currentWednesdayWorkingHours, currentThursdayWorkingHours,
                        currentFridayWorkingHours, currentSaturdayWorkingHours, currentSundayWorkingHours, currentBranchRating, currentBranchRatingVoteCount);
            }
        });

        sundayOpenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);
                BranchEmployee employee = new BranchEmployee();

                currentSundayOpen = isChecked;

                employee.updateWorkingHours(employeeUsername, currentBranchName, currentBranchAddress, currentBranchPhoneNumber, currentBranchServices,
                        currentMondayOpen, currentTuesdayOpen, currentWednesdayOpen, currentThursdayOpen, currentFridayOpen, currentSaturdayOpen,
                        currentSundayOpen, currentMondayWorkingHours, currentTuesdayWorkingHours, currentWednesdayWorkingHours, currentThursdayWorkingHours,
                        currentFridayWorkingHours, currentSaturdayWorkingHours, currentSundayWorkingHours, currentBranchRating, currentBranchRatingVoteCount);
            }
        });

        // OnClickListeners for the buttons
        mondaySetHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                mondaySetHoursDialog();
            }
        });

        tuesdaySetHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                tuesdaySetHoursDialog();
            }
        });

        wednesdaySetHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                wednesdaySetHoursDialog();
            }
        });

        thursdaySetHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                thursdaySetHoursDialog();
            }
        });

        fridaySetHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                fridaySetHoursDialog();
            }
        });

        saturdaySetHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                saturdaySetHoursDialog();
            }
        });

        sundaySetHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);

                sundaySetHoursDialog();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void mondaySetHoursDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.employee_set_hours_dialog, null);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        final TextView beginningTimeTextView = (TextView) dialogView.findViewById(R.id.beginningTimeTextView);
        final TextView endTimeTextView = (TextView) dialogView.findViewById(R.id.endTimeTextView);
        Button setBeginningTimeButton = (Button) dialogView.findViewById(R.id.setBeginningTimeButton);
        Button setEndTimeButton = (Button) dialogView.findViewById(R.id.setEndTimeButton);
        Button updateTimesButton = (Button) dialogView.findViewById(R.id.updateTimesButton);
        String beginningTime;
        String endTime;

        if (!currentMondayWorkingHours.equals("N/A")) {
            beginningTime = currentMondayWorkingHours.substring(0, currentMondayWorkingHours.indexOf('-')).trim();
            endTime = currentMondayWorkingHours.substring(currentMondayWorkingHours.indexOf('-') + 1, currentMondayWorkingHours.length()).trim();

            beginningTimeTextView.setText("Beginning: " + beginningTime);
            endTimeTextView.setText("End: " + endTime);
        }

        setBeginningTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

                        String time = (String) dateFormatter.format(calendar.getTime()).toString();

                        beginningTimeTextView.setText("Beginning Time: " + time);
                    }
                };

                new TimePickerDialog(EmployeeBranchHoursPage.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        });

        setEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

                        String time = (String) dateFormatter.format(calendar.getTime()).toString();

                        endTimeTextView.setText("End Time: " + time);
                    }
                };

                new TimePickerDialog(EmployeeBranchHoursPage.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        });

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        updateTimesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String beginningTime = beginningTimeTextView.getText().toString();
                String endTime = endTimeTextView.getText().toString();
                String newBeginningTime = null;
                String newEndTime = null;
                boolean isValid = true;
                BranchEmployee employee = new BranchEmployee();
                String newTime = "N/A";

                if (!beginningTime.equals("Beginning Time: N/A")) {
                    newBeginningTime = beginningTime.substring(beginningTime.indexOf(':') + 1, beginningTime.length()).trim();
                }

                if (!endTime.equals("End Time: N/A")) {
                    newEndTime = endTime.substring(endTime.indexOf(':') + 1, endTime.length()).trim();
                }

                if (newBeginningTime != null && newEndTime != null) {
                    int beginningHour = Integer.parseInt(newBeginningTime.substring(0, newBeginningTime.indexOf(':')).trim());
                    int endHour = Integer.parseInt(newEndTime.substring(0, newEndTime.indexOf(':')).trim());

                    if (beginningHour > endHour) {
                        beginningTimeTextView.setError("Beginning time is after end time");
                        endTimeTextView.setError("End time is before beginning time");
                        isValid = false;
                    } else {
                        newTime = newBeginningTime + " - " + newEndTime;
                        currentMondayWorkingHours = newTime;
                        mondayHours.setText("Hours: " + newTime);
                    }
                }

                if (newBeginningTime == null) {
                    beginningTimeTextView.setError("Beginning time cannot be null");
                    isValid = false;
                }

                if (newEndTime == null) {
                    beginningTimeTextView.setError("End time cannot be null");
                    isValid = false;
                }

                if (isValid) {
                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vb.vibrate(lowVibrateValue);

                    employee.updateWorkingHours(employeeUsername, currentBranchName, currentBranchAddress, currentBranchPhoneNumber, currentBranchServices,
                            currentMondayOpen, currentTuesdayOpen, currentWednesdayOpen, currentThursdayOpen, currentFridayOpen, currentSaturdayOpen,
                            currentSundayOpen, newTime, currentTuesdayWorkingHours, currentWednesdayWorkingHours, currentThursdayWorkingHours,
                            currentFridayWorkingHours, currentSaturdayWorkingHours, currentSundayWorkingHours, currentBranchRating, currentBranchRatingVoteCount);

                    Toast toast;
                    Toast.makeText(EmployeeBranchHoursPage.this, "Successfully updated the branch's Monday working hours", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void tuesdaySetHoursDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.employee_set_hours_dialog, null);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        final TextView beginningTimeTextView = (TextView) dialogView.findViewById(R.id.beginningTimeTextView);
        final TextView endTimeTextView = (TextView) dialogView.findViewById(R.id.endTimeTextView);
        Button setBeginningTimeButton = (Button) dialogView.findViewById(R.id.setBeginningTimeButton);
        Button setEndTimeButton = (Button) dialogView.findViewById(R.id.setEndTimeButton);
        Button updateTimesButton = (Button) dialogView.findViewById(R.id.updateTimesButton);
        String beginningTime;
        String endTime;

        if (!currentTuesdayWorkingHours.equals("N/A")) {
            beginningTime = currentTuesdayWorkingHours.substring(0, currentTuesdayWorkingHours.indexOf('-')).trim();
            endTime = currentTuesdayWorkingHours.substring(currentTuesdayWorkingHours.indexOf('-') + 1, currentTuesdayWorkingHours.length()).trim();

            beginningTimeTextView.setText("Beginning: " + beginningTime);
            endTimeTextView.setText("End: " + endTime);
        }

        setBeginningTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

                        String time = (String) dateFormatter.format(calendar.getTime()).toString();

                        beginningTimeTextView.setText("Beginning Time: " + time);
                    }
                };

                new TimePickerDialog(EmployeeBranchHoursPage.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        });

        setEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

                        String time = (String) dateFormatter.format(calendar.getTime()).toString();

                        endTimeTextView.setText("End Time: " + time);
                    }
                };

                new TimePickerDialog(EmployeeBranchHoursPage.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        });

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        updateTimesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String beginningTime = beginningTimeTextView.getText().toString();
                String endTime = endTimeTextView.getText().toString();
                String newBeginningTime = null;
                String newEndTime = null;
                boolean isValid = true;
                BranchEmployee employee = new BranchEmployee();
                String newTime = "N/A";

                if (!beginningTime.equals("Beginning Time: N/A")) {
                    newBeginningTime = beginningTime.substring(beginningTime.indexOf(':') + 1, beginningTime.length()).trim();
                }

                if (!endTime.equals("End Time: N/A")) {
                    newEndTime = endTime.substring(endTime.indexOf(':') + 1, endTime.length()).trim();
                }

                if (newBeginningTime != null && newEndTime != null) {
                    int beginningHour = Integer.parseInt(newBeginningTime.substring(0, newBeginningTime.indexOf(':')).trim());
                    int endHour = Integer.parseInt(newEndTime.substring(0, newEndTime.indexOf(':')).trim());

                    if (beginningHour > endHour) {
                        beginningTimeTextView.setError("Beginning time is after end time");
                        endTimeTextView.setError("End time is before beginning time");
                        isValid = false;
                    } else {
                        newTime = newBeginningTime + " - " + newEndTime;
                        currentTuesdayWorkingHours = newTime;
                        tuesdayHours.setText("Hours: " + newTime);
                    }
                }

                if (newBeginningTime == null) {
                    beginningTimeTextView.setError("Beginning time cannot be null");
                    isValid = false;
                }

                if (newEndTime == null) {
                    beginningTimeTextView.setError("End time cannot be null");
                    isValid = false;
                }

                if (isValid) {
                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vb.vibrate(lowVibrateValue);

                    employee.updateWorkingHours(employeeUsername, currentBranchName, currentBranchAddress, currentBranchPhoneNumber, currentBranchServices,
                            currentMondayOpen, currentTuesdayOpen, currentWednesdayOpen, currentThursdayOpen, currentFridayOpen, currentSaturdayOpen,
                            currentSundayOpen, currentMondayWorkingHours, newTime, currentWednesdayWorkingHours, currentThursdayWorkingHours,
                            currentFridayWorkingHours, currentSaturdayWorkingHours, currentSundayWorkingHours, currentBranchRating, currentBranchRatingVoteCount);

                    Toast toast;
                    Toast.makeText(EmployeeBranchHoursPage.this, "Successfully updated the branch's Tuesday working hours", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void wednesdaySetHoursDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.employee_set_hours_dialog, null);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        final TextView beginningTimeTextView = (TextView) dialogView.findViewById(R.id.beginningTimeTextView);
        final TextView endTimeTextView = (TextView) dialogView.findViewById(R.id.endTimeTextView);
        Button setBeginningTimeButton = (Button) dialogView.findViewById(R.id.setBeginningTimeButton);
        Button setEndTimeButton = (Button) dialogView.findViewById(R.id.setEndTimeButton);
        Button updateTimesButton = (Button) dialogView.findViewById(R.id.updateTimesButton);
        String beginningTime;
        String endTime;

        if (!currentWednesdayWorkingHours.equals("N/A")) {
            beginningTime = currentWednesdayWorkingHours.substring(0, currentWednesdayWorkingHours.indexOf('-')).trim();
            endTime = currentWednesdayWorkingHours.substring(currentWednesdayWorkingHours.indexOf('-') + 1, currentWednesdayWorkingHours.length()).trim();

            beginningTimeTextView.setText("Beginning: " + beginningTime);
            endTimeTextView.setText("End: " + endTime);
        }

        setBeginningTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

                        String time = (String) dateFormatter.format(calendar.getTime()).toString();

                        beginningTimeTextView.setText("Beginning Time: " + time);
                    }
                };

                new TimePickerDialog(EmployeeBranchHoursPage.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        });

        setEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

                        String time = (String) dateFormatter.format(calendar.getTime()).toString();

                        endTimeTextView.setText("End Time: " + time);
                    }
                };

                new TimePickerDialog(EmployeeBranchHoursPage.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        });

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        updateTimesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String beginningTime = beginningTimeTextView.getText().toString();
                String endTime = endTimeTextView.getText().toString();
                String newBeginningTime = null;
                String newEndTime = null;
                boolean isValid = true;
                BranchEmployee employee = new BranchEmployee();
                String newTime = "N/A";

                if (!beginningTime.equals("Beginning Time: N/A")) {
                    newBeginningTime = beginningTime.substring(beginningTime.indexOf(':') + 1, beginningTime.length()).trim();
                }

                if (!endTime.equals("End Time: N/A")) {
                    newEndTime = endTime.substring(endTime.indexOf(':') + 1, endTime.length()).trim();
                }

                if (newBeginningTime != null && newEndTime != null) {
                    int beginningHour = Integer.parseInt(newBeginningTime.substring(0, newBeginningTime.indexOf(':')).trim());
                    int endHour = Integer.parseInt(newEndTime.substring(0, newEndTime.indexOf(':')).trim());

                    if (beginningHour > endHour) {
                        beginningTimeTextView.setError("Beginning time is after end time");
                        endTimeTextView.setError("End time is before beginning time");
                        isValid = false;
                    } else {
                        newTime = newBeginningTime + " - " + newEndTime;
                        currentWednesdayWorkingHours = newTime;
                        wednesdayHours.setText("Hours: " + newTime);
                    }
                }

                if (newBeginningTime == null) {
                    beginningTimeTextView.setError("Beginning time cannot be null");
                    isValid = false;
                }

                if (newEndTime == null) {
                    beginningTimeTextView.setError("End time cannot be null");
                    isValid = false;
                }

                if (isValid) {
                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vb.vibrate(lowVibrateValue);

                    employee.updateWorkingHours(employeeUsername, currentBranchName, currentBranchAddress, currentBranchPhoneNumber, currentBranchServices,
                            currentMondayOpen, currentTuesdayOpen, currentWednesdayOpen, currentThursdayOpen, currentFridayOpen, currentSaturdayOpen,
                            currentSundayOpen, currentMondayWorkingHours, currentTuesdayWorkingHours, newTime, currentThursdayWorkingHours,
                            currentFridayWorkingHours, currentSaturdayWorkingHours, currentSundayWorkingHours, currentBranchRating, currentBranchRatingVoteCount);

                    Toast toast;
                    Toast.makeText(EmployeeBranchHoursPage.this, "Successfully updated the branch's Wednesday working hours", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void thursdaySetHoursDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.employee_set_hours_dialog, null);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        final TextView beginningTimeTextView = (TextView) dialogView.findViewById(R.id.beginningTimeTextView);
        final TextView endTimeTextView = (TextView) dialogView.findViewById(R.id.endTimeTextView);
        Button setBeginningTimeButton = (Button) dialogView.findViewById(R.id.setBeginningTimeButton);
        Button setEndTimeButton = (Button) dialogView.findViewById(R.id.setEndTimeButton);
        Button updateTimesButton = (Button) dialogView.findViewById(R.id.updateTimesButton);
        String beginningTime;
        String endTime;

        if (!currentThursdayWorkingHours.equals("N/A")) {
            beginningTime = currentThursdayWorkingHours.substring(0, currentThursdayWorkingHours.indexOf('-')).trim();
            endTime = currentThursdayWorkingHours.substring(currentThursdayWorkingHours.indexOf('-') + 1, currentThursdayWorkingHours.length()).trim();

            beginningTimeTextView.setText("Beginning: " + beginningTime);
            endTimeTextView.setText("End: " + endTime);
        }

        setBeginningTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

                        String time = (String) dateFormatter.format(calendar.getTime()).toString();

                        beginningTimeTextView.setText("Beginning Time: " + time);
                    }
                };

                new TimePickerDialog(EmployeeBranchHoursPage.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        });

        setEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

                        String time = (String) dateFormatter.format(calendar.getTime()).toString();

                        endTimeTextView.setText("End Time: " + time);
                    }
                };

                new TimePickerDialog(EmployeeBranchHoursPage.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        });

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        updateTimesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String beginningTime = beginningTimeTextView.getText().toString();
                String endTime = endTimeTextView.getText().toString();
                String newBeginningTime = null;
                String newEndTime = null;
                boolean isValid = true;
                BranchEmployee employee = new BranchEmployee();
                String newTime = "N/A";

                if (!beginningTime.equals("Beginning Time: N/A")) {
                    newBeginningTime = beginningTime.substring(beginningTime.indexOf(':') + 1, beginningTime.length()).trim();
                }

                if (!endTime.equals("End Time: N/A")) {
                    newEndTime = endTime.substring(endTime.indexOf(':') + 1, endTime.length()).trim();
                }

                if (newBeginningTime != null && newEndTime != null) {
                    int beginningHour = Integer.parseInt(newBeginningTime.substring(0, newBeginningTime.indexOf(':')).trim());
                    int endHour = Integer.parseInt(newEndTime.substring(0, newEndTime.indexOf(':')).trim());

                    if (beginningHour > endHour) {
                        beginningTimeTextView.setError("Beginning time is after end time");
                        endTimeTextView.setError("End time is before beginning time");
                        isValid = false;
                    } else {
                        newTime = newBeginningTime + " - " + newEndTime;
                        currentThursdayWorkingHours = newTime;
                        thursdayHours.setText("Hours: " + newTime);
                    }
                }

                if (newBeginningTime == null) {
                    beginningTimeTextView.setError("Beginning time cannot be null");
                    isValid = false;
                }

                if (newEndTime == null) {
                    beginningTimeTextView.setError("End time cannot be null");
                    isValid = false;
                }

                if (isValid) {
                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vb.vibrate(lowVibrateValue);

                    employee.updateWorkingHours(employeeUsername, currentBranchName, currentBranchAddress, currentBranchPhoneNumber, currentBranchServices,
                            currentMondayOpen, currentTuesdayOpen, currentWednesdayOpen, currentThursdayOpen, currentFridayOpen, currentSaturdayOpen,
                            currentSundayOpen, currentMondayWorkingHours, currentTuesdayWorkingHours, currentWednesdayWorkingHours, newTime,
                            currentFridayWorkingHours, currentSaturdayWorkingHours, currentSundayWorkingHours, currentBranchRating, currentBranchRatingVoteCount);

                    Toast toast;
                    Toast.makeText(EmployeeBranchHoursPage.this, "Successfully updated the branch's Thursday working hours", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void fridaySetHoursDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.employee_set_hours_dialog, null);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        final TextView beginningTimeTextView = (TextView) dialogView.findViewById(R.id.beginningTimeTextView);
        final TextView endTimeTextView = (TextView) dialogView.findViewById(R.id.endTimeTextView);
        Button setBeginningTimeButton = (Button) dialogView.findViewById(R.id.setBeginningTimeButton);
        Button setEndTimeButton = (Button) dialogView.findViewById(R.id.setEndTimeButton);
        Button updateTimesButton = (Button) dialogView.findViewById(R.id.updateTimesButton);
        String beginningTime;
        String endTime;

        if (!currentFridayWorkingHours.equals("N/A")) {
            beginningTime = currentFridayWorkingHours.substring(0, currentFridayWorkingHours.indexOf('-')).trim();
            endTime = currentFridayWorkingHours.substring(currentFridayWorkingHours.indexOf('-') + 1, currentFridayWorkingHours.length()).trim();

            beginningTimeTextView.setText("Beginning: " + beginningTime);
            endTimeTextView.setText("End: " + endTime);
        }

        setBeginningTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

                        String time = (String) dateFormatter.format(calendar.getTime()).toString();

                        beginningTimeTextView.setText("Beginning Time: " + time);
                    }
                };

                new TimePickerDialog(EmployeeBranchHoursPage.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        });

        setEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

                        String time = (String) dateFormatter.format(calendar.getTime()).toString();

                        endTimeTextView.setText("End Time: " + time);
                    }
                };

                new TimePickerDialog(EmployeeBranchHoursPage.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        });

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        updateTimesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String beginningTime = beginningTimeTextView.getText().toString();
                String endTime = endTimeTextView.getText().toString();
                String newBeginningTime = null;
                String newEndTime = null;
                boolean isValid = true;
                BranchEmployee employee = new BranchEmployee();
                String newTime = "N/A";

                if (!beginningTime.equals("Beginning Time: N/A")) {
                    newBeginningTime = beginningTime.substring(beginningTime.indexOf(':') + 1, beginningTime.length()).trim();
                }

                if (!endTime.equals("End Time: N/A")) {
                    newEndTime = endTime.substring(endTime.indexOf(':') + 1, endTime.length()).trim();
                }

                if (newBeginningTime != null && newEndTime != null) {
                    int beginningHour = Integer.parseInt(newBeginningTime.substring(0, newBeginningTime.indexOf(':')).trim());
                    int endHour = Integer.parseInt(newEndTime.substring(0, newEndTime.indexOf(':')).trim());

                    if (beginningHour > endHour) {
                        beginningTimeTextView.setError("Beginning time is after end time");
                        endTimeTextView.setError("End time is before beginning time");
                        isValid = false;
                    } else {
                        newTime = newBeginningTime + " - " + newEndTime;
                        currentFridayWorkingHours = newTime;
                        fridayHours.setText("Hours: " + newTime);
                    }
                }

                if (newBeginningTime == null) {
                    beginningTimeTextView.setError("Beginning time cannot be null");
                    isValid = false;
                }

                if (newEndTime == null) {
                    beginningTimeTextView.setError("End time cannot be null");
                    isValid = false;
                }

                if (isValid) {
                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vb.vibrate(lowVibrateValue);

                    employee.updateWorkingHours(employeeUsername, currentBranchName, currentBranchAddress, currentBranchPhoneNumber, currentBranchServices,
                            currentMondayOpen, currentTuesdayOpen, currentWednesdayOpen, currentThursdayOpen, currentFridayOpen, currentSaturdayOpen,
                            currentSundayOpen, currentMondayWorkingHours, currentTuesdayWorkingHours, currentWednesdayWorkingHours, currentThursdayWorkingHours,
                            newTime, currentSaturdayWorkingHours, currentSundayWorkingHours, currentBranchRating, currentBranchRatingVoteCount);

                    Toast toast;
                    Toast.makeText(EmployeeBranchHoursPage.this, "Successfully updated the branch's Friday working hours", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void saturdaySetHoursDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.employee_set_hours_dialog, null);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        final TextView beginningTimeTextView = (TextView) dialogView.findViewById(R.id.beginningTimeTextView);
        final TextView endTimeTextView = (TextView) dialogView.findViewById(R.id.endTimeTextView);
        Button setBeginningTimeButton = (Button) dialogView.findViewById(R.id.setBeginningTimeButton);
        Button setEndTimeButton = (Button) dialogView.findViewById(R.id.setEndTimeButton);
        Button updateTimesButton = (Button) dialogView.findViewById(R.id.updateTimesButton);
        String beginningTime;
        String endTime;

        if (!currentSaturdayWorkingHours.equals("N/A")) {
            beginningTime = currentSaturdayWorkingHours.substring(0, currentSaturdayWorkingHours.indexOf('-')).trim();
            endTime = currentSaturdayWorkingHours.substring(currentSaturdayWorkingHours.indexOf('-') + 1, currentSaturdayWorkingHours.length()).trim();

            beginningTimeTextView.setText("Beginning: " + beginningTime);
            endTimeTextView.setText("End: " + endTime);
        }

        setBeginningTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

                        String time = (String) dateFormatter.format(calendar.getTime()).toString();

                        beginningTimeTextView.setText("Beginning Time: " + time);
                    }
                };

                new TimePickerDialog(EmployeeBranchHoursPage.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        });

        setEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

                        String time = (String) dateFormatter.format(calendar.getTime()).toString();

                        endTimeTextView.setText("End Time: " + time);
                    }
                };

                new TimePickerDialog(EmployeeBranchHoursPage.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        });

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        updateTimesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String beginningTime = beginningTimeTextView.getText().toString();
                String endTime = endTimeTextView.getText().toString();
                String newBeginningTime = null;
                String newEndTime = null;
                boolean isValid = true;
                BranchEmployee employee = new BranchEmployee();
                String newTime = "N/A";

                if (!beginningTime.equals("Beginning Time: N/A")) {
                    newBeginningTime = beginningTime.substring(beginningTime.indexOf(':') + 1, beginningTime.length()).trim();
                }

                if (!endTime.equals("End Time: N/A")) {
                    newEndTime = endTime.substring(endTime.indexOf(':') + 1, endTime.length()).trim();
                }

                if (newBeginningTime != null && newEndTime != null) {
                    int beginningHour = Integer.parseInt(newBeginningTime.substring(0, newBeginningTime.indexOf(':')).trim());
                    int endHour = Integer.parseInt(newEndTime.substring(0, newEndTime.indexOf(':')).trim());

                    if (beginningHour > endHour) {
                        beginningTimeTextView.setError("Beginning time is after end time");
                        endTimeTextView.setError("End time is before beginning time");
                        isValid = false;
                    } else {
                        newTime = newBeginningTime + " - " + newEndTime;
                        currentSaturdayWorkingHours = newTime;
                        saturdayHours.setText("Hours: " + newTime);
                    }
                }

                if (newBeginningTime == null) {
                    beginningTimeTextView.setError("Beginning time cannot be null");
                    isValid = false;
                }

                if (newEndTime == null) {
                    beginningTimeTextView.setError("End time cannot be null");
                    isValid = false;
                }

                if (isValid) {
                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vb.vibrate(lowVibrateValue);

                    employee.updateWorkingHours(employeeUsername, currentBranchName, currentBranchAddress, currentBranchPhoneNumber, currentBranchServices,
                            currentMondayOpen, currentTuesdayOpen, currentWednesdayOpen, currentThursdayOpen, currentFridayOpen, currentSaturdayOpen,
                            currentSundayOpen, currentMondayWorkingHours, currentTuesdayWorkingHours, currentWednesdayWorkingHours, currentThursdayWorkingHours,
                            currentFridayWorkingHours, newTime, currentSundayWorkingHours, currentBranchRating, currentBranchRatingVoteCount);

                    Toast toast;
                    Toast.makeText(EmployeeBranchHoursPage.this, "Successfully updated the branch's Saturday working hours", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void sundaySetHoursDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.employee_set_hours_dialog, null);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        final TextView beginningTimeTextView = (TextView) dialogView.findViewById(R.id.beginningTimeTextView);
        final TextView endTimeTextView = (TextView) dialogView.findViewById(R.id.endTimeTextView);
        Button setBeginningTimeButton = (Button) dialogView.findViewById(R.id.setBeginningTimeButton);
        Button setEndTimeButton = (Button) dialogView.findViewById(R.id.setEndTimeButton);
        Button updateTimesButton = (Button) dialogView.findViewById(R.id.updateTimesButton);
        String beginningTime;
        String endTime;

        if (!currentSundayWorkingHours.equals("N/A")) {
            beginningTime = currentSundayWorkingHours.substring(0, currentSundayWorkingHours.indexOf('-')).trim();
            endTime = currentSundayWorkingHours.substring(currentSundayWorkingHours.indexOf('-') + 1, currentSundayWorkingHours.length()).trim();

            beginningTimeTextView.setText("Beginning: " + beginningTime);
            endTimeTextView.setText("End: " + endTime);
        }

        setBeginningTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

                        String time = (String) dateFormatter.format(calendar.getTime()).toString();

                        beginningTimeTextView.setText("Beginning Time: " + time);
                    }
                };

                new TimePickerDialog(EmployeeBranchHoursPage.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        });

        setEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

                        String time = (String) dateFormatter.format(calendar.getTime()).toString();

                        endTimeTextView.setText("End Time: " + time);
                    }
                };

                new TimePickerDialog(EmployeeBranchHoursPage.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        });

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        updateTimesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String beginningTime = beginningTimeTextView.getText().toString();
                String endTime = endTimeTextView.getText().toString();
                String newBeginningTime = null;
                String newEndTime = null;
                boolean isValid = true;
                BranchEmployee employee = new BranchEmployee();
                String newTime = "N/A";

                if (!beginningTime.equals("Beginning Time: N/A")) {
                    newBeginningTime = beginningTime.substring(beginningTime.indexOf(':') + 1, beginningTime.length()).trim();
                }

                if (!endTime.equals("End Time: N/A")) {
                    newEndTime = endTime.substring(endTime.indexOf(':') + 1, endTime.length()).trim();
                }

                if (newBeginningTime != null && newEndTime != null) {
                    int beginningHour = Integer.parseInt(newBeginningTime.substring(0, newBeginningTime.indexOf(':')).trim());
                    int endHour = Integer.parseInt(newEndTime.substring(0, newEndTime.indexOf(':')).trim());

                    if (beginningHour > endHour) {
                        beginningTimeTextView.setError("Beginning time is after end time");
                        endTimeTextView.setError("End time is before beginning time");
                        isValid = false;
                    } else {
                        newTime = newBeginningTime + " - " + newEndTime;
                        currentSundayWorkingHours = newTime;
                        sundayHours.setText("Hours: " + newTime);
                    }
                }

                if (newBeginningTime == null) {
                    beginningTimeTextView.setError("Beginning time cannot be null");
                    isValid = false;
                }

                if (newEndTime == null) {
                    beginningTimeTextView.setError("End time cannot be null");
                    isValid = false;
                }

                if (isValid) {
                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vb.vibrate(lowVibrateValue);

                    employee.updateWorkingHours(employeeUsername, currentBranchName, currentBranchAddress, currentBranchPhoneNumber, currentBranchServices,
                            currentMondayOpen, currentTuesdayOpen, currentWednesdayOpen, currentThursdayOpen, currentFridayOpen, currentSaturdayOpen,
                            currentSundayOpen, currentMondayWorkingHours, currentTuesdayWorkingHours, currentWednesdayWorkingHours, currentThursdayWorkingHours,
                            currentFridayWorkingHours, currentSaturdayWorkingHours, newTime, currentBranchRating, currentBranchRatingVoteCount);

                    Toast toast;
                    Toast.makeText(EmployeeBranchHoursPage.this, "Successfully updated the branch's Sunday working hours", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }
}
