import { useState } from "react";
import Modal from "../../../components/Modal/Modal";
import { useProfesores } from "../../../hooks/useProfesores";

const MantenimientoProfesoresPage = () => {
  const [showModal, setShowModal] = useState(false);
  const [editing, setEditing] = useState(false);
  const [query, setQuery] = useState("");
  const {
    filterProfesores,
    insertarProfesor,
    actualizarProfesor,
    eliminarProfesor,
  } = useProfesores({
    query,
  });
  const [profesor, setProfesor] = useState({
    cedula: "",
    nombre: "",
    telefono: "",
    email: "",
  });

  const toggleModal = () => {
    setShowModal((e) => !e);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!editing) insertarProfesor(profesor);
    else actualizarProfesor(profesor);

    toggleModal();
  };

  const handleQueryChange = (e) => {
    setQuery(e.target.value);
  };

  const handleInsert = () => {
    setProfesor({
      cedula: "",
      nombre: "",
      telefono: "",
      email: "",
    });
    setEditing(false);
    toggleModal();
  };

  const handleUpdate = (profesor) => {
    setProfesor(profesor);
    setEditing(true);
    toggleModal();
  };

  const handleDelete = (cedula) => {
    eliminarProfesor(cedula);
  };

  return (
    <>
      <div className="card-body">
        <h5 className="card-title">Lista de Profesores</h5>

        <div className="d-flex justify-content-end gap-3">
          <div>
            <input
              type="text"
              className="form-control form-control-sm"
              placeholder="Buscar"
              value={query}
              onChange={handleQueryChange}
            />
          </div>
          <div>
            <button
              type="button"
              className="btn btn-primary btn-sm"
              onClick={handleInsert}
            >
              Insertar Profesor
            </button>
          </div>
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
              {filterProfesores().map((profesor) => (
                <tr key={profesor.cedula}>
                  <td>{profesor.cedula}</td>
                  <td>{profesor.nombre}</td>
                  <td>{profesor.telefono}</td>
                  <td>{profesor.email}</td>
                  <td className="text-center">
                    <button
                      className="btn btn-success btn-sm mx-2"
                      onClick={() => handleUpdate(profesor)}
                    >
                      Editar
                    </button>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => handleDelete(profesor.cedula)}
                    >
                      Eliminar
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      <Modal
        title={!editing ? "Nuevo Profesor" : "Actualizar Profesor"}
        showModal={showModal}
        toggleModal={toggleModal}
      >
        <div className="card-body custom-modal__content-body">
          <form id="insertForm" onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="cedula" className="form-label">
                Cédula
              </label>
              <input
                type="text"
                id="cedula"
                className="form-control"
                name="cedula"
                value={profesor.cedula}
                onChange={(e) =>
                  setProfesor({ ...profesor, cedula: e.target.value })
                }
                disabled={editing}
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
                value={profesor.nombre}
                onChange={(e) =>
                  setProfesor({ ...profesor, nombre: e.target.value })
                }
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
                value={profesor.telefono}
                onChange={(e) =>
                  setProfesor({ ...profesor, telefono: e.target.value })
                }
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
                value={profesor.email}
                onChange={(e) =>
                  setProfesor({ ...profesor, email: e.target.value })
                }
              />
            </div>
          </form>
        </div>
      </Modal>
    </>
  );
};

export default MantenimientoProfesoresPage;
