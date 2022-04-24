import { useState } from "react";
import { useAlumnos } from "../hooks/useAlumnos";
import { useCiclos } from "../hooks/useCiclos";
import { useMatricula } from "../hooks/useMatricula";

const MatriculaPage = () => {
  const { ciclos } = useCiclos();
  const [cedulaEstudiante, setCedulaEstudiante] = useState("");
  const [estudiante, setEstudiante] = useState({
    cedula: "",
    nombre: "",
    telefono: "",
    email: "",
    fechaNacimiento: "",
    carrera: "",
  });
  const [query, setQuery] = useState({
    annoCiclo: "",
    numeroCiclo: "",
  });
  const { buscarAlumno } = useAlumnos();
  const { grupos, matricularEstudiante } = useMatricula({ estudiante, query });

  const handleBuscarEstudiante = async (e) => {
    e.preventDefault();

    setEstudiante(await buscarAlumno(cedulaEstudiante));
  };

  const handleChangeCiclo = (e) => {
    const ciclo = e.target.value.split("-");
    const [annoCiclo, numeroCiclo] = ciclo;
    setQuery({
      annoCiclo: annoCiclo,
      numeroCiclo: numeroCiclo,
    });
  };

  const handleMatricula = (grupo) => {
    const cedulaEstudiante = estudiante.cedula;
    matricularEstudiante(cedulaEstudiante, grupo);
  };

  return (
    <>
      <div className="container">
        <div className="row gap-2">
          <div className="col-sm-12 col-md-6 col-lg-3">
            <form onSubmit={handleBuscarEstudiante}>
              <label htmlFor="cedulaEstudiante" className="form-label">
                Cédula del Estudiante a matricular
              </label>
              <input
                type="text"
                id="cedulaEstudiante"
                className="form-control"
                name="cedulaEstudiante"
                placeholder="Digite la cédula"
                value={cedulaEstudiante}
                onChange={(e) => setCedulaEstudiante(e.target.value)}
              />
            </form>
          </div>
          <div className="col-sm-12 col-md-6 col-lg-4">
            <label htmlFor="nombreEstudiante" className="form-label">
              Nombre del Estudiante
            </label>
            <input
              type="text"
              id="nombreEstudiante"
              className="form-control"
              name="nombreEstudiante"
              value={estudiante.nombre}
              readOnly
            />
          </div>
          <div className="col-sm-12 col-md-4 col-lg-2">
            <label htmlFor="selectCiclo" className="form-label">
              Ciclo
            </label>
            <select
              id="selectCiclo"
              className="form-select"
              name="ciclo"
              value={`${query.annoCiclo}-${query.numeroCiclo}`}
              onChange={handleChangeCiclo}
            >
              <option value=""></option>
              {ciclos.map((ciclo) => (
                <option
                  key={`${ciclo.anno}-${ciclo.numero}`}
                  value={`${ciclo.anno}-${ciclo.numero}`}
                >
                  {(ciclo.numero === 1 && "I") || (ciclo.numero === 2 && "II")}{" "}
                  - Ciclo {ciclo.anno}
                </option>
              ))}
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
                {grupos.map((grupo) => (
                  <tr key={`${grupo.numeroGrupo}-${grupo.nombreCurso}`}>
                    <td>{grupo.nombreCurso}</td>
                    <td>{grupo.numeroGrupo}</td>
                    <td>{grupo.nombreProfesor}</td>
                    <td>{grupo.creditos}</td>
                    <td>{grupo.horario}</td>
                    <td>{grupo.horasSemanales}</td>
                    <td className="text-center">
                      <button
                        className="btn btn-danger btn-sm"
                        onClick={() => handleMatricula(grupo)}
                      >
                        Matricular
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </>
  );
};

export default MatriculaPage;
