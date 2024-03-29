drop table estudiantesGrupo;
drop table cursosCarrera;
drop table grupo;
drop table profesor;
drop table alumno;
drop table usuario;
drop table ciclo;
drop table carrera;
drop table curso;
drop table perfil;

create table carrera(
    codigo varchar2(10),
    nombre varchar2(100),
    titulo varchar2(50),
    constraint pkCarrera primary key (codigo)
);

create table curso(
    codigo varchar2(10),
    nombre varchar2(100),
    creditos int,
    horasSemanales int,
    constraint pkCurso primary key (codigo)
);

create table profesor(
    cedula varchar2(10),
    nombre varchar2(50),
    telefono varchar2(30),
    email varchar2(50),
    constraint pkProfesor primary key (cedula)
);

create table alumno(
    cedula varchar2(10),
    nombre varchar2(50),
    telefono varchar2(30),
    email varchar2(50),
    fechaNacimiento date,
    carrera varchar2(10) not null,
    constraint pkAlumno primary key (cedula),
    constraint fkCarrera foreign key (carrera) 
        references carrera (codigo)
);

create table ciclo(
    anno int,
    numero int,
    fechaInicio date,
    fechaFinal date,
    estado varchar(1),
    constraint pkCiclo primary key (anno, numero),
    constraint checkCicloNumero check (
        numero between 1 and 2
    )
    constraint checkCicloEstado check (
        estado = 'A' or estado = 'I'
    )
);

create table grupo(
    numero int,
    horario varchar2(50),
    cedulaProfesor varchar2(10),
    codigoCurso varchar2(10),
    annoCiclo int,
    numeroCiclo int,
    constraint pkGrupo primary key (
        numero, 
        codigoCurso, 
        annoCiclo, 
        numeroCiclo
    ),
    constraint fkProfesor foreign key (cedulaProfesor) 
        references profesor (cedula),
    constraint fkCurso foreign key (codigoCurso) 
        references curso (codigo),
    constraint fkCiclo foreign key (annoCiclo, numeroCiclo) 
        references ciclo (anno, numero)
);

create table usuario(
    cedula varchar2(10),
    clave varchar2(30),
    perfil int,
    constraint pkUsuario primary key (cedula)
);

create table cursosCarrera(
    codigoCarrera varchar2(10),
    codigoCurso varchar2(10),
    annoCiclo int,
    numeroCiclo int,
    constraint pkCarreraCursos primary key (codigoCarrera, codigoCurso),
    constraint fkCicloCurso foreign key (annoCiclo, numeroCiclo)
        references ciclo (anno, numero),
    constraint fkCarreraCurso foreign key (codigoCarrera)
        references carrera (codigo),
    constraint fkCursoCiclo foreign key (codigoCurso)
        references curso (codigo)
);

create table estudiantesGrupo(
    cedulaEstudiante varchar2(10),
    numeroGrupo int,
    codigoCurso varchar2(10), 
    annoCiclo int, 
    numeroCiclo int, 
    nota float,
    constraint pkEstudiantesGrupo primary key (cedulaEstudiante, numeroGrupo, codigoCurso, annoCiclo, numeroCiclo),
    constraint fkEstudiante foreign key (cedulaEstudiante) 
        references alumno (cedula),
    constraint fkGrupo foreign key (numeroGrupo, codigoCurso, annoCiclo, numeroCiclo) 
        references grupo (numero, codigoCurso, annoCiclo, numeroCiclo)
);