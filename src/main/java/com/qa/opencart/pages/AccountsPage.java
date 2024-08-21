package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AccountsPage {
    private WebDriver driver;
    private ElementUtil elementUtil;
    private By logoutLink = By.linkText("Logout");
    private By searchBar = By.name("search");
    private By searchBtn = By.cssSelector("div#search button");
    private By accountHeaders = By.cssSelector("div#content > h2");

    public AccountsPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(this.driver);
    }

    public String getAccountsPageTitle() {
        return elementUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE, AppConstants.DEFAULT_SHORT_WAIT);
    }

    public String getAccountsPageUrl() {
        return elementUtil.waitForURLContains(AppConstants.ACCOUNTS_PAGE_URL, AppConstants.DEFAULT_SHORT_WAIT);
    }

    public boolean doesLogoutLinkExist() {
        return elementUtil.waitForVisibilityOfElement(logoutLink, AppConstants.DEFAULT_SHORT_WAIT).isDisplayed();
    }

    public void logout() {
        if (doesLogoutLinkExist()) {
            elementUtil.performClick(logoutLink);
        }
    }

    public boolean doesSearchBarExist() {
        return elementUtil.waitForVisibilityOfElement(searchBar, AppConstants.DEFAULT_SHORT_WAIT).isDisplayed();
    }

    public List<String> getAccountHeaders() {
        List<WebElement> headerList = elementUtil.waitForVisibilityOfElements(accountHeaders, AppConstants.DEFAULT_MEDIUM_WAIT);
        List<String> headerTextList = new ArrayList<>();
        for (WebElement element : headerList) {
            headerTextList.add(element.getText());
        }
        return headerTextList;
    }

    public SearchResultsPage performSearch(String searchText){
//        elementUtil.waitForVisibilityOfElement(searchBar, AppConstants.DEFAULT_SHORT_WAIT).clear();
        elementUtil.performSendKeys(searchBar, searchText);
//        elementUtil.waitForVisibilityOfElement(searchBtn, AppConstants.DEFAULT_SHORT_WAIT).click();
        elementUtil.performClick(searchBtn);
        return new SearchResultsPage(driver);
    }
}
