export const login = async (dataAuth, setUser) => {
  const response = await getAuthorization(dataAuth);

  if (response !== "No connection to server") {
    if (response.cedula !== "None") {
      updateUser(response, setUser);
      return "Authorized";
    }
    return "Unauthorized";
  }

  return response;
};

export const logout = (setUser) => {
  setUser({ cedula: "", perfil: "" });
};

const getAuthorization = async (dataAuth) => {
  const url = "http://localhost:8081/gestion-academica/login";

  const response = await fetch(url, {
    method: "POST",
    body: JSON.stringify({ cedula: dataAuth.cedula, clave: dataAuth.clave }),
  })
    .then((data) => data.json())
    .then((res) => res)
    .catch((e) => "No connection to server");

  return response;
};

const updateUser = (response, setUser) => {
  const perfil = response?.perfil;

  switch (perfil) {
    case 1:
      setUser({
        cedula: response.cedula,
        nombre: "Administrador",
        perfil: perfil,
      });
      break;
    case 2:
      setUser({
        cedula: response.cedula,
        nombre: "Matriculador",
        perfil: perfil,
      });
      break;
    case 3:
      setUser({
        cedula: response.cedula,
        nombre: response.nombre,
        perfil: perfil,
      });
      break;
    case 4:
      setUser({
        cedula: response.cedula,
        nombre: response.nombre,
        telefono: response.telefono,
        email: response.email,
        fechaNacimiento: response.fechaNacimiento,
        perfil: perfil,
      });
      break;
    default:
      break;
  }
};
