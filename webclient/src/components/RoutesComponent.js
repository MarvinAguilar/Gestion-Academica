import { Route, Routes } from "react-router-dom";
import HomePage from "../Pages/HomePage/HomePage";
import OfertaAcademicaPage from "../Pages/OfertaAcademicaPage";
import MatriculaPage from "../Pages/MatriculaPage";
import RegistroNotasPage from "../Pages/RegistroNotasPage";
import HistorialAcademicoPage from "../Pages/HistorialAcademicoPage";
import NotFoundPage from "../Pages/NotFoundPage/NotFoundPage";
import MantenimientoPage from "../Pages/MantenimientoPage/MantenimientoPage";
import MantenimientoCarreraPage from "../Pages/MantenimientoPage/Pages/MantenimientoCarreraPage";
import MantenimientoCursoPage from "../Pages/MantenimientoPage/Pages/MantenimientoCursoPage";
import MantenimientoProfesorPage from "../Pages/MantenimientoPage/Pages/MantenimientoProfesorPage";
import MantenimientoAlumnoPage from "../Pages/MantenimientoPage/Pages/MantenimientoAlumnoPage";
import MantenimientoCicloPage from "../Pages/MantenimientoPage/Pages/MantenimientoCicloPage";

const RoutesComponent = () => {
  return (
    <>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/mantenimiento" element={<MantenimientoPage />}>
          <Route path="carreras" element={<MantenimientoCarreraPage />} />
          <Route path="cursos" element={<MantenimientoCursoPage />} />
          <Route path="profesores" element={<MantenimientoProfesorPage />} />
          <Route path="alumnos" element={<MantenimientoAlumnoPage />} />
          <Route path="ciclos" element={<MantenimientoCicloPage />} />
        </Route>
        <Route path="/oferta-academica" element={<OfertaAcademicaPage />} />
        <Route path="/matricula" element={<MatriculaPage />} />
        <Route path="/registro-notas" element={<RegistroNotasPage />} />
        <Route
          path="/historial-academico"
          element={<HistorialAcademicoPage />}
        />
        <Route path="*" element={<NotFoundPage />} />
      </Routes>
    </>
  );
};

export default RoutesComponent;