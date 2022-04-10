import { useState } from "react";
import Modal from "../../../components/Modal/Modal";

function MantenimientoCursoPage() {
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
      <div className="card-body">
        <h5 className="card-title">Lista de Cursos</h5>

        <div className="d-flex justify-content-end">
          <button
            type="button"
            className="btn btn-primary btn-sm"
            onClick={toggleModal}
          >
            Insertar Curso
          </button>
        </div>

        <div className="table-responsive mt-3">
          <table className="table table-bordered table-striped">
            <thead>
              <tr>
                <th>Código</th>
                <th>Nombre</th>
                <th>Créditos</th>
                <th>Horas Semanales</th>
                <th>Carrera</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>200</td>
                <td>Fundamentos de Informática</td>
                <td>4</td>
                <td>11</td>
                <td>Informática</td>
                <td className="text-center">
                  <button className="btn btn-success btn-sm mx-2">
                    Editar
                  </button>
                  <button className="btn btn-danger btn-sm">Eliminar</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <Modal
        title={"Nuevo Curso"}
        showModal={showModal}
        toggleModal={toggleModal}
      >
        <div className="card-body custom-modal__content-body">
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="codigo" className="form-label">
                Código
              </label>
              <input
                type="text"
                id="codigo"
                className="form-control"
                name="codigo"
              />
            </div>
            <div className="mb-3">
              <label htmlFor="nombre" className="form-label">
                Nombre
              </label>
              <input
                type="text"
                id="nombre"
                className="form-control"
                name="nombre"
              />
            </div>
            <div className="mb-3">
              <label htmlFor="creditos" className="form-label">
                Créditos
              </label>
              <input
                type="text"
                id="creditos"
                className="form-control"
                name="creditos"
              />
            </div>
            <div className="mb-3">
              <label htmlFor="horasSemanales" className="form-label">
                Horas Semanales
              </label>
              <input
                type="text"
                id="horasSemanales"
                className="form-control"
                name="horasSemanales"
              />
            </div>
            <div className="mb-3">
              <label htmlFor="carrera" className="form-label">
                Carrera
              </label>
              <input
                type="text"
                id="carrera"
                className="form-control"
                name="carrera"
              />
            </div>
          </form>
        </div>
      </Modal>
    </>
  );
}

export default MantenimientoCursoPage;