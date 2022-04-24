import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Modal from "../../../components/Modal/Modal";
import { useAlumnos } from "../../../hooks/useAlumnos";
import { useCarreras } from "../../../hooks/useCarreras";
import useGlobalContext from "../../../hooks/useGlobalContext";

const MantenimientoAlumnosPage = () => {
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);
  const [editing, setEditing] = useState(false);
  const [query, setQuery] = useState("");
  const { filterAlumnos, insertarAlumno, actualizarAlumno, eliminarAlumno } =
    useAlumnos({
      query,
    });
  const [alumno, setAlumno] = useState({
    cedula: "",
    nombre: "",
    telefono: "",
    email: "",
    fechaNacimiento: "",
    codigoCarrera: "",
    nombreCarrera: "",
  });
  const { carreras } = useCarreras();
  const { setHistorialEstudiante, formatDate } = useGlobalContext();

  const toggleModal = () => {
    setShowModal((e) => !e);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!editing) insertarAlumno(alumno);
    else actualizarAlumno(alumno);

    toggleModal();
  };

  const handleQueryChange = (e) => {
    setQuery(e.target.value);
  };

  const handleInsert = () => {
    setAlumno({
      cedula: "",
      nombre: "",
      telefono: "",
      email: "",
      fechaNacimiento: "",
      codigoCarrera: "",
      nombreCarrera: "",
    });
    setEditing(false);
    toggleModal();
  };

  const handleUpdate = (alumno) => {
    setAlumno(alumno);
    setEditing(true);
    toggleModal();
  };

  const handleDelete = (cedula) => {
    eliminarAlumno(cedula);
  };

  const handleVerHistorial = (alumno) => {
    setHistorialEstudiante(alumno);
    navigate("/historial-academico");
  };

  return (
    <>
      <div className="card-body">
        <h5 className="card-title">Lista de Alumnos</h5>

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
              Insertar Alumno
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
                <th>Fecha de Nacimiento</th>
                <th>Carrera</th>
                <th>Historial Académico</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {filterAlumnos().map((alumno) => (
                <tr key={alumno.cedula}>
                  <td>{alumno.cedula}</td>
                  <td>{alumno.nombre}</td>
                  <td>{alumno.telefono}</td>
                  <td>{alumno.email}</td>
                  <td>{formatDate(alumno.fechaNacimiento)}</td>
                  <td>{alumno.nombreCarrera}</td>
                  <td className="text-center">
                    <button
                      className="btn btn-danger btn-sm m-4"
                      onClick={() => handleVerHistorial(alumno)}
                    >
                      Ver Historial
                    </button>
                  </td>
                  <td className="text-center">
                    <button
                      className="btn btn-success btn-sm m-2"
                      onClick={() => handleUpdate(alumno)}
                    >
                      Editar
                    </button>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => handleDelete(alumno.cedula)}
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
        title={!editing ? "Nuevo Alumno" : "Actualizar Alumno"}
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
                value={alumno.cedula}
                onChange={(e) =>
                  setAlumno({ ...alumno, cedula: e.target.value })
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
                value={alumno.nombre}
                onChange={(e) =>
                  setAlumno({ ...alumno, nombre: e.target.value })
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
                value={alumno.telefono}
                onChange={(e) =>
                  setAlumno({ ...alumno, telefono: e.target.value })
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
                value={alumno.email}
                onChange={(e) =>
                  setAlumno({ ...alumno, email: e.target.value })
                }
              />
            </div>
            <div className="mb-3">
              <label htmlFor="fechaNacimiento" className="form-label">
                Fecha de Nacimiento
              </label>
              <input
                type="date"
                id="fechaNacimiento"
                className="form-control"
                name="fechaNacimiento"
                value={alumno.fechaNacimiento}
                onChange={(e) =>
                  setAlumno({ ...alumno, fechaNacimiento: e.target.value })
                }
              />
            </div>
            <div className="mb-3">
              <label htmlFor="carrera" className="form-label">
                Carrera
              </label>
              <select
                id="carrera"
                className="form-select"
                name="carrera"
                value={alumno.codigoCarrera}
                onChange={(e) => {
                  let index = e.target.selectedIndex;
                  setAlumno({
                    ...alumno,
                    codigoCarrera: e.target.value,
                    nombreCarrera: e.target.options[index].text,
                  });
                }}
              >
                <option value=""></option>
                {carreras.map((carrera) => (
                  <option key={carrera.codigo} value={carrera.codigo}>
                    {carrera.nombre}
                  </option>
                ))}
              </select>
            </div>
          </form>
        </div>
      </Modal>
    </>
  );
};

export default MantenimientoAlumnosPage;
