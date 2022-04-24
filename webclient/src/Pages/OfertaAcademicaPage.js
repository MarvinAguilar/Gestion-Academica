import { useState } from "react";
import Modal from "../components/Modal/Modal";
import { useCarreras } from "../hooks/useCarreras";
import { useCiclos } from "../hooks/useCiclos";
import { useCursosCarrera } from "../hooks/useCursosCarrera";
import { useGrupos } from "../hooks/useGrupos";

const OfertaAcademicaPage = () => {
  const [showModal, setShowModal] = useState(false);
  const [editing, setEditing] = useState(false);
  const { carreras } = useCarreras();
  const { ciclos } = useCiclos();
  const [query, setQuery] = useState({
    codigoCarrera: "",
    codigoCurso: "",
    annoCiclo: "",
    numeroCiclo: "",
  });
  const [grupo, setGrupo] = useState({
    numero: "",
    horario: "",
    cedulaProfesor: "",
    codigoCurso: "",
    annoCiclo: "",
    numeroCiclo: "",
  });
  const { cursos } = useCursosCarrera({ query });
  const { grupos, insertarGrupo, actualizarGrupo } = useGrupos({ query });

  const toggleModal = () => {
    setShowModal((e) => !e);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!editing) insertarGrupo(grupo);
    else actualizarGrupo(grupo);

    insertarGrupo(grupo);

    toggleModal();
  };

  const handleInsert = () => {
    setGrupo({ ...grupo, numero: "", horario: "", cedulaProfesor: "" });
    setEditing(false);
    toggleModal();
  };

  const handleUpdate = (grupo) => {
    setGrupo(grupo);
    setEditing(true);
    toggleModal();
  };

  const handleChangeCiclo = (e) => {
    const ciclo = e.target.value.split("-");
    const [annoCiclo, numeroCiclo] = ciclo;
    setQuery({
      ...query,
      annoCiclo: annoCiclo,
      numeroCiclo: numeroCiclo,
    });
    setGrupo({ ...grupo, annoCiclo: annoCiclo, numeroCiclo: numeroCiclo });
  };

  const handleChangeCurso = (e) => {
    setQuery({
      ...query,
      codigoCurso: e.target.value,
    });
    setGrupo({ ...grupo, codigoCurso: e.target.value });
  };

  return (
    <>
      <div className="container">
        <div className="row gap-2">
          <div className="col-sm-12 col-md-6 col-lg-4">
            <label htmlFor="selectCarrera" className="form-label">
              Carrera
            </label>
            <select
              id="selectCarrera"
              className="form-select"
              name="carrera"
              value={query.codigoCarrera}
              onChange={(e) =>
                setQuery({ ...query, codigoCarrera: e.target.value })
              }
            >
              <option value=""></option>
              {carreras.map((carrera) => (
                <option key={carrera.codigo} value={carrera.codigo}>
                  {carrera.codigo} - {carrera.nombre}
                </option>
              ))}
            </select>
          </div>
          <div className="col-sm-12 col-md-4 col-lg-2">
            <label htmlFor="selectCiclo" className="form-label">
              Ciclo
            </label>
            <select
              id="selectCiclo"
              className="form-select"
              name="ciclo"
              value={`${query.annoCiclo}-${query.numeroCiclo}`}
              onChange={handleChangeCiclo}
            >
              <option value=""></option>
              {ciclos.map((ciclo) => (
                <option
                  key={`${ciclo.anno}-${ciclo.numero}`}
                  value={`${ciclo.anno}-${ciclo.numero}`}
                >
                  {(ciclo.numero === 1 && "I") || (ciclo.numero === 2 && "II")}{" "}
                  - Ciclo {ciclo.anno}
                </option>
              ))}
            </select>
          </div>
          <div className="col-sm-12 col-md-6 col-lg-4">
            <label htmlFor="curso" className="form-label">
              Curso
            </label>
            <select
              id="curso"
              className="form-select"
              name="curso"
              onChange={handleChangeCurso}
              disabled={
                !query.codigoCarrera || (!query.annoCiclo && !query.numeroCiclo)
              }
            >
              <option value=""></option>
              {cursos.map((curso) => (
                <option key={curso.codigoCurso} value={curso.codigoCurso}>
                  {curso.nombreCurso}
                </option>
              ))}
            </select>
          </div>
        </div>

        <div className="mt-4">
          <h5 className="card-title">Lista de Grupos</h5>

          <div className="d-flex justify-content-end">
            <button
              type="button"
              className="btn btn-primary btn-sm"
              onClick={handleInsert}
              disabled={
                !query.codigoCarrera ||
                !query.codigoCurso ||
                (!query.annoCiclo && !query.numeroCiclo)
              }
            >
              Agregar Grupo
            </button>
          </div>

          <div className="table-responsive mt-3">
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>Número</th>
                  <th>Horario</th>
                  <th>Profesor</th>
                  <th>Curso</th>
                  <th>Ciclo</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {grupos.map((grupo) => (
                  <tr key={grupo.numero}>
                    <td>{grupo.numero}</td>
                    <td>{grupo.horario}</td>
                    <td>{grupo.nombreProfesor}</td>
                    <td>{grupo.nombreCurso}</td>
                    <td>
                      {(grupo.numeroCiclo === 1 && "I") ||
                        (grupo.numeroCiclo === 2 && "II")}{" "}
                      - Ciclo {grupo.annoCiclono}
                    </td>
                    <td className="text-center">
                      <button
                        className="btn btn-success btn-sm m-2"
                        onClick={() => handleUpdate(grupo)}
                      >
                        Editar
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
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
              <label htmlFor="numero" className="form-label">
                Número
              </label>
              <input
                type="text"
                id="numero"
                className="form-control"
                name="numero"
                value={grupo.numero}
                onChange={(e) => setGrupo({ ...grupo, numero: e.target.value })}
                disabled={editing}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="horario" className="form-label">
                Horario
              </label>
              <input
                type="text"
                id="horario"
                className="form-control"
                name="horario"
                value={grupo.horario}
                onChange={(e) =>
                  setGrupo({ ...grupo, horario: e.target.value })
                }
              />
            </div>
            <div className="mb-3">
              <label htmlFor="profesor" className="form-label">
                Profesor
              </label>
              <input
                type="text"
                id="profesor"
                className="form-control"
                name="profesor"
                value={grupo.cedulaProfesor}
                onChange={(e) =>
                  setGrupo({ ...grupo, cedulaProfesor: e.target.value })
                }
              />
            </div>
          </form>
        </div>
      </Modal>
    </>
  );
};

export default OfertaAcademicaPage;
