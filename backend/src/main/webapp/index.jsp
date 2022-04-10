<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>

    <script>
        addEventListener("load", () => {
            let urlWS = "ws://localhost:8081/gestion-academica/mensaje";
            let miWebsocket = new WebSocket(urlWS);
            console.log(miWebsocket);

            miWebsocket.onopen = (e) => {
                console.log("abierto");
                miWebsocket.send("Hola soy el cliente");
            }

            miWebsocket.onmessage = (e) => {
                console.log(e.data);
            }
        })
    </script>
</head>
<body>
<h1><%= "Hello World!" %></h1>
</body>
</html>