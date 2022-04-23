import { useEffect, useState } from "react";

export function useCursos({ query } = { query: "" }) {
  const [cursos, setCursos] = useState([]);

  const handleGetCursos = async () => {
    const url = "http://localhost:8081/gestion-academica/cursos";

    await fetch(url)
      .then((data) => data.json())
      .then((res) => setCursos(res))
      .catch((e) => []);
  };

  useEffect(() => {
    handleGetCursos();
  }, [setCursos]);

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
