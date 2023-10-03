/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cg.servicioSalud.controladores;

import com.cg.servicioSalud.entidades.Profesional;
import com.cg.servicioSalud.entidades.Turno;
import com.cg.servicioSalud.servicios.ProfesionalServicio;
import com.cg.servicioSalud.servicios.TurnoServicio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/turno")
public class TurnoControlador {

    @Autowired
    TurnoServicio turnoServicio;

    @Autowired
    ProfesionalServicio profesionalServicio;

    @GetMapping("/especialidad")
    public String especialidad(ModelMap modelo) {

        //List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        List<Profesional> profesionales = profesionalServicio.buscarActivos();

        modelo.addAttribute("profesionales", profesionales);

        return "turno_form_especialidad.html";

    }
//falta-try-catch
    @PostMapping("/registroEspecialidad")
    public String registroEspecialidad(@RequestParam String idProfesional, ModelMap modelo) {

        Profesional profesional = profesionalServicio.getOne(idProfesional);

        modelo.addAttribute("profesional", profesional);
        //validar que el profesional que este disponible ese dia/hora, el paciente no tenga turnos ese dia/hora

        List<String> calendario = new ArrayList();

        //"0m1m2m3m4m5m6m"
        
        //hacer calendario del profesional personalizado
        for (int i = 1; i <= 30; i++) {

            Date dia = new Date();

            // Obtener la representación en milisegundos de la fecha actual
            long tiempoEnMilisegundos = dia.getTime();

            // Sumar un día en milisegundos (86400000 milisegundos en un día)
            long unDiaEnMilisegundos = 86400000*i;
            tiempoEnMilisegundos += unDiaEnMilisegundos;

            // Crear una nueva instancia de Date a partir del tiempo en milisegundos modificado
            Date nuevoDia = new Date(tiempoEnMilisegundos);
            Integer j = nuevoDia.getDay();//0-1-2-3-4-5-6
            
            
            if(profesional.getDisponibilidad().contains(j.toString())&& turnoServicio.buscarDisponibilidad(profesional.getId(),nuevoDia)==null)
                calendario.add(nuevoDia.toString());
                
        }
       
        modelo.addAttribute("calendario", calendario);
        
        return "turno_form_doctor.html";

    }
//ya me perdi esto deberia ser un get xd
    @PostMapping("/registrarDoctor")
    public String registroDoctor(@RequestParam String idProfesional,String dia, String motivo, Double precioFinal,ModelMap modelo ) {//necesito el paciente es el usuario activo 
         
        Turno turno = turnoServicio.crearTurno(idProfesional, dia, motivo, precioFinal);
        
        System.out.println(turno);
        
        modelo.addAttribute("turno",turno);
      
        return "turno_mostrar.html";
    }

}
