/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cg.servicioSalud.servicios;
import com.cg.servicioSalud.entidades.Imagen;
import com.cg.servicioSalud.entidades.Profesional;
import com.cg.servicioSalud.enumeraciones.Dia;
import com.cg.servicioSalud.enumeraciones.Horario;
import com.cg.servicioSalud.enumeraciones.Jornada;
import com.cg.servicioSalud.enumeraciones.Modalidad;
import com.cg.servicioSalud.enumeraciones.Rol;
import com.cg.servicioSalud.repositorios.ProfesionalRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author JAVIER ESPINDOLA
 */
@Service
public class ProfesionalServicio {

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    // en el servicio admin hacer un listar profesional
    @Transactional
    public void crearProfesional(String nombreCompleto, String email,
            String clave, Long telefono,// Rol rol,
            Jornada disponibilidad, List<String> obraSocial, Modalidad modalidad,
            String ubicacion, String especialidad, Double tarifa) {

        //funcion validad
        Profesional profesional = new Profesional();
     
        profesional.setNombreCompleto(nombreCompleto);
        profesional.setTarifa(tarifa);
        profesional.setDisponibilidad(disponibilidad);//esto es un atributo de la clase profesional
        profesional.setActivo(Boolean.FALSE);
        profesional.setClave(clave);
       // profesional.setRol(rol);
        profesional.setEspecialidad(especialidad);
        profesional.setFechaAlta(new Date());
        profesional.setReputacion(0d);
        profesional.setModalidad(modalidad);
        profesional.setObrasSociales(obraSocial);
        profesional.setUbicacion(ubicacion);
        profesional.setTelefono(telefono);
        profesional.setEmail(email);

        profesionalRepositorio.save(profesional);
    }

    @Transactional
    public void modificarProfesional(String id, String nombreCompleto, String email,
            String clave, Long telefono, Rol rol, Imagen imagen,
            Jornada disponibilidad, List<String> obraSocial, Modalidad modalidad,
            String ubicacion, String especialidad, Double tarifa, Boolean activo) {

       
        Optional<Profesional> respuesta = profesionalRepositorio.findById(id);
       
        if (respuesta.isPresent()) {

            Profesional profesional = respuesta.get();
            profesional.setImagen(imagen);
            profesional.setNombreCompleto(nombreCompleto);
            profesional.setTarifa(tarifa);
            profesional.setDisponibilidad(disponibilidad);
            profesional.setActivo(Boolean.FALSE);
            profesional.setClave(clave);
            profesional.setRol(rol);
            profesional.setEspecialidad(especialidad);
            profesional.setFechaAlta(new Date());
            profesional.setReputacion(0d);
            profesional.setModalidad(modalidad);
            profesional.setObrasSociales(obraSocial);
            profesional.setUbicacion(ubicacion);
            profesional.setTelefono(telefono);
            profesional.setEmail(email);

            profesionalRepositorio.save(profesional);
        }

    }
    
    public Profesional getOne(String id){
        
    return profesionalRepositorio.findById(id).get();
    
    }
    
    //falta validad
    
}
