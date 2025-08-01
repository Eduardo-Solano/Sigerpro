/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import entidad.DocenteProyecto;
import entidad.DocenteProyectoPK;
import java.util.ArrayList;
import persistencia.ControladoraPersistencia;

/**
 *
 * @author es982
 */
public class ControladoraProyectoDocente {
    ControladoraPersistencia controlP=new ControladoraPersistencia();
    
    public void guardarDocenteProyecto(DocenteProyecto dp){
        controlP.guardarProyectoDocente(dp);
    }
    
    public void editarProyectoDocente(DocenteProyecto dp){
        controlP.editarProyectoDocente(dp);
    }
    
    public DocenteProyecto buscarPorPK(DocenteProyectoPK dPK){
        return controlP.buscarDocenteProyectoPorPK(dPK);
    }
    
    public ArrayList<DocenteProyecto> listarDocentesProyecto(){
       return controlP.listarDocenteProyectos();
    }
}
