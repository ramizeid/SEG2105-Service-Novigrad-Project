package com.seg2105.servicenovigrad;

/**
 * This class is the super class of all subclasses composed of various different
 * services it is the foundation all other ones
 */
public class Service {
    String serviceName;
    double price;
    boolean dateOfBirth;
    boolean address;
    boolean typeOfLicense;
    boolean proofOfResidence;
    boolean proofOfStatus;
    boolean proofOfPhoto;

    public Service() {
    }

    public Service(String serviceName, boolean dateOfBirth, boolean address, boolean typeOfLicense, boolean proofOfResidence,
                   boolean proofOfStatus, boolean proofOfPhoto, double price) {
        this.serviceName = serviceName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.typeOfLicense = typeOfLicense;
        this.proofOfResidence = proofOfResidence;
        this.proofOfStatus = proofOfStatus;
        this.proofOfPhoto = proofOfPhoto;
        this.price = price;

    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean getProofOfResidence() {
        return this.proofOfResidence;
    }

    public boolean getProofOfStatus() {
        return this.proofOfStatus;
    }

    public boolean getProofOfPhoto() {
        return this.proofOfPhoto;
    }

    public void setProofOfResidence(boolean proofOfResidence) {
        this.proofOfResidence = proofOfResidence;
    }

    public boolean getTypeOfLicense() {
        return typeOfLicense;
    }

    public void setTypeOfLicense(boolean typeOfLicense) {
        this.typeOfLicense = typeOfLicense;
    }

    public boolean getAddress() {
        return address;
    }

    public void setAddress(boolean address) {
        this.address = address;
    }

    public boolean getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(boolean dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setProofOfStatus(boolean proofOfStatus) {
        this.proofOfStatus = proofOfStatus;
    }

    public void setProofOfPhoto(boolean proofOfPhoto) {
        this.proofOfPhoto = proofOfPhoto;
    }

    public double getPrice(){return price;}

    public void setPrice(double price){this.price = price;}
}
