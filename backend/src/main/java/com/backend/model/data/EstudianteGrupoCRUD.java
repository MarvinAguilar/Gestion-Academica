package com.backend.model.data;

public class EstudianteGrupoCRUD {

    protected static final String INSERTARESTUDIANTEGRUPO = "{call spInsertarEstudianteGrupo(?, ?, ?, ?, ?, ?)}";
    protected static final String MODIFICARESTUDIANTEGRUPO = "{call spModificarEstudianteGrupo(?, ?, ?, ?, ?, ?)}";
    protected static final String ELIMINARESTUDIANTEGRUPO= "{call spEliminarEstudianteGrupo(?, ?, ?)}";
    protected static final String BUSCARESTUDIANTEGRUPO = "{?=call buscarEstudianteGrupo(?, ?, ?, ?, ?)}";
    protected static final String LISTARESTUDIANTEGRUPO = "{?=call listarEstudianteGrupo(?, ?, ?, ?)}";

}
