import { BrowserRouter } from "react-router-dom";
import useGlobalContext from "../hooks/useGlobalContext";
import Navbar from "./Navbar/Navbar";
import RoutesComponent from "./RoutesComponent";
import LoginPage from "../Pages/LoginPage/LoginPage";
import UserNavbar from "./UserNavbar";

const AppWrapper = () => {
  const { user } = useGlobalContext();

  return (
    <>
      {user.cedula !== "" ? (
        <BrowserRouter>
          <Navbar />
          <UserNavbar />
          <RoutesComponent />
        </BrowserRouter>
      ) : (
        <LoginPage />
      )}
    </>
  );
};

export default AppWrapper;
