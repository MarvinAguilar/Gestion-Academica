import { useEffect, useCallback } from "react";
import useGlobalContext from "./useGlobalContext";

export function useCarreras({ query } = { query: "" }) {
  const { carreras, setCarreras } = useGlobalContext();

  const handleGetCarreras = useCallback(() => {
    const url = "http://localhost:8081/gestion-academica/carreras";

    fetch(url)
      .then((data) => data.json())
      .then((res) => setCarreras(res))
      .catch((e) => []);
  }, [setCarreras]);

  useEffect(() => {
    handleGetCarreras();
  }, [handleGetCarreras]);

  const filterCarreras = () => {
    return query !== ""
      ? carreras.filter(
          (carrera) =>
            carrera.codigo?.toLowerCase().includes(query.toLowerCase()) ||
            carrera.nombre?.toLowerCase().includes(query.toLowerCase())
        )
      : carreras;
  };

  const insertarCarrera = async (carrera) => {
    const url = "http://localhost:8081/gestion-academica/carreras";

    await fetch(url, {
      method: "POST",
      body: JSON.stringify(carrera),
    }).then(() => setCarreras([...carreras, carrera]));
  };

  const actualizarCarrera = async (carrera) => {
    const url = "http://localhost:8081/gestion-academica/carreras";

    await fetch(url, {
      method: "PUT",
      body: JSON.stringify(carrera),
    }).then(() => handleGetCarreras());
  };

  const eliminarCarrera = async (codigo) => {
    const url = "http://localhost:8081/gestion-academica/carreras";

    await fetch(url, {
      method: "DELETE",
      body: JSON.stringify({ codigo }),
    }).then(() =>
      setCarreras(carreras.filter((carrera) => carrera.codigo !== codigo))
    );
  };

  return {
    carreras,
    filterCarreras,
    insertarCarrera,
    actualizarCarrera,
    eliminarCarrera,
  };
}
