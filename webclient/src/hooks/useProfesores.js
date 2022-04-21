import { useEffect, useState } from "react";

export function useProfesor({ query } = { query: "" }) {
  const [profesores, setProfesor] = useState([]);

  const handleGetProfesor = async () => {
    const url = "http://localhost:8081/gestion-academica/profesores";

    await fetch(url)
      .then((data) => data.json())
      .then((res) => setProfesor(res))
      .catch((e) => []);
  };

  useEffect(() => {
    handleGetProfesor();
  }, [profesores]);

  const filterProfesores = () => {
    return query !== ""
      ? profesores.filter(
          (profesor) =>
            profesor.cedula.toLowerCase().includes(query.toLowerCase()) ||
            profesor.nombre.toLowerCase().includes(query.toLowerCase())
        )
      : profesores;
  };

  const insertarProfesor = async (profesor) => {
    const url = "http://localhost:8081/gestion-academica/profesores";

    await fetch(url, {
      method: "POST",
      body: JSON.stringify(profesor),
    });
  };

  const actualizarProfesor = async (profesor) => {
    const url = "http://localhost:8081/gestion-academica/profesores";

    await fetch(url, {
      method: "PUT",
      body: JSON.stringify(profesor),
    });
  };

  const eliminarProfesor = async (cedula) => {
    const url = "http://localhost:8081/gestion-academica/profesores";

    await fetch(url, {
      method: "DELETE",
      body: JSON.stringify({ cedula }),
    });
  };

  return {
    filterProfesores,
    insertarProfesor,
    actualizarProfesor,
    eliminarProfesor,
  };
}
