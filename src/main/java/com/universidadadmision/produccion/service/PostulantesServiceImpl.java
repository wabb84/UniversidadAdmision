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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.universidadadmision.produccion.dto.GeneralDto;
import com.universidadadmision.produccion.dto.MigraAcadDto;
import com.universidadadmision.produccion.dto.PostulanteEstadoDto;
import com.universidadadmision.produccion.dto.PostulanteGrupoDto;
import com.universidadadmision.produccion.dto.PostulanteNotasDto;
import com.universidadadmision.produccion.dto.PostulanteNotasIDtoR;
import com.universidadadmision.produccion.dto.PostulanteRequisitoDto;
import com.universidadadmision.produccion.dto.PostulanteRequisitoDtoR;
import com.universidadadmision.produccion.dto.PostulantesDto;
import com.universidadadmision.produccion.dto.PostulantesDtoR;
import com.universidadadmision.produccion.dto.ValidaPostulanteDto;
import com.universidadadmision.produccion.dto.ValidaPostulanteDtoR;
import com.universidadadmision.produccion.entity.Postulantes;
import com.universidadadmision.produccion.repository.PostulantesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostulantesServiceImpl implements PostulantesService {
	@Autowired
	private PostulantesRepository postulantesrep;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public Postulantes save(Postulantes postulantes) {
		return postulantesrep.save(postulantes);
	}

	@Override
	public Postulantes read(Long id) {
		return postulantesrep.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		postulantesrep.deleteById(id);
	}

	@Override
	public List<PostulantesDto> listartodos() {
		return postulantesrep.ListaGrupo();
	}

	@Override
	public List<Postulantes> findpostulantevacante(Long idpersona, Long idvacante) {
		return postulantesrep.findByPersonaidAndVacanteid(idpersona, idvacante);
	};

	@Transactional
	@Override
	public GeneralDto generacodigo(Long periodoid) {
		return postulantesrep.GeneraCodigoPostulante(periodoid);
	}

	@Override
	public List<PostulanteNotasDto> postulantenotaso(Long periodoid) {
		return postulantesrep.PostulanteNotasO(periodoid);
	};

	public List<PostulanteGrupoDto> postulantegrupo(Long grupoid) {
		return postulantesrep.PostulantexGrupo(grupoid);
	};

	public PostulantesDto PostulantePassword(Long id) {
		return postulantesrep.PostulantePassword(id);
	};

	@Override
	@Transactional
	public void postulantenotasi(List<PostulanteNotasIDtoR> postulantes) {
		for (PostulanteNotasIDtoR postulante : postulantes) {
			Postulantes postulanteexis = postulantesrep.findById(postulante.getId()).orElse(null);

			postulanteexis.setNota(postulante.getNota());
			postulanteexis.setEstado_postulante("I");
			postulantesrep.save(postulanteexis);
			// System.out.println(postulante.getCodigo());
		}

	};

	@Override
	@Transactional
	public MigraAcadDto executeMigraAcademico(Long grupoid) {

		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("paPostulanteMigracionAcad")
				.withSchemaName("Admision")
				.declareParameters(
						new SqlParameter("pIdGrupo", Types.INTEGER),
						new SqlOutParameter("pResultado", Types.INTEGER),
						new SqlOutParameter("pMensaje", Types.VARCHAR));

		Map<String, Object> out = simpleJdbcCall.execute(
				grupoid);

		MigraAcadDto resultado = new MigraAcadDto();
		resultado.setCodigo((Integer) out.get("pResultado"));
		resultado.setDescripcion((String) out.get("pMensaje"));

		return resultado;
	}

	@Override
	public Postulantes actualizarPreRegistro(Long id, String nuevoEstado) {
		Postulantes postulante = postulantesrep.findById(id)
				.orElseThrow(() -> new RuntimeException("Postulante no encontrado"));

		postulante.setEstado_postulante(nuevoEstado);

		return postulantesrep.save(postulante);
	}

	@Override
	public MigraAcadDto executeActivarPago(String numeroPedido) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("paActivarPagoPostulante")
				.withSchemaName("Admision")
				.declareParameters(
						new SqlParameter("pNumeroPedido", Types.VARCHAR),
						new SqlOutParameter("pResultado", Types.INTEGER),
						new SqlOutParameter("pMensaje", Types.VARCHAR));

		Map<String, Object> out = simpleJdbcCall.execute(
				numeroPedido);

		MigraAcadDto resultado = new MigraAcadDto();
		resultado.setCodigo((Integer) out.get("pResultado"));
		resultado.setDescripcion((String) out.get("pMensaje"));

		return resultado;
	}

	@Override
	public ValidaPostulanteDtoR validarPostulante(Long idtipodoc, String numeroDoc, Long periodoid) {
		return postulantesrep.validarPostulante(idtipodoc, numeroDoc, periodoid);
	}

	@Override
	public PostulantesDto obtenerPostulante(Long postulanteId) {
		return postulantesrep.findPostulanteByPostulanteId(postulanteId);
	}

	@Override
	public List<PostulanteRequisitoDto> obtenerRequisitoPostulante(Long postulanteId) {
		return postulantesrep.findRequisitosByPostulanteId(postulanteId);
	}

	@Override
	@Transactional
	public MigraAcadDto executeActualizarEstado(Long postulanteId) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("paActualizarEstadoYCrearEvaluacion")
				.withSchemaName("Admision")
				.declareParameters(
						new SqlParameter("postulanteId", Types.INTEGER),
						new SqlOutParameter("pResultado", Types.INTEGER),
						new SqlOutParameter("pMensaje", Types.VARCHAR));

		Map<String, Object> out = simpleJdbcCall.execute(postulanteId);

		MigraAcadDto resultado = new MigraAcadDto();
		resultado.setCodigo((Integer) out.get("pResultado"));
		resultado.setDescripcion((String) out.get("pMensaje"));

		return resultado;
	}

	@Override
	public List<PostulanteEstadoDto> listarEstadosPostulante() {
		return postulantesrep.listarEstadosPostulante();
	}

	@Override
	public List<PostulanteGrupoDto> postulanteGeneralgrupo(Long grupoid) {
		return postulantesrep.PostulantesGrupo(grupoid);
	}

	public void eliminarRequisitosPostulante(Long id, Long requisitoId) {
		postulantesrep.eliminarRequisitosPorPostulanteId(id, requisitoId);
	}

	@Override
	public int actualizarModalidadPostulante(Long id, Long grupoId, Long modalidadId) {
		return postulantesrep.actualizarModalidadPostulante(id, grupoId, modalidadId);
	}

}
