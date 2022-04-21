import { useContext } from "react";
import { GlobalContext } from "../Context/GlobalContext";

export default function useGlobalContext() {
  return useContext(GlobalContext);
}
