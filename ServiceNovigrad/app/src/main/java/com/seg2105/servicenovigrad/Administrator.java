package com.seg2105.servicenovigrad;

import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Administrator extends User {
    FirebaseDatabase firebase;
    DatabaseReference reference;

    public Administrator() {}

    /**
     * This is a filled out constructor with all the admin information
     * @param firstname
     * @param lastname
     * @param email
     * @param username
     * @param password
     * @param branch
     * @param numberEmployee
     * @param isLoggedIn
     */

    public Administrator(String firstname, String lastname, String email, String username, String password,
                         String branch, String numberEmployee, boolean isLoggedIn) {
        super(firstname, lastname, email, username, password, branch, numberEmployee, isLoggedIn);
    }

    /**
     * This method is responsible for deleting service
     *
     * @param serviceName
     * @return
     */
    public void deleteService(String serviceName) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("ServiceRequests").child(serviceName);
        dR.getParent().child(serviceName).removeValue();
    }


    /**
     * This method is responsible for deleting service
     *
     * @param name
     * @return
     */
    public void deleteSelectedAccount(String name){
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("user_information").child(name);
        dR.getParent().child(name).removeValue();
    }

    public void deleteSelectedBranch(String name){
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Branches").child(name);
        dR.getParent().child(name).removeValue();
    }

    /**
     * This method is responsible to add new services in the application to which
     * later on can be changed and updated
     *
     * @param dateOfBirth
     * @param address
     * @param typeOfLicense
     * @param proofOfResidence
     * @param proofOfStatus
     * @param proofOfPhoto
     */
    public void addService(String serviceName, CheckBox dateOfBirth, CheckBox address, CheckBox typeOfLicense,
                           CheckBox proofOfResidence, CheckBox proofOfStatus, CheckBox proofOfPhoto, double servicePrice) {
        DatabaseReference dR;
        dR = FirebaseDatabase.getInstance().getReference("ServiceRequests");

        Service serviceRequest = new ServiceCategories(serviceName, dateOfBirth.isChecked(), address.isChecked(),
                typeOfLicense.isChecked(), proofOfResidence.isChecked(), proofOfStatus.isChecked(),
                proofOfPhoto.isChecked(), servicePrice);
        dR.child(serviceName).setValue(serviceRequest);

    }

    /**
     * This method is responsible for updating existing services in the db
     *
     * @param serviceName
     * @param newServiceName
     * @param dateOfBirth
     * @param address
     * @param typeOfLicense
     * @param proofOfResidence
     * @param proofOfStatus
     * @param proofOfPhoto
     */
    public void updateService(String serviceName, String newServiceName, CheckBox dateOfBirth, CheckBox address,
                              CheckBox typeOfLicense, CheckBox proofOfResidence, CheckBox proofOfStatus, CheckBox proofOfPhoto, double servicePrice) {
        DatabaseReference dR;

        dR = FirebaseDatabase.getInstance().getReference("ServiceRequests").child(serviceName);
        Service serviceRequest = new ServiceCategories(newServiceName, dateOfBirth.isChecked(), address.isChecked(),
                typeOfLicense.isChecked(), proofOfResidence.isChecked(), proofOfStatus.isChecked(),
                proofOfPhoto.isChecked(), servicePrice);
        dR.removeValue();
        dR.getParent().child(newServiceName).setValue(serviceRequest);
    }

}
