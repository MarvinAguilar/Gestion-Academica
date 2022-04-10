import React from "react";

const HistorialAcademicoPage = () => {
  return (
    <>
      <div className="container">
        <div className="mt-4">
          <div className="d-flex justify-content-between align-content-center">
            <h5 className="card-title">Historial Académico</h5>
            <p>
              <b>Estudiante:</b> Marvin Aguilar
            </p>
          </div>

          <div className="mt-4 mx-3">
            <h6>Ciclo: I-2022</h6>
            <div className="table-responsive mt-3">
              <table className="table table-bordered table-striped">
                <thead>
                  <tr>
                    <th>Código Curso</th>
                    <th>Nombre Curso</th>
                    <th>Créditos</th>
                    <th>Nota</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>EIF400</td>
                    <td>Móviles</td>
                    <td>4</td>
                    <td>100</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>

          <div className="mt-4 mx-3">
            <h6>Ciclo: II-2022</h6>
            <div className="table-responsive mt-3">
              <table className="table table-bordered table-striped">
                <thead>
                  <tr>
                    <th>Código Curso</th>
                    <th>Nombre Curso</th>
                    <th>Créditos</th>
                    <th>Nota</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>EIF466</td>
                    <td>Bases</td>
                    <td>3</td>
                    <td>98</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default HistorialAcademicoPage;
