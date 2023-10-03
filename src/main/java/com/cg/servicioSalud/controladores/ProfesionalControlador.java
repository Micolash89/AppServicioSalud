package com.cg.servicioSalud.controladores;

import com.cg.servicioSalud.entidades.Imagen;
import com.cg.servicioSalud.enumeraciones.Jornada;
import com.cg.servicioSalud.enumeraciones.Modalidad;
import com.cg.servicioSalud.enumeraciones.Rol;
import com.cg.servicioSalud.exepciones.MiException;
import com.cg.servicioSalud.servicios.ProfesionalServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Multipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestParam String clave, @RequestParam Long telefono, @RequestParam(required = false) MultipartFile archivo,
            @RequestParam(required = false) String disponibilidad, @RequestParam(required = false) List<String> obraSocial,
            @RequestParam(required = false) String modalidad,
            @RequestParam String ubicacion, @RequestParam String especialidad, @RequestParam Double tarifa,
            @RequestParam(required = false) String lunes, @RequestParam(required = false) String martes, @RequestParam(required = false) String miercoles,
            @RequestParam(required = false) String jueves, @RequestParam(required = false) String viernes, @RequestParam(required = false) String sabado, @RequestParam(required = false) String domingo) {
//    public String registro(@RequestParam String nombreCompleto, @RequestParam String email,
//            @RequestParam String clave, @RequestParam Long telefono,@RequestParam(required = false) MultipartFile archivo, 
//            @RequestParam(required = false) String disponibilidad, @RequestParam(required = false) List<String> obraSocial,
//            @RequestParam(required = false) String modalidad,
//            @RequestParam String ubicacion, @RequestParam String especialidad, @RequestParam Double tarifa) {

        List<String> obra = new ArrayList();

            disponibilidad = domingo+ lunes+martes+miercoles+jueves+viernes+sabado;
        
        try {
            profesionalServicio.crearProfesional(nombreCompleto, email, clave, telefono, Rol.USER,
                    archivo, disponibilidad, obra, Modalidad.PRECENCIAL,
                    ubicacion, especialidad, tarifa);
        } catch (MiException ex) {
            Logger.getLogger(ProfesionalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index.html";

    }

}
