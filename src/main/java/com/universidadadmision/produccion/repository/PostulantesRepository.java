package com.universidadadmision.produccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.universidadadmision.produccion.dto.GeneralDto;
import com.universidadadmision.produccion.dto.PostulanteEstadoDto;
import com.universidadadmision.produccion.dto.PostulanteGrupoDto;
import com.universidadadmision.produccion.dto.PostulanteNotasDto;
import com.universidadadmision.produccion.dto.PostulanteRequisitoDto;
import com.universidadadmision.produccion.dto.PostulantesDto;
import com.universidadadmision.produccion.dto.ValidaPostulanteDtoR;
import com.universidadadmision.produccion.entity.Postulantes;

@Repository
public interface PostulantesRepository extends JpaRepository<Postulantes, Long> {

	@Transactional(readOnly = true)
	@Query(value = "select a.id, a.persona_id as personaid,b.cat_tipo_documento_id as idtipodoc,c.abreviatura as tipodocumento, b.apellido_paterno, b.apellido_materno, b.nombre,b.nro_documento,i.departamento,i.provincia,i.distrito,\r\n"
			+ "   a.vacante_id,a.codigo,a.grupo_id,d.nombre as nombregrupo,a.modalidad_ingreso_id,e.nombre as nombremodalidad,a.estado_postulante,a.estado_auditoria as estado,b.discapacidad,b.carnet_conadis as carnetconadis,\r\n"
			+ "   b.sexo,b.direccion,b.email,b.fecha_nacimiento,b.celular,b.telefono,b.ubigeo_id,h.periodo_id as id_periodo,f.id as id_carrera, f.nombre as carrera,g.id as id_sede, g.nombre as sede, \r\n"
			+ "   f1.id as segunda_id_carrera, f1.nombre as segunda_carrera,case when a.modified_at is null then a.created_at else a.modified_at end as ts_movimiento \r\n"
			+ "   from Admision.postulantes a\r\n"
			+ "   inner join general.persona b on b.id = a.persona_id\r\n"
			+ "	  inner join general.Catalogo c on c.id = b.cat_tipo_documento_id\r\n"
			+ "	  inner join Admision.grupo d on d.id = a.grupo_id\r\n"
			+ "	  inner join Admision.modalidad e on e.id = a.modalidad_ingreso_id\r\n"
			+ "	  inner join admision.vacantes h on h.id = a.vacante_id\r\n"
			+ "	  inner join admision.vacantes h1 on h1.id = a.segunda_vacante_id\r\n"
			+ "   inner join General.Carrera f on f.id = h.carrera_id\r\n"
			+ "   inner join General.Carrera f1 on f1.id = h1.carrera_id\r\n"
			+ "   inner join General.Sede g on g.id = h.sede_id\r\n"
			+ "   inner join General.Ubigeo i on i.id = b.ubigeo_id\r\n"
			+ "   order by case when a.modified_at is null then a.created_at else a.modified_at end", nativeQuery = true)
	public List<PostulantesDto> ListaGrupo();

	public List<Postulantes> findByPersonaidAndVacanteid(Long idpersona, Long idvacante);

	@Transactional(readOnly = true)
	@Query(value = "select right('00000'+cast((cast(substring(coalesce(max(codigo),''),6,5) as int) + 1) as varchar(5)),5) codigo \r\n"
			+ " from admision.postulantes a\r\n"
			+ " inner join admision.vacantes b on b.id = a.vacante_id\r\n"
			+ " inner join General.Periodo c on c.id = b.periodo_id\r\n"
			+ " where c.id = :periodoid", nativeQuery = true)

	public GeneralDto GeneraCodigoPostulante(Long periodoid);

