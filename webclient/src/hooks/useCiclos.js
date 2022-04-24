import { useEffect, useCallback } from "react";
import useGlobalContext from "./useGlobalContext";

export function useCiclos({ query } = { query: "" }) {
  const { ciclos, setCiclos } = useGlobalContext();

  const handleGetCiclos = useCallback(() => {
    const url = "http://localhost:8081/gestion-academica/ciclos";

    fetch(url)
      .then((data) => data.json())
      .then((res) => setCiclos(res))
      .catch((e) => []);
  }, [setCiclos]);

  useEffect(() => {
    handleGetCiclos();
  }, [handleGetCiclos]);

  const filterCiclos = () => {
    return query !== ""
      ? ciclos.filter((ciclo) =>
          ciclo.anno?.toString().toLowerCase().includes(query.toLowerCase())
        )
      : ciclos;
  };

  const insertarCiclo = async (ciclo) => {
    const url = "http://localhost:8081/gestion-academica/ciclos";

    await fetch(url, {
      method: "POST",
      body: JSON.stringify(ciclo),
    }).then(() => setCiclos([...ciclos, ciclo]));
  };

  const actualizarCiclo = async (ciclo) => {
    const url = "http://localhost:8081/gestion-academica/ciclos";

    await fetch(url, {
      method: "PUT",
      body: JSON.stringify(ciclo),
    }).then(() => handleGetCiclos());
  };

  return {
    ciclos,
    filterCiclos,
    insertarCiclo,
    actualizarCiclo,
  };
}
