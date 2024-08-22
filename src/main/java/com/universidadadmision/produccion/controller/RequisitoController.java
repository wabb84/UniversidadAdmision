package com.universidadadmision.produccion.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.universidadadmision.produccion.dto.GrupoDto;
import com.universidadadmision.produccion.dto.ReqModDtoR;
import com.universidadadmision.produccion.dto.RequisitoDtoR;
import com.universidadadmision.produccion.entity.Requisitos;
import com.universidadadmision.produccion.entity.RequisitosModalidad;
import com.universidadadmision.produccion.service.RequisitoModalidadService;
import com.universidadadmision.produccion.service.RequisitosService;

@Controller
@CrossOrigin
@RequestMapping("/requisito")
@Validated
public class RequisitoController {
    @Autowired
    private RequisitosService reqservice;

    @PostMapping("/lista")
    public ResponseEntity<?> ListaReq() throws Exception {
        List<Requisitos> requisitolista = reqservice.list();
        return ResponseEntity.ok(requisitolista);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> NuevoReq(@RequestBody RequisitoDtoR reqDtor) throws Exception {
        Map<String, Object> response = new HashMap<>();

        Requisitos reqnew = new Requisitos();
        reqnew.setDescripcion(reqDtor.getDescripcion());
        reqnew.prePersist();

        try {
            reqservice.save(reqnew);

        } catch (Exception e) {
            response.put("resultado", 0);
            response.put("mensaje", "Error al Grabar el Requisito : " + e.getMessage());
            response.put("dato", "");
            return ResponseEntity.ok(response);
        }

        response.put("resultado", 1);
        response.put("mensaje", "Datos de Requisito grabados correctamente");
        response.put("dato", reqnew);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/edita")
    public ResponseEntity<?> EditaReq(@RequestBody RequisitoDtoR reqDtor) throws Exception {
        Map<String, Object> response = new HashMap<>();
        Requisitos reqmodedit = reqservice.read(reqDtor.getId());

        if (reqmodedit == null) {
            response.put("resultado", 0);
            response.put("mensaje", "No existe el Requisito");
            response.put("dato", "");

            return ResponseEntity.ok(response);
        }

        reqmodedit.setId(reqDtor.getId());
        reqmodedit.setDescripcion(reqDtor.getDescripcion());
        reqmodedit.preUpdate();

        try {

            reqservice.save(reqmodedit);

        } catch (Exception e) {
            response.put("resultado", 0);
            response.put("mensaje", "Error al Grabar Requisito : " + e.getMessage());
            response.put("dato", "");
            return ResponseEntity.ok(response);
        }

        response.put("resultado", 1);
        response.put("mensaje", "Datos de Requisitos grabados correctamente");
        response.put("dato", reqmodedit);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/elimina")
    public ResponseEntity<?> EliminaReq(@RequestBody RequisitoDtoR reqDtor, BindingResult result) throws Exception {
        Map<String, Object> response = new HashMap<>();

        Requisitos reqmodelimina = reqservice.read(reqDtor.getId());

        if (reqmodelimina == null) {
            response.put("resultado", 0);
            response.put("mensaje", "No existe el Requisito");
            response.put("dato", "");

            return ResponseEntity.ok(response);
        }

        try {
            reqservice.delete(reqDtor.getId());
        } catch (Exception e) {
            response.put("resultado", 0);
            response.put("mensaje", "Error al Eliminar el Requisito : " + e.getMessage());
            response.put("dato", "");
            return ResponseEntity.ok(response);
        }

        response.put("resultado", 1);
        response.put("mensaje", "Requisito eliminado correctamente");
        response.put("dato", reqmodelimina);
        return ResponseEntity.ok(response);
    }
}