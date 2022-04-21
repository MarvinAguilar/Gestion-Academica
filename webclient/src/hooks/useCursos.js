import { useEffect, useState } from "react";

export function useCursos({ query } = { query: "" }) {
  const [cursos, setCurso] = useState([]);

  const handleGetCursos = async () => {
    const url = "http://localhost:8081/gestion-academica/cursos";

    await fetch(url)
      .then((data) => data.json())
      .then((res) => setCurso(res))
      .catch((e) => []);
  };

  useEffect(() => {
    handleGetCursos();
  }, [cursos]);

  const filterCursos = () => {
    return query !== ""
      ? cursos.filter(
          (curso) =>
            curso.codigo.toLowerCase().includes(query.toLowerCase()) ||
            curso.nombre.toLowerCase().includes(query.toLowerCase()) ||
            curso.carrera.toLowerCase().includes(query.toLowerCase())
        )
      : cursos;
  };

  const insertarCurso = async (curso) => {
    const url = "http://localhost:8081/gestion-academica/cursos";

    await fetch(url, {
      method: "POST",
      body: JSON.stringify(curso),
    });
  };

  const actualizarCurso = async (curso) => {
    const url = "http://localhost:8081/gestion-academica/cursos";

    await fetch(url, {
      method: "PUT",
      body: JSON.stringify(curso),
    });
  };

  const eliminarCurso = async (codigo) => {
    const url = "http://localhost:8081/gestion-academica/cursos";

    await fetch(url, {
      method: "DELETE",
      body: JSON.stringify({ codigo }),
    });
  };

  return {
    filterCursos,
    insertarCurso,
    actualizarCurso,
    eliminarCurso,
  };
}
