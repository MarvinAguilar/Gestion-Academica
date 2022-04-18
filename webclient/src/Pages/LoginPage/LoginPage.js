import React from "react";
import logo from "../../assets/logo.png";
import "./LoginPage.css";

function LoginPage() {
  return (
    <>
      <div
        className="d-flex flex-column justify-content-center align-items-center"
        style={{ height: "100vh", background: "#0568a7" }}
      >
        <div className="row">
          <div className="col">
            <div className="d-flex flex-column justify-content-center align-items-center pb-0 mb-0 pb-lg-5 mb-lg-5">
              <header className="d-lg-flex justify-content-center align-content-center py-2 text-center">
                <img
                  src={logo}
                  alt="Logo"
                  className="me-lg-2"
                  style={{ width: "32px", height: "32px" }}
                />
                <h1
                  className="login-title display-6 fw-bold text-light"
                  style={{ fontSize: "1.8rem" }}
                >
                  Sistema de Gestión Académica
                </h1>
              </header>

              <section className="mt-2">
                <div className="login-form d-flex flex-column justify-content-center align-items-center">
                  <div>
                    <p className="lead">Iniciar Sesión</p>
                  </div>

                  <form className="w-75">
                    <div className="mb-4">
                      <input
                        type="text"
                        className="form-control"
                        placeholder="Nombre de Usuario"
                      />
                    </div>
                    <div className="mb-4">
                      <input
                        type="text"
                        className="form-control"
                        placeholder="Contraseña"
                      />
                    </div>
                    <div className="mb-5 form-check">
                      <input type="checkbox" className="form-check-input" />
                      <label className="form-check-label">
                        Recordarme en este equipo
                      </label>
                    </div>
                    <div className="d-flex justify-content-end">
                      <button type="submit" className="btn btn-primary">
                        Acceder
                      </button>
                    </div>
                  </form>
                </div>
              </section>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default LoginPage;
