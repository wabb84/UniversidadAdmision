package com.universidadadmision.produccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.universidadadmision.produccion.dto.PostulantesEvaluacionDto;
import com.universidadadmision.produccion.entity.Requisitos;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RequisitosRepository extends JpaRepository<Requisitos, Long>  {

}
