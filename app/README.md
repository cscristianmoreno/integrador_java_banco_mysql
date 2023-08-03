## INTRODUCCIÓN

- Banco realizado con MYSQL y JDBC.
- Las tablas deben ser creadas desde el menú de opciones.

## CLASES
- CUENTAS BANCARIAS (AccountsBank.java)
- CLIENTES (Clients.java)
- TARJETA DE CRÉDITOS (CreditsCard.java)
- FONDOS DE INVERSIÓN (Founds.java)
- CARTERA DE VALORES (Invetsments.java)

## CLIENTES
- Un cliente puede registrarse en el banco.
- Un cliente puede crear una cuenta bancaria (SI NO TIENE) al registrarse.
- Un cliente puede ingresar a su cuenta bancaria a través de su DNI.

## CUENTAS BANCARIAS
- Generan un ID de cuenta con sus 3 dígitos de seguridad.
- Poseen un CBU.
- Se puede ingresar dinero.
- Se puede retirar dinero.
- Generan un registro en caso de que retire o ingrese dinero.

## TARJETAS DE CRÉDITO
- Genera un ID de tarjeta con 3 dígitos de seguridad.
- Genera una fecha de apertura.
- Genera una fecha de vencimiento.
- Es posible vincular y desvincularse de la cuenta bancaria.

## FONDOS DE INVERSIÓN
- Genera una renta al azar.
- Genera la fecha de apertura.
- Genera un porcentaje de probabilidad de éxito de la inversión.
- Genera un porcentaje de ganancias en caso de que sea exitosa la inversión.
- Genera los días a invertir.
- Almacena la fecha de vencimiento.

## CLIENTES
- Almacena el nombre.
- Almacena el apellido.
- Almacena el dni.
- Almacena el teléfono.
- Almacena la dirección.

## CARTERA DE VALORES
- Genera el número de títulos en relación al fondo de inversión (inversión / cotización).
- Generara un precio de cotización al azar en relación a la inversión aportada.
- Genera un porcentaje en relación a la cotización ((cotización * 100) / inversión). 