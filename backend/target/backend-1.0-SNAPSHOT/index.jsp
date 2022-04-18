<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>

    <script>
        addEventListener("load", () => {
            let urlWS = "ws://localhost:8081/gestion-academica/carreras";
            let miWebsocket = new WebSocket(urlWS);

            let carrera1 = {
                request: "PUT",
                codigo: "ADM",
                nombre: "Administración vieja confiable 666",
                titulo: "Licenciatura"
            }

            let carrera2 = {
                request: "POST",
                codigo: "VET",
                nombre: "Veterinaria",
                titulo: "Licenciatura"
            }

            let obtenerCarreras = {
                request: "GET_ALL"
            }

            let buscarCarrera = {
                request: "DELETE",
                codigo: "ADM"
            }

            miWebsocket.onopen = (e) => {
                miWebsocket.send(JSON.stringify(buscarCarrera));
            }

            let contenedor = document.getElementById("contenedor");

            miWebsocket.onmessage = ({data}) => {
                console.log(data);
                contenedor.innerHTML = data;
            }
        })
    </script>
</head>
<body>
<div id="contenedor"></div>
</body>
</html>