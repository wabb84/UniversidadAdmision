package com.universidadadmision.produccion.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.universidadadmision.produccion.entity.Carrera;

public interface CarreraRepository extends JpaRepository<Carrera, Long>  {
	public List<Carrera> findByEstado(boolean estado);
}
