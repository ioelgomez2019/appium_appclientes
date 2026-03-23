# language: es
@login
Característica: Login movil

  Escenario: Ingreso basico con documento
    Cuando ingresa el documento "71052981"
    Y continua con el login
    Entonces el documento usado en login es "71052981"
