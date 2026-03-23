package com.ngtest.screens.login;

import com.ngtest.core.hooks.ScenarioContext;

public class LoginActions {

    private static final String DOCUMENT_KEY = "login.documentNumber";

    private final ScenarioContext scenarioContext;
    private final LoginScreen loginScreen;

    public LoginActions(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
        this.loginScreen = new LoginScreen();
    }

    public void ingresarDocumento(String documentNumber) {
        scenarioContext.set(DOCUMENT_KEY, documentNumber);
        loginScreen.tapRegister();
        loginScreen.acceptMediaPermissionsIfVisible();
        loginScreen.typeDocument(documentNumber);
    }

    public void continuar() {
        loginScreen.tapContinue();
    }

    public String documentoIngresado() {
        return scenarioContext.get(DOCUMENT_KEY);
    }
}
