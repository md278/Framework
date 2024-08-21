package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class AccountsPageTest extends BaseTest {

    @BeforeClass
    public void accountsPageSetup() {
        accountsPage = loginPage.performLogin(prop.getProperty("username"), prop.getProperty("password"));
    }

    @Test
    public void accountsPageTitleTest() {
        Assert.assertEquals(accountsPage.getAccountsPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);
    }

    @Test
    public void accountsPageUrlTest() {
        Assert.assertTrue(accountsPage.getAccountsPageUrl().contains(AppConstants.ACCOUNTS_PAGE_URL));
    }

    @Test
    public void doesLogoutLinkExistTest() {
        Assert.assertTrue(accountsPage.doesLogoutLinkExist());
    }

    @Test
    public void doesSearchBarExistTest() {
        Assert.assertTrue(accountsPage.doesSearchBarExist());
    }

    @Test
    public void accountsPageHeadersCountTest() {
        List<String> actualHeadersList = accountsPage.getAccountHeaders();
        Assert.assertEquals(actualHeadersList.size(), AppConstants.ACCOUNTS_PAGE_HEADER_COUNT);

    }

    @Test
    public void accountsPageHeadersTest() {
        List<String> accountsPageHeadersList = accountsPage.getAccountHeaders();
        Assert.assertEquals(accountsPageHeadersList, AppConstants.ACCOUNTS_PAGE_HEADER_LIST);
    }

    @Test
    public void performSearchTest() {
        searchResultsPage = accountsPage.performSearch("MacBook");
        productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
        String actualProductName = productInfoPage.getProductHeaderName();
        Assert.assertEquals(actualProductName, "MacBook Pro");
    }
}
