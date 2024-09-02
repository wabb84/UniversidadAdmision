package com.universidadadmision.produccion.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.universidadadmision.produccion.dto.GeneralDto;
import com.universidadadmision.produccion.dto.MigraAcadDto;
import com.universidadadmision.produccion.dto.PostulanteEstadoDto;
import com.universidadadmision.produccion.dto.PostulanteGrupoDto;
import com.universidadadmision.produccion.dto.PostulanteNotasDto;
import com.universidadadmision.produccion.dto.PostulanteNotasIDtoR;
import com.universidadadmision.produccion.dto.PostulanteRequisitoDto;
import com.universidadadmision.produccion.dto.PostulantesDto;
import com.universidadadmision.produccion.dto.PostulantesDtoR;
import com.universidadadmision.produccion.dto.PostulantesadjuntoDtoR;
import com.universidadadmision.produccion.dto.ValidaPostulanteDto;
import com.universidadadmision.produccion.dto.ValidaPostulanteDtoR;
import com.universidadadmision.produccion.entity.Postulantes;

public interface PostulantesService {
	public Postulantes save(Postulantes postulantes);

	// public void registropostulantesrequisitos(PostulantesadjuntoDtoR
	// postulantesDTOR, List<MultipartFile> archivos) throws IOException ;
	public Postulantes read(Long id);

	public void delete(Long id);

	public List<PostulantesDto> listartodos();

	public List<Postulantes> findpostulantevacante(Long idpersona, Long idvacante);

	public GeneralDto generacodigo(Long periodoid);

	public List<PostulanteNotasDto> postulantenotaso(Long periodoid);

	public void postulantenotasi(List<PostulanteNotasIDtoR> postulantes);

	public MigraAcadDto executeMigraAcademico(Long grupoid);

	public List<PostulanteGrupoDto> postulantegrupo(Long grupoid);

	public List<PostulanteGrupoDto> postulanteGeneralgrupo(Long grupoid);

	public PostulantesDto PostulantePassword(Long id);

	public Postulantes actualizarPreRegistro(Long id, String nuevoEstado);

	public MigraAcadDto executeActivarPago(String numeroPedido);

	public ValidaPostulanteDtoR validarPostulante(Long idtipodoc, String numeroDoc, Long periodoid);

	public PostulantesDto obtenerPostulante(Long postulanteId);

	public List<PostulanteRequisitoDto> obtenerRequisitoPostulante(Long postulanteId);

	public MigraAcadDto executeActualizarEstado(Long postulanteId);

	public List<PostulanteEstadoDto> listarEstadosPostulante();
}
