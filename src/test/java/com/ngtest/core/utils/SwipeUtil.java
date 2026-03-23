package com.ngtest.core.utils;

import io.appium.java_client.android.AndroidDriver;

import java.util.Map;

public class SwipeUtil {

    private final AndroidDriver driver;

    public SwipeUtil(AndroidDriver driver) {
        this.driver = driver;
    }

    public void swipeDown() {
        try {
            driver.executeScript("mobile: scrollGesture", Map.of(
                    "left", 100,
                    "top", 350,
                    "width", 880,
                    "height", 1500,
                    "direction", "down",
                    "percent", 0.8
            ));
        } catch (RuntimeException ignored) {
            // No todas las vistas aceptan scroll en cualquier momento.
        }
    }
}
