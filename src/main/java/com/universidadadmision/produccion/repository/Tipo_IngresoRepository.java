package com.universidadadmision.produccion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.universidadadmision.produccion.dto.Tipo_IngresoDto;
import com.universidadadmision.produccion.entity.Tipo_Ingreso;

@Repository
public interface Tipo_IngresoRepository extends JpaRepository<Tipo_Ingreso, Long>  {
	Optional<Tipo_IngresoDto> findTipo_IngresoByid(Long id);
	List<Tipo_IngresoDto> findAllProjectedBy();
}