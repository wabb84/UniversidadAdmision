package com.universidadadmision.produccion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.universidadadmision.produccion.entity.PostulantesRequisitos;

@Repository
public interface PostulantesRequisitosRepository extends JpaRepository<PostulantesRequisitos, Long>  {

}
