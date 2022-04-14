package com.backend.services;

public class CicloCRUD {

    protected static final String INSERTARCICLO = "{call spInsertarCiclo(?, ?, ?, ?, ?)}";
    protected static final String MODIFICARCICLO = "{call spModificarCiclo(?, ?, ?, ?, ?)}";
    protected static final String ELIMINARCICLO = "{call spEliminarCiclo(?, ?)}";
    protected static final String BUSCARCICLO = "{?=call buscarCiclo(?, ?)}";
    protected static final String LISTARCICLO = "{?=call listarCiclo()}";

}
