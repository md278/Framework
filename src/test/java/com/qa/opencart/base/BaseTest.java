package com.qa.opencart.base;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected Properties prop;
    DriverFactory factory;
    protected LoginPage loginPage;
    protected AccountsPage accountsPage;
    protected SearchResultsPage searchResultsPage;
    protected ProductInfoPage productInfoPage;

    protected RegistrationPage registrationPage;
    protected SoftAssert softAssert;

    @Parameters({"browser"})
    @BeforeTest
    public void setUp(String browserName) {
        factory = new DriverFactory();
        prop = factory.initProp();

        if (browserName != null) {
            prop.setProperty("browser", browserName);
        }

        driver = factory.initDriver(prop);
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
