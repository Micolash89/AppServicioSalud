/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cg.servicioSalud.controladores;

import com.cg.servicioSalud.entidades.Imagen;
import com.cg.servicioSalud.enumeraciones.Jornada;
import com.cg.servicioSalud.enumeraciones.Modalidad;
import com.cg.servicioSalud.enumeraciones.Rol;
import com.cg.servicioSalud.servicios.ProfesionalServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author JAVIER ESPINDOLA
 */
@Controller
@RequestMapping("/profesional")
public class ProfesionalControlador {

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @GetMapping("/registrar")
    public String registrar() {

        return "profesional_form.html";

    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombreCompleto, @RequestParam String email,
            @RequestParam String clave, @RequestParam Long telefono, // 
            @RequestParam(required = false) Jornada disponibilidad, @RequestParam(required = false) List<String> obraSocial, @RequestParam(required = false) Modalidad modalidad,
            @RequestParam String ubicacion, @RequestParam String especialidad, @RequestParam Double tarifa) {

        List <String> obra = new ArrayList<>();
        
        profesionalServicio.crearProfesional(nombreCompleto, email, clave, telefono, Jornada.LUNESM,obra , Modalidad.PRECENCIAL, ubicacion, especialidad, tarifa);

        return "index.html";

    }

}
