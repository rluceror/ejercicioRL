## Requerimientos.

Desarrolle una aplicación que exponga una API RESTful de creación de usuarios.
Todos los endpoints deben aceptar y retornar solamente JSON, inclusive al para los mensajes de error.
Todos los mensajes deben seguir el formato:
{"mensaje": "mensaje de error"}

## Registro

- Ese endpoint deberá recibir un usuario con los campos "nombre", "correo", "contraseña", más
un listado de objetos "teléfono", respetando el siguiente formato:

        {
        "name": "Juan Juanito",
        "email": "juan@juanito.cl",
        "password": "a2asfGfdfdf4",
        "phones": [
        {
        "number": "1234567",
        "citycode": "2",
        "contrycode": "56"
        }
        ]
        }


### Dependencias

| Dependency | Version |
| ------ | ------ |
| IntelliJ IDEA | 1.8.0_152 |
| Gradle | 4.10.3|
| H2 | 2.4.1 |
| Java JDK  | 1.8 |
| Postman  | 7.18.0 |
| Windows | 10 |


### Instalacion

- Instalar las dependencias.
- Importar proyecto a su IDE.
- Ejecute la prueba y arranque gradle en el proyecto.
- Ahora pruebe todos los puntos finales.
- Base de datos en Memoria, H2

http://localhost:8080/v1/ejercicio/sign-up

{
    "name": "NameExample LastNameExample",
    "email": "email@example.com",
    "password": "Pass12",
    "phones": [
        {
            "number": "946644558",
            "city_code": "1",
            "country_code": "57"
        }
    ]
}

http://localhost:8080/v1/ejercicio/login

{
"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBleGFtcGxlLmNvbSIsInJvbGVzIjpbIlJPTEVfQURNSU4iLCJST0xFX1VTRVIiXSwiaWF0IjoxNzAwOTUxMzIwLCJleHAiOjE3MDA5NTE2MjB9.9e8okqBF445ThZVPQWuIHky8q3w9wzbGmPb8w9Dhs9o"
}



