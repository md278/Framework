package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.pages.LoginPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("SUI-10101: Design OpenCart Login page")
@Epic("CP-10102: Login page scenarios")
@Feature("F50: Feature login page")
public class LoginPageTest extends BaseTest {

    @Description("It's a login page title test.")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void loginPageTitleTest() {
        String actualTitle = loginPage.getLoginPageTitle();
        Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE);
    }

    @Description("It's a login page url test.")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void loginPageUrlTest() {
        String actualUrl = loginPage.getLoginPageUrl();
        Assert.assertTrue(actualUrl.contains(AppConstants.LOGIN_PAGE_URL));
    }

    @Description("It's a forgot password link test.")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void forgotPwdLinkExistTest() {
        Assert.assertTrue(loginPage.isForgotPwdLinkAvailable());
    }

    @Description("It's a login page logo test.")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void isLogoExistTest() {
        Assert.assertTrue(loginPage.isLogoAvailable());
    }

    @Description("It's a login test.")
    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 5)
    public void loginTest() {
        accountsPage = loginPage.performLogin(prop.getProperty("username"), prop.getProperty("password"));
        Assert.assertTrue(accountsPage.doesLogoutLinkExist());
    }
}
