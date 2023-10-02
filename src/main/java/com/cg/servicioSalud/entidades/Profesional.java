/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cg.servicioSalud.entidades;

import com.cg.servicioSalud.enumeraciones.Jornada;
import com.cg.servicioSalud.enumeraciones.Modalidad;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Profesional extends Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private Double reputacion;

    //JORANADA CAMBIARLO POR UNA LISTA MAS ADELANTE
    @Enumerated(EnumType.STRING)
    private Jornada disponibilidad;//lunesm
    
    @Enumerated(EnumType.STRING)
    private Modalidad modalidad;

    private String ubicacion;

    @ElementCollection
    private List<String> obrasSociales; // id - string / clave foranea(id fprofesional)

    private String especialidad;
    
    private Double tarifa;
    
    private Boolean activo; //true = alta; false = baja

}
