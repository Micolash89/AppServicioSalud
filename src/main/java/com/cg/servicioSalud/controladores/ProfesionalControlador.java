package com.cg.servicioSalud.controladores;

import com.cg.servicioSalud.entidades.Turno;
import com.cg.servicioSalud.enumeraciones.Modalidad;
import com.cg.servicioSalud.enumeraciones.Rol;
import com.cg.servicioSalud.exepciones.MiException;
import com.cg.servicioSalud.servicios.HistorialClinicoServicio;
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

@Controller
@RequestMapping("/profesional")
public class ProfesionalControlador {

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Autowired
    private TurnoServicio turnoServicio;
    
    @Autowired
    private HistorialClinicoServicio historialClinicoServicio;
    
    @GetMapping("/registrar")
    public String registrar() {

        return "profesional_form.html";

    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombreCompleto, @RequestParam String email,
            @RequestParam String clave, @RequestParam Long telefono, @RequestParam(required = false) MultipartFile archivo,
            @RequestParam(required = false) String disponibilidad,
            @RequestParam(required = false) String modalidad,
            @RequestParam String ubicacion, @RequestParam String especialidad, @RequestParam Double tarifa,
            @RequestParam(required = false) String lunes, @RequestParam(required = false) String martes, @RequestParam(required = false) String miercoles,
            @RequestParam(required = false) String jueves, @RequestParam(required = false) String viernes, @RequestParam(required = false) String sabado, @RequestParam(required = false) String domingo,
            @RequestParam(required = false) String[] obraSocial, @RequestParam(required = false) String otraObraSocial) {
//    public String registro(@RequestParam String nombreCompleto, @RequestParam String email,
//            @RequestParam String clave, @RequestParam Long telefono,@RequestParam(required = false) MultipartFile archivo, 
//            @RequestParam(required = false) String disponibilidad, @RequestParam(required = false) List<String> obraSocial,
//            @RequestParam(required = false) String modalidad,
//            @RequestParam String ubicacion, @RequestParam String especialidad, @RequestParam Double tarifa) {

        List<String> obra = new ArrayList();

        for (String os : obraSocial) {
            obra.add(os);
        }

        for (String otra : otraObraSocial.replaceAll(" ", "").split(",")) {
                if(!otra.equals(""))
                    obra.add(otra);
        }

        disponibilidad = (lunes + martes + miercoles + jueves + viernes + sabado + domingo).replaceAll("null", "");//0d2s0a3d

        try {
            profesionalServicio.crearProfesional(nombreCompleto, email, clave, telefono, Rol.USER,
                    archivo, disponibilidad, obra, Modalidad.PRECENCIAL,
                    ubicacion, especialidad, tarifa);
        } catch (MiException ex) {
            Logger.getLogger(ProfesionalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index.html";
    }
    
    
     @GetMapping("/mis-turnos")
     public String listarTurnos(ModelMap modelo  ){ ///hhttp session hacerlo lo voy a caster con otro id
         
         //hacer lo del sesion para profesional
         
         String idProfesional = "e910573f-be34-4245-9976-0d670eb306ee";
         
         List <Turno> turnos = turnoServicio.listarTurnosPorP(idProfesional);
         List <Turno> turnosC = turnoServicio.listarTurnosPorPCompletos(idProfesional);
         
         modelo.addAttribute("turnos", turnos);
         modelo.addAttribute("turnosC", turnosC);
         
         return "lista_turnos_profesional.html";
         
     }
     
     @GetMapping("/cancelar-turno/{id}")
     public String cancelarTurno(@PathVariable String id){
         
         turnoServicio.cancelarTurno(id);
         
         return "redirect:../mis-turnos";
     
     }
     
     @GetMapping("/modificar-turno/{id}")
     public String modificarTurno(@PathVariable String id, ModelMap modelo){
         
         Turno turno = turnoServicio.getOne(id);
         
         modelo.addAttribute("turno", turno);
         
         
         return "modificar_turno.html";
     
     }
     
     @PostMapping("/completar-turno")
     public String completarTurno( @RequestParam String idTurno,  @RequestParam String nota, ModelMap modelo){
     
         turnoServicio.completarTurno(idTurno);
         
         historialClinicoServicio.crearHistorialClinico(idTurno, nota);
         
         List <Turno> turnos = turnoServicio.listarTurnosPorP(turnoServicio.getOne(idTurno).getProfesional().getId());
         List <Turno> turnosC = turnoServicio.listarTurnosPorPCompletos(turnoServicio.getOne(idTurno).getProfesional().getId());
         
         modelo.addAttribute("turnos", turnos);
         modelo.addAttribute("turnosC", turnosC);
         
         return "lista_turnos.html";
     
     }
     
     @PostMapping("/cambiar-turno")
      public String cambiarTurno( @RequestParam String idTurno,  @RequestParam String dia, ModelMap modelo){
     
         turnoServicio.modificarTurno(idTurno,dia);
         
         List <Turno> turnos = turnoServicio.listarTurnosPorP(turnoServicio.getOne(idTurno).getProfesional().getId());
         List <Turno> turnosC = turnoServicio.listarTurnosPorPCompletos(turnoServicio.getOne(idTurno).getProfesional().getId());
         
         modelo.addAttribute("turnos", turnos);
         modelo.addAttribute("turnosC", turnosC);
         
         return "lista_turnos.html";
          
      }
}