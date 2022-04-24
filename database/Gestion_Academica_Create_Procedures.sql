create or replace package types
as
    type refCursor is ref cursor;
end;
/

--==================PROCEDIMIENTOS ALMACENADOS - TABLA CARRERA==================
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


--==================PROCEDIMIENTOS ALMACENADOS - TABLA CURSO==================
--INSERT
create or replace procedure spInsertarCurso(
    in_codigo in curso.codigo%type, 
    in_nombre in curso.nombre%type, 
    in_creditos in curso.creditos%type,
    in_horasSemanales in curso.horasSemanales%type
)
as
begin
    insert into curso values(in_codigo, in_nombre, in_creditos, in_horasSemanales);
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
        select curso.*, carrera.codigo as codigoCarrera, carrera.nombre as nombreCarrera from ((cursoscarrera inner join curso on curso.codigo = cursoscarrera.codigocurso) inner join carrera on carrera.codigo = cursoscarrera.codigocarrera);
    return cursoCursor;
end;
/


--=================PROCEDIMIENTOS ALMACENADOS - TABLA PROFESOR=================
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
end;
/
--DELETE
create or replace procedure spEliminarProfesor(
    in_cedula in profesor.cedula%type 
)
as
begin
    delete from profesor where cedula = in_cedula;
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


--=================PROCEDIMIENTOS ALMACENADOS - TABLA ALUMNO=================
--INSERT
create or replace procedure spInsertarAlumno(
    in_cedula in alumno.cedula%type, 
    in_nombre in alumno.nombre%type, 
    in_telefono in alumno.telefono%type,
    in_email in alumno.email%type,
    in_fechaNacimiento in alumno.fechaNacimiento%type,
    in_carrera in alumno.carrera%type
)
as
begin
    insert into alumno values(in_cedula, in_nombre, in_telefono, in_email, in_fechaNacimiento, in_carrera);
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
end;
/
--DELETE
create or replace procedure spEliminarAlumno(
    in_cedula in alumno.cedula%type 
)
as
begin
    delete from alumno where cedula = in_cedula;
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
        select alumno.*, carrera.codigo as codigoCarrera, carrera.nombre as nombreCarrera from alumno inner join carrera on carrera.codigo = alumno.carrera;
    return alumnoCursor;
end;
/


--=================PROCEDIMIENTOS ALMACENADOS - TABLA CICLO=================
--INSERT
create or replace procedure spInsertarCiclo(
    in_anno in ciclo.anno%type, 
    in_numero in ciclo.numero%type, 
    in_fechaInicio in ciclo.fechaInicio%type,
    in_fechaFinal in ciclo.fechaFinal%type,
    in_estado in ciclo.estado%type
)
as
begin
    insert into ciclo values(in_anno, in_numero, in_fechaInicio, in_fechaFinal, in_estado);
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

    if in_estado = 'A' then
        update ciclo set estado = 'I'
        where estado = 'A';
    end if;
    update ciclo set fechaInicio = in_fechaInicio, fechaFinal = in_fechaFinal, estado = in_estado
        where anno = in_anno and numero = in_numero;
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


--=================PROCEDIMIENTOS ALMACENADOS - TABLA GRUPO=================
--INSERT
create or replace procedure spInsertarGrupo(
    in_numero in grupo.numero%type, 
    in_horario in grupo.horario%type, 
    in_cedulaProfesor in grupo.cedulaProfesor%type,
    in_codigoCurso in grupo.codigoCurso%type,
    in_annoCiclo in grupo.annoCiclo%type,
    in_numeroCiclo in grupo.numeroCiclo%type
)
as
begin
    insert into grupo values(in_numero, in_horario, in_cedulaProfesor, in_codigoCurso, in_annoCiclo, in_numeroCiclo);
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
end;
/
--FUNCIONES - TABLA GRUPO
create or replace function buscarGrupo(
    in_numero in grupo.numero%type,
    in_cedulaProfesor in grupo.cedulaProfesor%type,
    in_codigoCurso in grupo.codigoCurso%type,
    in_annoCiclo in grupo.annoCiclo%type,
    in_numeroCiclo in grupo.numeroCiclo%type
)
return types.refCursor
as 
    grupoCursor types.refCursor;
begin 
    open grupoCursor for
        select * from grupo where numero = in_numero and cedulaProfesor = in_cedulaProfesor and codigoCurso = in_codigoCurso and annoCiclo = in_annoCiclo and numeroCiclo = in_numeroCiclo;
    return grupoCursor;
end;
/
create or replace function listarGrupo(
    in_codigoCurso in grupo.codigoCurso%type,
    in_annoCiclo in grupo.annoCiclo%type,
    in_numeroCiclo in grupo.numeroCiclo%type
)
return types.refCursor
as
    grupoCursor types.refCursor;
