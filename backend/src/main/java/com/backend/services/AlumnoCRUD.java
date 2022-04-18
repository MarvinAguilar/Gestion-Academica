package com.backend.services;

public class AlumnoCRUD {
    protected static final String INSERTARALUMNO = "{call spInsertarAlumno(?, ?, ?, ?, ?, ?)}";
    protected static final String MODIFICARALUMNO = "{call spModificarAlumno(?, ?, ?, ?, ?, ?)}";
    protected static final String ELIMINARALUMNO = "{call spEliminarAlumno(?)}";
    protected static final String BUSCARALUMNO = "{?=call buscarAlumno(?)}";
    protected static final String LISTARALUMNO = "{?=call listarAlumno()}";

}
