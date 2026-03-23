package com.ngtest.core.base;

import com.ngtest.core.config.DriverManager;
import com.ngtest.core.utils.SwipeUtil;
import com.ngtest.core.utils.WaitUtil;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public abstract class BaseScreen {

    protected final AndroidDriver driver;
    protected final WaitUtil waitUtil;
    protected final SwipeUtil swipeUtil;

    protected BaseScreen() {
        this.driver = DriverManager.getDriver();
        this.waitUtil = new WaitUtil(driver);
        this.swipeUtil = new SwipeUtil(driver);
    }

    protected WebElement waitForAnyVisible(Duration timeout, By... locators) {
        return waitUtil.waitForAnyVisible(timeout, locators);
    }

    protected WebElement waitForTargetWhileHandlingOptional(Duration timeout, By[] optionalLocators, By... targetLocators) {
        return waitUtil.waitForTargetWhileHandlingOptional(timeout, optionalLocators, targetLocators);
    }

    protected void clickIfVisible(By locator, Duration timeout) {
        waitUtil.clickIfVisible(locator, timeout);
    }
}