begin
    open grupoCursor for
        select g.numero, g.horario, g.annoCiclo, g.numeroCiclo, p.cedula as cedulaProfesor, p.nombre as nombreProfesor, c.codigo as codigoCurso, c.nombre as nombreCurso
        from grupo g, profesor p, curso c 
        where in_codigoCurso = codigoCurso and in_annoCiclo = annoCiclo and in_numeroCiclo = numeroCiclo and p.cedula = g.cedulaprofesor and c.codigo = g.codigocurso;
    return grupoCursor;
end;
/

create or replace function listarGrupoCarrera(
    in_codigoCarrera in grupo.codigoCurso%type,
    in_annoCiclo in grupo.annoCiclo%type,
    in_numeroCiclo in grupo.numeroCiclo%type
)
return types.refCursor
as
    grupoCursor types.refCursor;
begin
    open grupoCursor for
        select g.numero, g.horario, g.annoCiclo, g.numeroCiclo, c.creditos, c.horasSemanales, p.cedula as cedulaProfesor, p.nombre as nombreProfesor, c.codigo as codigoCurso, c.nombre as nombreCurso
        from grupo g, profesor p, curso c, cursoscarrera cc
        where in_codigoCarrera = cc.codigocarrera and g.codigocurso = cc.codigocurso and in_annoCiclo = g.annoCiclo and in_numeroCiclo = g.numeroCiclo and p.cedula = g.cedulaprofesor and c.codigo = g.codigocurso;
    return grupoCursor;
end;
/


--=================PROCEDIMIENTOS ALMACENADOS - TABLA USUARIO=================
--INSERT
create or replace procedure spInsertarUsuario(
    in_cedula in usuario.cedula%type, 
    in_clave in usuario.clave%type,
    in_perfil in usuario.perfil%type
)
as
begin
    insert into usuario values(in_cedula, in_clave, in_perfil);
end;
/
--UPDATE
create or replace procedure spModificarUsuario(
    in_cedula in usuario.cedula%type, 
    in_clave in usuario.clave%type
)
as
begin
    update usuario set clave = in_clave
        where cedula = in_cedula;
end;
/
--DELETE
create or replace procedure spEliminarUsuario(
    in_cedula in usuario.cedula%type
)
as
begin
    delete from usuario where cedula = in_cedula;
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
create or replace function login(
    in_cedula in usuario.cedula%type,
    in_clave in usuario.clave%type
)
return varchar
is
    user varchar(10);
    pass varchar(30);
begin
    select clave
        into pass
        from usuario
        where cedula = in_cedula;

    if in_clave = pass then
        return 'Autorizado';
    else
        return 'None';
    end if;
    
    exception  
    when no_data_found then  
        return 'None';
end;
/


--===============PROCEDIMIENTOS ALMACENADOS - TABLA CURSOSCARRERA===============
--INSERT
create or replace procedure spInsertarCursosCarrera(
    in_codigoCarrera in cursosCarrera.codigoCarrera%type, 
    in_codigoCurso in cursosCarrera.codigoCurso%type,
    in_annoCiclo in cursosCarrera.annoCiclo%type,
    in_numeroCiclo in cursosCarrera.numeroCiclo%type
)
as
begin
    insert into cursosCarrera values(in_codigoCarrera, in_codigoCurso, in_annoCiclo, in_numeroCiclo);
end;
/

--UPDATE
create or replace procedure spModificarCursosCarrera(
    in_codigoCarrera in cursosCarrera.codigoCarrera%type, 
    in_codigoCurso in cursosCarrera.codigoCurso%type,
    in_annoCiclo in cursosCarrera.annoCiclo%type,
    in_numeroCiclo in cursosCarrera.numeroCiclo%type
)
as
begin
    update cursosCarrera set annoCiclo = in_annoCiclo, numeroCiclo = in_numeroCiclo
        where codigoCarrera = in_codigoCarrera and codigoCurso = in_codigoCurso;
end;
/

--DELETE
create or replace procedure spEliminarCursosCarrera(
    in_codigoCarrera in cursosCarrera.codigoCarrera%type, 
    in_codigoCurso in cursosCarrera.codigoCurso%type
)
as
begin
    delete from cursosCarrera where codigoCarrera = in_codigoCarrera and codigoCurso = in_codigoCurso;
end;
/
create or replace function buscarCursosCarrera(
    in_codigoCarrera in cursosCarrera.codigoCarrera%type, 
    in_codigoCurso in cursosCarrera.codigoCurso%type
)
return types.refCursor
as 
    cursosCarreraCursor types.refCursor;
begin 
    open cursosCarreraCursor for
        select * from cursosCarrera where codigoCarrera = in_codigoCarrera and codigoCurso = in_codigoCurso;
    return cursosCarreraCursor;
