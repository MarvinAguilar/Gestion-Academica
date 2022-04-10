import { useState } from "react";

const MatriculaPage = () => {
  const [filter, setFilter] = useState({
    ciclo: "",
  });

  return (
    <>
      <div className="container">
        <div className="row gap-2">
          <div className="col-sm-12 col-md-4 col-lg-2">
            <label htmlFor="selectCiclo" className="form-label">
              Ciclo
            </label>
            <select
              id="selectCiclo"
              className="form-select"
              name="ciclo"
              value={filter.ciclo}
              onChange={(e) => setFilter({ ...filter, ciclo: e.target.value })}
            >
              <option value=""></option>
              <option value="1">II - Ciclo</option>
            </select>
          </div>
        </div>

        <div className="mt-4">
          <h5 className="card-title">Lista de Cursos</h5>

          <div className="table-responsive mt-3">
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>Curso</th>
                  <th>Grupo</th>
                  <th>Profesor</th>
                  <th>Créditos</th>
                  <th>Horario</th>
                  <th>Horas Semanales</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>1</td>
                  <td>1</td>
                  <td>Luis López</td>
                  <td>4</td>
                  <td>8 am</td>
                  <td>11</td>
                  <td className="text-center">
                    <button className="btn btn-danger btn-sm">
                      Matricular
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </>
  );
};

export default MatriculaPage;
