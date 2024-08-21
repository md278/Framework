package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultsPage {
    protected WebDriver driver;
    private ElementUtil elementUtil;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(this.driver);
    }

    public ProductInfoPage selectProduct(String productName) {
        elementUtil.waitForVisibilityOfElement(By.linkText(productName), AppConstants.DEFAULT_SHORT_WAIT).click();
        return new ProductInfoPage(driver);
    }
}
