package com.qa.opencart.factory;

import com.qa.opencart.exception.FrameworkException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DriverFactory {
    WebDriver driver;
    private Properties prop;
    private OptionsManager optionsManager;
    public static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static String getScreenshot(String methodName) {

        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

        String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()
                + ".png";

        File destination = new File(path);

        try {
            FileHandler.copy(srcFile, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

    public WebDriver initDriver(Properties prop) {
        String browserName = prop.getProperty("browser");
//        String browserName = System.getProperty("browser");
        optionsManager = new OptionsManager(prop);
        switch (browserName.toLowerCase().trim()) {
            case "chrome":
                driverThreadLocal.set(new ChromeDriver(optionsManager.getChromeOptions()));
                break;
            case "firefox":
                driverThreadLocal.set(new FirefoxDriver());
                break;
            case "edge":
                driverThreadLocal.set(new EdgeDriver());
                break;
            default:
                System.out.println("Please pass the correct browser name " + browserName);
                throw new FrameworkException("Supplied browser was not found!");
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().get(prop.getProperty("url"));
        return getDriver();
    }

    public static WebDriver getDriver() {
       return driverThreadLocal.get();
    }

    public Properties initProp() {
        FileInputStream fileInputStream = null;
        prop = new Properties();
        String envName = System.getProperty("env");
        System.out.println("Env: " + envName);

        try {
            if (envName == null) {
                fileInputStream = new FileInputStream("./src/test/resources/config/config.properties");
            } else {
                switch (envName.toLowerCase().trim()) {
                    case "qa":
                        fileInputStream = new FileInputStream("./src/test/resources/config/config.qa.properties");
                        break;
                    case "stage":
                        fileInputStream = new FileInputStream("./src/test/resources/config/config.stage.properties");
                        break;
                    case "uat":
                        fileInputStream = new FileInputStream("./src/test/resources/config/config.uat.properties");
                        break;
                    default:
                        System.out.println("Please pass a valid environment name!");
                        throw new FrameworkException("Invalid environment name: " + envName);
                }
            }
        } catch (FileNotFoundException e) {
        }
        try {
            prop.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return prop;
    }

}
