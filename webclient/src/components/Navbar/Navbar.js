import { NavLink, useNavigate } from "react-router-dom";
import logo from "../../assets/logo.png";
import useGlobalContext from "../../hooks/useGlobalContext";
import { logout } from "../../services/LoginService";

function Navbar() {
  const { user, setUser } = useGlobalContext();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout(setUser);

    navigate("/");
  };

  return (
    <>
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
        <div className="container">
          <NavLink to="/" className="navbar-brand">
            <img
              src={logo}
              alt="logo"
              className="d-inline-block align-text-top me-1"
              style={{ width: "26px", height: "26px" }}
            />
          </NavLink>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNavDropdown"
            aria-controls="navbarNavDropdown"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNavDropdown">
            <ul className="navbar-nav me-auto">
              <li className="nav-item">
                <NavLink
                  to="/"
                  className={({ isActive }) =>
                    "nav-link" + (isActive ? " active" : "")
                  }
                  aria-current="page"
                >
                  Inicio
                </NavLink>
              </li>
              {user?.perfil === 1 && (
                <>
                  <li className="nav-item">
                    <NavLink
                      to="/mantenimiento"
                      className={({ isActive }) =>
                        "nav-link" + (isActive ? " active" : "")
                      }
                      aria-current="page"
                    >
                      Mantenimiento
                    </NavLink>
                  </li>
                  <li className="nav-item">
                    <NavLink
                      to="/seguridad"
                      className={({ isActive }) =>
                        "nav-link" + (isActive ? " active" : "")
                      }
                      aria-current="page"
                    >
                      Seguridad
                    </NavLink>
                  </li>
                  <li className="nav-item">
                    <NavLink
                      to="/oferta-academica"
                      className={({ isActive }) =>
                        "nav-link" + (isActive ? " active" : "")
                      }
                      aria-current="page"
                    >
                      Oferta Académica
                    </NavLink>
                  </li>
                </>
              )}
              {(user?.perfil === 1 || user?.perfil === 2) && (
                <li className="nav-item">
                  <NavLink
                    to="/matricula"
                    className={({ isActive }) =>
                      "nav-link" + (isActive ? " active" : "")
                    }
                    aria-current="page"
                  >
                    Matrícula
                  </NavLink>
                </li>
              )}
              {user?.perfil === 3 && (
                <li className="nav-item">
                  <NavLink
                    to="/registro-notas"
                    className={({ isActive }) =>
                      "nav-link" + (isActive ? " active" : "")
                    }
                    aria-current="page"
                  >
                    Registro de Notas
                  </NavLink>
                </li>
              )}
              {user?.perfil === 4 && (
                <li className="nav-item">
                  <NavLink
                    to="/historial-academico"
                    className={({ isActive }) =>
                      "nav-link" + (isActive ? " active" : "")
                    }
                    aria-current="page"
                  >
                    Historial Académico
                  </NavLink>
                </li>
              )}
            </ul>
            <button className="btn btn-primary" onClick={handleLogout}>
              Logout
            </button>
          </div>
        </div>
      </nav>
    </>
  );
}

export default Navbar;