	@Transactional(readOnly = true)
	@Query(value = "select a.id,c.anio_semestre as aniosemestre,a.codigo,d.cod_carrera as codcarrera,e.cod_sede as codsede, 0 as nota\r\n"
			+ " from Admision.postulantes a\r\n"
			+ "   inner join admision.vacantes b on b.id = a.vacante_id\r\n"
			+ "   inner join general.periodo c on c.id = b.periodo_id\r\n"
			+ "   inner join General.Carrera d on d.id = b.carrera_id\r\n"
			+ "   inner join General.Sede e on e.id = b.sede_id\r\n"
			+ "   where c.id = :periodoid and a.estado_postulante = 'P'", nativeQuery = true)

	public List<PostulanteNotasDto> PostulanteNotasO(Long periodoid);

	@Transactional(readOnly = true)
	@Query(value = "select a.id,c.anio_semestre as aniosemestre,a.codigo,f.nombre, f.apellido_paterno,f.apellido_materno,d.id as codcarrera,h.nombre as nombre_modalidad,\r\n"
			+ "e.id as codsede, d.nombre as carrera, e.nombre as sede, a.estado_postulante as estado, a.nota as nota, isnull(a.estado_puntaje,'') as estadopuntaje,\r\n"
			+ "g.descripcion as tipodocumento, f.nro_documento as nrodocumento,f.email,f.celular,f.telefono,f.discapacidad,f.carnet_conadis as carnetconadis\r\n"
			+ " from Admision.Postulantes a\r\n"
			+ "   inner join Admision.Vacantes b on b.id = a.vacante_id\r\n"
			+ "   inner join General.periodo c on c.id = b.periodo_id\r\n"
			+ "   inner join General.Carrera d on d.id = b.carrera_id\r\n"
			+ "   inner join General.Sede e on e.id = b.sede_id\r\n"
			+ "   inner join General.persona f on f.id = a.persona_id\r\n"
			+ "   inner join General.Catalogo g on f.cat_tipo_documento_id=g.id\r\n"
			+ "   inner join Admision.Modalidad h on a.modalidad_ingreso_id=h.id\r\n"
			+ "   where a.grupo_id = :grupoid and a.estado_postulante in ('P','I','A')", nativeQuery = true)

	public List<PostulanteGrupoDto> PostulantexGrupo(Long grupoid);

	@Transactional(readOnly = true)
	@Query(value = "select a.id,c.anio_semestre as aniosemestre,a.codigo,f.nombre, f.apellido_paterno,f.apellido_materno,d.id as codcarrera,h.nombre as nombre_modalidad,\r\n"
			+ "e.id as codsede, d.nombre as carrera, e.nombre as sede, i.descripcion as estado, a.nota as nota, isnull(a.estado_puntaje,'') as estadopuntaje,\r\n"
			+ "g.descripcion as tipodocumento, f.nro_documento as nrodocumento,f.email,f.celular,f.telefono,f.discapacidad,f.carnet_conadis as carnetconadis\r\n"
			+ " from Admision.Postulantes a\r\n"
			+ "   inner join Admision.Vacantes b on b.id = a.vacante_id\r\n"
			+ "   inner join General.periodo c on c.id = b.periodo_id\r\n"
			+ "   inner join General.Carrera d on d.id = b.carrera_id\r\n"
			+ "   inner join General.Sede e on e.id = b.sede_id\r\n"
			+ "   inner join General.persona f on f.id = a.persona_id\r\n"
			+ "   inner join General.Catalogo g on f.cat_tipo_documento_id=g.id\r\n"
			+ "   inner join Admision.Modalidad h on a.modalidad_ingreso_id=h.id\r\n"
			+ "   inner join Admision.Estado_Postulante i on a.estado_postulante=i.estado\r\n"
			+ "   where a.grupo_id = :grupoid", nativeQuery = true)

	public List<PostulanteGrupoDto> PostulantesGrupo(Long grupoid);

	@Transactional(readOnly = true)
	@Query(value = "SELECT CAST(DECRYPTBYPASSPHRASE('politecnica',password) AS CHAR(8)) as password \r\n"
			+ "from Admision.Postulantes\r\n"
			+ "WHERE id = :postulanteid \r\n", nativeQuery = true)

