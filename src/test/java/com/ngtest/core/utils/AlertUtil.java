package com.ngtest.core.utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;

public class AlertUtil {

    private final AndroidDriver driver;

    public AlertUtil(AndroidDriver driver) {
        this.driver = driver;
    }

    public boolean acceptIfPresent() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
            return true;
        } catch (NoAlertPresentException ignored) {
            return false;
        }
    }
}
