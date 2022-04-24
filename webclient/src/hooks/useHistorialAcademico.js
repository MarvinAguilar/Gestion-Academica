import { useCallback, useEffect, useState } from "react";

export function useHistorialAcademico(
  { cedulaEstudiante } = { cedulaEstudiante: "" }
) {
  const [historialAcademico, setHistorialAcademico] = useState([]);

  const handleGetHistorialAcademico = useCallback(() => {
    const url = "http://localhost:8081/gestion-academica/grupos-estudiante";

    fetch(url, {
      method: "POST",
      body: JSON.stringify({ cedulaEstudiante }),
    })
      .then((data) => data.json())
      .then((res) => setHistorialAcademico(res))
      .catch((e) => []);
  }, [cedulaEstudiante]);

  useEffect(() => {
    handleGetHistorialAcademico();
  }, [handleGetHistorialAcademico]);

  const groupHistorialAcademico = () => {
    const cursos = new Map();

    historialAcademico.forEach(
      ({
        numeroCiclo,
        annoCiclo,
        codigoCurso,
        nombreCurso,
        creditosCurso,
        nota,
      }) => {
        let ciclo = `${
          (numeroCiclo === 1 && "I") || (numeroCiclo === 2 && "II")
        }-${annoCiclo}`;
        if (cursos.has(ciclo)) {
          let arrayCurso = cursos.get(ciclo);
          cursos.set(ciclo, [
            ...arrayCurso,
            { codigoCurso, nombreCurso, creditosCurso, nota },
          ]);
        } else {
          cursos.set(ciclo, [
            { codigoCurso, nombreCurso, creditosCurso, nota },
          ]);
        }
      }
    );

    return Array.from(cursos);
  };

  return { groupHistorialAcademico };
}
