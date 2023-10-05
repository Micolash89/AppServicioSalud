/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cg.servicioSalud.servicios;

import com.cg.servicioSalud.entidades.HistorialClinico;
import com.cg.servicioSalud.entidades.Turno;
import com.cg.servicioSalud.repositorios.HistorialClinicoRepositorio;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistorialClinicoServicio {
    
    @Autowired
    HistorialClinicoRepositorio historialClinicoRepositorio;
    
    @Autowired
    TurnoServicio turnoServicio;
    
    @Transactional
    public void crearHistorialClinico(String idTurno, String nota){
        
        HistorialClinico historialClinico = new HistorialClinico();
        
        //validar nota
        //hacer  con opction y con un if hay q verlo por uso find en ves de get one 
        Turno turno = turnoServicio.getOne(idTurno);
        
        System.out.println(turno);
        
        historialClinico.setNota(nota);
        historialClinico.setTurno(turno);
        
        historialClinicoRepositorio.save(historialClinico);
        
    }
    
    
}
