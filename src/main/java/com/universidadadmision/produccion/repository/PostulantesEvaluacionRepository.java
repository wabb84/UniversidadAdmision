package com.universidadadmision.produccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.universidadadmision.produccion.dto.PostulantesEvaluacionDto;
import com.universidadadmision.produccion.dto.PostulantesEvaluacionesDto;
import com.universidadadmision.produccion.entity.PostulantesEvaluacion;

@Repository
public interface PostulantesEvaluacionRepository extends JpaRepository<PostulantesEvaluacion, Long> {
        @Transactional(readOnly = true)
        @Query(value = "SELECT " +
                        "    a.id, " +
                        "    a.codigo, " +
                        "    c.apellido_paterno AS apellidoPaterno, " +
                        "    c.apellido_materno AS apellidoMaterno, " +
                        "    c.nombre, " +
                        "    d.descripcion AS nombreTipodoc, " +
                        "    c.nro_documento AS nroDocumento, " +
                        "    a.modalidad_ingreso_id AS modalidadIngresoId, " +
                        "    d1.nombre AS nombreModalidad, " +
                        "    CASE " +
                        "        WHEN COALESCE(x.contador_postulante, 0) < b.contador_modalidad THEN 'Notas Pendientes' "
                        +
                        "        ELSE 'Notas Registradas' " +
                        "    END AS estado, " +
                        "    d3.nombre AS nombre_escuela " +
                        "FROM " +
                        "    Admision.Postulantes a " +
                        "INNER JOIN ( " +
                        "    SELECT " +
                        "        a.modalidad_id, " +
                        "        COUNT(COALESCE(c.id, 0)) AS contador_modalidad " +
                        "    FROM " +
                        "        Admision.Modalidad_Evaluacion a " +
                        "    INNER JOIN " +
                        "        Admision.Evaluacion b ON b.id = a.evaluacion_id " +
                        "    LEFT JOIN " +
                        "        Admision.Criterio_Evaluacion c ON c.evaluacion_id = b.id " +
                        "    WHERE " +
                        "        es_insertado = 1 " +
                        "    GROUP BY " +
                        "        a.modalidad_id " +
                        ") b ON a.modalidad_ingreso_id = b.modalidad_id " +
                        "INNER JOIN " +
                        "    General.Persona c ON a.persona_id = c.id " +
                        "INNER JOIN " +
                        "    General.Catalogo d ON c.cat_tipo_documento_id = d.id " +
                        "INNER JOIN " +
                        "    Admision.Modalidad d1 ON a.modalidad_ingreso_id = d1.id " +
                        "INNER JOIN " +
                        "    Admision.Vacantes d2 ON a.vacante_id = d2.id " +
                        "INNER JOIN " +
                        "    General.Carrera d3 ON d2.carrera_id = d3.id " +
                        "LEFT JOIN ( " +
                        "    SELECT " +
                        "        pe.postulante_id, " +
                        "        COUNT(pec.id) AS contador_postulante " +
                        "    FROM " +
                        "        Admision.Postulantes_Evaluacion pe " +
                        "    INNER JOIN " +
                        "        Admision.Postulantes_Evaluacion_Criterio pec ON pe.id = pec.postulante_evaluacion_id "
                        +
                        "    WHERE " +
                        "        pec.nota_criterio IS NOT NULL " +
                        "    GROUP BY " +
                        "        pe.postulante_id " +
                        ") x ON a.id = x.postulante_id", nativeQuery = true)
        public List<PostulantesEvaluacionDto> listaPostulantesEvaluacion(Long grupoId);

        @Transactional(readOnly = true)
        @Query(value = "select a.id, a.evaluacion_id as evaluacionId,d.porcentaje, b.nombre as nombreEvaluacion,a.nota_total, b.es_externo\n"
                        +
                        "from  Admision.Postulantes_Evaluacion a\n" +
                        "inner join Admision.Postulantes c on a.postulante_id =c.id\n" +
                        "inner join Admision.Modalidad_Evaluacion d on a.evaluacion_id=d.evaluacion_id and c.modalidad_ingreso_id =d.modalidad_id\n"
                        +
                        "inner join Admision.Evaluacion b on a.evaluacion_id = b.id where a.postulante_id = :postulanteId  and b.es_insertado = 1", nativeQuery = true)
        List<PostulantesEvaluacionesDto> findByPostulanteId(Long postulanteId);

}