/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import controladores.ControladoraUsuario;
import entidad.Usuario;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author es982
 */


public class EncriptarPassword {
    
    public static void main(String[] args) {
        // Ejemplo de uso
        String password = "admin";
        // Hasheamos la contraseña antes de guardarla
        String passwordEncriptada = encriptarPassword(password);
        System.out.println("Contraseña encriptada para guardar en base de datos: " + passwordEncriptada);
        
        //Insertar usuario en la base de datos
        Usuario user=new Usuario();
        user.setNombreUsuario("Sabas");
        user.setCorreo("sabas@gmail.com");
        user.setContraseniaHash(passwordEncriptada);
        ControladoraUsuario controlU=new ControladoraUsuario();
        controlU.crearUsuario(user);
        System.out.println("Usuario creado: "+user);
        // Para verificar la contraseña durante el login:
        boolean coincide = verificarPassword("admin1", passwordEncriptada);
        System.out.println("¿La contraseña ingresada es correcta?: " + coincide);
    }
    
    // Método para generar el hash seguro de la contraseña
    public static  String encriptarPassword(String passwordPlano) {
        return BCrypt.hashpw(passwordPlano, BCrypt.gensalt());
    }

    // Método para verificar si la contraseña ingresada coincide con el hash guardado
    public static boolean verificarPassword(String passwordPlano, String hashGuardado) {
        return BCrypt.checkpw(passwordPlano, hashGuardado);
    }


}
