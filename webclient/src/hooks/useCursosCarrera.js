import { useCallback, useEffect, useState } from "react";

export function useCursosCarrera({ query } = { query: "" }) {
  const [cursos, setCursos] = useState([]);

  const handleGetCursosCarrera = useCallback(() => {
    const url = "http://localhost:8081/gestion-academica/curso-carrera-ciclo";

    const { codigoCarrera, annoCiclo, numeroCiclo } = query;

    fetch(url, {
      method: "POST",
      body: JSON.stringify({ codigoCarrera, annoCiclo, numeroCiclo }),
    })
      .then((data) => data.json())
      .then((res) => setCursos(res))
      .catch((e) => []);
  }, [query]);

  useEffect(() => {
    handleGetCursosCarrera();
  }, [handleGetCursosCarrera]);

  return {
    cursos,
  };
}
