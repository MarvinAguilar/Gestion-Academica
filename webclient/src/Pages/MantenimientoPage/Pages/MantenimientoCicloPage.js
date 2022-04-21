import { useState } from "react";
import Modal from "../../../components/Modal/Modal";

const MantenimientoCiclosPage = () => {
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
        <h5 className="card-title">Lista de Ciclos</h5>

        <div className="d-flex justify-content-end">
          <button
            type="button"
            className="btn btn-primary btn-sm"
            onClick={toggleModal}
          >
            Insertar Ciclo
          </button>
        </div>

        <div className="table-responsive mt-3">
          <table className="table table-bordered table-striped">
            <thead>
              <tr>
                <th>Año</th>
                <th>Número</th>
                <th>Fecha Inicio</th>
                <th>Fecha Final</th>
                <th>Estado</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>2022</td>
                <td>1</td>
                <td>25/02/2022</td>
                <td>17/08/2022</td>
                <td>Activo</td>
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
        title={"Nuevo Ciclo"}
        showModal={showModal}
        toggleModal={toggleModal}
      >
        <div className="card-body custom-modal__content-body">
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="anno" className="form-label">
                Año
              </label>
              <input
                type="text"
                id="anno"
                className="form-control"
                name="anno"
              />
            </div>
            <div className="mb-3">
              <label htmlFor="numero" className="form-label">
                Número
              </label>
              <input
                type="text"
                id="numero"
                className="form-control"
                name="numero"
              />
            </div>
            <div className="mb-3">
              <label htmlFor="fechaInicio" className="form-label">
                Fecha Inicio
              </label>
              <input
                type="text"
                id="fechaInicio"
                className="form-control"
                name="fechaInicio"
              />
            </div>
            <div className="mb-3">
              <label htmlFor="fechaFinal" className="form-label">
                Fecha Final
              </label>
              <input
                type="fechaFinal"
                id="email"
                className="form-control"
                name="fechaFinal"
              />
            </div>
            <div className="mb-3">
              <label htmlFor="estado" className="form-label">
                Estado
              </label>
              <select id="estado" className="form-select" name="estado">
                <option value="1">Activo</option>
                <option value="0">Inactivo</option>
              </select>
            </div>
          </form>
        </div>
      </Modal>
    </>
  );
};

export default MantenimientoCiclosPage;
