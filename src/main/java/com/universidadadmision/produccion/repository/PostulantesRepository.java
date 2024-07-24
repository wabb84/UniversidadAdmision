package com.universidadadmision.produccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.universidadadmision.produccion.dto.GeneralDto;
import com.universidadadmision.produccion.dto.MigraAcadDto;
import com.universidadadmision.produccion.dto.PostulanteNotasDto;
import com.universidadadmision.produccion.dto.PostulantesDto;
import com.universidadadmision.produccion.entity.Postulantes;

//import jakarta.persistence.StoredProcedureQuery;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManager;
//import javax.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public interface PostulantesRepository extends JpaRepository<Postulantes, Long>  {
	
	@Transactional(readOnly=true)
	@Query(value = "select a.id, a.persona_id as personaid,c.abreviatura as tipodocumento, b.apellido_paterno +' '+ b.apellido_materno +' '+ b.nombre as nombrepostulante,b.nro_documento,\r\n"
			+ "   a.vacante_id,a.codigo,a.grupo_id,d.nombre as nombregrupo,a.modalidad_ingreso_id,e.nombre as nombremodalidad,a.estado_postulante,a.estado_auditoria as estado,\r\n"
			+ "   b.sexo,b.direccion,b.email,b.fecha_nacimiento,b.celular,b.telefono,b.ubigeo_id\r\n"
			+ "   from Admision.postulantes a\r\n"
			+ "   inner join general.persona b on b.id = a.persona_id\r\n"
			+ "	  inner join general.Catalogo c on c.id = b.cat_tipo_documento_id\r\n"
			+ "	  inner join Admision.grupo d on d.id = a.grupo_id\r\n"
			+ "	  inner join Admision.modalidad e on e.id = a.modalidad_ingreso_id\r\n"
			+ "   order by a.id", nativeQuery = true )
	public List<PostulantesDto> ListaGrupo();
	
	public List<Postulantes> findByPersonaidAndVacanteid(Long idpersona, Long idvacante);
	
	@Transactional(readOnly=true)
	@Query(value = "select right('00000'+cast((cast(substring(coalesce(max(codigo),''),6,5) as int) + 1) as varchar(5)),5) codigo \r\n"
			+ " from admision.postulantes a\r\n"
			+ " inner join admision.vacantes b on b.id = a.vacante_id\r\n"
			+ " inner join General.Periodo c on c.id = b.periodo_id\r\n"
			+ " where c.id = :periodoid", nativeQuery = true )
	
	public GeneralDto GeneraCodigoPostulante(Long periodoid);
	
	@Transactional(readOnly=true)
	@Query(value = "select a.id,c.anio_semestre as aniosemestre,a.codigo,d.cod_carrera as codcarrera,e.cod_sede as codsede, 0 as nota\r\n"
			+ " from Admision.postulantes a\r\n"
			+ "   inner join admision.vacantes b on b.id = a.vacante_id\r\n"
			+ "   inner join general.periodo c on c.id = b.periodo_id\r\n"
			+ "   inner join General.Carrera d on d.id = b.carrera_id\r\n"
			+ "   inner join General.Sede e on e.id = b.sede_id\r\n"
			+ "   where c.id = :periodoid and a.estado_postulante = 'P'", nativeQuery = true )
	
	public List<PostulanteNotasDto> PostulanteNotasO(Long periodoid);
	
	
    //@Procedure(procedureName = "Admision.paPostulanteMigracionAcad")
	//void executeMigraAcademico(@Param("pIdGrupo") Long pIdGrupo, @Param("pResultado") Long pResultado, @Param("pMensaje") String pMensaje);
    
    /*public MigraAcadDto executeMigraAcademico(Long grupoid) {
    
    
    	StoredProcedureQuery query = EntityManager.createStoredProcedureQuery("Admision.paPostulanteMigracionAcad");
    
    	return "";
    	
    }*/
    
    
    
    
	
}
