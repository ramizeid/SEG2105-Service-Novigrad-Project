package com.seg2105.servicenovigrad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegistration extends AppCompatActivity {
    private int normalVibrateValue = 10;
    private int lowVibrateValue = normalVibrateValue / 2;
    EditText firstNameTextBox;
    EditText lastNameTextBox;
    EditText emailTextBox;
    EditText usernameTextBox;
    EditText passwordTextBox;
    EditText confirmPasswordTextBox;
    FirebaseDatabase firebase;
    DatabaseReference reference;
    DatabaseReference referenceBranch;
    EditText employeeNumber;
    RadioButton branchemployeebutton;
    RadioButton customerRadioButton;
    TextView alreadyRegistered;
    TextView employeeNumberTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        /*************************** Variables **********************************/
        customerRadioButton = (RadioButton) findViewById(R.id.customerRadioButton);
        branchemployeebutton = (RadioButton) findViewById(R.id.branchemployeebutton);
        alreadyRegistered = (TextView) findViewById(R.id.alreadyRegisteredTextView);
        employeeNumberTitle = (TextView) findViewById(R.id.employeeNumberTitle);
        employeeNumber = (EditText) findViewById(R.id.employeeNumber);
        final Button btnRegister = (Button) findViewById(R.id.btnRegister);
        usernameTextBox = (EditText) findViewById(R.id.usernameTextBox);
        firstNameTextBox = (EditText) findViewById(R.id.firstNameTextBox);
        lastNameTextBox = (EditText) findViewById(R.id.lastNameTextBox);
        emailTextBox = findViewById(R.id.emailTextBox);
        passwordTextBox = (EditText) findViewById(R.id.passwordTextBox);
        confirmPasswordTextBox = (EditText) findViewById(R.id.confirmPasswordTextBox);
        /*************************** Variables **********************************/

        /******************* Method **********************/
        hideEmployeeNumberAndBranch(employeeNumberTitle, employeeNumber);
        /******************* Method **********************/

        /* Start of btn register method */
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebase = FirebaseDatabase.getInstance();
                reference = firebase.getReference("user_information");
                referenceBranch = firebase.getReference("Branches");

                /****************************************************
                 * Retrieving user input
                 *************************************************************/
                String firstName = firstNameTextBox.getText().toString().length() > 0
                        ? firstNameTextBox.getText().toString().trim()
                        : "";
                String lastName = lastNameTextBox.getText().toString().length() > 0
                        ? lastNameTextBox.getText().toString().trim()
                        : "";
                String email = emailTextBox.getText().toString().length() > 0 ? emailTextBox.getText().toString().trim()
                        : "";
                String username = usernameTextBox.getText().toString().length() > 0
                        ? usernameTextBox.getText().toString().trim()
                        : "";
                String password = passwordTextBox.getText().toString().length() > 0
                        ? passwordTextBox.getText().toString().trim()
                        : "";
                String numberEmployee = employeeNumber.getText().length() > 0
                        ? employeeNumber.getText().toString().trim()
                        : "";
                String confirmpassword = confirmPasswordTextBox.getText().toString().length() > 0
                        ? confirmPasswordTextBox.getText().toString().trim()
                        : "";
                ArrayList<String> userInputs = new ArrayList<>();
                userInputs.add(firstName);
                userInputs.add(lastName);
                userInputs.add(email);
                userInputs.add(username);
                userInputs.add(password);
                userInputs.add(confirmpassword);
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(normalVibrateValue);
                /****************************************************
                 * Retrieving user input
                 *************************************************************/
                /******* CAPITALIZE FUNCTION ***/
                if (firstName != "" && lastName != "") {
                    firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
                    lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
                }
                /******* CAPITALIZE FUNCTION ***/

                /** This if statement checks if the user forgot to enter inputs **/
                if (firstName == "" || lastName == "" || email == "" || username == "" || password == ""
                        || confirmpassword == "" || !checkIfUserClickedRadioButtons(numberEmployee)) {
                    checksIfInputIsInvalid(userInputs);
                } else {

                    if (validateEntries(firstName, lastName, email, username, password, confirmpassword)) {
                        if (isnewUser(username, email)) {

                            String role = EmployeeOrCustomerBranch();
                            username = username.toLowerCase();
                            password = hashPassword(password, username);


                            if (role.equals("employee")) {
                                User user = new BranchEmployee(firstName, lastName, email, username, password, role,
                                        numberEmployee, false);

                                Branch branch = new Branch(username + " Branch", "N/A", "N/A",
                                        "N/A", false, false, false, false,
                                        false, false, false, "N/A", "N/A",
                                        "N/A", "N/A", "N/A", "N/A",
                                        "N/A", "1", "0");

                                reference.child(username).setValue(user);
                                referenceBranch.child(username + " Branch").setValue(branch);
                            } else if (role.equals("customer")) {
                                User user = new Customer(firstName, lastName, email, username, password, role,
                                        numberEmployee, false);

                                reference.child(username).setValue(user);
                            } else {
                                User user = new Administrator(firstName, lastName, email, username, password, role,
                                        numberEmployee, false);

                                reference.child(username).setValue(user);
                            }

                            Toast toast;
                            Toast.makeText(UserRegistration.this, "Account successfully created!", Toast.LENGTH_SHORT)
                                    .show();

                            // redirect client to Login page
                            startActivity(new Intent(UserRegistration.this, Login.class));
                            finish();
                        }
                    }

                }
            }

        });
        /* End of btn register method */

        callAllButtons();
    }

    /**
     * This method is responsible for hashing the passwords in our real time database
     *
     * @param password user password
     * @param salt     password salt
     * @return string hashed password
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

    public void openLogin(View view) {
        startActivity(new Intent(UserRegistration.this, Login.class));
    }

    /**
     * This method checks if the user pressed on the customer radio button or the
     * employee button
     *
     * @return role
     */
    public String EmployeeOrCustomerBranch() {
        if (customerRadioButton.isChecked() || customerRadioButton == null  ) {
            return "customer";
        }

        return "employee";
    }

    /**
     * This method checks if the user pressed the radio buttons
     *
     * @param numberEmployee
     * @return true or false depending on conditions
     */
    public boolean checkIfUserClickedRadioButtons(String numberEmployee) {
        if (!branchemployeebutton.isChecked() && !customerRadioButton.isChecked()) {
            branchemployeebutton.setError("Field is required");
            customerRadioButton.setError("Field is required");
            return false;
        }
        return true;
    }

    /**
     * This method is responsible to check if one of the inputs is empty after
     * filling a few inputs it will prompt us to check each elements of the
     * arraylist and return error to the one that was false program using <code>
     * java
     *
     * @param userInputs ArrayList of all the user inputs
     * @return true or false depending on if they catch the error
     */
    public void checksIfInputIsInvalid(ArrayList<String> userInputs) {
        for (int i = 0; i < userInputs.size(); i++) {
            if (userInputs.get(i) == "") {
                switch (i) {
                    case 0:
                        firstNameTextBox.setError("First name is required");
                        break;
                    case 1:
                        lastNameTextBox.setError("Last name is required");
                        break;
                    case 2:
                        emailTextBox.setError("Email address is required");
                        break;
                    case 3:
                        usernameTextBox.setError("Username is required");
                        break;
                    case 4:
                        passwordTextBox.setError("Password is required");
                        break;
                    case 5:
                        confirmPasswordTextBox.setError("Password confirmation is required");
                        break;
                }
            }
        }
        userInputs.clear();

    }

    /**
     * This method is responsible for hinding all the textviews and text from a
     * customer if the customer radio button is clicked
     *
     * @param employeeNumberTitle
     * @param employeeNumber
     */
    public void hideEmployeeNumberAndBranch(TextView employeeNumberTitle,
                                            EditText employeeNumber) {
        employeeNumber.setVisibility(View.GONE);
        employeeNumberTitle.setVisibility(View.GONE);
    }

    /**
     * This method is responsible of all the logical code behind each button press
     * they are placed here for cleanliness purposes program using <code> java
     */
    public void callAllButtons() {
        customerRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);
                employeeNumber.setVisibility(View.GONE);
                employeeNumberTitle.setVisibility(View.GONE);
            }
        });
        branchemployeebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);
                employeeNumber.setVisibility(View.VISIBLE);
                employeeNumberTitle.setVisibility(View.VISIBLE);
            }
        });

        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(lowVibrateValue);
                Intent intent = new Intent(UserRegistration.this, Login.class);
                startActivity(intent);
            }
        });
    }

    /**
     * This helper method is being called in the valid entried method and is
     * responsible to check if the user has entered a number in their firstname or
     * lastname it will return false if it catches a number program using <code>
     * java
     *
     * @param name any user input
     * @return true or false depending on if they catch the error
     */
    public boolean checkValidName(String name) {
        // puts each character of the string in a char array
        char[] c = name.toCharArray();
        boolean flag = true;
        for (char i : c) {
            if (!Character.isLetter(i))
                return false;
        }
        return true;
    }

    /**
     * This helper method is being called in the valid entried method and is
     * responsible to check if the user has entered a valid email although it is not
     * checking if it's an active email just mainly checks if there is an extension
     * and a "@" symbol program using <code> java
     *
     * @param email The users email
     * @return true or false depending on if they catch the error
     */
    public static boolean checkEmailValidation(String email) {
        String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        // This line was taken from https://stackoverflow.com/questions/31262250/how-to-check-whether-email-is-valid-format-or-not-in-android/31262363
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()){
            return true;
        }
        return  false;
    }

    /**
     * This method is responsible for the validation of the users entries in the
     * registration form we mainly want to check if the user passes the conditions
     * and meets the requirement to register on the app program using <code> java
     *
     * @param firstName       The first name entered by the user.
     * @param lastName        The last name entered by the user.
     * @param email           The email entered by the user.
     * @param username        The username entered. by the user
     * @param password        The password entered by the user.
     * @param confirmpassword The confirmedpassword entered.
     * @return flag whether or not the user has passed the conditions
     */
    public boolean validateEntries(String firstName, String lastName, String email, String username, String password,
                                   String confirmpassword) {
        boolean flag = true;
        // All inputs are empty
        if (firstName == "" && lastName == "" && email == "" && username == "" && password == ""
                && confirmpassword == "") {
            firstNameTextBox.setError("Invalid Name");
            lastNameTextBox.setError("Invalid Name");
            usernameTextBox.setError("Username can't start with a number and must be a minimum length of 3");
            emailTextBox.setError("Email address is invalid");
            passwordTextBox.setError("Password must be larger than 5 characters");
            confirmPasswordTextBox.setError("Passwords do not match!");
            flag = false;

        }
        // Check if firstname and lastname is valid
        if (!checkValidName(firstName) || !checkValidName(lastName)) {
            firstNameTextBox.setError("Invalid Name");
            lastNameTextBox.setError("Invalid Name");
            flag = false;
        }
        // Checking if username is valid
        if (Character.isDigit(username.charAt(0)) || username.length() < 2) {
            usernameTextBox.setError("Username can't start with a number and must be a minimum length of 3");
            flag = false;
        }
        // Checking for email validation
        if (!checkEmailValidation(email)) {
            emailTextBox.setError("Email address is invalid");
            flag = false;
        }
        // Checking for password validation
        if (password.length() <= 5) {
            passwordTextBox.setError("Password must be larger than 5 characters");
            flag = false;
        } else if (!password.equals(confirmpassword)) {
            confirmPasswordTextBox.setError("Passwords do not match!");
            flag = false;
        }
        return flag;
    }

    // FUTURE IMPLEMENTATION
    public boolean isnewUser(String username, String email) {
        return true;
    }
}
