package com.seg2105.servicenovigrad;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UserTestCase {
    ArrayList<String> userInformation = new ArrayList<>();

    // Testing the creation of a customer account
    @Test
    public void customerUser() {
        userInformation.add("Robert");
        userInformation.add("Basile");
        userInformation.add("robert@hotmail.com");
        userInformation.add("RobertBasile");
        userInformation.add("Test123");
        userInformation.add("customer");
        userInformation.add("n/a");
        User customer = new Customer("Robert", "Basile", "robert@hotmail.com", "RobertBasile", "Test123", "customer", "n/a",false);
        assertEquals(userInformation.get(0),customer.getFirstname()); // Testing first name
        assertEquals(userInformation.get(1),customer.getLastname()); // Testing last name
        assertEquals(userInformation.get(2),customer.getEmail()); // Testing email
        assertEquals(userInformation.get(3),customer.getUsername()); // Testing username
        assertEquals(userInformation.get(4),customer.getPassword()); // Testing password
        assertEquals(userInformation.get(5),customer.getBranch()); // Testing account type
        assertEquals(userInformation.get(6),customer.getNumberEmployee());
        assertEquals(userInformation.get(6),customer.getisLoggedIn());
        assertEquals(false,customer.getisLoggedIn());// Testing employee number
        userInformation.clear();

    }

    // Testing the creation of an employee account
    @Test
    public void employeeUser() {
        userInformation.add("Robert");
        userInformation.add("Basile");
        userInformation.add("robert@hotmail.com");
        userInformation.add("RobertBasile");
        userInformation.add("Test123");
        userInformation.add("Branch 1");
        userInformation.add("123456");

        User employeee = new BranchEmployee("Robert", "Basile", "robert@hotmail.com", "RobertBasile", "Test123", "Branch 1", "123456",false);
        assertEquals(userInformation.get(0),employeee.getFirstname()); // Testing first name
        assertEquals(userInformation.get(1),employeee.getLastname()); // Testing last name
        assertEquals(userInformation.get(2),employeee.getEmail()); // Testing email
        assertEquals(userInformation.get(3),employeee.getUsername()); // Testing username
        assertEquals(userInformation.get(4),employeee.getPassword()); // Testing password
        assertEquals(userInformation.get(5),employeee.getBranch()); // Testing account type
        assertEquals(userInformation.get(6),employeee.getNumberEmployee()); // Testing employee number
        assertEquals(false,employeee.getisLoggedIn());
        userInformation.clear();
    }

    // Testing the creation of an admin account
    @Test
    public void adminUser() {
        userInformation.add("Robert");
        userInformation.add("Basile");
        userInformation.add("robert@hotmail.com");
        userInformation.add("RobertBasile");
        userInformation.add("Test123");
        userInformation.add("admin");
        userInformation.add("1");
        User admin = new Administrator("Robert", "Basile", "robert@hotmail.com", "RobertBasile", "Test123", "admin", "1",false);
        assertEquals(userInformation.get(0),admin.getFirstname()); // Testing first name
        assertEquals(userInformation.get(1),admin.getLastname()); // Testing last name
        assertEquals(userInformation.get(2),admin.getEmail()); // Testing email
        assertEquals(userInformation.get(3),admin.getUsername()); // Testing username
        assertEquals(userInformation.get(4),admin.getPassword()); // Testing password
        assertEquals(userInformation.get(5),admin.getBranch()); // Testing account type
        assertEquals(userInformation.get(6),admin.getNumberEmployee()); // Testing employee number
        assertEquals(false,admin.getisLoggedIn());

        userInformation.clear();
    }
}

