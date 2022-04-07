package com.backend.model.data;

public class GrupoCRUD {

    protected static final String INSERTARGRUPO = "{call spInsertarGrupo(?, ?, ?, ?, ?, ?)}";
    protected static final String MODIFICARGRUPO = "{call spModificarGrupo(?, ?, ?, ?, ?, ?)}";
    protected static final String ELIMINARGRUPO= "{call spEliminarGrupo(?, ?, ?, ?)}";
    protected static final String BUSCARGRUPO = "{?=call buscarGrupo(?, ?, ?, ?)}";
    protected static final String LISTARGRUPO = "{?=call listarGrupo()}";

}
