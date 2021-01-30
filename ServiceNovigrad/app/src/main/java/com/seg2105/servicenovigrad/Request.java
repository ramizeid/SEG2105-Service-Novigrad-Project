package com.seg2105.servicenovigrad;

public class Request {
    String customerUsername;
    String branchName;
    String serviceName;
    String dateOfBirth;
    String address;
    boolean proofOfPhotoAttached;
    boolean proofOfResidenceAttached;
    boolean proofOfLicenseAttached;
    boolean proofOfStatusAttached;

    public Request() {
    }

    public Request(String customerUsername, String branchName, String serviceName, String dateOfBirth, String address, boolean proofOfPhotoAttached,
                   boolean proofOfResidenceAttached, boolean proofOfLicenseAttached, boolean proofOfStatusAttached) {
        this.customerUsername = customerUsername;
        this.branchName = branchName;
        this.serviceName = serviceName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.proofOfPhotoAttached = proofOfPhotoAttached;
        this.proofOfResidenceAttached = proofOfResidenceAttached;
        this.proofOfLicenseAttached = proofOfLicenseAttached;
        this.proofOfStatusAttached = proofOfStatusAttached;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isProofOfPhotoAttached() {
        return proofOfPhotoAttached;
    }

    public void setProofOfPhotoAttached(boolean proofOfPhotoAttached) {
        this.proofOfPhotoAttached = proofOfPhotoAttached;
    }

    public boolean isProofOfResidenceAttached() {
        return proofOfResidenceAttached;
    }

    public void setProofOfResidenceAttached(boolean proofOfResidenceAttached) {
        this.proofOfResidenceAttached = proofOfResidenceAttached;
    }

    public boolean isProofOfLicenseAttached() {
        return proofOfLicenseAttached;
    }

    public void setProofOfLicenseAttached(boolean proofOfLicenseAttached) {
        this.proofOfLicenseAttached = proofOfLicenseAttached;
    }

    public boolean isProofOfStatusAttached() {
        return proofOfStatusAttached;
    }

    public void setProofOfStatusAttached(boolean proofOfStatusAttached) {
        this.proofOfStatusAttached = proofOfStatusAttached;
    }
}
