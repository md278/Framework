package com.qa.opencart.factory;

import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Properties;

public class OptionsManager {
    private Properties prop;
    private ChromeOptions chromeOptions;

    public OptionsManager(Properties prop) {
        this.prop = prop;
    }

    public ChromeOptions getChromeOptions() {
        chromeOptions = new ChromeOptions();
        if (Boolean.parseBoolean(prop.getProperty("headless").trim())) chromeOptions.addArguments("--headless");
        if (Boolean.parseBoolean(prop.getProperty("incognito").trim())) chromeOptions.addArguments("--incognito");
        return chromeOptions;
    }
}
