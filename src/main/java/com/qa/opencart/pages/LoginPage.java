package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;
    private ElementUtil elementUtil;

    // By Locators - private locators is an example of Encapsulation in OOP.
    private By userName = By.id("input-email");
    private By password = By.id("input-password");
    private By loginBtn = By.xpath("//input[@value='Login']");
    private By forgotPwdLink = By.linkText("Forgotten Password");
    private By registerLink = By.linkText("Register");

    private By logo = By.cssSelector("img[title='naveenopencart']");


    // Page Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.elementUtil = new ElementUtil(this.driver);
    }

    // Page Actions/Methods
    @Step("It gets the login page title.") // coming from Allure report
    public String getLoginPageTitle() {
        return elementUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_SHORT_WAIT);
    }

    public String getLoginPageUrl() {
        return elementUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL, AppConstants.DEFAULT_SHORT_WAIT);
    }

    public boolean isForgotPwdLinkAvailable() {
        return elementUtil.waitForVisibilityOfElement(forgotPwdLink, AppConstants.DEFAULT_SHORT_WAIT).isDisplayed();
    }

    public boolean isLogoAvailable() {
        return elementUtil.waitForVisibilityOfElement(logo, AppConstants.DEFAULT_SHORT_WAIT).isDisplayed();
    }

    @Step("Username is: {0} and Password: {1}")
    public AccountsPage performLogin(String username, String pass) {
        elementUtil.waitForVisibilityOfElement(userName, AppConstants.DEFAULT_MEDIUM_WAIT).sendKeys(username);
        elementUtil.performSendKeys(password, pass);
        elementUtil.performClick(loginBtn);
        return new AccountsPage(driver);
    }

    public RegistrationPage navigateToRegistrationPage(){
        elementUtil.waitForVisibilityOfElement(registerLink, AppConstants.DEFAULT_SHORT_WAIT).click();
        return new RegistrationPage(driver);
    }
}
