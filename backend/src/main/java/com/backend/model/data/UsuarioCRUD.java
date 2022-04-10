package com.backend.model.data;

public class UsuarioCRUD {

    protected static final String INSERTARUSUARIO = "{call spInsertarUsuario(?, ?, ?)}";
    protected static final String MODIFICARUSUARIO = "{call spModificarUsuario(?, ?, ?)}";
    protected static final String ELIMINARUSUARIO = "{call spEliminarUsuario(?)}";
    protected static final String BUSCARUSUARIO = "{?=call buscarUsuario(?)}";
    protected static final String LISTARUSUARIO = "{?=call listarUsuario()}";

}
