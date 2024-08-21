package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.pages.RegistrationPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.UUID;

public class RegistrationPageTest extends BaseTest {

    @BeforeClass
    public void registrationSetup() {
        registrationPage = loginPage.navigateToRegistrationPage();
    }

    private String dynamicEmailAddress() {
//        return "testautomation" + System.currentTimeMillis() + "@opencart.com";
        return "testautomation_" + UUID.randomUUID() + "@opencart.com";
    }

    @DataProvider
    private Object[][] getRegistrationData() {
        return new Object[][]{
                {"Fido", "Dido", "1234567890", "test1234", "test1234", "yes"},
                {"Rajesh", "Taylor", "7658607880", "test1234", "test1234", "yes"},
                {"Gajodhar", "Khan", "3658797809", "test1234", "test1234", "yes"},
        };
    }

    @Test(dataProvider = "getRegistrationData")
    public void fillRegistrationFormTest(String firstName, String lastName, String telephone, String password, String confirmPassword, String subscribe) {
        boolean isRegistrationDone = registrationPage.fillRegistrationForm(firstName, lastName, dynamicEmailAddress(), telephone, password, confirmPassword, subscribe);
        Assert.assertTrue(isRegistrationDone);
    }
}
