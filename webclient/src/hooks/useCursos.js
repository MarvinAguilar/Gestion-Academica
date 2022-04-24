import { useCallback, useEffect } from "react";
import useGlobalContext from "./useGlobalContext";

export function useCursos({ query } = { query: "" }) {
  const { cursos, setCursos } = useGlobalContext();

  const handleGetCursos = useCallback(() => {
    const url = "http://localhost:8081/gestion-academica/cursos";

    fetch(url)
      .then((data) => data.json())
      .then((res) => setCursos(res))
      .catch((e) => []);
  }, [setCursos]);

  useEffect(() => {
    handleGetCursos();
  }, [handleGetCursos]);

  const filterCursos = () => {
    return query !== ""
      ? cursos.filter(
          (curso) =>
            curso.codigo?.toLowerCase().includes(query.toLowerCase()) ||
            curso.nombre?.toLowerCase().includes(query.toLowerCase()) ||
            curso.nombreCarrera?.toLowerCase().includes(query.toLowerCase())
        )
      : cursos;
  };

  const insertarCurso = async (curso) => {
    const url = "http://localhost:8081/gestion-academica/cursos";

    await fetch(url, {
      method: "POST",
      body: JSON.stringify(curso),
    }).then(() => setCursos([...cursos, curso]));
  };

  const actualizarCurso = async (curso) => {
    const url = "http://localhost:8081/gestion-academica/cursos";

    await fetch(url, {
      method: "PUT",
      body: JSON.stringify(curso),
    }).then(() => handleGetCursos());
  };

  const eliminarCurso = async (codigo) => {
    const url = "http://localhost:8081/gestion-academica/cursos";

    await fetch(url, {
      method: "DELETE",
      body: JSON.stringify({ codigo }),
    }).then(() => setCursos(cursos.filter((curso) => curso.codigo !== codigo)));
  };

  return {
    filterCursos,
    insertarCurso,
    actualizarCurso,
    eliminarCurso,
  };
}
