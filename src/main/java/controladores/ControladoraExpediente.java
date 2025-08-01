/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import entidad.Expediente;
import java.util.ArrayList;
import persistencia.ControladoraPersistencia;

/**
 *
 * @author es982
 */
public class ControladoraExpediente {
    
    ControladoraPersistencia controlP=new ControladoraPersistencia();
    
    public void guardarExpediente(Expediente exp){
        controlP.guardarExpediente(exp);
    }
    
    public void editarExpediente(Expediente exp){
        controlP.editarExpediente(exp);
    }
    
    public void eliminarExpediente(Integer idE ){
        controlP.eliminarExpediente(idE);
    }
    
    public Expediente buscarExpedientePorId(Integer idE){
       return controlP.buscarExpedientePorId(idE);
    }
    
    public ArrayList<Expediente> listaExpediente(){
        return controlP.listarExpediente();
    }
    
}
