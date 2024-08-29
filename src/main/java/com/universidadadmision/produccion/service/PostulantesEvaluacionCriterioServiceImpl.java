package com.universidadadmision.produccion.service;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.universidadadmision.produccion.dto.MigraAcadDto;
import com.universidadadmision.produccion.dto.PostulantesEvaluacionesCriterioDto;
import com.universidadadmision.produccion.entity.Postulantes;
import com.universidadadmision.produccion.entity.PostulantesEvaluacionCriterio;
import com.universidadadmision.produccion.repository.PostulantesEvaluacionCriterioRepository;

import jakarta.transaction.Transactional;

@Service
public class PostulantesEvaluacionCriterioServiceImpl implements PostulantesEvaluacionCriterioService {

    @Autowired
    private PostulantesEvaluacionCriterioRepository postulanteEvaluacionCriterioRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<PostulantesEvaluacionCriterio> findAll() {
        return postulanteEvaluacionCriterioRepository.findAll();
    }

    @Override
    public Optional<PostulantesEvaluacionCriterio> findById(Long id) {
        return postulanteEvaluacionCriterioRepository.findById(id);
    }

    @Override
    public PostulantesEvaluacionCriterio save(PostulantesEvaluacionCriterio postulanteEvaluacionCriterio) {
        return postulanteEvaluacionCriterioRepository.save(postulanteEvaluacionCriterio);
    }

    @Override
    public void deleteById(Long id) {
        postulanteEvaluacionCriterioRepository.deleteById(id);
    }

    @Override
    public List<PostulantesEvaluacionesCriterioDto> findByPostulanteEvaluacionId(Long postulanteEvaluacionId)
            throws Exception {
        return postulanteEvaluacionCriterioRepository.findByPostulanteEvaluacionId(postulanteEvaluacionId);

    }

    @Override
    @Transactional
    public MigraAcadDto executeActualizarNota(Long id, Long nota) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("paActualizarNotaCriterio")
                .withSchemaName("Admision")
                .declareParameters(
                        new SqlParameter("pIdCriterio", Types.INTEGER),
                        new SqlParameter("pNotaCriterio", Types.INTEGER),
                        new SqlOutParameter("pResultado", Types.INTEGER),
                        new SqlOutParameter("pMensaje", Types.VARCHAR));

        Map<String, Object> out = simpleJdbcCall.execute(id, nota);
        MigraAcadDto resultado = new MigraAcadDto();
        resultado.setCodigo((Integer) out.get("pResultado"));
        resultado.setDescripcion((String) out.get("pMensaje"));
        return resultado;
    }

}