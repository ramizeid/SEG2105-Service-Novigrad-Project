package com.seg2105.servicenovigrad;
import org.hamcrest.Factory;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import android.view.View;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.ContentView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class CustomerTestCase {

    @Test
    public void test_requestValidation(){
        Request obj = new Request();
        obj.setCustomerUsername("robert");
        String customer = "robert";
        assertThat(obj.getCustomerUsername(), is(customer));
    }

    @Test
    public void test_branchName(){
        Request obj = new Request();
        obj.setBranchName("Best Branch");
        String customer = "Best Branch";
        assertThat(obj.getBranchName(), is(customer));
    }

    @Test
    public void test_birthDate(){
        Request obj = new Request();
        obj.setDateOfBirth("Oct72001");
        String customer = "Oct72001";
        assertThat(obj.getDateOfBirth(), is(customer));
    }

    @Test
    public void test_birthDate1(){
        Request obj = new Request();
        obj.setDateOfBirth("Oct7200");
        String customer = "Oct7200";
        assertThat(obj.getDateOfBirth(), is(customer));
    }

    @Test
    public void test_serviceName(){
        Request obj = new Request();
        obj.setServiceName("Driver's");
        String customer = "Driver's";
        assertThat(obj.getServiceName(), is(customer));
    }

    @Test
    public void test_address(){
        CustomerBranchInformationPage obj = new CustomerBranchInformationPage();
        obj.getAddress();
        String customer = null;
        assertThat(obj.getAddress(), is(customer));
    }

    @Test
    public void test_address1(){
        Request obj = new Request();
        obj.setAddress("123 road");
        String customer = "123 road";
        assertThat(obj.getAddress(), is(customer));
    }

    @Test
    public void test_username(){
        CustomerBranchInformationPage obj = new CustomerBranchInformationPage();
        obj.getCustomerName();
        String customer = null;
        assertEquals(obj.getAddress(), null);
    }

    @Test
    public void test_serviceName1(){
        Request obj = new Request();
        obj.setServiceName("");
        String customer = "";
        assertThat(obj.getServiceName(), is(customer));
    }

    @Test
    public void test_serviceName2(){
        Request obj = new Request();
        obj.setServiceName("service");
        String customer = "service";
        assertEquals(obj.getServiceName(), customer);
    }

}
