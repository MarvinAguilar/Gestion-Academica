--drop table estudiantesGrupo;
--drop table cursosCarrera;
--drop table grupo;
--drop table profesor;
--drop table alumno;
--drop table usuario;
--drop table ciclo;
--drop table carrera;
--drop table curso;
--drop table perfil;


--=====================TABLA CARRERA=====================
create table carrera(
    codigo varchar(10),
    nombre varchar(100),
    titulo varchar(50),
    constraint pkCarrera primary key (codigo)
);

--CURSOR - TABLA CARRERA

create or replace package types
as
    type refCursor is ref cursor;
end;
/

--PROCEDIMIENTOS ALMACENADOS - TABLA CARRERA

--INSERT
create or replace procedure spInsertarCarrera(
    in_codigo in carrera.codigo%type, 
    in_nombre in carrera.nombre%type, 
    in_titulo in carrera.titulo%type
)
as
begin
    insert into carrera values(in_codigo, in_nombre, in_titulo);
end;
/

--UPDATE
create or replace procedure spModificarCarrera(
    in_codigo in carrera.codigo%type, 
    in_nombre in carrera.nombre%type, 
    in_titulo in carrera.titulo%type
)
as
begin
    update carrera set nombre = in_nombre, titulo = in_titulo 
        where codigo = in_codigo;
end;
/
--DELETE
create or replace procedure spEliminarCarrera(
    in_codigo in carrera.codigo%type 
)
as
begin
    delete from carrera where codigo = in_codigo;
end;
/

--FUNCIONES - TABLA CARRERA

create or replace function buscarCarrera(
    in_codigo in carrera.codigo%type
)
return types.refCursor
as 
    carreraCursor types.refCursor;
begin 
    open carreraCursor for
        select * from carrera where codigo = in_codigo;
    return carreraCursor;
end;
/

create or replace function listarCarrera
return types.refCursor
as
    carreraCursor types.refCursor;
begin
    open carreraCursor for
        select * from carrera;
    return carreraCursor;
end;
/

--=====================TABLA CURSO=====================
create table curso(
    codigo varchar(10),
    nombre varchar(100),
    creditos int,
    horasSemanales int,
    constraint pkCurso primary key (codigo)
);

--CURSOR - TABLA CURSO

create or replace package types
as
    type refCursor is ref cursor;
end;
/

--PROCEDIMIENTOS ALMACENADOS - TABLA CURSO

--INSERT
create or replace procedure spInsertarCurso(
    codigo in curso.codigo%type, 
    nombre in curso.nombre%type, 
    creditos in curso.creditos%type,
    horasSemanales in curso.horasSemanales%type
)
as
begin
    insert into curso values(codigo, nombre, creditos, horasSemanales);
end;
/

--UPDATE
create or replace procedure spModificarCurso(
    in_codigo in curso.codigo%type,
    in_nombre in curso.nombre%type,
    in_creditos in curso.creditos%type,
    in_horasSemanales in curso.horasSemanales%type
)
as
begin
    update curso set nombre = in_nombre, creditos = in_creditos, horasSemanales = in_horasSemanales
        where codigo = in_codigo;
end;
/
--DELETE
create or replace procedure spEliminarCurso(
    in_codigo in curso.codigo%type 
)
as
begin
    delete from curso where codigo = in_codigo;
end;
/

--FUNCIONES - TABLA CURSO

create or replace function buscarCurso(
    in_codigo in curso.codigo%type
)
return types.refCursor
as 
    cursoCursor types.refCursor;
begin 
    open cursoCursor for
        select * from curso where codigo = in_codigo;
    return cursoCursor;
end;
/

create or replace function listarCurso
return types.refCursor
as
    cursoCursor types.refCursor;
begin
    open cursoCursor for
        select * from curso;
    return cursoCursor;
end;
/


--=====================TABLA PROFESOR=====================
create table profesor(
    cedula varchar(10),
    nombre varchar(50),
    telefono varchar(30),
    email varchar(50),
    constraint pkProfesor primary key (cedula)
);

--CURSOR - TABLA PROFESOR

create or replace package types
as
    type refCursor is ref cursor;
end;
/

--PROCEDIMIENTOS ALMACENADOS - TABLA PROFESOR

--INSERT
create or replace procedure spInsertarProfesor(
    in_cedula in profesor.cedula%type, 
    in_nombre in profesor.nombre%type, 
    in_telefono in profesor.telefono%type,
    in_email in profesor.email%type
)
as
begin
    insert into profesor values(in_cedula, in_nombre, in_telefono, in_email);
	commit;
end;
/

