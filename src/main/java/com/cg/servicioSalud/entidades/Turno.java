/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cg.servicioSalud.entidades;

import com.cg.servicioSalud.enumeraciones.Estado;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author JAVIER ESPINDOLA
 */
@Entity
@Data
public class Turno {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    @OneToOne
    private Paciente paciente;
    
    @OneToOne
    private Profesional profesional;
    
    @Enumerated(EnumType.STRING)
    private Estado estado;// cancelado - pendiente - libre - completado
    
    private String motivo;
    
    private Double precioFinal;
    
}
