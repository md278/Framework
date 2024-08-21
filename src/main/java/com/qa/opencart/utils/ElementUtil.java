package com.qa.opencart.utils;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.exception.FrameworkException;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ElementUtil {
    private WebDriver driver;

    public ElementUtil(WebDriver driver) {
        this.driver = driver;
    }

    public By getBy(String locatorType, String locatorValue) {
        By by = null;

        switch (locatorType.toUpperCase().trim()) {
            case "ID":
                by = By.id(locatorValue);
                break;

            case "NAME":
                by = By.name(locatorValue);
                break;

            case "CLASS_NAME":
                by = By.className(locatorValue);
                break;

            case "TAG_NAME":
                by = By.tagName(locatorValue);
                break;

            case "XPATH":
                by = By.xpath(locatorValue);
                break;

            case "LINK_TEXT":
                by = By.linkText(locatorValue);
                break;

            case "PARTIAL_LINK_TEXT":
                by = By.partialLinkText(locatorValue);
                break;

            case "CSS_SELECTOR":
                by = By.cssSelector(locatorValue);
                break;

            default:
                System.out.println("Incorrect locator type is passed: " + locatorType);
                throw new FrameworkException("Please pass the correct locator type.");
        }
        return by;
    }

    public WebElement getElement(By byLocator) {
        return driver.findElement(byLocator);
    }

    public WebElement getElement(String locatorType, String locatorValue) {
        return driver.findElement(getBy(locatorType, locatorValue));
    }

    public List<WebElement> getElements(By byLocator) {
        return driver.findElements(byLocator);
    }

    public List<WebElement> getElements(String locatorType, String locatorValue) {
        return driver.findElements(getBy(locatorType, locatorValue));
    }

    public void performSendKeys(By byLocator, String value) {
        waitForVisibilityOfElement(byLocator, AppConstants.DEFAULT_SHORT_WAIT);
        getElement(byLocator).clear();
        getElement(byLocator).sendKeys(value);
    }

    public void performSendKeys(String locatorType, String locatorValue, String value) {
        getElement(locatorType, locatorValue).sendKeys(value);
    }

    public void performClick(By byLocator) {
        waitForVisibilityOfElement(byLocator, AppConstants.DEFAULT_SHORT_WAIT);
        getElement(byLocator).click();
    }

    public void performClick(String locatorType, String locatorValue) {
        getElement(locatorType, locatorValue).click();
    }

    public String getElementText(By byLocator) {
        return getElement(byLocator).getText();
    }

    public String getElementText(String locatorType, String locatorValue) {
        return getElement(locatorType, locatorValue).getText();
    }

    public String getAttribute(By byLocator, String attributeName) {
        return getElement(byLocator).getAttribute(attributeName);
    }

    public int getElementCount(By byLocator) {
        return getElements(byLocator).size();
    }

    public List<String> getElementsTextList(By byLocator) {
        List<WebElement> elementList = getElements(byLocator);
        List<String> elementTextList = new ArrayList<>();

        for (WebElement element : elementList) {
            String elementText = element.getText().trim();
            if (!element.getText().isEmpty()) {
                elementTextList.add(elementText);
            }
        }
        return elementTextList;
    }

    public List<String> getElementsAttributeList(By byLocator, String attributeName) {
        List<WebElement> elementList = getElements(byLocator);
        List<String> attributeList = new ArrayList<>();

        for (WebElement element : elementList) {
            attributeList.add(element.getAttribute(attributeName));
        }
        return attributeList;
    }

    public boolean checkIfElementPresent(By byLocator) {
        return !getElements(byLocator).isEmpty();
    }

    public boolean checkIfSingleElementPresent(By byLocator) {
        return getElements(byLocator).size() == 1;
    }

    /********************** Select DropDown Methods ***************************/
    public void selectDropdownByIndex(By byLocator, int index) {
        Select select = new Select(getElement(byLocator));
        select.selectByIndex(index);
    }

    private Select createSelect(By byLocator) {
        Select select = new Select(getElement(byLocator));
        return select;
    }

    public void selectDropdownByValue(By byLocator, String value) {
        createSelect(byLocator).selectByValue(value);
    }

    public void selectDropdownByVisibleText(By byLocator, String visibleText) {
        createSelect(byLocator).selectByVisibleText(visibleText);
    }

    public void selectDropDownOption(By byLocator, String dropDownValue) {
        List<WebElement> dropDownList = createSelect(byLocator).getOptions();

        for (WebElement element : dropDownList) {
            if (element.getText().equals(dropDownValue)) {
                element.click();
                break;
            }
        }
    }

    public int getDropDownOptionsCount(By byLocator) {
        return createSelect(byLocator).getOptions().size();
    }

    public List<String> getDropDownOptions(By byLocator) {
        List<WebElement> dropDownList = createSelect(byLocator).getOptions();
        List<String> optionsTextList = new ArrayList<>();

        for (WebElement element : dropDownList) {
            optionsTextList.add(element.getText());
        }
        return optionsTextList;
    }

    public void selectDropDownValue(By byLocator, String value) {
        List<WebElement> optionsList = getElements(byLocator);
        for (WebElement element : optionsList) {
            if (element.getText().equals(value)) {
                element.click();
                break;
            }
        }
    }

    private boolean isDropDownMultiple(By byLocator) {
        return createSelect(byLocator).isMultiple();
    }

    public void selectMultipleDropdownValues(By byLocator, By optionLocator, String... dropDownValues) {
        Select select = createSelect(byLocator);

        if (isDropDownMultiple(byLocator)) {
            if (dropDownValues[0].equalsIgnoreCase("selectAll")) {
                List<WebElement> optionsList = getElements(optionLocator);
                for (WebElement element : optionsList) {
                    element.click();
                }
            } else {
                for (String value : dropDownValues) {
                    select.selectByVisibleText(value);
                }
            }
        }
    }


    /**************************** Actions Utils *****************************/


    public void twoLevelMenuHandle(By byParentMenuLocator, By byChildMenuLocator) {
        Actions actions = new Actions(driver);
        actions.moveToElement(getElement(byParentMenuLocator)).build().perform();
        performClick(byChildMenuLocator);
    }

    public void multiMenuHandle(By parentMenuLocator, By firstChildMenuLocator, By secondMenuChildMenuLocator, By thirdMenuLocator) throws InterruptedException {
        Actions actions = new Actions(driver);
        performClick(parentMenuLocator);
        Thread.sleep(1000);
        actions.moveToElement(getElement(firstChildMenuLocator)).build().perform();
        Thread.sleep(1000);
        actions.moveToElement(getElement(secondMenuChildMenuLocator)).build().perform();
        Thread.sleep(1000);
        performClick(thirdMenuLocator);
    }

    public void scrollToElementAndClick() {
        Actions actions = new Actions(driver);
        actions.scrollToElement(driver.findElement(By.linkText("Help")))
                .click(driver.findElement(By.linkText("Help"))).perform();
    }

    /**
     * waitForPresenceOfElement
     * An expectation for checking that an element is present in the DOM of the web page.
     * This does not necessarily mean that the element is visible.
     *
     * @param byLocator
     * @param timeOut
     * @return
     */

    public WebElement waitForPresenceOfElement(By byLocator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.presenceOfElementLocated(byLocator));
    }

    public WebElement waitForPresenceOfElement(By byLocator, int timeOut, int intervalTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));
        return wait.until(ExpectedConditions.presenceOfElementLocated(byLocator));
    }

    /**
     * waitForVisibilityOfElement
     * An expectation for checking that an element is present in the DOM of the web page and visible.
     * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
     *
     * @param byLocator
     * @param timeOut
     * @return
     */

    public WebElement waitForVisibilityOfElement(By byLocator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
    }


    public List<WebElement> waitForPresenceOfElements(By byLocator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byLocator));
    }

    public List<WebElement> waitForVisibilityOfElements(By byLocator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byLocator));
    }

    public void doClickWithWait(By byLocator, int timeOut) {
        waitForVisibilityOfElement(byLocator, timeOut);
    }

    public void doSendKeysWithWait(By byLocator, int timeOut) {
        waitForVisibilityOfElement(byLocator, timeOut);
    }

    public Alert waitForJSAlert(int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public void acceptJSAlert(int timeOut) {
        waitForJSAlert(timeOut).accept();
    }

    public void dismissJSAlert(int timeOut) {
        waitForJSAlert(timeOut).dismiss();
    }

    public String getJSAlertText(int timeOut) {
        return waitForJSAlert(timeOut).getText();
    }

    public void getJSAlertText(int timeOut, String value) {
        waitForJSAlert(timeOut).sendKeys(value);
    }

    public String waitForTitleContains(String partialTitleText, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            if (wait.until(ExpectedConditions.titleContains(partialTitleText))) {
                return driver.getTitle();
            }
        } catch (TimeoutException e) {
            System.out.println("Title does not contain :" + partialTitleText);
            e.printStackTrace();
        }
        return null;
    }

    @Step("Waiting for the page title: {0} and timeout: {1]") //coming from Allure
    public String waitForTitleIs(String titleText, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            if (wait.until(ExpectedConditions.titleIs(titleText))) {
                return driver.getTitle();
            }
        } catch (TimeoutException e) {
            System.out.println("Title does not match :" + titleText);
            e.printStackTrace();
        }
        return null;
    }

    public String waitForURLContains(String partialURLText, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            if (wait.until(ExpectedConditions.urlContains(partialURLText))) {
                return driver.getCurrentUrl();
            }
        } catch (TimeoutException e) {
            System.out.println("URL does not contain :" + partialURLText);
            e.printStackTrace();
        }
        return null;
    }

    public String waitForURLIs(String urlText, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            if (wait.until(ExpectedConditions.urlToBe(urlText))) {
                return driver.getCurrentUrl();
            }
        } catch (TimeoutException e) {
            System.out.println("URL does not match :" + urlText);
            e.printStackTrace();
        }
        return null;
    }

    public void clickElementWhenReady(By byLocator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(byLocator)).click();
        } catch (TimeoutException e) {
            System.out.println("Element is not Clickable or Enabled.");
        }
    }

    public WebElement waitForElementWithFluentWait(By byLocator, int timeOut, int interval){
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2))
                .withMessage("Element not Found")
                .ignoring(NoSuchElementException.class);

        return fluentWait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
    }


}