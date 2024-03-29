import { useState } from "react";
import Modal from "../components/Modal/Modal";
import { useRegistraNota } from "../hooks/useRegistraNota";

const RegistroNotasPage = () => {
  const [showModal, setShowModal] = useState(false);
  const [query, setQuery] = useState({
    numeroGrupo: "",
    codigoCurso: "",
  });
  const [calificacion, setCalificacion] = useState({
    cedulaEstudiante: "",
    numeroGrupo: "",
    codigoCurso: "",
    nota: "",
  });
  const { grupos, estudiantes, ingresaNota } = useRegistraNota({ query });

  const toggleModal = () => {
    setShowModal((e) => !e);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    ingresaNota(calificacion);

    toggleModal();
  };

  const handleChangeGrupo = (e) => {
    const grupo = e.target.value.split("-");
    const [numeroGrupo, codigoCurso] = grupo;
    setQuery({ ...query, numeroGrupo: numeroGrupo, codigoCurso: codigoCurso });
  };

  const handleIngresaNota = (estudiante) => {
    const { cedulaEstudiante, numeroGrupo, nota } = estudiante;
    const { codigoCurso } = query;
    setCalificacion({
      cedulaEstudiante,
      numeroGrupo,
      codigoCurso,
      nota,
    });
    toggleModal();
  };

  return (
    <>
      <div className="container">
        <div className="row gap-2">
          <div className="col-sm-12 col-md-6 col-lg-3">
            <label htmlFor="selectGrupo" className="form-label">
              Grupo
            </label>
            <select
              id="selectGrupo"
              className="form-select"
              name="grupo"
              value={`${query.numeroGrupo}-${query.codigoCurso}`}
              onChange={handleChangeGrupo}
            >
              <option value=""></option>
              {grupos.map((grupo) => (
                <option
                  key={`${grupo.numeroGrupo}-${grupo.codigoCurso}`}
                  value={`${grupo.numeroGrupo}-${grupo.codigoCurso}`}
                >{`${grupo.numeroGrupo}- ${grupo.codigoCurso}:${grupo.nombreCurso}`}</option>
              ))}
            </select>
          </div>
        </div>

        <div className="mt-4">
          <h5 className="card-title">Lista de Estudiantes</h5>

          <div className="table-responsive mt-3">
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>Cédula</th>
                  <th>Nombre</th>
                  <th>Carrera</th>
                  <th>Curso</th>
                  <th>Grupo</th>
                  <th>Nota</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {estudiantes.map((estudiante) => (
                  <tr key={estudiante.cedulaEstudiante}>
                    <td>{estudiante.cedulaEstudiante}</td>
                    <td>{estudiante.nombreEstudiante}</td>
                    <td>{estudiante.carreraEstudiante}</td>
                    <td>{estudiante.nombreCurso}</td>
                    <td>{estudiante.numeroGrupo}</td>
                    <td>{estudiante.nota}</td>
                    <td className="text-center">
                      <button
                        className="btn btn-success btn-sm"
                        onClick={() => handleIngresaNota(estudiante)}
                      >
                        Ingresar Nota
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <Modal
        title={"Ingresar Nota"}
        showModal={showModal}
        toggleModal={toggleModal}
      >
        <div className="card-body custom-modal__content-body">
          <form id="insertForm" onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="cedula" className="form-label">
                Cédula del Estudiante
              </label>
              <input
                type="text"
                id="cedula"
                className="form-control"
                name="cedula"
                value={calificacion.cedulaEstudiante}
                disabled
              />
            </div>
            <div className="mb-3">
              <label htmlFor="nota" className="form-label">
                Nota
              </label>
              <input
                type="text"
                id="nota"
                className="form-control"
                name="nota"
                value={calificacion.nota}
                onChange={(e) =>
                  setCalificacion({ ...calificacion, nota: e.target.value })
                }
              />
            </div>
          </form>
        </div>
      </Modal>
    </>
  );
};

export default RegistroNotasPage;
