import { useEffect, useCallback } from "react";
import useGlobalContext from "./useGlobalContext";

export function useGrupos({ query } = { query: "" }) {
  const { grupos, setGrupos } = useGlobalContext();

  const handleGetGrupos = useCallback(() => {
    const url = "http://localhost:8081/gestion-academica/grupos";

    const { codigoCurso, annoCiclo, numeroCiclo } = query;

    fetch(url, {
      method: "POST",
      body: JSON.stringify({ codigoCurso, annoCiclo, numeroCiclo }),
    })
      .then((data) => data.json())
      .then((res) => setGrupos(res))
      .catch((e) => []);
  }, [setGrupos, query]);

  useEffect(() => {
    handleGetGrupos();
  }, [handleGetGrupos]);

  /* const filterGrupos = () => {
    return query !== ""
      ? grupos.filter((Grupo) =>
          Grupo.anno?.toString().toLowerCase().includes(query.toLowerCase())
        )
      : grupos;
  }; */

  const insertarGrupo = async (grupo) => {
    const url = "http://localhost:8081/gestion-academica/grupo";

    await fetch(url, {
      method: "POST",
      body: JSON.stringify(grupo),
    }).then(() => handleGetGrupos());
  };

  const actualizarGrupo = async (Grupo) => {
    const url = "http://localhost:8081/gestion-academica/grupos";

    await fetch(url, {
      method: "PUT",
      body: JSON.stringify(Grupo),
    }).then(() => handleGetGrupos());
  };

  return {
    grupos,
    /* filterGrupos, */
    insertarGrupo,
    actualizarGrupo,
  };
}
