package Test;

import controladores.ValidadorCampos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorCamposTest {

    @Test
    void testValidarTodosLlenos() {
        assertTrue(ValidadorCampos.validarTodosLlenos(new String[]{"Juan", "Perez", "Lopez"}));
        assertFalse(ValidadorCampos.validarTodosLlenos(new String[]{"Juan", "", "Lopez"}));
        assertFalse(ValidadorCampos.validarTodosLlenos(new String[]{"", "", ""}));
    }

    @Test
    void testValidarNumero() {
        assertTrue(ValidadorCampos.validarNumero("12345678"));
        assertFalse(ValidadorCampos.validarNumero("1234567"));
        assertFalse(ValidadorCampos.validarNumero("abcdefgh"));
        assertFalse(ValidadorCampos.validarNumero("123456789"));
    }

    @Test
    void testValidarNumeroTelefinico() {
        assertTrue(ValidadorCampos.validarNumeroTelefinico("1234567890"));
        assertTrue(ValidadorCampos.validarNumeroTelefinico("123456789012345"));
        assertFalse(ValidadorCampos.validarNumeroTelefinico("123456789")); // 9 dígitos, muy corto
        assertFalse(ValidadorCampos.validarNumeroTelefinico("1234567890123456")); // 16 dígitos, muy largo
        assertFalse(ValidadorCampos.validarNumeroTelefinico("12345abcde"));
    }

    @Test
    void testValidarSemestre() {
        assertTrue(ValidadorCampos.validarSemestre("10")); // >8 || <13, es true
        assertTrue(ValidadorCampos.validarSemestre("7"));  // <13 es true
        assertTrue(ValidadorCampos.validarSemestre("14")); // >8 es true
        // Nota: la lógica del método está al revés de lo típico. 
    }

    @Test
    void testValidarNombre() {
        assertTrue(ValidadorCampos.validarNombre("Leo"));
        assertTrue(ValidadorCampos.validarNombre("Juan"));
        assertFalse(ValidadorCampos.validarNombre("Juaann")); // doble n (más de 2 iguales)
        assertFalse(ValidadorCampos.validarNombre("Aaaa"));   // triple a
        assertFalse(ValidadorCampos.validarNombre("Maaario")); // triple a
        assertFalse(ValidadorCampos.validarNombre("Ju@n"));   // símbolo no permitido
        assertFalse(ValidadorCampos.validarNombre("Ju"));     // menos de 3 letras
    }

    @Test
    void testValidar() {
        // Caso válido
        String[] datos = {"Juan", "Perez", "Lopez", "12345678"};
        assertNull(ValidadorCampos.validar(datos));

        // No todos llenos
        String[] incompleto = {"", "Perez", "Lopez", "12345678"};
        assertTrue(ValidadorCampos.validar(incompleto).contains("Rellene todos los campos"));

        // Número no válido
        String[] malNum = {"Juan", "Perez", "Lopez", "123"};
        assertTrue(ValidadorCampos.validar(malNum).contains("El número debe ser de 8 Dígitos numéricos"));

        // Nombre no válido
        String[] malNom = {"Aaa", "Perez", "Lopez", "12345678"};
        assertTrue(ValidadorCampos.validar(malNom).contains("Ingrese un nombre con formato valido"));

        // Apellido no válido
        String[] malApe = {"Juan", "Peeerez", "Lopez", "12345678"};
        assertTrue(ValidadorCampos.validar(malApe).contains("Ingrese un apellido  con formato valido"));

        // Semestre inválido (6 campos)
        String[] malSemestre = {"Juan", "Perez", "Lopez", "12345678", "carrera", "7"};
        assertTrue(ValidadorCampos.validar(malSemestre).contains("Semestre inválido para residencia"));
    }

    @Test
    void testValidarDocente() {
        assertTrue(ValidadorCampos.validarDocente("Juan", "Perez", "Lopez", "1234"));
        assertFalse(ValidadorCampos.validarDocente("Juan3", "Perez", "Lopez", "5678"));
        assertFalse(ValidadorCampos.validarDocente("Juan", "Perez@", "Lopez", "3456"));
        assertFalse(ValidadorCampos.validarDocente("Juan", "Perez", "Lopez", "123")); // solo 7 dígitos
        assertFalse(ValidadorCampos.validarDocente("Juan", "Perez", "Lopez", "abcd")); // letras
    }

    @Test
    void testValidarEstudiante() {
        assertTrue(ValidadorCampos.validarEstudiante("Juan", "Perez", "Lopez", "C12345678", "Ingenieria en Sistemas Computacionales", "9"));
        assertTrue(ValidadorCampos.validarEstudiante("Juan", "Perez", "Lopez", "12345678", "Ingenieria en Sistemas Computacionales", "12"));
        assertFalse(ValidadorCampos.validarEstudiante("Juan3", "Perez", "Lopez", "C12345678", "Ingenieria en Sistemas Computacionales", "9"));
        assertFalse(ValidadorCampos.validarEstudiante("Juan", "Perez", "Lopez", "A12345678", "Ingenieria en Sistemas Computacionales", "9"));
        assertFalse(ValidadorCampos.validarEstudiante("Juan", "Perez", "Lopez", "12345678", "Otra carrera", "9"));
        assertFalse(ValidadorCampos.validarEstudiante("Juan", "Perez", "Lopez", "12345678", "Ingenieria en Sistemas Computacionales", "6"));
        assertFalse(ValidadorCampos.validarEstudiante("Juan", "Perez", "Lopez", "12345678", "Ingenieria en Sistemas Computacionales", "14"));
    }

    @Test
    void testValidarDatosProyecto() {
        // Caso válido
        assertNull(ValidadorCampos.validarDatosProyecto("Proyecto X", "Descripción válida para proyecto", "2", "Empresa X", "1234567890"));

        // Nombre proyecto vacío y fuera de rango
        String r = ValidadorCampos.validarDatosProyecto("", "Descripción válida para proyecto", "2", "Empresa X", "1234567890");
        assertTrue(r.contains("El nombre del proyecto no puede estar vacío"));

        // Descripción fuera de rango
        r = ValidadorCampos.validarDatosProyecto("Proyecto X", "Desc", "2", "Empresa X", "1234567890");
        assertTrue(r.contains("La descripción del proyecto debe tener entre 10 y 50 caracteres"));

        // Número de alumnos inválido
        r = ValidadorCampos.validarDatosProyecto("Proyecto X", "Descripción válida para proyecto", "10", "Empresa X", "1234567890");
        assertTrue(r.contains("El número de alumnos sugeridos debe ser entre 1 y 5"));

        // Empresa nombre fuera de rango
        r = ValidadorCampos.validarDatosProyecto("Proyecto X", "Descripción válida para proyecto", "2", "Em", "1234567890");
        assertTrue(r.contains("El nombre de la empresa debe tener entre 3 y 30 caracteres"));

        // Teléfono empresa inválido
        r = ValidadorCampos.validarDatosProyecto("Proyecto X", "Descripción válida para proyecto", "2", "Empresa X", "12345");
        assertTrue(r.contains("El número de teléfono de la empresa debe ser numérico y tener entre 10 y 15 dígitos"));
    }
}
