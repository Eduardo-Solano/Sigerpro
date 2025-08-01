/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author jc389
 */
public class ValidadorCampos {
    public void rellenarArreglo(){
        
    }
    
    /*El método se encarga de recorrer los strings entrantes en un arreglo para validar
    que cada uno tenga texto, si en algun caso llega uno vacio el metodo retorna false y listo.*/
    public static boolean validarTodosLlenos(String []  nombres){
        for (String nombre: nombres){
            if(nombre.isEmpty()){
                return false;
            }
        }
        return true;
    }
    //El metodo valida que el numero tenga 8 digitos. Ni mas ni menos
    public static boolean validarNumero(String numero){
        if (numero.matches("\\d{8}")){
            return true;
        }else {
            return false;
        }
    }
    
    //El metodo valida que el numero tenga 8 digitos. Ni mas ni menos
    public static boolean validarNumeroT(String numero){
        if (numero.matches("\\d{4}")){
            return true;
        }else {
            return false;
        }
    }
    
    public static boolean validarNumeroTelefinico(String numero){
        if (numero.matches("\\d{10,15}")){
            return true;
        }else {
            return false;
        }
    }
    public static boolean validarSemestre(String semestre){
        int semes= Integer.parseInt(semestre);
        if(semes>8 || semes <13){
            return true;
        }
        else{
            return false;
        }
    }
    
    /*El metodo más complejo dee sta clase porque valida el formato de un nombre o apellido
    aunque no es 100% correcto, pero se tratavde manejar la mayor cantidad de excepciones 
    para un nombre como consonantes repetidas consecutivamente, al igual que vocales*/
    public static boolean validarNombre(String nombre) {
        String[] nombres= {
    "Leo", "Max", "Ian", "Eli", "Jhon", "Sam", "Ben",
    "Ana", "Eva", "Lia", "Mia", "Luz", "Sol", "Ada", "Isa"
};
   
    nombre = nombre.trim().replaceAll("\\s+", " "); // elimina espacios dobles
    if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
        return false; // Solo letras y espacios
    } else if(nombre.length()<3 ){
        return false;
    } 
    for (int i=0; i<nombres.length; i++){
         if (nombre.equalsIgnoreCase(nombres[i])){
        return true;
    }
    }

    String vocales = "aeiouáéíóúAEIOUÁÉÍÓÚ";
    int consecutivasVocal = 0;
    int consecutivasConsonante = 0;
    int consecutivasIguales = 0;
    char anterior = 0;

    for (int i = 0; i < nombre.length(); i++) {
        char actual = nombre.charAt(i);

        if (actual == ' ') {
            consecutivasVocal = 0;
            consecutivasConsonante = 0;
            consecutivasIguales = 0;
            anterior = 0;
            continue;
        }

        boolean esVocal = vocales.indexOf(actual) != -1;

        // Contar repetidos
        if (Character.toLowerCase(actual) == Character.toLowerCase(anterior)) {
            consecutivasIguales++;
            if (consecutivasIguales >= 2) return false; // más de 2 letras iguales
        } else {
            consecutivasIguales = 0;
        }

        // Contar vocales/consonantes seguidas
        if (esVocal) {
            consecutivasVocal++;
            consecutivasConsonante = 0;
            if (consecutivasVocal > 2) return false;
        } else {
            consecutivasConsonante++;
            consecutivasVocal = 0;
            if (consecutivasConsonante > 2) return false;
        }

        anterior = actual;
    }

