package com.universidadadmision.produccion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.universidadadmision.produccion.entity.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
	public Persona findByTipodocumentoidAndNrodocumento(Long id, String numero); 

}
