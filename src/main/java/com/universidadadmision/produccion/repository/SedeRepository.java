package com.universidadadmision.produccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.universidadadmision.produccion.entity.Sede;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Long>  {
	public List<Sede> findByEstado(boolean estado);
}
