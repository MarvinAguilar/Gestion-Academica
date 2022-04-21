import "./App.css";
import { GlobalProvider } from "./Context/GlobalContext";
import AppWrapper from "./components/AppWrapper";

function App() {
  return (
    <GlobalProvider>
      <AppWrapper />
    </GlobalProvider>
  );
}

export default App;
