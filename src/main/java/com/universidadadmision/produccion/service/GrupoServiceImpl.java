package com.universidadadmision.produccion.service;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.universidadadmision.produccion.dto.GrupoDto;
import com.universidadadmision.produccion.dto.MigraAcadDto;
import com.universidadadmision.produccion.entity.Grupo;
import com.universidadadmision.produccion.repository.GrupoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GrupoServiceImpl implements GrupoService {
	@Autowired
	private GrupoRepository gruporep;

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public Grupo save(Grupo grupo) {
		return gruporep.save( grupo );
	}

	@Override
	public Grupo read(Long id) {
		return gruporep.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		gruporep.deleteById(id);
	}

	@Override
	public List<GrupoDto> listartodos() {
		return gruporep.ListaGrupo();
	}
	
	@Override
	public List<Grupo> listarxperiodo(Long periodoid, Long tipoingresoid){
		return gruporep.findByPeriodoidAndTipoingresoid(periodoid,tipoingresoid);
	}

	@Override
	public MigraAcadDto executeCargarNotas(Long grupoid) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("paGrupoCargarNotas")
                .withSchemaName("Admision") 
                .declareParameters(
                        new SqlParameter("pIdGrupo", Types.INTEGER),
                        new SqlOutParameter("pResultado", Types.INTEGER),
                        new SqlOutParameter("pMensaje", Types.VARCHAR)
                );
		
		Map<String, Object> out = simpleJdbcCall.execute(
				grupoid
        );

		MigraAcadDto resultado = new MigraAcadDto();
		resultado.setCodigo((Integer) out.get("pResultado"));
		resultado.setDescripcion((String) out.get("pMensaje"));

        return resultado;
	};
}
