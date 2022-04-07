import { useState } from "react";
import Modal from "../components/Modal/Modal";

const RegistroNotasPage = () => {
  const [filter, setFilter] = useState({
    curso: "",
    grupo: "",
  });

  const [showModal, setShowModal] = useState(false);

  const toggleModal = () => {
    setShowModal((e) => !e);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    toggleModal();
  };

  return (
    <>
      <div className="container">
        <div className="row gap-2">
          <div className="col-sm-12 col-md-6 col-lg-3">
            <label htmlFor="selectCurso" className="form-label">
              Curso
            </label>
            <select
              id="selectCurso"
              className="form-select"
              name="curso"
              value={filter.ciclo}
              onChange={(e) => setFilter({ ...filter, curso: e.target.value })}
            >
              <option value=""></option>
              <option value="1">Fundamentos</option>
            </select>
          </div>
          <div className="col-sm-12 col-md-6 col-lg-3">
            <label htmlFor="selectGrupo" className="form-label">
              Grupo
            </label>
            <select
              id="selectGrupo"
              className="form-select"
              name="grupo"
              value={filter.ciclo}
              onChange={(e) => setFilter({ ...filter, grupo: e.target.value })}
              disabled={!filter.curso}
            >
              <option value=""></option>
              <option value="1">Fundamentos</option>
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
                <tr>
                  <td>116830152</td>
                  <td>Marvin Aguilar Fuentes</td>
                  <td>Ing. Sistemas</td>
                  <td>Móviles</td>
                  <td>1</td>
                  <td>0</td>
                  <td className="text-center">
                    <button
                      className="btn btn-success btn-sm"
                      onClick={toggleModal}
                    >
                      Ingresar Nota
                    </button>
                  </td>
                </tr>
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
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="cedula" className="form-label">
                Cédula del Estudiante
              </label>
              <input
                type="text"
                id="cedula"
                className="form-control"
                name="cedula"
                disabled
              />
            </div>
            <div className="mb-3">
              <label htmlFor="nombre" className="form-label">
                Nombre del Estudiante
              </label>
              <input
                type="text"
                id="nombre"
                className="form-control"
                name="nombre"
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
              />
            </div>
          </form>
        </div>
      </Modal>
    </>
  );
};

export default RegistroNotasPage;