end;
/
create or replace function buscarCursoCarrera(
    in_codigoCurso in cursosCarrera.codigoCurso%type
)
return types.refCursor
as 
    cursosCarreraCursor types.refCursor;
begin 
    open cursosCarreraCursor for
        select * from cursosCarrera where codigoCurso = in_codigoCurso;
    return cursosCarreraCursor;
end;
/
create or replace function listarCursosCarrera(
    in_codigoCarrera in cursosCarrera.codigoCarrera%type
)
return types.refCursor
as
    cursosCarreraCursor types.refCursor;
begin
    open cursosCarreraCursor for
        select * from cursosCarrera where codigoCarrera = in_codigoCarrera;
    return cursosCarreraCursor;
end;
/

create or replace function listarCursosCarreraCiclo(
    in_codigoCarrera in cursosCarrera.codigoCarrera%type,
    in_annoCiclo in cursosCarrera.annociclo%type,
    in_numeroCiclo in cursoscarrera.numerociclo%type
)
return types.refCursor
as
    cursosCarreraCursor types.refCursor;
begin
    open cursosCarreraCursor for
        select c.codigo as codigoCurso, c.nombre as nombreCurso from curso c, cursoscarrera cc where
        in_codigoCarrera = cc.codigocarrera and 
        in_annoCiclo = cc.annociclo and 
        in_numerociclo = cc.numerociclo and 
        c.codigo = cc.codigocurso;
    return cursosCarreraCursor;
end;
/


--=============PROCEDIMIENTOS ALMACENADOS - TABLA ESTUDIANTESGRUPO=============
--INSERT
create or replace procedure spMatriculaEstudiante(
    in_cedulaEstudiante in estudiantesGrupo.cedulaEstudiante%type, 
    in_numeroGrupo in estudiantesGrupo.numeroGrupo%type,
    in_codigoCurso in estudiantesGrupo.codigoCurso%type,
    in_annoCiclo in estudiantesGrupo.annoCiclo%type, 
    in_numeroCiclo in estudiantesGrupo.numeroCiclo%type,
    in_nota in estudiantesGrupo.nota%type
)
as
begin
    insert into estudiantesGrupo values(in_cedulaEstudiante, in_numeroGrupo, in_codigoCurso, in_annoCiclo, in_numeroCiclo, in_nota);
end;
/
--UPDATE
create or replace procedure spIngresaNota(
    in_cedulaEstudiante in estudiantesGrupo.cedulaEstudiante%type, 
    in_numeroGrupo in estudiantesGrupo.numeroGrupo%type,
    in_codigoCurso in estudiantesGrupo.codigoCurso%type,
    in_annoCiclo in estudiantesGrupo.annoCiclo%type, 
    in_numeroCiclo in estudiantesGrupo.numeroCiclo%type,
    in_nota in estudiantesGrupo.nota%type
)
as
begin
    update estudiantesGrupo set nota = in_nota
        where cedulaEstudiante = in_cedulaEstudiante and numeroGrupo = in_numeroGrupo and codigoCurso = in_codigoCurso and annoCiclo = in_annoCiclo and numeroCiclo = in_numeroCiclo;
end;
/
--DELETE
create or replace procedure spDesmatriculaEstudiante(
    in_cedulaEstudiante in estudiantesGrupo.cedulaEstudiante%type, 
    in_numeroGrupo in estudiantesGrupo.numeroGrupo%type,
	in_codigoCurso in estudiantesGrupo.codigoCurso%type
)
as
begin
    delete from estudiantesGrupo where cedulaEstudiante = in_cedulaEstudiante and numeroGrupo = in_numeroGrupo and codigoCurso = in_codigoCurso;
end;
/
--FUNCIONES - TABLA ESTUDIANTESGRUPO
create or replace function buscarGruposEstudiante(
    in_cedulaEstudiante in estudiantesGrupo.cedulaEstudiante%type
)
return types.refCursor
as 
    estudianteGrupoCursor types.refCursor;
begin 
    open estudianteGrupoCursor for
        select * from estudiantesGrupo where cedulaEstudiante = in_cedulaEstudiante;
    return estudianteGrupoCursor;
end;
/
create or replace function listarEstudiantesGrupo(
    in_numeroGrupo in estudiantesGrupo.numeroGrupo%type,
    in_codigoCurso in estudiantesGrupo.codigoCurso%type,
    in_annoCiclo in estudiantesGrupo.annoCiclo%type, 
    in_numeroCiclo in estudiantesGrupo.numeroCiclo%type
)
return types.refCursor
as
    estudianteGrupoCursor types.refCursor;
begin
    open estudianteGrupoCursor for
        select * from estudiantesGrupo where numeroGrupo = in_numeroGrupo and codigoCurso = in_codigoCurso and annoCiclo = in_annoCiclo and numeroCiclo = in_numeroCiclo;
    return estudianteGrupoCursor;
end;
/