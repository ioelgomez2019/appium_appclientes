package co.qaconf;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleTest {

    private AndroidDriver driver;


    @BeforeEach
    public void configuracionAppium() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("RSCR702DMRR")
                .setPlatformVersion("16.0")
                .setAppPackage("com.losandes.bancamovil.qa")
                .setAppActivity("com.losandes.bancamovil.MainActivity");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
    }

    @AfterEach
    public void cerrarSesion() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void sampleTestSerenity() {
        By[] initialOptionalLocators = new By[]{
                AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\")"),
                AppiumBy.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]"),
                AppiumBy.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_button\"]")
        };

        WebElement registerButton = waitForTargetWhileHandlingOptional(
                Duration.ofSeconds(120),
                initialOptionalLocators,
                AppiumBy.xpath("//android.view.View[@resource-id=\"welcome_register_button\"]/android.widget.Button"),
                AppiumBy.xpath("//android.widget.Button[@resource-id=\"welcome_register_button\"]"),
                AppiumBy.id("welcome_register_button"),
                AppiumBy.id("com.losandes.bancamovil.qa:id/welcome_register_button")
        );
        assertTrue(registerButton != null && registerButton.isDisplayed(), "No se mostro el boton Registrar");
        registerButton.click();

        By[] mediaPermissionLocators = new By[]{
                AppiumBy.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]"),
                AppiumBy.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_button\"]"),
                AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\")")
        };

        clickIfVisible(mediaPermissionLocators[0], Duration.ofSeconds(8));

        WebElement documentField = waitForTargetWhileHandlingOptional(
                Duration.ofSeconds(90),
                mediaPermissionLocators,
                AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,\"document\")]"),
                AppiumBy.xpath("(//android.widget.EditText)[1]"),
                AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\")")
        );

        if (documentField == null) {
            WebElement documentLabel = waitForTargetWhileHandlingOptional(
                    Duration.ofSeconds(20),
                    mediaPermissionLocators,
                    AppiumBy.xpath("//android.widget.TextView[@text=\"N\u00FAmero de documento\"]"),
                    AppiumBy.xpath("//android.widget.TextView[contains(@text,\"documento\")]"),
                    AppiumBy.androidUIAutomator("new UiSelector().textMatches(\"(?i).*documento.*\")")
            );
            assertTrue(documentLabel != null, "No se mostro el campo Numero de documento");
            documentLabel.click();

            documentField = waitForAnyVisible(
                    Duration.ofSeconds(20),
                    AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,\"document\")]"),
                    AppiumBy.xpath("(//android.widget.EditText)[1]"),
                    AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\")")
            );
        }

        assertTrue(documentField != null && documentField.isDisplayed(), "No se pudo ubicar el input de Numero de documento");
        documentField.click();
        documentField.clear();
        documentField.sendKeys("71052981");
        try {
            driver.executeScript("mobile: performEditorAction", Map.of("action", "done"));
        } catch (RuntimeException ignored) {
            // Si el editor action no aplica en esta vista, continuamos
        }
        try {
            driver.hideKeyboard();
        } catch (RuntimeException ignored) {
            // Si no hay teclado visible, continuamos
        }

        By[] continueOptionalLocators = new By[]{
                AppiumBy.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]"),
                AppiumBy.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_button\"]")
        };
        By[] continueLocators = new By[]{
                AppiumBy.xpath("//android.widget.ScrollView/android.view.View[2]/android.widget.Button"),
                AppiumBy.xpath("//android.view.View[@resource-id=\"login_button\"]"),
                AppiumBy.xpath("//android.view.View[@resource-id=\"login_button\"]/android.widget.Button"),
                AppiumBy.id("login_button"),
                AppiumBy.id("com.losandes.bancamovil.qa:id/login_button"),
                AppiumBy.xpath("//android.widget.Button[contains(@text,\"Continuar\")]"),
                AppiumBy.androidUIAutomator("new UiSelector().textMatches(\"(?i).*iniciar.*\")"),
                AppiumBy.androidUIAutomator("new UiSelector().textMatches(\"(?i).*(continuar|siguiente).*\")"),
                AppiumBy.androidUIAutomator("new UiSelector().descriptionMatches(\"(?i).*(continuar|siguiente).*\")")
        };

        WebElement continueButton = waitForContinueButton(
                Duration.ofSeconds(60),
                continueOptionalLocators,
                continueLocators
        );
        assertTrue(continueButton != null && continueButton.isDisplayed(), "No se mostro el boton Continuar/Iniciar sesion");
        if (continueButton.isEnabled()) {
            continueButton.click();
        }
    }

    private void clickIfVisible(By locator, Duration timeout) {
        WebElement element = waitForAnyVisible(timeout, locator);
        if (element != null) {
            try {
                element.click();
            } catch (RuntimeException ignored) {
                // Si desaparece justo al click, continuamos
            }
        }
    }

    private WebElement waitForAnyVisible(Duration timeout, By... locators) {
        long deadline = System.nanoTime() + timeout.toNanos();
        while (System.nanoTime() < deadline) {
            WebElement visibleElement = findFirstVisibleNow(locators);
            if (visibleElement != null) {
                return visibleElement;
            }

            try {
                Thread.sleep(300);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return null;
    }

    private WebElement waitForTargetWhileHandlingOptional(Duration timeout, By[] optionalLocators, By... targetLocators) {
        long deadline = System.nanoTime() + timeout.toNanos();
        while (System.nanoTime() < deadline) {
            for (By optionalLocator : optionalLocators) {
                clickIfVisibleNow(optionalLocator);
            }

            WebElement target = findFirstVisibleNow(targetLocators);
            if (target != null) {
                return target;
            }

            try {
                Thread.sleep(400);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return null;
    }

    private WebElement waitForContinueButton(Duration timeout, By[] optionalLocators, By[] continueLocators) {
        long deadline = System.nanoTime() + timeout.toNanos();
        int iteration = 0;
        while (System.nanoTime() < deadline) {
            for (By optionalLocator : optionalLocators) {
                clickIfVisibleNow(optionalLocator);
            }

            WebElement continueButton = findFirstVisibleNow(continueLocators);
            if (continueButton != null) {
                return continueButton;
            }

            if (iteration % 5 == 0) {
                scrollDown();
            }
            iteration++;

            try {
                Thread.sleep(400);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return null;
    }

    private void scrollDown() {
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
            // Si no se puede hacer scroll en este momento, continuamos
        }
    }

    private void clickIfVisibleNow(By locator) {
        WebElement element = findFirstVisibleNow(locator);
        if (element != null && element.isEnabled()) {
            try {
                element.click();
            } catch (RuntimeException ignored) {
                // Si desaparece justo al click, continuamos con el flujo
            }
        }
    }

    private WebElement findFirstVisibleNow(By... locators) {
        for (By locator : locators) {
            List<WebElement> elements = driver.findElements(locator);
            for (WebElement element : elements) {
                try {
                    if (element.isDisplayed()) {
                        return element;
                    }
                } catch (RuntimeException ignored) {
                    // Intentamos con el siguiente elemento/locator
                }
            }
        }
        return null;
    }
}
