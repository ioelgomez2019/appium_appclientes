package com.ngtest.screens.login;

import com.ngtest.core.base.BaseScreen;
import com.ngtest.core.constants.Timeout;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Map;

public class LoginScreen extends BaseScreen {

    private final By[] initialOptionalLocators = new By[]{
            AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\")"),
            AppiumBy.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]"),
            AppiumBy.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_button\"]")
    };

    private final By[] mediaPermissionLocators = new By[]{
            AppiumBy.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]"),
            AppiumBy.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_button\"]"),
            AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\")")
    };

    private final By[] continueButtonLocators = new By[]{
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

    public void tapRegister() {
        WebElement registerButton = waitForTargetWhileHandlingOptional(
                Timeout.EXTRA_LONG,
                initialOptionalLocators,
                AppiumBy.xpath("//android.view.View[@resource-id=\"welcome_register_button\"]/android.widget.Button"),
                AppiumBy.xpath("//android.widget.Button[@resource-id=\"welcome_register_button\"]"),
                AppiumBy.id("welcome_register_button"),
                AppiumBy.id("com.losandes.bancamovil.qa:id/welcome_register_button")
        );

        if (registerButton == null || !registerButton.isDisplayed()) {
            throw new IllegalStateException("No se mostro el boton Registrar");
        }
        registerButton.click();
    }

    public void acceptMediaPermissionsIfVisible() {
        clickIfVisible(mediaPermissionLocators[0], Timeout.SHORT);
        clickIfVisible(mediaPermissionLocators[1], Timeout.SHORT);
    }

    public void typeDocument(String documentNumber) {
        WebElement documentField = waitForTargetWhileHandlingOptional(
                Timeout.LONG,
                mediaPermissionLocators,
                AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,\"document\")]"),
                AppiumBy.xpath("(//android.widget.EditText)[1]"),
                AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\")")
        );

        if (documentField == null) {
            WebElement documentLabel = waitForTargetWhileHandlingOptional(
                    Timeout.MEDIUM,
                    mediaPermissionLocators,
                    AppiumBy.xpath("//android.widget.TextView[@text=\"Numero de documento\"]"),
                    AppiumBy.xpath("//android.widget.TextView[contains(@text,\"documento\")]"),
                    AppiumBy.androidUIAutomator("new UiSelector().textMatches(\"(?i).*documento.*\")")
            );

            if (documentLabel == null) {
                throw new IllegalStateException("No se mostro el campo Numero de documento");
            }
            documentLabel.click();

            documentField = waitForAnyVisible(
                    Timeout.MEDIUM,
                    AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,\"document\")]"),
                    AppiumBy.xpath("(//android.widget.EditText)[1]"),
                    AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\")")
            );
        }

        if (documentField == null || !documentField.isDisplayed()) {
            throw new IllegalStateException("No se pudo ubicar el input de Numero de documento");
        }

        documentField.click();
        documentField.clear();
        documentField.sendKeys(documentNumber);
        hideKeyboard();
    }

    public void tapContinue() {
        WebElement continueButton = findContinueButton();
        if (continueButton == null || !continueButton.isDisplayed()) {
            throw new IllegalStateException("No se mostro el boton Continuar/Iniciar sesion");
        }
        if (continueButton.isEnabled()) {
            continueButton.click();
        }
    }

    private WebElement findContinueButton() {
        long deadline = System.nanoTime() + Timeout.LONG.toNanos();
        int iteration = 0;

        while (System.nanoTime() < deadline) {
            for (By optionalLocator : mediaPermissionLocators) {
                waitUtil.clickIfVisibleNow(optionalLocator);
            }

            WebElement continueButton = waitUtil.findFirstVisibleNow(continueButtonLocators);
            if (continueButton != null) {
                return continueButton;
            }

            if (iteration % 5 == 0) {
                swipeUtil.swipeDown();
            }
            iteration++;
            sleep(400);
        }

        return null;
    }

    private void hideKeyboard() {
        try {
            driver.executeScript("mobile: performEditorAction", Map.of("action", "done"));
        } catch (RuntimeException ignored) {
            // Algunas vistas no soportan editor actions.
        }

        try {
            driver.hideKeyboard();
        } catch (RuntimeException ignored) {
            // Si el teclado no esta visible, continuamos.
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
