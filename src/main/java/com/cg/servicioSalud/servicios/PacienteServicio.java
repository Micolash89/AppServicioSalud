/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cg.servicioSalud.servicios;

import com.cg.servicioSalud.entidades.Imagen;
import com.cg.servicioSalud.entidades.Paciente;
import com.cg.servicioSalud.enumeraciones.Jornada;
import com.cg.servicioSalud.enumeraciones.Modalidad;
import com.cg.servicioSalud.enumeraciones.Rol;
import com.cg.servicioSalud.exepciones.MiException;
import com.cg.servicioSalud.repositorios.PacienteRepositorio;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author JAVIER ESPINDOLA
 */
@Service
public class PacienteServicio {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void crearProfesional(String nombreCompleto, String email,
            String clave, Long telefono, Rol rol, MultipartFile archivo,
            String obraSocial) throws MiException {

        //falta validae
        
        Paciente paciente = new Paciente();

        paciente.setNombreCompleto(nombreCompleto);
        paciente.setClave(clave);
        paciente.setRol(rol);

        paciente.setFechaAlta(new Date());

        paciente.setObraSocial(obraSocial);

        paciente.setTelefono(telefono);
        paciente.setEmail(email);

        Imagen imagen = imagenServicio.guardar(archivo);
        paciente.setImagen(imagen);

        pacienteRepositorio.save(paciente);

    }

    
    
    //falta la funcion validad
}