	public PostulantesDto PostulantePassword(Long postulanteid);

	@Transactional(readOnly = true)
	@Query(value = "select a.id, a.codigo,1 as pedido,case when a.estado_postulante ='B' then 'B' else 'R' end as estado \r\n"
			+ "from Admision.Postulantes a\r\n"
			+ "where a.persona_id = (select id from General.Persona where cat_tipo_documento_id =:idtipodoc and nro_documento=:numeroDoc) \r\n"
			+ "and vacante_id  in (select id from Admision.Vacantes where periodo_id = :periodoid)", nativeQuery = true)

	public ValidaPostulanteDtoR validarPostulante(Long idtipodoc, String numeroDoc, Long periodoid);

	@Transactional(readOnly = true)
	@Query(value = "select a.id, b.cat_tipo_documento_id as idtipodoc, b.nro_documento as nro_documento, \r\n"
			+ "b.apellido_paterno, b.apellido_materno, b.nombre, b.sexo, b.email, b.celular, b.telefono, \r\n"
			+ "b.fecha_nacimiento, b.direccion, d.carrera_id as id_carrera, d.sede_id as id_sede, \r\n"
			+ "d1.carrera_id as segunda_id_carrera, b.ubigeo_id, i.departamento, i.provincia, i.distrito, \r\n"
			+ "b.discapacidad, b.carnet_conadis as carnetconadis, a.modalidad_ingreso_id as modalidad_ingreso_id, \r\n"
			+ "d.periodo_id as id_periodo, g.id as grupo_id \r\n"
			+ "from Admision.Postulantes a \r\n"
			+ "inner join General.Persona b on a.persona_id = b.id \r\n"
			+ "inner join General.Carrera c on c.id = b.cat_tipo_documento_id \r\n"
			+ "inner join Admision.Vacantes d on d.id = a.vacante_id \r\n"
			+ "inner join Admision.Vacantes d1 on d1.id = a.segunda_vacante_id \r\n"
			+ "inner join Admision.Modalidad e on e.id = a.modalidad_ingreso_id \r\n"
			+ "inner join Admision.Tipo_Ingreso f on f.id = e.tipo_ingreso_id \r\n"
			+ "inner join Admision.Grupo g on g.periodo_id = d.periodo_id and g.tipo_ingreso_id = f.id \r\n"
			+ "inner join General.Ubigeo i on i.id = b.ubigeo_id \r\n"
			+ "where a.id = :postulanteId", nativeQuery = true)
	public PostulantesDto findPostulanteByPostulanteId(Long postulanteId);

	@Transactional(readOnly = true)
	@Query(value = "select postulante_id as postulanteId, requisito_modalidad_id as requisitomodalidadid, url " +
			"from Admision.Postulantes_Requisitos " +
			"where postulante_id = :postulanteId", nativeQuery = true)
	List<PostulanteRequisitoDto> findRequisitosByPostulanteId(Long postulanteId);

	@Transactional(readOnly = true)
	@Query(value = "select estado as codigo, descripcion, orden from Admision.Estado_Postulante order by 3", nativeQuery = true)
	List<PostulanteEstadoDto> listarEstadosPostulante();

	@Transactional
	@Modifying
	@Query(value = "delete from Admision.Postulantes_Requisitos where postulante_id = :id and requisito_modalidad_id = :requisitoId", nativeQuery = true)
	void eliminarRequisitosPorPostulanteId(Long id, Long requisitoId);

	@Transactional
	@Modifying
	@Query(value = "update Admision.Postulantes set grupo_id = :grupoId, modalidad_ingreso_id = :modalidadId where id = :id", nativeQuery = true)
	int actualizarModalidadPostulante(Long id, Long grupoId, Long modalidadId);
}
