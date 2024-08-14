package com.universidadadmision.produccion.service;

import java.util.List;
import com.universidadadmision.produccion.dto.RequisitoModalidadDto;
import com.universidadadmision.produccion.entity.RequisitosModalidad;

public interface RequisitoModalidadService {
	public RequisitosModalidad save( RequisitosModalidad reqmod);
	public RequisitosModalidad read( Long id );
	public void delete( Long id );
	public List<RequisitoModalidadDto> listarxrequisitomodalidad(Long modalidadid);
}
