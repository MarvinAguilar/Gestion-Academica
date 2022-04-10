import { BrowserRouter } from "react-router-dom";
import Navbar from "./components/Navbar/Navbar";
import "./App.css";
import RoutesComponent from "./components/RoutesComponent";

/* import LoginPage from "./Pages/LoginPage/LoginPage"; */

function App() {
  return (
    <>
      {/* <LoginPage /> */}
      <BrowserRouter>
        <Navbar />
        <RoutesComponent />
      </BrowserRouter>
    </>
  );
}

export default App;
