package com.backend.model.data;

public class CarreraCRUD {
    protected static final String INSERTARCARRERA = "{call spInsertarCarrera(?, ?, ?)}";
    protected static final String MODIFICARCARRERA = "{call spModificarCarrera(?, ?, ?)}";
    protected static final String ELIMINARCARRERA= "{call spEliminarCarrera(?)}";
    protected static final String BUSCARCARRERA = "{?=call buscarCarrera(?)}";
    protected static final String LISTARCARRERA = "{?=call listarCarrera()}";
}
