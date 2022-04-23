import { useState } from "react";
import Modal from "../../../components/Modal/Modal";
import { useCiclos } from "../../../hooks/useCiclos";

const MantenimientoCiclosPage = () => {
  const [showModal, setShowModal] = useState(false);
  const [editing, setEditing] = useState(false);
  const [query, setQuery] = useState("");
  const { filterCiclos, insertarCiclo, actualizarCiclo, eliminarCiclo } =
    useCiclos({
      query,
    });
  const date = new Date();
  const [ciclo, setCiclo] = useState({
    anno: date.getFullYear(),
    numero: "",
    fechaInicio: "",
    fechaFinal: "",
    estado: "",
  });

  const toggleModal = () => {
    setShowModal((e) => !e);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!editing) insertarCiclo(ciclo);
    else actualizarCiclo(ciclo);

    toggleModal();
  };

  const handleQueryChange = (e) => {
    setQuery(e.target.value);
  };

  const handleInsert = () => {
    setCiclo({
      anno: date.getFullYear(),
      numero: "",
      fechaInicio: "",
      fechaFinal: "",
      estado: "",
    });
    setEditing(false);
    toggleModal();
  };

  const handleUpdate = (ciclo) => {
    setCiclo(ciclo);
    setEditing(true);
    toggleModal();
  };

  const handleDelete = (anno, numero) => {
    eliminarCiclo(anno, numero);
  };

  return (
    <>
      <div className="card-body">
        <h5 className="card-title">Lista de Ciclos</h5>

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
              Insertar Ciclo
            </button>
          </div>
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
              {filterCiclos().map((ciclo, index) => (
                <tr key={index}>
                  <td>{ciclo.anno}</td>
                  <td>{ciclo.numero}</td>
                  <td>{ciclo.fechaFinal}</td>
                  <td>{ciclo.estado === 1 ? "Activo" : "Inactivo"}</td>
                  <td className="text-center">
                    <button
                      className="btn btn-success btn-sm mx-2"
                      onClick={() => handleUpdate(ciclo)}
                    >
                      Editar
                    </button>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => handleDelete(ciclo.anno, ciclo.numero)}
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
        title={!editing ? "Nueva Ciclo" : "Actualizar Ciclo"}
        showModal={showModal}
        toggleModal={toggleModal}
      >
        <div className="card-body custom-modal__content-body">
          <form id="insertForm" onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="anno" className="form-label">
                Año
              </label>
              <input
                type="text"
                id="anno"
                className="form-control"
                name="anno"
                value={ciclo.anno}
                onChange={(e) => setCiclo({ ...ciclo, anno: e.target.value })}
                disabled={editing}
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
                value={ciclo.numero}
                onChange={(e) => setCiclo({ ...ciclo, numero: e.target.value })}
                disabled={editing}
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
                value={ciclo.fechaInicio}
                onChange={(e) =>
                  setCiclo({ ...ciclo, fechaInicio: e.target.value })
                }
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
                value={ciclo.fechaFinal}
                onChange={(e) =>
                  setCiclo({ ...ciclo, fechaFinal: e.target.value })
                }
              />
            </div>
            <div className="mb-3">
              <label htmlFor="estado" className="form-label">
                Estado
              </label>
              <select
                id="estado"
                className="form-select"
                name="estado"
                value={ciclo.estado}
                onChange={(e) => setCiclo({ ...ciclo, estado: e.target.value })}
              >
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
