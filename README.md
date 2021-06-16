# 3DMarket

3DMarket es una app creada en android nativo que trata de una tienda online de commpra - venta de archivos 3d para modelado 3d o impresion 3d.
En la parte de frontedn tenemos la app en android y en la parte del backend tenemos una pequeña API para conectar nuestra app con Stripe (La pasarela de pago).
Todo el proyecto está en modo test de Stripe ya que para acceder a la version real tenemos que poner nuestros datos bancarios.



### FRONTEND
* Simplemente es arrancar el proyecto con android studio y en el archivo ``src/main/res/xml/network_security_config.xml``
cambiamos la configuración de las ip a las nuestras

![alt text](https://github.com/adrianavilatorres/3DMarket/blob/master/res_readme/Captura2.PNG)



### BACKEND

* Primero debes crearte una cuenta en Stripe y coger tu clave privada y cambiarla en el archivo index.js

![alt text](https://github.com/adrianavilatorres/3DMarket/blob/master/res_readme/Captura.PNG)


* Para iniciar la API en node:
``node index.js``

# DEMO

*Aquí dejo el enlace de descarga del video de la demo explicando la app [DEMO HERE](https://github.com/adrianavilatorres/3DMarket/blob/master/res_readme/demo.mp4)

## Objetvos

- [x] hacer método de pago con Stripe
- [x] Filtro para el buscador
- [x] Subida de archivos de forma independiente para cada usuario
- [x] Modo oscuro
- [x] Modo invitado
- [ ] CRUD de productos para e lcliete
- [ ] Web para facilitar la subida de ficheros

