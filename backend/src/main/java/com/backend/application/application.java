package com.backend.application;

import com.backend.model.data.*;
import com.backend.model.entity.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class application {

//    static class Student {
//        String name;
//        String age;
//        public Student(String name, String age) {
//            this.name = name;
//            this.age = age;
//        }
//
//        @Override
//        public String toString() {
//            return "Student{" +
//                    "name='" + name + '\'' +
//                    ", age='" + age + '\'' +
//                    '}';
//        }
//    }

    public static void main(String[] args) {

//        Gson gson = new Gson();
//
//        String jsonText = "{\"name\":\"Arman\",\"age\":\"24\"}";
//        Student student1 = gson.fromJson(jsonText, Student.class);
//        System.out.println(student1);
//
//        Student student2 = new Student("Arman", "24");
//        String json = gson.toJson(student2);
//        System.out.println(json);


//        ProfesorDAO p = ProfesorDAO.getInstance();
//        p.insertarProfesor(new Profesor("6667", "Manuel", "487878", "manuel@gmail.com"));

//        p.modificarProfesor(new Profesor("6667", "Manuel MODIFICADO", "487878", "manuel@gmail.com"));

//        p.eliminarProfesor("6667");

//        System.out.println(p.buscarProfesor("110350647"));

//        List<Profesor> listaProfesores = p.listarProfesor();
//        for (Profesor profesor : listaProfesores) {
//            System.out.println(profesor);
//        }

//        PerfilDAO p = PerfilDAO.getInstance();
//        p.insertarPerfil(new Perfil(2, "Estudiante"));

//        p.modificarPerfil(new Perfil(2, "EstudianteESCLAVO"));

//        p.eliminarPerfil(2);

//        System.out.println(p.buscarPerfil(1));

//         List<Perfil> listaPerfiles = p.listarPerfil();
//         for (Perfil perfil : listaPerfiles) {
//             System.out.println(perfil);
//         }

//        CarreraDAO ca = CarreraDAO.getInstance();
//        ca.insertarCarrera(new Carrera("EF00", "Administración", "BACHI"));
//        c.modificarCarrera(new Carrera("EF00", "Administración de Empresas", "BACHILLERATO"));

//        c.eliminarCarrera("EF00");

//        System.out.println(c.buscarCarrera("EF00"));
//
//        List<Carrera> carreras = c.listarCarrera();
//        for (Carrera carrera: carreras) {
//            System.out.println(carrera);
//        }

//        UsuarioDAO p = UsuarioDAO.getInstance();
//        p.insertarUsuario(new Usuario("12345", "Ricardo milos", 2));

//        p.modificarUsuario(new Usuario("12345", "Ricardo arjona", 2));

//        p.eliminarUsuario("12345");

//        System.out.println(p.buscarUsuario("admin"));

//         List<Usuario> listaUsuario = p.listarUsuario();
//         for (Usuario usuario : listaUsuario) {
//             System.out.println(usuario);
//         }

//        CursoDAO cu = CursoDAO.getInstance();
//        cu.insertarCurso(new Curso("EF08", "Admin 1", 4, 2));
//        cu.insertarCurso(new Curso("EF06", "Conta 1", 4, 2));

//        c.modificarCurso(new Curso("AAA", "progra 3", 4, 2));

//        c.eliminarCurso("AAA");

//        System.out.println(c.buscarCurso("AAA"));

//        List<Curso> cursos = c.listarCurso();
 //       for (Curso curso : cursos) {
//            System.out.println(curso);
//        }

//        CursosCarreraDAO c = CursosCarreraDAO.getInstance();
//        c.insertarCursoCarrera("EF00", "EF08", 2022, 1);
//       c.insertarCursoCarrera("EF00", "EF06", 2022, 1);

//        c.modificarCurso(new Curso("AAA", "progra 3", 4, 2));

//        c.eliminarCurso("AAA");

//        System.out.println(c.buscarCurso("AAA"));

//        List<Curso> cursos = c.listarCurso("EF00");
//        for (Curso curso : cursos) {
//            System.out.println(curso);
//        }

        EstudianteGrupoDAO estg = EstudianteGrupoDAO.getInstance();
//        estg.insertarEstudianteGrupo("117520958", "1", "200", 2022, 1, (float) 0);

//        c.modificarCurso(new Curso("AAA", "progra 3", 4, 2));

//        c.eliminarCurso("AAA");

//        System.out.println(c.buscarCurso("AAA"));

        List<Alumno> alumnos = estg.listarEstudianteGrupo("1", "200", 2022, 1);
        for (Alumno alumno : alumnos) {
            System.out.println(alumno);
        }

        // QUEDA PENDIENTE PROBAR CICLO
    }
}
