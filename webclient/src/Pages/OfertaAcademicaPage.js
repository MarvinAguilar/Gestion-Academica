import { useState } from "react";
import Modal from "../components/Modal/Modal";
import { useCarreras } from "../hooks/useCarreras";
import { useCiclos } from "../hooks/useCiclos";

const OfertaAcademicaPage = () => {
  const { carreras } = useCarreras();
  const { ciclos } = useCiclos();
  const [filter, setFilter] = useState({
    carrera: "",
    ciclo: "",
    curso: "",
  });

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
              value={filter.carrera}
              onChange={(e) =>
                setFilter({ ...filter, carrera: e.target.value })
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
              value={filter.ciclo}
              onChange={(e) => setFilter({ ...filter, ciclo: e.target.value })}
            >
              <option value=""></option>
              {ciclos.map((ciclo, index) => (
                <option key={index} value={ciclo.anno}>
                  {(ciclo.numero === 1 && "I") || (ciclo.numero === 2 && "II")}{" "}
                  - Ciclo {ciclo.anno}
                </option>
              ))}
            </select>
          </div>
          <div className="col-sm-12 col-md-6 col-lg-4">
            <label htmlFor="selectCurso" className="form-label">
              Curso
            </label>
            <select
              id="selectCurso"
              className="form-select"
              name="curso"
              disabled={!filter.carrera || !filter.ciclo}
            >
              <option value=""></option>
            </select>
          </div>
        </div>

        <div className="mt-4">
          <h5 className="card-title">Lista de Grupos</h5>

          <div className="d-flex justify-content-end">
            <button
              type="button"
              className="btn btn-primary btn-sm"
              onClick={toggleModal}
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
              <tbody></tbody>
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
          <form onSubmit={handleSubmit}>
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
              <label htmlFor="horario" className="form-label">
                Horario
              </label>
              <input
                type="text"
                id="horario"
                className="form-control"
                name="horario"
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
              />
            </div>
            <div className="mb-3">
              <label htmlFor="curso" className="form-label">
                Curso
              </label>
              <select id="curso" class="form-select" name="curso">
                <option value=""></option>
                <option value="1">Fundamentos de Informática</option>
              </select>
            </div>
            <div className="mb-3">
              <label htmlFor="ciclo" className="form-label">
                Ciclo
              </label>
              <select id="ciclo" class="form-select" name="ciclo">
                <option value=""></option>
                <option value="1">II - Ciclo</option>
              </select>
            </div>
          </form>
        </div>
      </Modal>
    </>
  );
};

export default OfertaAcademicaPage;
