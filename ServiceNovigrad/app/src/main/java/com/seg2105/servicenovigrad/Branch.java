package com.seg2105.servicenovigrad;

public class Branch {
    String branchName;
    String branchAddress;
    String branchPhoneNumber;
    String branchServices;
    boolean mondayOpen;
    boolean tuesdayOpen;
    boolean wednesdayOpen;
    boolean thursdayOpen;
    boolean fridayOpen;
    boolean saturdayOpen;
    boolean sundayOpen;
    String mondayWorkingHours;
    String tuesdayWorkingHours;
    String wednesdayWorkingHours;
    String thursdayWorkingHours;
    String fridayWorkingHours;
    String saturdayWorkingHours;
    String sundayWorkingHours;
    String branchRating;
    String branchRatingVoteCount;
    public Branch(){}
    public Branch(String branchName, String branchAddress, String branchPhoneNumber, String branchServices,
                  boolean mondayOpen, boolean tuesdayOpen, boolean wednesdayOpen, boolean thursdayOpen, boolean fridayOpen,
                  boolean saturdayOpen, boolean sundayOpen, String mondayWorkingHours, String tuesdayWorkingHours,
                  String wednesdayWorkingHours, String thursdayWorkingHours, String fridayWorkingHours,
                  String saturdayWorkingHours, String sundayWorkingHours, String branchRating, String branchRatingVoteCount) {
        this.branchName = branchName;
        this.branchAddress = branchAddress;
        this.branchPhoneNumber = branchPhoneNumber;
        this.branchServices = branchServices;
        this.mondayOpen = mondayOpen;
        this.tuesdayOpen = tuesdayOpen;
        this.wednesdayOpen = wednesdayOpen;
        this.thursdayOpen = thursdayOpen;
        this.fridayOpen = fridayOpen;
        this.saturdayOpen = saturdayOpen;
        this.sundayOpen = sundayOpen;
        this.mondayWorkingHours = mondayWorkingHours;
        this.tuesdayWorkingHours = tuesdayWorkingHours;
        this.wednesdayWorkingHours = wednesdayWorkingHours;
        this.thursdayWorkingHours = thursdayWorkingHours;
        this.fridayWorkingHours = fridayWorkingHours;
        this.saturdayWorkingHours = saturdayWorkingHours;
        this.sundayWorkingHours = sundayWorkingHours;
        this.branchRating = branchRating;
        this.branchRatingVoteCount = branchRatingVoteCount;
    }

    public String getBranchRatingVoteCount() {
        return branchRatingVoteCount;
    }

    public void setBranchRatingVoteCount(String branchRatingVoteCount) {
        this.branchRatingVoteCount = branchRatingVoteCount;
    }

    public String getBranchRating() {
        return branchRating;
    }

    public void setBranchRating(String branchRating) {
        this.branchRating = branchRating;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getBranchPhoneNumber() {
        return branchPhoneNumber;
    }

    public void setBranchPhoneNumber(String branchPhoneNumber) {
        this.branchPhoneNumber = branchPhoneNumber;
    }

    public String getBranchServices() {
        return branchServices;
    }

    public void setBranchServices(String branchServices) {
        this.branchServices = branchServices;
    }

    public String getMondayWorkingHours() {
        return mondayWorkingHours;
    }

    public void setMondayWorkingHours(String mondayWorkingHours) {
        this.mondayWorkingHours = mondayWorkingHours;
    }

    public String getTuesdayWorkingHours() {
        return tuesdayWorkingHours;
    }

    public void setTuesdayWorkingHours(String tuesdayWorkingHours) {
        this.tuesdayWorkingHours = tuesdayWorkingHours;
    }

    public String getWednesdayWorkingHours() {
        return wednesdayWorkingHours;
    }

    public void setWednesdayWorkingHours(String wednesdayWorkingHours) {
        this.wednesdayWorkingHours = wednesdayWorkingHours;
    }

    public String getThursdayWorkingHours() {
        return thursdayWorkingHours;
    }

    public void setThursdayWorkingHours(String thursdayWorkingHours) {
        this.thursdayWorkingHours = thursdayWorkingHours;
    }

    public String getFridayWorkingHours() {
        return fridayWorkingHours;
    }

    public void setFridayWorkingHours(String fridayWorkingHours) {
        this.fridayWorkingHours = fridayWorkingHours;
    }

    public String getSaturdayWorkingHours() {
        return saturdayWorkingHours;
    }

    public void setSaturdayWorkingHours(String saturdayWorkingHours) {
        this.saturdayWorkingHours = saturdayWorkingHours;
    }

    public String getSundayWorkingHours() {
        return sundayWorkingHours;
    }

    public void setSundayWorkingHours(String sundayWorkingHours) {
        this.sundayWorkingHours = sundayWorkingHours;
    }

    public boolean isMondayOpen() {
        return mondayOpen;
    }

    public void setMondayOpen(boolean mondayOpen) {
        this.mondayOpen = mondayOpen;
    }

    public boolean isTuesdayOpen() {
        return tuesdayOpen;
    }

    public void setTuesdayOpen(boolean tuesdayOpen) {
        this.tuesdayOpen = tuesdayOpen;
    }

    public boolean isWednesdayOpen() {
        return wednesdayOpen;
    }

    public void setWednesdayOpen(boolean wednesdayOpen) {
        this.wednesdayOpen = wednesdayOpen;
    }

    public boolean isThursdayOpen() {
        return thursdayOpen;
    }

    public void setThursdayOpen(boolean thursdayOpen) {
        this.thursdayOpen = thursdayOpen;
    }

    public boolean isFridayOpen() {
        return fridayOpen;
    }

    public void setFridayOpen(boolean fridayOpen) {
        this.fridayOpen = fridayOpen;
    }

    public boolean isSaturdayOpen() {
        return saturdayOpen;
    }

    public void setSaturdayOpen(boolean saturdayOpen) {
        this.saturdayOpen = saturdayOpen;
    }

    public boolean isSundayOpen() {
        return sundayOpen;
    }

    public void setSundayOpen(boolean sundayOpen) {
        this.sundayOpen = sundayOpen;
    }
}
