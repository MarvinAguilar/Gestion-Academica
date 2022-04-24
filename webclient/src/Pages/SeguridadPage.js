import { useState } from "react";
import Modal from "../components/Modal/Modal";
import { useSeguridadPerfil } from "../hooks/useSeguridadPerfil";

const SeguridadPage = () => {
  const [showModal, setShowModal] = useState(false);
  const [editing, setEditing] = useState(false);
  const [usuario, setUsuario] = useState({
    cedula: "",
    clave: "",
    perfil: "",
  });
  const { usuarios, insertarUsuario, actualizarUsuario, eliminarUsuario } =
    useSeguridadPerfil();

  const toggleModal = () => {
    setShowModal((e) => !e);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!editing) insertarUsuario(usuario);
    else actualizarUsuario(usuario);

    toggleModal();
  };

  const handleInsert = () => {
    setUsuario({
      cedula: "",
      clave: "",
      perfil: "",
    });
    setEditing(false);
    toggleModal();
  };

  const handleUpdate = (usuario) => {
    setUsuario(usuario);
    setEditing(true);
    toggleModal();
  };

  const handleDelete = (cedula) => {
    eliminarUsuario(cedula);
  };

  return (
    <>
      <>
        <div className="container">
          <div className="mt-4">
            <h5 className="card-title">
              Mantenimiento de Administradores y Matriculadores
            </h5>

            <div className="d-flex justify-content-end">
              <button
                type="button"
                className="btn btn-primary btn-sm"
                onClick={handleInsert}
              >
                Agregar Usuario
              </button>
            </div>

            <div className="row d-flex justify-content-center">
              <div className="col-sm-12 col-md-8 col-lg-6">
                <div className="table-responsive">
                  <table className="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>Usuario</th>
                        <th>Perfil</th>
                        <th></th>
                      </tr>
                    </thead>
                    <tbody>
                      {usuarios.map((usuario) => (
                        <tr key={usuario.cedula}>
                          <td>{usuario.cedula}</td>
                          <td>
                            {usuario.perfil === 1
                              ? "Administrador"
                              : "Matriculador"}
                          </td>
                          <td className="text-center">
                            <button
                              className="btn btn-success btn-sm m-2"
                              onClick={() => handleUpdate(usuario)}
                            >
                              Editar
                            </button>
                            <button
                              className="btn btn-danger btn-sm"
                              onClick={() => handleDelete(usuario.cedula)}
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
            </div>
          </div>
        </div>

        <Modal
          title={"Nuevo Grupo"}
          showModal={showModal}
          toggleModal={toggleModal}
        >
          <div className="card-body custom-modal__content-body">
            <form id="insertForm" onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="cedula" className="form-label">
                  Usuario
                </label>
                <input
                  type="text"
                  id="cedula"
                  className="form-control"
                  name="cedula"
                  value={usuario.cedula}
                  onChange={(e) =>
                    setUsuario({ ...usuario, cedula: e.target.value })
                  }
                  disabled={editing}
                />
              </div>
              <div className="mb-3">
                <label htmlFor="clave" className="form-label">
                  Clave
                </label>
                <input
                  type="password"
                  id="clave"
                  className="form-control"
                  name="clave"
                  value={usuario.clave}
                  onChange={(e) =>
                    setUsuario({ ...usuario, clave: e.target.value })
                  }
                />
              </div>
              <div className="mb-3">
                <label htmlFor="perfil" className="form-label">
                  Perfil
                </label>
                <select
                  id="perfil"
                  className="form-select"
                  name="perfil"
                  value={usuario.perfil}
                  onChange={(e) =>
                    setUsuario({ ...usuario, perfil: e.target.value })
                  }
                >
                  <option value=""></option>
                  <option value="1">Administrador</option>
                  <option value="2">Matriculador</option>
                </select>
              </div>
            </form>
          </div>
        </Modal>
      </>
    </>
  );
};

export default SeguridadPage;
