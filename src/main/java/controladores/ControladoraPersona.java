/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import entidad.Persona;
import java.util.ArrayList;
import persistencia.ControladoraPersistencia;

/**
 *
 * @author es982
 */
public class ControladoraPersona {
    ControladoraPersistencia controlPersis=new ControladoraPersistencia();
    
    //Metodo crearPersona
    public void crearPersona (Persona pers){
        controlPersis.crearPersona(pers);
    }
    
    //Metodo eliminarPersona
    public void eliminarPersona(Integer id){
        controlPersis.eliminarPersona(id);
    }
    
    //Metodo editarPersona
    public void editarPersona(Persona pers){
        controlPersis.editarPersona(pers);
    }
    
    //Metodo buscarPersonaPorId
    public Persona buscarPorId(Integer id){
        return controlPersis.buscarPersonaPorId(id);
    }
    
    //Metodo listarPersonas
    public ArrayList<Persona> listarPersonas(){
        return controlPersis.listarPersonas();
    }
    
    public boolean existePersona(Persona p) {
        ArrayList<Persona> lista = listarPersonas();

        for (Persona existente : lista) {
            if (existente.getNombre().equalsIgnoreCase(p.getNombre())
                    && existente.getApellidoP().equalsIgnoreCase(p.getApellidoP())
                    && existente.getApellidoM().equalsIgnoreCase(p.getApellidoM())) {
                return true;
            }
        }
        return false;
    }
}