--UPDATE
create or replace procedure spModificarProfesor(
    in_cedula in profesor.cedula%type,
    in_nombre in profesor.nombre%type,
    in_telefono in profesor.telefono%type,
    in_email in profesor.email%type
)
as
begin
    update profesor set nombre = in_nombre, telefono = in_telefono, email = in_email
        where cedula = in_cedula;
	commit;
end;
/
--DELETE
create or replace procedure spEliminarProfesor(
    in_cedula in profesor.cedula%type 
)
as
begin
    delete from profesor where cedula = in_cedula;
	commit;
end;
/

--FUNCIONES - TABLA PROFESOR

create or replace function buscarProfesor(
	in_cedula in profesor.cedula%type
)
return types.refCursor
as
    profesorCursor types.refCursor;
begin
    open profesorCursor for
        select * from profesor where cedula = in_cedula;
    return profesorCursor;
end;
/

create or replace function listarProfesor
return types.refCursor
as
    profesorCursor types.refCursor;
begin
    open profesorCursor for
        select * from profesor;
    return profesorCursor;
end;
/

--=====================TABLA ALUMNO=====================
create table alumno(
    cedula varchar(10),
    nombre varchar(50),
    telefono varchar(30),
    email varchar(50),
    fechaNacimiento date,
    carrera varchar(10) not null,
    constraint pkAlumno primary key (cedula),
    constraint fkCarrera foreign key (carrera) 
        references carrera (codigo)
);

--CURSOR - TABLA ALUMNO

create or replace package types
as
    type refCursor is ref cursor;
end;
/

--PROCEDIMIENTOS ALMACENADOS - TABLA ALUMNO

--INSERT
create or replace procedure spInsertarAlumno(
    cedula in alumno.cedula%type, 
    nombre in alumno.nombre%type, 
    telefono in alumno.telefono%type,
    email in alumno.email%type,
    fechaNacimiento in alumno.fechaNacimiento%type,
    carrera in alumno.carrera%type
)
as
begin
    insert into alumno values(cedula, nombre, telefono, email, fechaNacimiento, carrera);
	commit;
end;
/

--UPDATE
create or replace procedure spModificarAlumno(
    in_cedula in alumno.cedula%type, 
    in_nombre in alumno.nombre%type, 
    in_telefono in alumno.telefono%type,
    in_email in alumno.email%type,
    in_fechaNacimiento in alumno.fechaNacimiento%type,
    in_carrera in alumno.carrera%type
)
as
begin
    update alumno set nombre = in_nombre, telefono = in_telefono, email = in_email, carrera = in_carrera, fechaNacimiento = in_fechaNacimiento
        where cedula = in_cedula;
	commit;
end;
/
--DELETE
create or replace procedure spEliminarAlumno(
    in_cedula in alumno.cedula%type 
)
as
begin
    delete from alumno where cedula = in_cedula;
	commit;
end;
/

--FUNCIONES - TABLA ALUMNO

create or replace function buscarAlumno(
    in_cedula in alumno.cedula%type
)
return types.refCursor
as 
    alumnoCursor types.refCursor;
begin 
    open alumnoCursor for
        select * from alumno where cedula = in_cedula;
    return alumnoCursor;
end;
/

create or replace function listarAlumno
return types.refCursor
as
    alumnoCursor types.refCursor;
begin
    open alumnoCursor for
        select * from alumno;
    return alumnoCursor;
end;
/

--=====================TABLA CICLO=====================
create table ciclo(
    anno int,
    numero int,
    fechaInicio date,
    fechaFinal date,
    estado int,
    constraint pkCiclo primary key (anno, numero),
    constraint checkCiclo check (
        numero between 1 and 2
    )
);

--CURSOR - TABLA CICLO

create or replace package types
as
    type refCursor is ref cursor;
end;
/

--PROCEDIMIENTOS ALMACENADOS - TABLA CICLO

--INSERT
create or replace procedure spInsertarCiclo(
    anno in ciclo.anno%type, 
    numero in ciclo.numero%type, 
    fechaInicio in ciclo.fechaInicio%type,
    fechaFinal in ciclo.fechaFinal%type,
    estado in ciclo.estado%type
)
as
begin
    insert into ciclo values(anno, numero, fechaInicio, fechaFinal, estado);
	commit;
end;
/

--UPDATE
create or replace procedure spModificarCiclo(
    in_anno in ciclo.anno%type, 
    in_numero in ciclo.numero%type, 
    in_fechaInicio in ciclo.fechaInicio%type,
    in_fechaFinal in ciclo.fechaFinal%type,
    in_estado in ciclo.estado%type
)
as
begin
    update ciclo set fechaInicio = in_fechaInicio, fechaFinal = in_fechaFinal, estado = in_estado
        where anno = in_anno and numero = in_numero;
	commit;
