import { useState } from "react";
import Modal from "../../../components/Modal/Modal";
import { useCarreras } from "../../../hooks/useCarreras";

const MantenimientoCarrerasPage = () => {
  const [showModal, setShowModal] = useState(false);
  const [editing, setEditing] = useState(false);
  const [query, setQuery] = useState("");
  const {
    filterCarreras,
    insertarCarrera,
    actualizarCarrera,
    eliminarCarrera,
  } = useCarreras({
    query,
  });
  const [carrera, setCarrera] = useState({
    codigo: "",
    nombre: "",
    titulo: "",
  });

  const toggleModal = () => {
    setShowModal((e) => !e);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!editing) insertarCarrera(carrera);
    else actualizarCarrera(carrera);

    toggleModal();
  };

  const handleQueryChange = (e) => {
    setQuery(e.target.value);
  };

  const handleInsert = () => {
    setCarrera({
      codigo: "",
      nombre: "",
      titulo: "",
    });
    setEditing(false);
    toggleModal();
  };

  const handleUpdate = (carrera) => {
    setCarrera(carrera);
    setEditing(true);
    toggleModal();
  };

  const handleDelete = (codigo) => {
    eliminarCarrera(codigo);
  };

  return (
    <>
      <div className="card-body">
        <h5 className="card-title">Lista de Carreras</h5>

        <div className="d-flex justify-content-end align-content-center gap-3">
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
              Insertar Carrera
            </button>
          </div>
        </div>

        <div className="table-responsive mt-3">
          <table className="table table-bordered table-striped">
            <thead>
              <tr>
                <th>Código</th>
                <th>Nombre</th>
                <th>Título</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {filterCarreras().map((carrera) => (
                <tr key={carrera.codigo}>
                  <td>{carrera.codigo}</td>
                  <td>{carrera.nombre}</td>
                  <td>{carrera.titulo}</td>
                  <td className="text-center">
                    <button
                      className="btn btn-success btn-sm m-2"
                      onClick={() => handleUpdate(carrera)}
                    >
                      Editar
                    </button>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => handleDelete(carrera.codigo)}
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
        title={!editing ? "Nueva Carrera" : "Actualizar Carrera"}
        showModal={showModal}
        toggleModal={toggleModal}
      >
        <div className="card-body custom-modal__content-body">
          <form id="insertForm" onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="codigo" className="form-label">
                Código
              </label>
              <input
                type="text"
                id="codigo"
                className="form-control"
                name="codigo"
                value={carrera.codigo}
                onChange={(e) =>
                  setCarrera({ ...carrera, codigo: e.target.value })
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
                value={carrera.nombre}
                onChange={(e) =>
                  setCarrera({ ...carrera, nombre: e.target.value })
                }
              />
            </div>
            <div className="mb-3">
              <label htmlFor="titulo" className="form-label">
                Titulo
              </label>
              <input
                type="text"
                id="titulo"
                className="form-control"
                name="titulo"
                value={carrera.titulo}
                onChange={(e) =>
                  setCarrera({ ...carrera, titulo: e.target.value })
                }
              />
            </div>
          </form>
        </div>
      </Modal>
    </>
  );
};

export default MantenimientoCarrerasPage;
