package com.backend.model.data;

public class CursosCarreraCRUD {

    protected static final String INSERTARCURSOCARRERA = "{call spInsertarCursosCarrera(?, ?, ?, ?)}";
    protected static final String ELIMINARCURSOCARRERA= "{call spEliminarCursosCarrera(?, ?, ?, ?)}";
    protected static final String BUSCARCURSOCARRERA = "{?=call buscarCursosCarrera(?, ?, ?, ?)}";
    protected static final String LISTARCURSOCARRERA = "{?=call listarCursosCarrera(?)}";

}
