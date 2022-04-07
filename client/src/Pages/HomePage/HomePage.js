import React from "react";
import logo from "../../assets/logo.png";

function HomePage() {
  return (
    <>
      <div className="container">
        <div className="row">
          <div className="col">
            <div className="px-4 py-5 my-5 text-center">
              <img
                src={logo}
                alt="logo"
                className="d-block mx-auto mb-4"
                style={{ width: "64px", height: "64px" }}
              />
              <h1 className="display-6 fw-bold">
                Bienvenido/a Marvin Aguilar!
              </h1>
              <div className="col-lg-8 mx-auto">
                <p className="lead text-secondary">
                  Este es el Sistema de Gestión Académica en este sitio podrás
                  consultar la oferta académica es decir las diferentes
                  carreras, cursos y grupos disponibles del curso lectivo
                  actual, también es posible consultar tú historial académico
                  para conocer las notas obtenidas en cada curso.
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default HomePage;
