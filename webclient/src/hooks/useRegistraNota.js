import { useEffect, useCallback, useState } from "react";
import useGlobalContext from "./useGlobalContext";

export function useRegistraNota({ query } = { query: "" }) {
  const { user } = useGlobalContext();
  const [grupos, setGrupos] = useState([]);
  const [estudiantes, setEstudiantes] = useState([]);

  const handleGetGruposProfesor = useCallback(() => {
    const url = "http://localhost:8081/gestion-academica/grupos-profesor";

    const { cedula } = user;

    fetch(url, {
      method: "POST",
      body: JSON.stringify({ cedulaProfesor: cedula }),
    })
      .then((data) => data.json())
      .then((res) => setGrupos(res))
      .catch((e) => []);
  }, [user]);

  useEffect(() => {
    handleGetGruposProfesor();
  }, [handleGetGruposProfesor]);

  const handleGetEstudiantesGrupo = useCallback(() => {
    const url = "http://localhost:8081/gestion-academica/estudiantes-grupo";

    const { numeroGrupo, codigoCurso } = query;

    fetch(url, {
      method: "POST",
      body: JSON.stringify({ numeroGrupo, codigoCurso }),
    })
      .then((data) => data.json())
      .then((res) => setEstudiantes(res))
      .catch((e) => []);
  }, [query]);

  useEffect(() => {
    handleGetEstudiantesGrupo();
  }, [handleGetEstudiantesGrupo]);

  const ingresaNota = async (calificacion) => {
    const url = "http://localhost:8081/gestion-academica/grupos-estudiante";

    await fetch(url, {
      method: "PUT",
      body: JSON.stringify(calificacion),
    }).then(() => handleGetEstudiantesGrupo());
  };

  return {
    grupos,
    estudiantes,
    ingresaNota,
  };
}
