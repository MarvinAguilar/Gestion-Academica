import { useState } from "react";
import Modal from "../../../components/Modal/Modal";
import { useCursos } from "../../../hooks/useCursos";

function MantenimientoCursoPage() {
  const [showModal, setShowModal] = useState(false);
  const [editing, setEditing] = useState(false);
  const [query, setQuery] = useState("");
  const { filterCursos, insertarCurso, actualizarCurso, eliminarCurso } =
    useCursos({
      query,
    });
  const [curso, setCurso] = useState({
    codigo: "",
    nombre: "",
    creditos: "",
    horasSemanales: "",
    carrera: "",
  });

  const toggleModal = () => {
    setShowModal((e) => !e);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!editing) insertarCurso(curso);
    else actualizarCurso(curso);

    toggleModal();
  };

  const handleQueryChange = (e) => {
    setQuery(e.target.value);
  };

  const handleInsert = () => {
    setCurso({
      codigo: "",
      nombre: "",
      creditos: "",
      horasSemanales: "",
      carrera: "",
    });
    setEditing(false);
    toggleModal();
  };

  const handleUpdate = (curso) => {
    setCurso(curso);
    setEditing(true);
    toggleModal();
  };

  const handleDelete = (codigo) => {
    eliminarCurso(codigo);
  };

  return (
    <>
      <div className="card-body">
        <h5 className="card-title">Lista de Cursos</h5>

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
              Insertar Curso
            </button>
          </div>
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
              {filterCursos().map((curso) => (
                <tr key={curso.codigo}>
                  <td>{curso.codigo}</td>
                  <td>{curso.nombre}</td>
                  <td>{curso.creditos}</td>
                  <td>{curso.horasSemanales}</td>
                  <td>{curso.carrera}</td>
                  <td className="text-center">
                    <button
                      className="btn btn-success btn-sm mx-2"
                      onClick={() => handleUpdate(curso)}
                    >
                      Editar
                    </button>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => handleDelete(curso.codigo)}
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
        title={!editing ? "Nuevo Curso" : "Actualizar Curso"}
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
                value={curso.codigo}
                onChange={(e) => setCurso({ ...curso, codigo: e.target.value })}
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
                value={curso.nombre}
                onChange={(e) => setCurso({ ...curso, nombre: e.target.value })}
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
                value={curso.creditos}
                onChange={(e) =>
                  setCurso({ ...curso, creditos: e.target.value })
                }
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
                value={curso.horasSemanales}
                onChange={(e) =>
                  setCurso({ ...curso, horasSemanales: e.target.value })
                }
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
                value={curso.carrera}
                onChange={(e) =>
                  setCurso({ ...curso, carrera: e.target.value })
                }
                disabled={editing}
              />
            </div>
          </form>
        </div>
      </Modal>
    </>
  );
}

export default MantenimientoCursoPage;
