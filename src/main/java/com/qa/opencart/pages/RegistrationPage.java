package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {
    private WebDriver driver;
    private ElementUtil elementUtil;

    private By firstName = By.id("input-firstname");
    private By lastName = By.id("input-lastname");
    private By email = By.id("input-email");
    private By telephone = By.id("input-telephone");
    private By password = By.id("input-password");
    private By confirmPassword = By.id("input-confirm");
    private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input[@type='radio']");
    private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input[@type='radio']");
    private By agreeCheckbox = By.name("agree");
    private By continueBtn = By.xpath("//input[@type='submit' and @value='Continue']");
    private By successMsg = By.cssSelector("div#content h1");
    private By logoutLink = By.linkText("Logout");
    private By registerLink = By.linkText("Register");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(this.driver);
    }

    public boolean fillRegistrationForm(String firstName, String lastName, String email,
                                        String telephone, String password, String confirmPassword, String subscribe) {
        elementUtil.performSendKeys(this.firstName, firstName);
        elementUtil.performSendKeys(this.lastName, lastName);
        elementUtil.performSendKeys(this.email, email);
        elementUtil.performSendKeys(this.telephone, telephone);
        elementUtil.performSendKeys(this.password, password);
        elementUtil.performSendKeys(this.confirmPassword, confirmPassword);

        if (subscribe.equalsIgnoreCase("yes")) {
            elementUtil.performClick(subscribeYes);
        } else {
            elementUtil.performClick(subscribeNo);
        }

        elementUtil.performClick(agreeCheckbox);
        elementUtil.performClick(continueBtn);

        String message = elementUtil.waitForVisibilityOfElement(successMsg, AppConstants.DEFAULT_SHORT_WAIT).getText();

        if (message.contains(AppConstants.REGISTRATION_SUCCESS_MESSAGE)) {
            elementUtil.performClick(logoutLink);
            elementUtil.performClick(registerLink);
            return true;
        }
        return false;

    }


}
