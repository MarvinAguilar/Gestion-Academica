import { useState } from "react";
import { createContext } from "react";

export const GlobalContext = createContext();

export const GlobalProvider = ({ children }) => {
  const [user, setUser] = useState({ cedula: "", perfil: "" });
  const [carreras, setCarreras] = useState([]);
  const [ciclos, setCiclos] = useState([]);

  const value = {
    user,
    setUser,
    carreras,
    setCarreras,
    ciclos,
    setCiclos,
  };

  return (
    <GlobalContext.Provider value={value}>{children}</GlobalContext.Provider>
  );
};
