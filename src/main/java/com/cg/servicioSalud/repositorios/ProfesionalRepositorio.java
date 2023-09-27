/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cg.servicioSalud.repositorios;

import com.cg.servicioSalud.entidades.Profesional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
/**
 *
 * @author JAVIER ESPINDOLA
 */

@Repository
public interface ProfesionalRepositorio extends JpaRepository<Profesional, String>{
    // @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    @Query("SELECT p FROM Profesional p WHERE p.especialidad = :especialidad AND p.activo = true")
    public List <Profesional> buscarPorEspecialidad(@Param("especialidad") String especialidad);
}
