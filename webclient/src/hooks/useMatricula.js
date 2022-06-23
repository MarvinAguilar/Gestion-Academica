import { useEffect, useCallback, useState } from "react";

export function useMatricula(
  { estudiante, query } = { estudiante: "", query: "" }
) {
  const [grupos, setGrupos] = useState([]);

  const handleGetGrupos = useCallback(() => {
    const url = "http://localhost:8081/gestion-academica/grupos-carrera";

    const { carrera } = estudiante;
    const { annoCiclo, numeroCiclo } = query;

    fetch(url, {
      method: "POST",
      body: JSON.stringify({ codigoCarrera: carrera, annoCiclo, numeroCiclo }),
    })
      .then((data) => data.json())
      .then((res) => setGrupos(res))
      .catch((e) => []);
  }, [query, estudiante]);

  useEffect(() => {
    handleGetGrupos();
  }, [handleGetGrupos]);

  const matricularEstudiante = async (cedulaEstudiante, grupo) => {
    const url = "http://localhost:8081/gestion-academica/matricula-estudiante";

    const { numeroGrupo, codigoCurso, annoCiclo, numeroCiclo } = grupo;

    await fetch(url, {
      method: "POST",
      body: JSON.stringify({
        cedulaEstudiante,
        numeroGrupo,
        codigoCurso,
        annoCiclo,
        numeroCiclo,
      }),
    });
  };

  return {
    grupos,
    matricularEstudiante,
  };
}
