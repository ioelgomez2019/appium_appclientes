package com.ngtest.core.config;

import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;

public final class DriverManager {

    private static final ThreadLocal<AndroidDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static AndroidDriver getDriver() {
        AndroidDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("AndroidDriver no inicializado. Ejecuta el escenario mediante CucumberHooks.");
        }
        return driver;
    }

    public static void startDriver() {
        if (DRIVER.get() != null) {
            return;
        }

        try {
            AndroidDriver driver = new AndroidDriver(new URL(AppiumConfig.serverUrl()), AppiumConfig.androidOptions());
            DRIVER.set(driver);
        } catch (MalformedURLException exception) {
            throw new IllegalStateException("La URL del servidor Appium es invalida", exception);
        }
    }

    public static void quitDriver() {
        AndroidDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
