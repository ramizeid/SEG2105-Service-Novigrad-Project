package com.seg2105.servicenovigrad;

/**
 * @author  Robert, Rami , Ilyas, Giorgio
 * This class is the abstract superclass of all users in the applications
 * the getters and setters are used as concrete methods since they are simply returning the inputed variables

 */
public  class User {
    String firstname;
    String lastname;
    String email;
    String username;
    String password;
    String branch;
    String numberEmployee;
    boolean isLoggedIn;

    public User(){}
    public User(String firstname, String branch){
        this.firstname = firstname;
        this.branch = branch;
    }

    public User(String firstname, String lastname, String email, String username, String password, String branch, String numberEmployee, boolean isLoggedIn){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.branch = branch;
        this.numberEmployee = numberEmployee;
        this.isLoggedIn = isLoggedIn;
    }

    public boolean getLoginStatus() {
        return isLoggedIn;
    }

    public void toggleLoginStatus() {
        isLoggedIn = !isLoggedIn;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getNumberEmployee() {
        return numberEmployee;
    }

    public void setNumberEmployee(String numberEmployee) {
        this.numberEmployee = numberEmployee;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean getisLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", branch='" + branch + '\'' +
                ", numberEmployee='" + numberEmployee + '\'' +
                '}';
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
