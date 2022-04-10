import { useNavigate } from "react-router-dom";
import errorImage from "./assets/error-404.png";

const NotFoundPage = () => {
  const navigate = useNavigate();

  const handlerClick = () => {
    navigate(-1);
  };

  return (
    <>
      <div className="container">
        <div className="row">
          <div className="col">
            <div className="px-4 py-5 my-5 text-center">
              <img
                src={errorImage}
                alt="Error 404"
                className="d-block mx-auto mb-4"
                style={{ width: "20rem" }}
              />
              <h1 className="display-6 fw-bold">Oops! Error 404 Not Found</h1>
              <button className="btn btn-primary" onClick={handlerClick}>
                Regresar
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default NotFoundPage;
