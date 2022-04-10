import { useState } from "react";
import Modal from "../../../components/Modal/Modal";

const MantenimientoProfesoresPage = () => {
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
        <h5 className="card-title">Lista de Profesores</h5>

        <div className="d-flex justify-content-end">
          <button
            type="button"
            className="btn btn-primary btn-sm"
            onClick={toggleModal}
          >
            Insertar Profesor
          </button>
        </div>

        <div className="table-responsive mt-3">
          <table className="table table-bordered table-striped">
            <thead>
              <tr>
                <th>Cédula</th>
                <th>Nombre</th>
                <th>Teléfono</th>
                <th>Email</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>110350647</td>
                <td>Luis Lopez</td>
                <td>87365479</td>
                <td>lilc@gmail.com</td>
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
        title={"Nuevo Profesor"}
        showModal={showModal}
        toggleModal={toggleModal}
      >
        <div className="card-body custom-modal__content-body">
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="cedula" className="form-label">
                Cédula
              </label>
              <input
                type="text"
                id="cedula"
                className="form-control"
                name="cedula"
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
              <label htmlFor="telefono" className="form-label">
                Teléfono
              </label>
              <input
                type="text"
                id="telefono"
                className="form-control"
                name="telefono"
              />
            </div>
            <div className="mb-3">
              <label htmlFor="email" className="form-label">
                Email
              </label>
              <input
                type="text"
                id="email"
                className="form-control"
                name="email"
              />
            </div>
          </form>
        </div>
      </Modal>
    </>
  );
};

export default MantenimientoProfesoresPage;
