import { useState } from "react";
import { createContext } from "react";

export const GlobalContext = createContext();

export const GlobalProvider = ({ children }) => {
  const [user, setUser] = useState({ cedula: "", perfil: "" });
  const [carreras, setCarreras] = useState([]);
  const [cursos, setCursos] = useState([]);
  const [ciclos, setCiclos] = useState([]);
  const [grupos, setGrupos] = useState([]);

  const formatDate = (date) =>
    `${date.substring(8)}/${date.substring(5, 7)}/${date.substring(0, 4)}`;

  const value = {
    user,
    setUser,
    carreras,
    setCarreras,
    cursos,
    setCursos,
    ciclos,
    setCiclos,
    grupos,
    setGrupos,
    formatDate,
  };

  return (
    <GlobalContext.Provider value={value}>{children}</GlobalContext.Provider>
  );
};