    return true;
}

    public static String validar(String [] datos) {
        
        StringBuilder errores = new StringBuilder();

        
        if (!validarTodosLlenos(datos)) {
            errores.append("Rellene todos los campos\n");
        } else {
            if (!validarNumero(datos[3])) {
                errores.append("El número debe ser de 8 Dígitos numéricos\n");
            }
            if (!validarNombre(datos[0])) {
                errores.append("Ingrese un nombre con formato valido\n");
            }
            if (!validarNombre(datos[1]) || !validarNombre(datos[2])) {
                errores.append("Ingrese un apellido  con formato valido\n");
            }
            if(datos.length==6){
            int semestre= Integer.parseInt(datos[5]);
            if (semestre >13 || semestre<8){
                errores.append("Semestre inválido para residencia");
            }
        }
            
        }
        if (errores.length() > 0) {
            return errores.toString();
        } else {
            return null;
        }
    }
    
    public static String validarD(String [] datos) {
        
        StringBuilder errores = new StringBuilder();

        
        if (!validarTodosLlenos(datos)) {
            errores.append("Rellene todos los campos\n");
        } else {
            if (!validarNumeroT(datos[3])) {
                errores.append("El número debe ser de 8 Dígitos numéricos\n");
            }
            if (!validarNombre(datos[0])) {
                errores.append("Ingrese un nombre con formato valido\n");
            }
            if (!validarNombre(datos[1]) || !validarNombre(datos[2])) {
                errores.append("Ingrese un apellido  con formato valido\n");
            }
            if(datos.length==6){
            int semestre= Integer.parseInt(datos[5]);
            if (semestre >13 || semestre<8){
                errores.append("Semestre inválido para residencia");
            }
        }
            
        }
        if (errores.length() > 0) {
            return errores.toString();
        } else {
            return null;
        }
    }
    
     public static boolean validarDocente(String nombre, String apPaterno, String apMaterno, String tarjeta) {
        if (!nombre.matches("^[a-zA-ZÁÉÍÓÚáéíóúñÑ\\s]+$")) return false;
        if (!apPaterno.matches("^[a-zA-ZÁÉÍÓÚáéíóúñÑ\\s]+$")) return false;
        if (!apMaterno.matches("^[a-zA-ZÁÉÍÓÚáéíóúñÑ\\s]+$")) return false;
        if (!tarjeta.matches("^\\d{4}$")) return false;
        return true;
    }

    public static boolean validarEstudiante(String nombre, String apPaterno, String apMaterno, String numControl, String carrera, String semestreStr) {
    nombre = nombre.trim();
    apPaterno = apPaterno.trim();
    apMaterno = apMaterno.trim();
    numControl = numControl.trim().toUpperCase();
    carrera = carrera.trim();
    semestreStr = semestreStr.trim();

    if (!nombre.matches("^[a-zA-ZÁÉÍÓÚáéíóúñÑ\\s]+$")) return false;
    if (!apPaterno.matches("^[a-zA-ZÁÉÍÓÚáéíóúñÑ\\s]+$")) return false;
    if (!apMaterno.matches("^[a-zA-ZÁÉÍÓÚáéíóúñÑ\\s]+$")) return false;
    if (!numControl.matches("^(C\\d{8}|\\d{8})$")) return false;
    if (!"Ingenieria en Sistemas Computacionales".equalsIgnoreCase(carrera)) return false;
    try {
        int semestre = Integer.parseInt(semestreStr);
        if (semestre < 8 || semestre > 12) return false;
    } catch (NumberFormatException e) {
        return false;
    }

    return true;
}

    
 public static String validarDatosProyecto(String nombreProyecto, String descripcion, String numAlumnosSugeridosStr, String nombreEmpresa, String numTelefonoEmpresa) {
        StringBuilder errores = new StringBuilder();

        if (nombreProyecto == null || nombreProyecto.trim().isEmpty()) {
            errores.append("- El nombre del proyecto no puede estar vacío.\n");
//        } else if (nombreProyecto.length() < 5 || nombreProyecto.length() > 30) {
            errores.append("- El nombre del proyecto debe tener entre 5 y 30 caracteres.\n");
        }

        if (descripcion == null || descripcion.trim().isEmpty()) {
            errores.append("- La descripción del proyecto no puede estar vacía.\n");
        } else if (descripcion.length() < 10 || descripcion.length() > 50) { 
            errores.append("- La descripción del proyecto debe tener entre 10 y 50 caracteres.\n");
        }

        try {
            int numAlumnos = Integer.parseInt(numAlumnosSugeridosStr);
            if (numAlumnos < 1 || numAlumnos > 5) { 
                errores.append("- El número de alumnos sugeridos debe ser entre 1 y 5.\n");
            }
        } catch (NumberFormatException e) {
            errores.append("- El número de alumnos sugeridos debe ser un numero valido.\n");
        }

        if (nombreEmpresa == null || nombreEmpresa.trim().isEmpty()) {
            errores.append("- El nombre de la empresa no puede estar vacío.\n");
        } else if (nombreEmpresa.length() < 3 || nombreEmpresa.length() > 30) { 
            errores.append("- El nombre de la empresa debe tener entre 3 y 30 caracteres.\n");
        }

        if (!validarNumeroTelefinico(numTelefonoEmpresa)) { 
            errores.append("- El número de teléfono de la empresa debe ser numérico y tener entre 10 y 15 dígitos.\n"); 
        }

        if (errores.length() > 0) {
            return errores.toString();
        } else {
            return null; 
        }
    }
    
}
