package com.backend.model.data;

public class PerfilCRUD {

    protected static final String INSERTARPERFIL = "{call spInsertarPerfil(?, ?)}";
    protected static final String MODIFICARPERFIL = "{call spModificarPerfil(?, ?)}";
    protected static final String ELIMINARPERFIL = "{call spEliminarPerfil(?)}";
    protected static final String BUSCARPERFIL = "{?=call buscarPerfil(?)}";
    protected static final String LISTARPERFIL = "{?=call listarPerfil()}";

}
