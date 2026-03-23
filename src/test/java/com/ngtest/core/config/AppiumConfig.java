package com.ngtest.core.config;

import io.appium.java_client.android.options.UiAutomator2Options;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public final class AppiumConfig {

    private AppiumConfig() {
    }

    public static UiAutomator2Options androidOptions() {
        EnvironmentConfig.AppTarget appTarget = EnvironmentConfig.appTarget();
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(System.getProperty("platformName", "Android"))
                .setAutomationName(System.getProperty("automationName", "UiAutomator2"))
                .setDeviceName(System.getProperty("deviceName", "AndroidDevice"))
                .setNewCommandTimeout(Duration.ofSeconds(Long.getLong("newCommandTimeout", 180L)));

        String platformVersion = System.getProperty("platformVersion", "");
        if (!platformVersion.isBlank()) {
            options.setPlatformVersion(platformVersion);
        }

        String udid = System.getProperty("udid", "");
        if (!udid.isBlank()) {
            options.setUdid(udid);
        }

        String appPath = System.getProperty("app", "");
        if (!appPath.isBlank() && Files.exists(Path.of(appPath))) {
            options.setApp(appPath);
        } else {
            options.setAppPackage(System.getProperty("appPackage", appTarget.appPackage()));
            options.setAppActivity(System.getProperty("appActivity", appTarget.appActivity()));
        }

        return options;
    }

    public static String serverUrl() {
        return System.getProperty("appiumServerUrl", "http://127.0.0.1:4723");
    }
}
