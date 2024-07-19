package com.universidadadmision.produccion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.universidadadmision.produccion.entity.Periodo;

@Repository
public interface PeriodoRepository extends JpaRepository<Periodo, Long> {
	public List<Periodo> findByEstadoAndPeriodotipo(boolean estado, Long idtipo);
}
