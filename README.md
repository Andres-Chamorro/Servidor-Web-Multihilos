# Servidor Web Multihilos

En esta práctica se completo el código, en lenguaje Java, para construir un servidor web. Al final se tendrá un servidor web multi-hilos (multi-threaded) con la capacidad de procesar solicitudes simultáneas en paralelo. Para este servidor se implementará parcialmente la versión 1.0 de HTTP, como está definida en el RFC 1945, donde las solicitudes (request) HTTP son enviadas separadas para cada componente de la página web. El servidor debe ser capaz de manipular solicitudes de servicio simultáneas en paralelo. Esto significa que el servidor es multi-hilos (multi-threaded). En el hilo principal, el servidor escuchará por un puerto fijo. Cuando reciba una solicitud de conexión TCP, establecerá una conexión TCP a través de otro socket (socket de conexión) y atenderá la solicitud en un hilo separado. 

# Prueba en el navegador

Coloca en tu navegador la siguiente direccion para que funcione despues de compilar y ejecutar el programa:

http://localhost:6789/index.html

# Compilacion

`javac ServidorWeb.java`

# Ejecución

`java ServidorWeb.java`
