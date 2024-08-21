package com.qa.opencart.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
    private WebDriver driver;
    private JavascriptExecutor javascriptExecutor;

    public JavaScriptUtil(WebDriver driver) {
        this.driver = driver;
        javascriptExecutor = (JavascriptExecutor) this.driver;
    }

    public String getTitleUsingJS() {
        return javascriptExecutor.executeScript("return document.title").toString();
    }

    public String getURLUsingJS() {
        return javascriptExecutor.executeScript("return document.URL").toString();
    }

    public void generateJSAlert(String message) {
        javascriptExecutor.executeScript("alert('" + message + "') ");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.switchTo().alert().accept();
    }

    public void generateJSConfirm(String message) {
        javascriptExecutor.executeScript("confirm('" + message + "') ");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.switchTo().alert().accept();
    }

    public void generateJSPrompt(String message, String value) {
        javascriptExecutor.executeScript("prompt('" + message + "') ");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(value);
        alert.accept();
    }

    public void goBackUsingJS() {
        javascriptExecutor.executeScript("history.go(-1)");
    }

    public void goForwardUsingJS() {
        javascriptExecutor.executeScript("history.go(1)");
    }

    public void pageRefreshUsingJS() {
        javascriptExecutor.executeScript("history.go(0)");
    }

    public String getPageInnerHTMLUsingJS() {
        return javascriptExecutor.executeScript("return document.documentElement.innerHTML;").toString();
    }

    public void scrollToTopUsingJS() {
        javascriptExecutor.executeScript("window.scrollTo(document.body.scrollHeight ,0)");
    }

    public void scrollToBottomUsingJS() {
        javascriptExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollToHeightUsingJS(String height) {
        javascriptExecutor.executeScript("window.scrollTo(0, '" + height + "')");
    }

    public void scrollToCenterUsingJS() {
        javascriptExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight/2)");
    }

    public void scrollIntoView(WebElement element) {
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void drawBorder(WebElement element) {
        javascriptExecutor.executeScript("argument[0].style.border='3px solid red");
    }

    public void flash(WebElement element) {
        String backgroundColor = element.getCssValue("backgroundColor");
        for (int i = 0; i <= 10; i++) {
            changeColor("rgb(0, 200, 0)", element); //Green
            changeColor(backgroundColor, element); //Purple
        }
    }

    private void changeColor(String color, WebElement element) {
        javascriptExecutor.executeScript("arguments[0].style.backgroundColor= '" + color + "'", element); //G->P->G->P
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickElementUsingJS(WebElement element){
        javascriptExecutor.executeScript("arguments[0].click();", element);
    }
}
