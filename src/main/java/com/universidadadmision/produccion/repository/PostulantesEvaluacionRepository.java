package com.universidadadmision.produccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.universidadadmision.produccion.dto.PostulantesEvaluacionDto;
import com.universidadadmision.produccion.dto.PostulantesEvaluacionesDto;
import com.universidadadmision.produccion.entity.Modalidad;
import com.universidadadmision.produccion.entity.PostulantesEvaluacion;
import com.universidadadmision.produccion.entity.Vacantes;

@Repository
public interface PostulantesEvaluacionRepository extends JpaRepository<PostulantesEvaluacion, Long> {
        @Transactional(readOnly = true)
        @Query(value = "SELECT a.id, a.codigo, c.apellido_paterno AS apellidoPaterno, c.apellido_materno AS apellidoMaterno, c.nombre, d.descripcion AS nombreTipodoc, c.nro_documento AS nroDocumento, "
                        +
                        "a.modalidad_ingreso_id AS modalidadIngresoId, d1.nombre AS nombreModalidad, " +
                        "CASE " +
                        "   WHEN COALESCE(contador_postulante, 0) < b.contador_modalidad THEN 'Notas Pendientes' " +
                        "   ELSE 'Notas Registradas' " +
                        "END AS estado, d3.nombre as nombre_escuela " +
                        "FROM Admision.Postulantes a " +
                        "INNER JOIN ( " +
                        "    SELECT a.modalidad_id, a.evaluacion_id, b.nombre AS nombre_evaluacion, es_externo, COUNT(COALESCE(c.id, 0)) AS contador_modalidad "
                        +
                        "    FROM Admision.Modalidad_Evaluacion a " +
                        "    INNER JOIN Admision.Evaluacion b ON b.id = a.evaluacion_id " +
                        "    LEFT JOIN Admision.Criterio_Evaluacion c ON c.evaluacion_id = b.id " +
                        "    WHERE es_insertado = 0" +
                        "    GROUP BY a.modalidad_id, a.evaluacion_id, b.nombre, es_externo " +
                        ") b ON a.modalidad_ingreso_id = b.modalidad_id " +
                        "INNER JOIN General.Persona c ON a.persona_id = c.id " +
                        "INNER JOIN General.Catalogo d ON c.cat_tipo_documento_id = d.id " +
                        "INNER JOIN Admision.Modalidad d1 ON a.modalidad_ingreso_id = d1.id " +
                        "INNER JOIN Admision.Vacantes d2 on a.vacante_id = d2.id " +
                        "INNER JOIN General.Carrera d3 on d2.carrera_id = d3.id\r\n" +
                        "LEFT JOIN ( " +
                        "    SELECT pe.postulante_id, pe.evaluacion_id, COUNT(pec.id) AS contador_postulante " +
                        "    FROM Admision.Postulantes_Evaluacion pe " +
                        "    INNER JOIN Admision.Postulantes_Evaluacion_Criterio pec ON pe.id = pec.postulante_evaluacion_id where pec.nota_criterio is not null "
                        +
                        "    GROUP BY pe.postulante_id, pe.evaluacion_id " +
                        ") e ON a.id = e.postulante_id AND b.evaluacion_id = e.evaluacion_id WHERE a.grupo_id = :grupoId", nativeQuery = true)
        public List<PostulantesEvaluacionDto> listaPostulantesEvaluacion(Long grupoId);

        @Transactional(readOnly = true)
        @Query(value = "select a.id, a.evaluacion_id as evaluacionId, c.nombre as nombreEvaluacion,a.nota_total\n" +
                        "from  Admision.Postulantes_Evaluacion a\n" +
                        "inner join Admision.Evaluacion c on a.evaluacion_id = c.id where a.postulante_id = :postulanteId  and c.es_externo = 0", nativeQuery = true)
        List<PostulantesEvaluacionesDto> findByPostulanteId(Long postulanteId);

}