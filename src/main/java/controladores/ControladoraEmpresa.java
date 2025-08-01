package controladores;

import entidad.Empresa;
import entidad.Proyecto;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import persistencia.ControladoraPersistencia;

/**
 *
 * @author es982
 */
public class ControladoraEmpresa {
    
    ControladoraPersistencia controlPersis = new ControladoraPersistencia();
    
    public void guardarEmpresa(Empresa emp){
        controlPersis.guardarEmpresa(emp);
    }
    
    public Empresa buscarEmpresaPorNombre(String nombre){
        return controlPersis.buscarEmpresaPorNombre(nombre);
    }
    
    public void eliminarEmpresa(Integer id){
        controlPersis.eliminarEmpresa(id);
    }
    
    public void editarEmpresa(Empresa emp){
        controlPersis.editarEmpresa(emp);
    }
    
    public Empresa buscarEmpresaPorId(Integer id){
        return controlPersis.buscarEmpresaPorId(id);
    }
    
    public ArrayList<Empresa> listarEmpresas(){
        return controlPersis.listarEmpresas();
    }

    // Método para verificar si una empresa ya existe por nombre (ignorando mayúsculas/minúsculas)
    public boolean existeEmpresa(Empresa e) {
        ArrayList<Empresa> lista = listarEmpresas();

        for (Empresa existente : lista) {
            if (existente.getRfc().equalsIgnoreCase(e.getRfc())) {
                return true;
            }
        }
        return false;
    }
    
    // Método para verificar si una empresa ya existe por nombre (ignorando mayúsculas/minúsculas)
    public boolean existeEmpresaNombre(Empresa e) {
    ArrayList<Empresa> lista = listarEmpresas();
    for (Empresa existente : lista) {
        if (existente.getNombreEmpresa().trim().equalsIgnoreCase(e.getNombreEmpresa().trim())) {
            return true;
        }
    }
    return false;
}

    
    public boolean existeNumeroDeTelefono(String numero){
        ArrayList<Empresa> listaEmpresa=listarEmpresas();
        for(Empresa emp: listaEmpresa){
            if(emp.getNumeroTelefonico().equals(numero)){
            return true;
        }
        }
    
    return false;
    }
}
