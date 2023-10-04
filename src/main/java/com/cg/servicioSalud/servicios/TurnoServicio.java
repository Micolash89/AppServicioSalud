package com.cg.servicioSalud.servicios;

import com.cg.servicioSalud.entidades.Paciente;
import com.cg.servicioSalud.entidades.Profesional;
import com.cg.servicioSalud.entidades.Turno;
import com.cg.servicioSalud.enumeraciones.Estado;
import com.cg.servicioSalud.repositorios.PacienteRepositorio;
import com.cg.servicioSalud.repositorios.ProfesionalRepositorio;
import com.cg.servicioSalud.repositorios.TurnoRepositorio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurnoServicio {

    @Autowired
    private TurnoRepositorio turnoRepositorio;

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Transactional
    public Turno crearTurno(String idProfesional, String dia, String motivo, Double precioFinal) {//falta el id del paciente paciente provicional cdd17546-5edc-4d08-9626-59f2bc365ee8

        Turno turno = new Turno();

        Profesional profesional = profesionalRepositorio.findById(idProfesional).get();//hacerlooptional y verificar si existe

        Paciente paciente = pacienteRepositorio.findById("cdd17546-5edc-4d08-9626-59f2bc365ee8").get();

        System.out.println(dia);

        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            Date fecha = formatoFecha.parse(dia);
            turno.setFecha(fecha); // arregla esto o cambiar a atributo string /// depues veo q hacer con esto
        } catch (ParseException ex) {
            Logger.getLogger(TurnoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        turno.setMotivo(motivo);
        turno.setPaciente(paciente);
        turno.setProfesional(profesional);
        turno.setPrecioFinal(precioFinal);
        turno.setEstado(Estado.PENDIENTE);

        return (Turno) turnoRepositorio.save(turno);
    }

    public Turno buscarDisponibilidad(String idProfesional, Date fecha) {

        return turnoRepositorio.buscarDisponibilidad(idProfesional, fecha);

    }

    public List<Turno> listarTurnosPorP(String idProfesional) {

        return turnoRepositorio.listarTurnosPorP(idProfesional);
    }

    public Turno getOne(String id) {

        return turnoRepositorio.findById(id).get();

    }

    @Transactional
    public void cancelarTurno(String id){
    
        Turno turno = getOne(id);
        
        turno.setEstado(Estado.CANCELADO);
    
        turnoRepositorio.save(turno);
        
    }
    @Transactional
    public void completarTurno(String id){
    
        Turno turno = getOne(id);
        
        turno.setEstado(Estado.COMPLETADO);
    
        turnoRepositorio.save(turno);
        
    }
    
}
