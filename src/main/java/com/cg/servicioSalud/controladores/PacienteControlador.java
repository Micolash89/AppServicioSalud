/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cg.servicioSalud.controladores;

import com.cg.servicioSalud.entidades.Profesional;
import com.cg.servicioSalud.entidades.Turno;
import com.cg.servicioSalud.enumeraciones.Jornada;
import com.cg.servicioSalud.enumeraciones.Modalidad;
import com.cg.servicioSalud.enumeraciones.Rol;
import com.cg.servicioSalud.exepciones.MiException;
import com.cg.servicioSalud.servicios.PacienteServicio;
import com.cg.servicioSalud.servicios.ProfesionalServicio;
import com.cg.servicioSalud.servicios.TurnoServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author JAVIER ESPINDOLA
 */
@Controller
@RequestMapping("/paciente")
public class PacienteControlador {
    @Autowired
    private PacienteServicio pacienteServicio;
    
    @Autowired
    private TurnoServicio turnoServicio;
    
    @Autowired
    private ProfesionalServicio profesionalServicio;
    
    @GetMapping("/registrar")
    public String registrar() {

        return "paciente_form.html";

    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombreCompleto, @RequestParam String email,
            @RequestParam String clave, @RequestParam Long telefono,@RequestParam(required = false) MultipartFile archivo, 
             @RequestParam(required = false) String obraSocial){
        try {
           pacienteServicio.crearProfesional(nombreCompleto, email, clave, telefono, Rol.USER, archivo, obraSocial);
        } catch (MiException ex) {
            Logger.getLogger(ProfesionalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index.html";

    }
    
    
    
    @GetMapping("mis_turnos")//misturnos
    public String listarTrunos(ModelMap modelo){ //http de session  hacerlo 
          //hacer lo de session para paciente 
        String idPaciente ="8613f59c-cba3-4c6c-86c3-762a70f26d2e";
        
        List <Turno> turnos = turnoServicio.listarTurnosPorPa(idPaciente);
        List <Turno> turnosC = turnoServicio.listarTurnosPorPaCompletos(idPaciente);
        
        System.out.println(turnos);
        System.out.println(turnosC);
        
        modelo.addAttribute("turnos", turnos);
        modelo.addAttribute("turnosC", turnosC);
        
        return "lista_turnos_paciente.html";
        
    }
    
    @GetMapping("/puntuar/{id}")
    public String puntuar(@PathVariable String id, ModelMap modelo){
        
        System.out.println("---------------------------------");
        Profesional profesional = profesionalServicio.getOne(id);
        
        System.out.println(profesional);
        System.out.println(profesional.getNombreCompleto());
        System.out.println("---------------------------------");
        modelo.addAttribute("profesional",profesional);
        
        return "puntuar_form.html";
    }
    
    //@PostMapping("/registro_puntos")
    //public 
    
    
    
}
