import { useEffect, useState } from "react";

export function useSeguridadPerfil() {
  const [usuarios, setUsuarios] = useState([]);

  const handleGetUsuarios = async () => {
    const url = "http://localhost:8081/gestion-academica/usuarios";

    await fetch(url)
      .then((data) => data.json())
      .then((res) => setUsuarios(res))
      .catch((e) => []);
  };

  useEffect(() => {
    handleGetUsuarios();
  }, [setUsuarios]);

  const insertarUsuario = async (usuario) => {
    const url = "http://localhost:8081/gestion-academica/usuarios";

    await fetch(url, {
      method: "POST",
      body: JSON.stringify(usuario),
    }).then(() => handleGetUsuarios());
  };

  const actualizarUsuario = async (usuario) => {
    const url = "http://localhost:8081/gestion-academica/usuarios";

    await fetch(url, {
      method: "PUT",
      body: JSON.stringify(usuario),
    }).then(() => handleGetUsuarios());
  };

  const eliminarUsuario = async (cedula) => {
    const url = "http://localhost:8081/gestion-academica/usuarios";

    await fetch(url, {
      method: "DELETE",
      body: JSON.stringify({ cedula }),
    }).then(() => handleGetUsuarios());
  };

  return { usuarios, insertarUsuario, actualizarUsuario, eliminarUsuario };
}
