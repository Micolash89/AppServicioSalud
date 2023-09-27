/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cg.servicioSalud.repositorios;

import com.cg.servicioSalud.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author JAVIER ESPINDOLA
 */
@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, String>{
   
}
