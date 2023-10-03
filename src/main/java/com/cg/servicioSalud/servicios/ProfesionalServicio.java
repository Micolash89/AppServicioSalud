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
import com.cg.servicioSalud.exepciones.MiException;
import com.cg.servicioSalud.repositorios.ProfesionalRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author JAVIER ESPINDOLA
 */
@Service
public class ProfesionalServicio {

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;
    
    @Autowired
    private ImagenServicio imagenServicio;

    // en el servicio admin hacer un listar profesional
    @Transactional
    public void crearProfesional(String nombreCompleto, String email,
            String clave, Long telefono, Rol rol, MultipartFile archivo,
            String disponibilidad, List<String> obraSocial, Modalidad modalidad,
            String ubicacion, String especialidad, Double tarifa) throws MiException {

        //funcion validad
        //validar(nombreCompleto, email, clave, telefono, rol, disponibilidad, obraSocial, modalidad, ubicacion, especialidad, tarifa);
        
        Profesional profesional = new Profesional();
     
        profesional.setNombreCompleto(nombreCompleto);
        profesional.setTarifa(tarifa);
        profesional.setDisponibilidad(disponibilidad);//esto es un atributo de la clase profesional
        profesional.setActivo(Boolean.TRUE);
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
        
        Imagen imagen = imagenServicio.guardar(archivo);
        profesional.setImagen(imagen);

        profesionalRepositorio.save(profesional);
    }
    
   public List<Profesional> listarProfesionales(){
   
       List<Profesional> profesionales = new ArrayList();
       
       profesionales = profesionalRepositorio.findAll();
       
       return profesionales; 
   
   }

   
   public List<Profesional> buscarXEspecialidad(String especialidad){
   
       List<Profesional> profesionales = new ArrayList();
       
       profesionales = profesionalRepositorio.buscarPorEspecialidad(especialidad);
       
       return profesionales; 
       
   }
   public List<Profesional> buscarActivos(){
   
       List<Profesional> profesionales = new ArrayList();
       
       profesionales = profesionalRepositorio.buscarActivos();
       
       return profesionales; 
       
   }
   
    @Transactional
    public void modificarProfesional(String id, String nombreCompleto, String email,
            String clave, Long telefono, Rol rol, MultipartFile archivo,
            String disponibilidad, List<String> obraSocial, Modalidad modalidad,
            String ubicacion, String especialidad, Double tarifa, Boolean activo) throws MiException {

       
        Optional<Profesional> respuesta = profesionalRepositorio.findById(id);
       
        if (respuesta.isPresent()) {

            Profesional profesional = respuesta.get();
            
           String idImagen = null;

            if (profesional.getImagen() != null) {
                idImagen = profesional.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            profesional.setImagen(imagen);
            profesional.setNombreCompleto(nombreCompleto);
            profesional.setTarifa(tarifa);
            profesional.setDisponibilidad(disponibilidad);
            profesional.setActivo(Boolean.TRUE);//cambiar
            profesional.setClave(clave);
            profesional.setRol(rol);
            profesional.setEspecialidad(especialidad);
            profesional.setFechaAlta(new Date());
            profesional.setReputacion(tarifa);
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
    
    private void validar(String nombreCompleto, String email,
            String clave, Long telefono, Rol rol,
            Jornada disponibilidad, List<String> obraSocial, Modalidad modalidad,
            String ubicacion, String especialidad, Double tarifa) throws MiException {

        if (nombreCompleto.isEmpty() || nombreCompleto == null) {
            throw new MiException("el nombre no puede estar vacio");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede estar vacio");
        }
        if (clave.isEmpty() || clave == null || clave.length() <= 5) {
            throw new MiException("el clave  no puede estar vacio");
        }
        if (telefono == null || clave.length() <= 5) {
            throw new MiException("el telefono no puede estar vacio");
        }
        if (obraSocial == null) {
            throw new MiException("el obrasocial no puede estar vacio");
        }
        if (modalidad == null) {
            throw new MiException("el modalidad no puede estar vacio");
        }
        if (tarifa == null) {
            throw new MiException("el tarifa no puede estar vacio");
        }
        if (especialidad == null) {
            throw new MiException("el especialidad no puede estar vacio");
        }
        if (ubicacion == null) {
            throw new MiException("el ubicacion  no puede estar vacio");
        }
        if (disponibilidad == null) {
            throw new MiException("el disponibilidad no puede estar vacio");
        }
        if (rol == null) {
            throw new MiException("el rol no puede estar vacio");
        }
//        if (!password.equals(clave2)) {
//            throw new MiException("los password tienen que ser iguales");
//        }

    }
    
}