end;
/
--DELETE
create or replace procedure spEliminarCiclo(
    in_anno in ciclo.anno%type,
    in_numero in ciclo.numero%type
)
as
begin
    delete from ciclo where anno = in_anno and numero = in_numero;
    commit;
end;
/

--FUNCIONES - TABLA CICLO

create or replace function buscarCiclo(
    in_anno in ciclo.anno%type,
    in_numero in ciclo.numero%type
)
return types.refCursor
as 
    cicloCursor types.refCursor;
begin 
    open cicloCursor for
        select * from ciclo where anno = in_anno and numero = in_numero;
    return cicloCursor;
end;
/

create or replace function listarCiclo
return types.refCursor
as
    cicloCursor types.refCursor;
begin
    open cicloCursor for
        select * from ciclo;
    return cicloCursor;
end;
/

--=====================TABLA GRUPO=====================
create table grupo(
    numero int,
    horario varchar(50),
    cedulaProfesor varchar(10),
    codigoCurso varchar(10),
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

--CURSOR - TABLA GRUPO

create or replace package types
as
    type refCursor is ref cursor;
end;
/

--PROCEDIMIENTOS ALMACENADOS - TABLA GRUPO

--INSERT
create or replace procedure spInsertarGrupo(
    numero in grupo.numero%type, 
    horario in grupo.horario%type, 
    cedulaProfesor in grupo.cedulaProfesor%type,
    codigoCurso in grupo.codigoCurso%type,
    annoCiclo in grupo.annoCiclo%type,
    numeroCiclo in grupo.numeroCiclo%type
)
as
begin
    insert into grupo values(numero, horario, cedulaProfesor, codigoCurso, annoCiclo, numeroCiclo);
	commit;
end;
/

--UPDATE
create or replace procedure spModificarGrupo(
    in_numero in grupo.numero%type, 
    in_horario in grupo.horario%type, 
    in_cedulaProfesor in grupo.cedulaProfesor%type,
    in_codigoCurso in grupo.codigoCurso%type,
    in_annoCiclo in grupo.annoCiclo%type,
    in_numeroCiclo in grupo.numeroCiclo%type
)
as
begin
    update grupo set horario = in_horario, cedulaProfesor = in_cedulaProfesor
        where codigoCurso = in_codigoCurso and numero = in_numero and annoCiclo = in_annoCiclo and numeroCiclo = in_numeroCiclo;
	commit;
end;
/
--DELETE
create or replace procedure spEliminarGrupo(
    in_numero in grupo.numero%type, 
    in_codigoCurso in grupo.codigoCurso%type,
    in_annoCiclo in grupo.annoCiclo%type,
    in_numeroCiclo in grupo.numeroCiclo%type
)
as
begin
    delete from grupo where codigoCurso = in_codigoCurso and numero = in_numero and annoCiclo = in_annoCiclo and numeroCiclo = in_numeroCiclo;
	commit;
end;
/

--FUNCIONES - TABLA GRUPO

create or replace function buscarGrupo(
    in_numero in grupo.numero%type, 
    in_codigoCurso in grupo.codigoCurso%type,
    in_annoCiclo in grupo.annoCiclo%type,
    in_numeroCiclo in grupo.numeroCiclo%type
)
return types.refCursor
as 
    grupoCursor types.refCursor;
begin 
    open grupoCursor for
        select * from grupo where codigoCurso = in_codigoCurso and numero = in_numero and annoCiclo = in_annoCiclo and numeroCiclo = in_numeroCiclo;
    return grupoCursor;
end;
/

create or replace function listarGrupo
return types.refCursor
as
    grupoCursor types.refCursor;
begin
    open grupoCursor for
        select * from grupo;
    return grupoCursor;
end;
/

--=====================TABLA PERFIL=====================
create table perfil(
    id int,
    nombre varchar(50),
    constraint pkPerfil primary key (id)
);

--CURSOR - TABLA PERFIL

create or replace package types
as
    type refCursor is ref cursor;
end;
/

--PROCEDIMIENTOS ALMACENADOS - TABLA PERFIL

--INSERT
create or replace procedure spInsertarPerfil(
    id in perfil.id%type, 
    nombre in perfil.nombre%type
)
as
begin
    insert into perfil values(id, nombre);
	commit;
end;
/

--UPDATE
create or replace procedure spModificarPerfil(
    in_id in grupo.numero%type, 
    in_nombre in grupo.horario%type
)
as
begin
    update perfil set nombre = in_nombre
        where id = in_id;
	commit;
end;
/
--DELETE
create or replace procedure spEliminarPerfil(
    in_id in grupo.numero%type
)
as
begin
    delete from perfil where id = in_id;
	commit;
end;
/

--FUNCIONES - TABLA PERFIL

create or replace function buscarPerfil(
    in_id in grupo.numero%type
)
return types.refCursor
as 
    perfilCursor types.refCursor;
begin 
    open perfilCursor for
        select * from perfil where id = in_id;
    return perfilCursor;
end;
/

create or replace function listarPerfil
return types.refCursor
as
    perfilCursor types.refCursor;
begin
    open perfilCursor for
        select * from perfil;
    return perfilCursor;
end;
/


--=====================TABLA USUARIO=====================
create table usuario(
    cedula varchar(10),
    clave varchar(30),
    perfil int,
    constraint pkUsuario primary key (cedula),
    constraint fkPerfil foreign key (perfil) 
        references perfil (id)
);

--CURSOR - TABLA USUARIO

create or replace package types
as
    type refCursor is ref cursor;
end;
/

--PROCEDIMIENTOS ALMACENADOS - TABLA USUARIO

--INSERT
create or replace procedure spInsertarUsuario(
    cedula in usuario.cedula%type, 
    clave in usuario.clave%type,
    perfil in usuario.perfil%type
)
as
begin
    insert into usuario values(cedula, clave, perfil);
	commit;
end;
/

--UPDATE
create or replace procedure spModificarUsuario(
    in_cedula in usuario.cedula%type, 
    in_clave in usuario.clave%type,
    in_perfil in usuario.perfil%type
)
as
begin
    update usuario set clave = in_clave, perfil = in_perfil
        where cedula = in_cedula;
	commit;
end;
/
--DELETE
create or replace procedure spEliminarUsuario(
    in_cedula in usuario.cedula%type
)
as
begin
    delete from usuario where cedula = in_cedula;
	commit;
end;
/

--FUNCIONES - TABLA USUARIO

create or replace function buscarUsuario(
    in_cedula in usuario.cedula%type
)
return types.refCursor
as 
    usuarioCursor types.refCursor;
begin 
    open usuarioCursor for
        select * from usuario where cedula = in_cedula;
    return usuarioCursor;
end;
/

create or replace function listarUsuario
return types.refCursor
as
    usuarioCursor types.refCursor;
begin
    open usuarioCursor for
        select * from usuario;
    return usuarioCursor;
end;
/


--TABLAS RELACIÓN

--=====================TABLA CURSOSCARRERA=====================
create table cursosCarrera(
    codigoCarrera varchar(10),
    codigoCurso varchar(10),
    annoCiclo int,
    numeroCiclo int,
    constraint pkCarreraCursos primary key (
        codigoCarrera,
        codigoCurso,
        annoCiclo,
        numeroCiclo
    ),
    constraint fkCicloCurso foreign key (annoCiclo, numeroCiclo)
        references ciclo (anno, numero),
    constraint fkCarreraCurso foreign key (codigoCarrera)
        references carrera (codigo),
    constraint fkCursoCiclo foreign key (codigoCurso)
        references curso (codigo)
);

--CURSOR - TABLA CURSOSCARRERA

create or replace package types
as
    type refCursor is ref cursor;
end;
/

--PROCEDIMIENTOS ALMACENADOS - TABLA CURSOSCARRERA

--INSERT
create or replace procedure spInsertarCursosCarrera(
    codigoCarrera in cursosCarrera.codigoCarrera%type, 
    codigoCurso in cursosCarrera.codigoCurso%type,
    annoCiclo in cursosCarrera.annoCiclo%type,
    numeroCiclo in cursosCarrera.numeroCiclo%type
)
as
begin
    insert into cursosCarrera values(codigoCarrera, codigoCurso, annoCiclo, numeroCiclo);
	commit;
end;
/

--DELETE
create or replace procedure spEliminarCursosCarrera(
    in_codigoCarrera in cursosCarrera.codigoCarrera%type, 
    in_codigoCurso in cursosCarrera.codigoCurso%type,
    in_annoCiclo in cursosCarrera.annoCiclo%type,
    in_numeroCiclo in cursosCarrera.numeroCiclo%type
)
as
begin
    delete from cursosCarrera where codigoCarrera = in_codigoCarrera and codigoCurso = in_codigoCurso and annoCiclo = in_annoCiclo and numeroCiclo = in_numeroCiclo;
	commit;
end;
/

create or replace function buscarCursosCarrera(
    in_codigoCarrera in cursosCarrera.codigoCarrera%type, 
    in_codigoCurso in cursosCarrera.codigoCurso%type,
    in_annoCiclo in cursosCarrera.annoCiclo%type,
    in_numeroCiclo in cursosCarrera.numeroCiclo%type
)
return types.refCursor
as 
    cursosCarreraCursor types.refCursor;
begin 
    open cursosCarreraCursor for
        select * from cursosCarrera where codigoCarrera = in_codigoCarrera and codigoCurso = in_codigoCurso and annoCiclo = in_annoCiclo and numeroCiclo = in_numeroCiclo;
    return cursosCarreraCursor;
end;
/

create or replace function listarCursosCarrera
return types.refCursor
as
    cursosCarreraCursor types.refCursor;
begin
    open cursosCarreraCursor for
        select * from cursosCarrera;
    return cursosCarreraCursor;
end;
/

--=====================TABLA ESTUDIANTESGRUPO=====================
create table estudiantesGrupo(
    cedulaEstudiante varchar(10),
    codigoGrupo int,
    codigoCurso varchar(10), 
    annoCiclo int, 
    numeroCiclo int, 
    nota float,
    constraint pkEstudiantesGrupo primary key (cedulaEstudiante, codigoGrupo, codigoCurso, annoCiclo, numeroCiclo),
    constraint fkEstudiante foreign key (cedulaEstudiante) 
        references alumno (cedula),
    constraint fkGrupo foreign key (codigoGrupo, codigoCurso, annoCiclo, numeroCiclo) 
        references grupo (numero, codigoCurso, annoCiclo, numeroCiclo)
);

--CURSOR - TABLA ESTUDIANTESGRUPO

create or replace package types
as
    type refCursor is ref cursor;
end;
/

--PROCEDIMIENTOS ALMACENADOS - TABLA ESTUDIANTESGRUPO

--INSERT
create or replace procedure spInsertarEstudianteGrupo(
    cedulaEstudiante in estudiantesGrupo.cedulaEstudiante%type, 
    codigoGrupo in estudiantesGrupo.codigoGrupo%type,
    codigoCurso in estudiantesGrupo.codigoCurso%type,
    annoCiclo in estudiantesGrupo.annoCiclo%type, 
    numeroCiclo in estudiantesGrupo.numeroCiclo%type,
    nota in estudiantesGrupo.nota%type
)
as
begin
    insert into estudiantesGrupo values(cedulaEstudiante, codigoGrupo,codigoCurso, annoCiclo, numeroCiclo, nota);
	commit;
end;
/

--UPDATE
create or replace procedure spModificarEstudianteGrupo(
    in_cedulaEstudiante in estudiantesGrupo.cedulaEstudiante%type, 
    in_codigoGrupo in estudiantesGrupo.codigoGrupo%type,
    in_codigoCurso in estudiantesGrupo.codigoCurso%type,
    in_annoCiclo in estudiantesGrupo.annoCiclo%type, 
    in_numeroCiclo in estudiantesGrupo.numeroCiclo%type,
    in_nota in estudiantesGrupo.nota%type
)
as
begin
    update estudiantesGrupo set nota = in_nota
        where cedulaEstudiante = in_cedulaEstudiante and codigoGrupo = in_codigoGrupo and codigoCurso = in_codigoCurso and annoCiclo = in_annoCiclo and numeroCiclo = in_numeroCiclo;
	commit;
end;
/
--DELETE
create or replace procedure spEliminarEstudianteGrupo(
    in_cedulaEstudiante in estudiantesGrupo.cedulaEstudiante%type, 
    in_codigoGrupo in estudiantesGrupo.codigoGrupo%type,
    in_nota in estudiantesGrupo.nota%type
)
as
begin
    delete from estudiantesGrupo where cedulaEstudiante = in_cedulaEstudiante and codigoGrupo = in_codigoGrupo;
	commit;
end;
/

--FUNCIONES - TABLA ESTUDIANTESGRUPO

create or replace function buscarEstudianteGrupo(
    in_cedulaEstudiante in estudiantesGrupo.cedulaEstudiante%type, 
    in_codigoGrupo in estudiantesGrupo.codigoGrupo%type
)
return types.refCursor
as 
    estudianteGrupoCursor types.refCursor;
begin 
    open estudianteGrupoCursor for
        select * from estudiantesGrupo where cedulaEstudiante = in_cedulaEstudiante and codigoGrupo = in_codigoGrupo;
    return estudianteGrupoCursor;
end;
/

create or replace function listarEstudianteGrupo
return types.refCursor
as
    estudianteGrupoCursor types.refCursor;
begin
    open estudianteGrupoCursor for
        select * from estudiantesGrupo;
    return estudianteGrupoCursor;
end;
/