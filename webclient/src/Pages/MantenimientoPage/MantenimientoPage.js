import { NavLink, Outlet } from "react-router-dom";

const MantenimientoPage = () => {
  return (
    <>
      <div className="container">
        <div className="card">
          <div className="card-header">
            <ul className="nav nav-tabs card-header-tabs">
              <li className="nav-item">
                <NavLink
                  to="/mantenimiento/carreras"
                  className={({ isActive }) =>
                    "nav-link" + (isActive ? " active" : "")
                  }
                >
                  Carreras
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink
                  to="/mantenimiento/cursos"
                  className={({ isActive }) =>
                    "nav-link" + (isActive ? " active" : "")
                  }
                >
                  Cursos
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink
                  to="/mantenimiento/profesores"
                  className={({ isActive }) =>
                    "nav-link" + (isActive ? " active" : "")
                  }
                >
                  Profesores
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink
                  to="/mantenimiento/alumnos"
                  className={({ isActive }) =>
                    "nav-link" + (isActive ? " active" : "")
                  }
                >
                  Alumnos
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink
                  to="/mantenimiento/ciclos"
                  className={({ isActive }) =>
                    "nav-link" + (isActive ? " active" : "")
                  }
                >
                  Ciclos
                </NavLink>
              </li>
            </ul>
          </div>

          <Outlet />
        </div>
      </div>
    </>
  );
};

export default MantenimientoPage;
