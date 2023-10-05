/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cg.servicioSalud.repositorios;

import com.cg.servicioSalud.entidades.Turno;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, String>{
    
    @Query("SELECT t FROM Turno t WHERE t.profesional.id = :id")
    public List<Turno> buscarPorProfesional(@Param("id") String id);
    
    
    @Query("SELECT t FROM Turno t WHERE t.profesional.id =:id and t.fecha =:fecha")
    public Turno buscarDisponibilidad(@Param("id") String id, @Param("fecha") Date fecha);
    
    
    @Query("SELECT t FROM Turno t WHERE t.profesional.id =:id and t.estado ='PENDIENTE'")
    public List <Turno> listarTurnosPorP(@Param("id") String id);
    
    @Query("SELECT t FROM Turno t WHERE t.profesional.id =:id and t.estado ='COMPLETADO'")
    public List <Turno> listarTurnosPorPCompletos(@Param("id") String id);
}
