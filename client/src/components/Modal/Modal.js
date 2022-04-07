import "./Modal.css";

const Modal = ({ title, showModal, toggleModal, children }) => {
  if (showModal) {
    document.body.classList.add("active-modal");
  } else {
    document.body.classList.remove("active-modal");
  }

  return (
    <>
      {showModal && (
        <div className="custom-modal">
          <div className="overlay">
            <div className="card custom-modal__content">
              <div className="card-header py-3 d-flex justify-content-between">
                <h5 className="card-title">{title}</h5>
                <button className="btn-close" onClick={toggleModal}></button>
              </div>
              {children}
              <div className="card-footer">
                <div className="d-flex justify-content-end gap-3">
                  <button
                    type="button"
                    className="btn btn-secondary"
                    onClick={toggleModal}
                  >
                    Atrás
                  </button>
                  <button type="submit" className="btn btn-primary">
                    Guardar
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default Modal;
