/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import entidad.Usuario;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import persistencia.ControladoraPersistencia;

/**
 *
 * @author es982
 */
public class ControladoraUsuario {

    ControladoraPersistencia controlPersis = new ControladoraPersistencia();
    

    //Metodo crearUsuario
    public void crearUsuario(Usuario user) {
        controlPersis.crearUsuario(user);
    }

    //Metodo eliminarPersona
    public void eliminarUsuario(Integer id) {
        controlPersis.eliminarUsuario(id);
    }

    //Metodo editarPersona
    public void editarUsuario(Usuario user) {
        controlPersis.editarUsuario(user);
    }

    //Metodo buscarPersonaPorId
    public Usuario buscarPorId(Integer id) {
        return controlPersis.buscarUsuarioPorId(id);
    }

    //Metodo listarPersonas
    public ArrayList<Usuario> listarUsuarios() {
        return controlPersis.listarUsuario();
    }

    //Carga los archivos de la base de datos en un arrayList y luego comprueba
    public String buscarCorreoOUser(String userCorreo) {
        ArrayList<Usuario> listaUsuario = listarUsuarios();
        String auxCorreo = "";
        
        if (listaUsuario != null && !listaUsuario.isEmpty()) {
            
            for (Usuario u : listaUsuario) {
                
                if (!u.getCorreo().equals(userCorreo)) {

                    if (!u.getNombreUsuario().equals(userCorreo)) {
                        System.out.println("Buscando");

                    } else{
                        auxCorreo=u.getCorreo();
                        return auxCorreo;
                    }
                } else {
                    auxCorreo = u.getCorreo();
                    return auxCorreo;
                }                           
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro ningun Usuario Registrado", "ERROR USUARIO", JOptionPane.ERROR_MESSAGE);
        }
        return auxCorreo;
    }

}
