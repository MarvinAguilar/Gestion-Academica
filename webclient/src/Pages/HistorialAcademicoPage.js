import useGlobalContext from "../hooks/useGlobalContext";
import { useHistorialAcademico } from "../hooks/useHistorialAcademico";

const HistorialAcademicoPage = () => {
  const { user } = useGlobalContext();
  const cedulaEstudiante = user.cedula;
  const { groupHistorialAcademico } = useHistorialAcademico({
    cedulaEstudiante,
  });

  return (
    <>
      <div className="container">
        <div className="mt-4">
          <div className="d-flex justify-content-between align-content-center">
            <h5 className="card-title">Historial Académico</h5>
            <p>
              <b>Estudiante:</b> {user.nombre}
            </p>
          </div>

          {groupHistorialAcademico().map((cursos) => (
            <div key={cursos[0]} className="mt-4 mx-3">
              <h6>Ciclo: {cursos[0]}</h6>
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
                    {cursos[1].map((curso) => (
                      <tr key={curso.codigoCurso}>
                        <td>{curso.codigoCurso}</td>
                        <td>{curso.nombreCurso}</td>
                        <td>{curso.creditosCurso}</td>
                        <td>{curso.nota}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
};

export default HistorialAcademicoPage;
