package com.ngtest.screens.login;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginSteps {

    private final LoginActions loginActions;

    public LoginSteps(LoginActions loginActions) {
        this.loginActions = loginActions;
    }

    @Cuando("ingresa el documento {string}")
    public void ingresaElDocumento(String documentNumber) {
        loginActions.ingresarDocumento(documentNumber);
    }

    @Y("continua con el login")
    public void continuaConElLogin() {
        loginActions.continuar();
    }

    @Entonces("el documento usado en login es {string}")
    public void elDocumentoUsadoEnLoginEs(String expectedDocument) {
        assertEquals(expectedDocument, loginActions.documentoIngresado());
    }
}
