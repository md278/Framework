package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

public class ProductResultsPageTest extends BaseTest {

    @BeforeClass
    public void productInfoPageSetup() {
        accountsPage = loginPage.performLogin(prop.getProperty("username"), prop.getProperty("password"));
    }

    @DataProvider
    public Object[][] getSearchData() {
        return new Object[][]{
                {"MacBook", "MacBook Pro", 4},
                {"MacBook", "MacBook Air", 4},
                {"iMac", "iMac", 3},
                {"Samsung", "Samsung SyncMaster 941BW", 1},
        };
    }

    @Test(dataProvider = "getSearchData")
    public void productImagesTest(String searchKey, String productName, int imagesCount) {
        searchResultsPage = accountsPage.performSearch(searchKey);
        productInfoPage = searchResultsPage.selectProduct(productName);
        Assert.assertEquals(productInfoPage.getProductImagesCount(), imagesCount);
    }

    @Test
    public void productInfoTest() {
        searchResultsPage = accountsPage.performSearch("MacBook");
        productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
        HashMap<String, String> productDetailsMap = productInfoPage.getProductDetails();

        softAssert.assertEquals(productDetailsMap.get("Brand"), "Apple");
        softAssert.assertEquals(productDetailsMap.get("Availability"), "In Stock");
        softAssert.assertEquals(productDetailsMap.get("Product Code"), "Product 18");
        softAssert.assertEquals(productDetailsMap.get("Reward Points"), "800");
        softAssert.assertEquals(productDetailsMap.get("price"), "$2,000.00");
        softAssert.assertEquals(productDetailsMap.get("gst"), "$2,000.00");
        softAssert.assertAll();
    }

}
