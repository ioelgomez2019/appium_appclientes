package com.ngtest.core.utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

public class WaitUtil {

    private final AndroidDriver driver;

    public WaitUtil(AndroidDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForAnyVisible(Duration timeout, By... locators) {
        long deadline = System.nanoTime() + timeout.toNanos();
        while (System.nanoTime() < deadline) {
            WebElement visibleElement = findFirstVisibleNow(locators);
            if (visibleElement != null) {
                return visibleElement;
            }
            sleep(300);
        }
        return null;
    }

    public WebElement waitForTargetWhileHandlingOptional(Duration timeout, By[] optionalLocators, By... targetLocators) {
        long deadline = System.nanoTime() + timeout.toNanos();
        while (System.nanoTime() < deadline) {
            for (By optionalLocator : optionalLocators) {
                clickIfVisibleNow(optionalLocator);
            }

            WebElement target = findFirstVisibleNow(targetLocators);
            if (target != null) {
                return target;
            }
            sleep(400);
        }
        return null;
    }

    public WebElement waitForVisible(Duration timeout, By locator) {
        return waitForAnyVisible(timeout, locator);
    }

    public void clickIfVisible(By locator, Duration timeout) {
        WebElement element = waitForVisible(timeout, locator);
        if (element != null) {
            safeClick(element);
        }
    }

    public WebElement findFirstVisibleNow(By... locators) {
        for (By locator : locators) {
            List<WebElement> elements = driver.findElements(locator);
            for (WebElement element : elements) {
                try {
                    if (element.isDisplayed()) {
                        return element;
                    }
                } catch (RuntimeException ignored) {
                    // El elemento pudo invalidarse; seguimos probando.
                }
            }
        }
        return null;
    }

    public void clickIfVisibleNow(By locator) {
        WebElement element = findFirstVisibleNow(locator);
        if (element != null && element.isEnabled()) {
            safeClick(element);
        }
    }

    public void safeClick(WebElement element) {
        try {
            element.click();
        } catch (RuntimeException ignored) {
            // Si el elemento desaparece durante el click, el flujo volvera a buscarlo.
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
