package com.backend.services;

public class CursosCarreraCRUD {

    protected static final String INSERTARCURSOCARRERA = "{call spInsertarCursosCarrera(?, ?, ?, ?)}";
    protected static final String ELIMINARCURSOCARRERA= "{call spEliminarCursosCarrera(?, ?)}";
    protected static final String MODIFICARCURSOCARRERA = "{call spModificarCursosCarrera(?, ?, ?, ?)}";
    protected static final String BUSCARCURSOSCARRERA = "{?=call buscarCursosCarrera(?, ?)}";
    protected static final String BUSCARCURSOCARRERA = "{?=call buscarCursoCarrera(?)}";
    protected static final String LISTARCURSOCARRERA = "{?=call listarCursosCarrera(?)}";
    protected static final String LISTARCURSOCARRERACICLO = "{? = call listarCursosCarreraCiclo(?, ?, ?)}";

}
