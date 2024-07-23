package com.universidadadmision.produccion.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.universidadadmision.produccion.dto.GeneralDto;
import com.universidadadmision.produccion.dto.PostulanteNotasDto;
import com.universidadadmision.produccion.dto.PostulanteNotasIDtoR;
import com.universidadadmision.produccion.dto.PostulantesDto;
import com.universidadadmision.produccion.dto.PostulantesDtoR;
import com.universidadadmision.produccion.entity.Postulantes;

public interface PostulantesService {
	public Postulantes save( Postulantes postulantes);
	public void registropostulantesrequisitos(PostulantesDtoR postulantesDTOR, List<MultipartFile> archivos) throws IOException ;
	public Postulantes read( Long id );
	public void delete( Long id );
	public List<PostulantesDto> listartodos();
	public List<Postulantes> findpostulantevacante(Long idpersona, Long idvacante);
	public GeneralDto generacodigo(Long periodoid);
	public List<PostulanteNotasDto> postulantenotaso(Long periodoid);
	public void postulantenotasi(List<PostulanteNotasIDtoR> postulantes);
}
