import useGlobalContext from "../hooks/useGlobalContext";

const UserNavbar = () => {
  const { user } = useGlobalContext();

  const getProfileName = (profile) => {
    switch (profile) {
      case 1:
        return "Administrador";
      case 2:
        return "Matriculador";
      case 3:
        return "Profesor";
      case 4:
        return "Estudiante";
      default:
        return "";
    }
  };

  return (
    <>
      <section>
        <div className="container mb-3">
          <div className="bg-light border  border-1 rounded p-2">
            <div className="d-flex justify-content-end gap-3">
              <div>
                <span className="fw-bold">Usuario: </span>
                <span>{user.nombre}</span>
              </div>
              <div>
                <span className="fw-bold">Perfil: </span>
                <span>{getProfileName(user.perfil)}</span>
              </div>
            </div>
          </div>
        </div>
      </section>
    </>
  );
};

export default UserNavbar;
