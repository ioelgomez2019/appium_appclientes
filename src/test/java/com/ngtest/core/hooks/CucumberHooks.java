package com.ngtest.core.hooks;

import com.ngtest.core.config.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class CucumberHooks {

    private final ScenarioContext scenarioContext;

    public CucumberHooks(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Before(order = 0)
    public void setUp() {
        scenarioContext.clear();
        DriverManager.startDriver();
    }

    @After(order = 100)
    public void tearDown(Scenario scenario) {
        AndroidDriver driver = null;
        try {
            driver = DriverManager.getDriver();
        } catch (IllegalStateException ignored) {
            // El driver podria no haberse inicializado si el hook fallo antes.
        }

        if (scenario.isFailed() && driver instanceof TakesScreenshot screenshotCapable) {
            byte[] screenshot = screenshotCapable.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "failure-screenshot");
        }

        DriverManager.quitDriver();
        scenarioContext.clear();
    }
}
