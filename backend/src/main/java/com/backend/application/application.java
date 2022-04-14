package com.backend.application;

import com.backend.services.*;

public class application {
    public static void main(String[] args) {

        //LA FECHA ESTA OCUPANDO ESTE FORMATO 1988-03-31

        AlumnoDAO alumnoDAO = AlumnoDAO.getInstance();
        System.out.println("Alumno");
//        alumnoDAO.insertarAlumno("11", "Pepe", "11", "pepe@gmail.com", "1988-03-31", "100");
//        alumnoDAO.modificarAlumno("11", "Pepe MODIFICADO", "11", "pepeMODIFICADO@gmail.com", "1988-03-31", "100");
//        alumnoDAO.eliminarAlumno("11");
        System.out.println(alumnoDAO.buscarAlumno("117520958"));
        System.out.println(alumnoDAO.listarAlumno());
        System.out.println();

        CarreraDAO carreraDAO = CarreraDAO.getInstance();
        System.out.println("Carrera");
//        carreraDAO.insertarCarrera("200", "Administracion", "Licenciatura");
//        carreraDAO.modificarCarrera("200", "Administracion viejaconfiable", "Licenciatura");
//        carreraDAO.eliminarCarrera("200");
        System.out.println(carreraDAO.buscarCarrera("100"));
        System.out.println(carreraDAO.listarCarrera());
        System.out.println();

        CicloDAO cicloDAO = CicloDAO.getInstance();
//        cicloDAO.insertarCiclo(2021, 2, "2021-02-07", "2021-05-07", 0);
//        cicloDAO.modificarCiclo(2021, 2, "2021-02-07", "2021-05-08", 0);
//        cicloDAO.eliminarCiclo(2021, 2);
        System.out.println("Ciclo");
        System.out.println(cicloDAO.buscarCiclo(2022, 1));
        System.out.println(cicloDAO.listarCiclo());
        System.out.println();

        CursoDAO cursoDAO = CursoDAO.getInstance();
//        cursoDAO.insertarCurso("666", "Moviles", 3, 8);
//        cursoDAO.modificarCurso("666", "Moviles ////", 3, 8);
//        cursoDAO.eliminarCurso("666");
        System.out.println("Curso");
        System.out.println(cursoDAO.buscarCurso("200"));
        System.out.println(cursoDAO.listarCurso());
        System.out.println();

        GrupoDAO grupoDAO = GrupoDAO.getInstance();
//        grupoDAO.insertarGrupo(2, "hora", "110350647", "200", 2022, 1);
//        grupoDAO.modificarGrupo(2, "10:00am 12:00pm", "110350647", "200", 2022, 1);
//        grupoDAO.eliminarGrupo(2, "200", 2022, 1);
        System.out.println("Grupo");
        System.out.println(grupoDAO.buscarGrupo(1, "110350647", "200", 2022, 1));
        System.out.println(grupoDAO.listarGrupo());
        System.out.println();

        PerfilDAO perfilDAO = PerfilDAO.getInstance();
//        perfilDAO.insertarPerfil(2, "a");
//        perfilDAO.modificarPerfil(2, "Perfil prueba");
//        perfilDAO.eliminarPerfil(2);
        System.out.println("Perfil");
        System.out.println(perfilDAO.buscarPerfil(1));
        System.out.println(perfilDAO.listarPerfil());
        System.out.println();

        ProfesorDAO profesorDAO = ProfesorDAO.getInstance();
//        profesorDAO.insertarProfesor("11", "Profe", "11", "profe@gmail.com");
//        profesorDAO.modificarProfesor("11", "Profe prueba", "11", "profe@gmail.com");
//        profesorDAO.eliminarProfesor("11");
        System.out.println("Profesor");
        System.out.println(profesorDAO.buscarProfesor("110350647"));
        System.out.println(profesorDAO.listarProfesor());
        System.out.println();

        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
//        usuarioDAO.insertarUsuario("11", "11", 1);
//        usuarioDAO.modificarUsuario("11", "pepe", 1);
//        usuarioDAO.eliminarUsuario("11");
        System.out.println("Usuario");
        System.out.println(usuarioDAO.buscarUsuario("admin"));
        System.out.println(usuarioDAO.listarUsuario());
        System.out.println();


        // TABLAS RELACIÓN




        EstudianteGrupoDAO estudianteGrupoDAO = EstudianteGrupoDAO.getInstance();
        System.out.println("Grupos de Estudiantes");
//        estudianteGrupoDAO.matriculaEstudiante("11", 1, "200", 2022, 1);
//        estudianteGrupoDAO.matriculaEstudiante("117520958", 2, "200", 2022, 1);
//        estudianteGrupoDAO.ingresaNota("117520958", 1, "200", 2022, 1, 100F);
//        estudianteGrupoDAO.desmatriculaEstudiante("117520958", 1, "200");
        System.out.println(estudianteGrupoDAO.buscarGruposEstudiante("117520958"));
        System.out.println(estudianteGrupoDAO.listarEstudiantesGrupo(1, "200", 2022, 1));
    }
}
