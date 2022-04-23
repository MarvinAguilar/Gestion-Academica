import { useEffect, useState } from "react";

export function useAlumnos({ query } = { query: "" }) {
  const [alumnos, setAlumnos] = useState([]);

  const handleGetAlumnos = async () => {
    const url = "http://localhost:8081/gestion-academica/alumnos";

    await fetch(url)
      .then((data) => data.json())
      .then((res) => setAlumnos(res))
      .catch((e) => []);
  };

  useEffect(() => {
    handleGetAlumnos();
  }, [setAlumnos]);

  const filterAlumnos = () => {
    return query !== ""
      ? alumnos.filter(
          (alumno) =>
            alumno.cedula.toLowerCase().includes(query.toLowerCase()) ||
            alumno.nombre.toLowerCase().includes(query.toLowerCase()) ||
            alumno.carrera.toLowerCase().includes(query.toLowerCase())
        )
      : alumnos;
  };

  const insertarAlumno = async (alumno) => {
    const url = "http://localhost:8081/gestion-academica/alumnos";

    await fetch(url, {
      method: "POST",
      body: JSON.stringify(alumno),
    }).then(() => setAlumnos([...alumnos, alumno]));
  };

  const actualizarAlumno = async (alumno) => {
    const url = "http://localhost:8081/gestion-academica/alumnos";

    await fetch(url, {
      method: "PUT",
      body: JSON.stringify(alumno),
    }).then(() => handleGetAlumnos());
  };

  const eliminarAlumno = async (cedula) => {
    const url = "http://localhost:8081/gestion-academica/alumnos";

    await fetch(url, {
      method: "DELETE",
      body: JSON.stringify({ cedula }),
    }).then(() =>
      setAlumnos(alumnos.filter((alumno) => alumno.cedula !== cedula))
    );
  };

  return {
    filterAlumnos,
    insertarAlumno,
    actualizarAlumno,
    eliminarAlumno,
  };
}
