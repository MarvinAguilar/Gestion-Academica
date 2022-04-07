package com.backend.model.data;

public class ProfesorCRUD {
    protected static final String INSERTARPROFESOR = "{call spInsertarProfesor(?, ?, ?, ?)}";
    protected static final String MODIFICARPROFESOR = "{call spModificarProfesor(?, ?, ?, ?)}";
    protected static final String ELIMINARPROFESOR = "{call spEliminarProfesor(?)}";
    protected static final String BUSCARPROFESOR = "{?=call buscarProfesor(?)}";
    protected static final String LISTARPROFESOR = "{?=call listarProfesor()}";
}
