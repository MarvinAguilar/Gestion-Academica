import { useEffect, useState } from "react";

export function useProfesores({ query } = { query: "" }) {
  const [profesores, setProfesores] = useState([]);

  const handleGetProfesores = async () => {
    const url = "http://localhost:8081/gestion-academica/profesores";

    await fetch(url)
      .then((data) => data.json())
      .then((res) => setProfesores(res))
      .catch((e) => []);
  };

  useEffect(() => {
    handleGetProfesores();
  }, [setProfesores]);

  const filterProfesores = () => {
    return query !== ""
      ? profesores.filter(
          (profesor) =>
            profesor.cedula?.toLowerCase().includes(query.toLowerCase()) ||
            profesor.nombre?.toLowerCase().includes(query.toLowerCase())
        )
      : profesores;
  };

  const insertarProfesor = async (profesor) => {
    const url = "http://localhost:8081/gestion-academica/profesores";

    await fetch(url, {
      method: "POST",
      body: JSON.stringify(profesor),
    }).then(() => setProfesores([...profesores, profesor]));
  };

  const actualizarProfesor = async (profesor) => {
    const url = "http://localhost:8081/gestion-academica/profesores";

    await fetch(url, {
      method: "PUT",
      body: JSON.stringify(profesor),
    }).then(() => handleGetProfesores());
  };

  const eliminarProfesor = async (cedula) => {
    const url = "http://localhost:8081/gestion-academica/profesores";

    await fetch(url, {
      method: "DELETE",
      body: JSON.stringify({ cedula }),
    }).then(() =>
      setProfesores(profesores.filter((profesor) => profesor.cedula !== cedula))
    );
  };

  return {
    filterProfesores,
    insertarProfesor,
    actualizarProfesor,
    eliminarProfesor,
  };
}
