package com.backend.services;

public class CursoCRUD {
    protected static final String INSERTARCURSO = "{call spInsertarCurso(?, ?, ?, ?)}";
    protected static final String MODIFICARCURSO = "{call spModificarCurso(?, ?, ?, ?)}";
    protected static final String ELIMINARCURSO= "{call spEliminarCurso(?)}";
    protected static final String BUSCARCURSO = "{?=call buscarCurso(?)}";
    protected static final String LISTARCURSO = "{?=call listarCurso()}";
}
