package com.seg2105.servicenovigrad;

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

public class EmployeeTestCase {
    /**
     *
     * We are testing if the email is valid or not
     * in this case I feed it proper input
     */
    @Test
    public void test_IsEmailValid(){
        UserRegistration obj = new UserRegistration();
        boolean result = obj.checkEmailValidation("robert@hotmail.com");
        assertThat(result, is(true));
    }

    /**
     *
     * We are testing if the email is valid or not
     * in this case I feed it wrong input that should give a false output
     */

    @Test
    public void test_isNotValidEmail(){
        UserRegistration obj = new UserRegistration();
        boolean result = obj.checkEmailValidation("robert.com");
        assertThat(result, is(false));
    }

    @Test
    public void test_name() {
        UserRegistration obj = new UserRegistration();
        String name = "rojn223";
        boolean res = obj.checkValidName(name);
        assertThat(res,is(false));
    }

    @Test

    public void test_EmployeeOrCustomer(){
        UserRegistration user2 = new UserRegistration();
        boolean res = user2.validateEntries("firstName", "lastName", "rob@hotmail.com", "username", "password",
                "password");
        assertEquals(true,res);

    }
}

