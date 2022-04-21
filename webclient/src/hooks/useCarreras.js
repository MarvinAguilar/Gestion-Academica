import { useEffect, useState } from "react";

export function useCarreras({ query } = { query: "" }) {
  const [carreras, setCarreras] = useState([]);

  const handleGetCarreras = async () => {
    const url = "http://localhost:8081/gestion-academica/carreras";

    await fetch(url)
      .then((data) => data.json())
      .then((res) => setCarreras(res))
      .catch((e) => []);
  };

  useEffect(() => {
    handleGetCarreras();
  }, [carreras]);

  const filterCarreras = () => {
    return query !== ""
      ? carreras.filter(
          (carrera) =>
            carrera.codigo.toLowerCase().includes(query.toLowerCase()) ||
            carrera.nombre.toLowerCase().includes(query.toLowerCase())
        )
      : carreras;
  };

  const insertarCarrera = async (carrera) => {
    const url = "http://localhost:8081/gestion-academica/carreras";

    await fetch(url, {
      method: "POST",
      body: JSON.stringify(carrera),
    });
  };

  const actualizarCarrera = async (carrera) => {
    const url = "http://localhost:8081/gestion-academica/carreras";

    await fetch(url, {
      method: "PUT",
      body: JSON.stringify(carrera),
    });
  };

  const eliminarCarrera = async (codigo) => {
    const url = "http://localhost:8081/gestion-academica/carreras";

    await fetch(url, {
      method: "DELETE",
      body: JSON.stringify({ codigo }),
    });
  };

  return {
    filterCarreras,
    insertarCarrera,
    actualizarCarrera,
    eliminarCarrera,
  };
}
