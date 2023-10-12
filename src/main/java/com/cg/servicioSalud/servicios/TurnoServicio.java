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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

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

        Paciente paciente = pacienteRepositorio.findById("8613f59c-cba3-4c6c-86c3-762a70f26d2e").get();

        String cadenaDia = dia.substring(0, 29);

        Integer horario = Integer.valueOf(dia.substring(dia.length() - 2));

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
        turno.setHorario(horario);

        return (Turno) turnoRepositorio.save(turno);
    }

    @Transactional
    public Turno modificarTurno(String idTurno, String dia) {

        Turno turno = getOne(idTurno);

        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            Date fecha = formatoFecha.parse(dia);
            turno.setFecha(fecha); // arregla esto o cambiar a atributo string /// depues veo q hacer con esto
        } catch (ParseException ex) {
            Logger.getLogger(TurnoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return turnoRepositorio.save(turno);

    }

    public Turno buscarDisponibilidad(String idProfesional, Date fecha) {

        return turnoRepositorio.buscarDisponibilidad(idProfesional, fecha);

    }

    public List<Turno> listarTurnosPorP(String idProfesional) {

        return turnoRepositorio.listarTurnosPorP(idProfesional);
    }

    public List<Turno> listarTurnosPorPa(String idPaciente) {

        return turnoRepositorio.listarTurnosPorPa(idPaciente);
    }

    public List<Turno> listarTurnosPorPCompletos(String idProfesional) {

        return turnoRepositorio.listarTurnosPorPCompletos(idProfesional);
    }

    public List<Turno> listarTurnosPorPaCompletos(String idPaciente) {

        return turnoRepositorio.listarTurnosPorPaCompletos(idPaciente);
    }

    public Turno getOne(String id) {

        return turnoRepositorio.findById(id).get();

    }

    @Transactional
    public void cancelarTurno(String id) {

        Turno turno = getOne(id);

        turno.setEstado(Estado.CANCELADO);

        turnoRepositorio.save(turno);

    }

    @Transactional
    public void completarTurno(String id) {

        Turno turno = getOne(id);

        turno.setEstado(Estado.COMPLETADO);

        turnoRepositorio.save(turno);

    }

//    public List<String> listarTrunos2(Profesional profesional){
//    
//    }
    public List<String> listarTurnos(Profesional profesional) {///cambiarlo a id

        List<String> calendario = new ArrayList();

        //"0m1m2m3m4m5m6m"
        List<Turno> turnos = turnoRepositorio.listarTurnosPorP(profesional.getId());//tengo los turno en estado pendiente

        System.out.println("-----------------------------");
        for (Turno turno : turnos) {
            System.out.println(turno);
        }
        //hacer calendario del profesional personalizado
        for (int i = 1; i <= 60; i++) {

            Date dia = new Date();
            // String horario;
            // Obtener la representación en milisegundos de la fecha actual
            long tiempoEnMilisegundos = dia.getTime();

            // Sumar un día en milisegundos (86400000 milisegundos en un día)
            long unDiaEnMilisegundos = 86400000 * i;
            tiempoEnMilisegundos += unDiaEnMilisegundos;

            // Crear una nueva instancia de Date a partir del tiempo en milisegundos modificado
            Date nuevoDia = new Date(tiempoEnMilisegundos);
            nuevoDia.setHours(0);
            nuevoDia.setMinutes(0);
            nuevoDia.setSeconds(0);
            Integer j = nuevoDia.getDay();//0-1-2-3-4-5-6

            String horario = String.valueOf(profesional.getDisponibilidad().indexOf(j.toString()) + 1);

            int indice = Integer.parseInt(horario);

            char horarioChar = profesional.getDisponibilidad().charAt(indice);

            switch (horarioChar) {
                case 'm':
                    horario = "mañana";
                    break;
                case 't':
                    horario = "tarde";
                    break;
                case 'n':
                    horario = "noche";

            }

            //horario = disponibilidad(profesional.getDisponibilidad(), j.toString());
            if (profesional.getDisponibilidad().contains(j.toString())) {

                List<Integer> numeros = buscarTurno(nuevoDia, horarioChar, turnos);

                for (Integer numero : numeros) {

                    calendario.add(nuevoDia.toString() + " " + horario + " " + ((numero.toString().length() == 1) ? "0" + numero : numero));
                }
            }
        }//PREGUNTAR POR EL DIA Q SEA IGUAL, PREGUNTAR POR EL HORARIO Q SEA IGUAL SI EXISTE ELIMINARLO DE LA LISTA DE TURNOS, MARCAR CON UN VECTOR 1234 Y RECORRER EL Q NO ESTA, CONESO LO ARMO EL CALENDARIO ESE DIA CON LOS Q NO ESTAN

        return calendario;
    }

    ///horario 1
    private List<Integer> buscarTurno(Date dia, char horario, List<Turno> turnos) {

        List<Integer> numeroTurno = crearNumero(horario);//lista de horaios m = 0,1,2,3

        Iterator ti = turnos.iterator();

        while (ti.hasNext()) {

            Turno t = (Turno) ti.next();

            if (dia.getYear() == t.getFecha().getYear() && dia.getMonth() == t.getFecha().getMonth() && dia.getDate() == t.getFecha().getDate()) {//mañana lunes 0
                Iterator ni = numeroTurno.iterator();
                while (ni.hasNext()) {
                    Integer next = (Integer) ni.next();
                 
                    if (next == t.getHorario()) {
                        ni.remove();
                    }
                }
                ti.remove();
            }
        }
        System.out.println(numeroTurno);

        return numeroTurno;
    }

    private List<Integer> crearNumero(char horario) {
        List<Integer> num = new ArrayList<>();

        int inicio, fin;

        switch (horario) {
            case 'm':
                inicio = 0;
                fin = 4;
                break;
            case 't':
                inicio = 4;
                fin = 8;
                break;
            case 'n':
                inicio = 8;
                fin = 12;
                break;
            case 'j':
                inicio = 0;
                fin = 8;
                break;
            case 'k':
                inicio = 4;
                fin = 12;
                break;
            case 'l'://caso extraordinario
                inicio = 0;
                fin = 12;
                break;

            default:
                inicio = fin = 0;
        }

        for (int i = inicio; i < fin; i++) {

            num.add(i);

        }

        return num;
    }

}
