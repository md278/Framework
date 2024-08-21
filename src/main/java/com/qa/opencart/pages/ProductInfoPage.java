package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

public class ProductInfoPage {
    protected WebDriver driver;
    protected ElementUtil elementUtil;
    HashMap<String, String> productDataMap = new HashMap<>();
    private By productHeader = By.cssSelector("div#content h1");
    private By productImages = By.cssSelector("ul.thumbnails img");
    private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
    private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");

    public ProductInfoPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(this.driver);
    }

    public String getProductHeaderName() {
        return elementUtil.getElementText(productHeader);
    }

    public int getProductImagesCount() {
        return elementUtil.waitForVisibilityOfElements(productImages, AppConstants.DEFAULT_SHORT_WAIT).size();
    }

    private void getProductMetaData() {
        List<WebElement> metaDataList = elementUtil.waitForVisibilityOfElements(productMetaData, AppConstants.DEFAULT_MEDIUM_WAIT);
        for (WebElement element : metaDataList) {
            String metaData = element.getText();
            String metaKey = metaData.split(":")[0].trim();
            String metaValue = metaData.split(":")[1].trim();
            productDataMap.put(metaKey, metaValue);
        }
    }

    private void getProductPriceData() {
        List<WebElement> metaPriceList = elementUtil.waitForVisibilityOfElements(productPriceData, AppConstants.DEFAULT_MEDIUM_WAIT);
        String productPrice = metaPriceList.get(0).getText();
        String productGST = metaPriceList.get(1).getText().split(":")[1].trim();
        productDataMap.put("price", productPrice);
        productDataMap.put("gst", productGST);
    }

    public HashMap<String, String> getProductDetails() {
        productDataMap.put("productname", getProductHeaderName());
        getProductMetaData();
        getProductPriceData();
        System.out.println(productDataMap);
        return productDataMap;
    }
}
