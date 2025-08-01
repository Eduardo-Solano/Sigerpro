package Test;

import controladores.ControladoraDocente;
import controladores.ValidadorCampos;
import entidad.Docente;
import entidad.Persona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControladoraDocenteTest {

    private ControladoraDocente controladora;
    private ControladoraDocente controladoraSpy;

    @BeforeEach
    void setUp() {
        controladora = new ControladoraDocente();
        controladoraSpy = Mockito.spy(new ControladoraDocente());
    }

    // ---- tieneTresCaracteresRepetidos ----

    @Test
    void testTieneTresCaracteresRepetidos_true() {
        assertTrue(ControladoraDocente.tieneTresCaracteresRepetidos("aaab"));
        assertTrue(ControladoraDocente.tieneTresCaracteresRepetidos("hellooo"));
        assertTrue(ControladoraDocente.tieneTresCaracteresRepetidos("baaaa"));
    }

    @Test
    void testTieneTresCaracteresRepetidos_false() {
        assertFalse(ControladoraDocente.tieneTresCaracteresRepetidos("aabbcc"));
        assertFalse(ControladoraDocente.tieneTresCaracteresRepetidos("abcabcabc"));
        assertFalse(ControladoraDocente.tieneTresCaracteresRepetidos(""));
        assertFalse(ControladoraDocente.tieneTresCaracteresRepetidos("ab"));
    }

    // ---- existeDocente ----

    @Test
    void testExisteDocente_True() {
        // Arrange
        Docente d1 = new Docente("1234");
        Docente d2 = new Docente("9999");
        ArrayList<Docente> lista = new ArrayList<>();
        lista.add(d1);
        lista.add(d2);

        ControladoraDocente spy = Mockito.spy(controladora);
        doReturn(lista).when(spy).listarDocentes();

        // Act
        boolean existe = spy.existeDocente("1234");

        // Assert
        assertTrue(existe);
    }

    @Test
    void testExisteDocente_False() {
        Docente d1 = new Docente("1111");
        Docente d2 = new Docente("2222");
        ArrayList<Docente> lista = new ArrayList<>();
        lista.add(d1);
        lista.add(d2);

        ControladoraDocente spy = Mockito.spy(controladora);
        doReturn(lista).when(spy).listarDocentes();

        boolean existe = spy.existeDocente("9999");

        assertFalse(existe);
    }

    // ---- actualizarDesdeTabla ----

    @Test
void testActualizarDesdeTabla_valido() {
    // Creamos modelo con datos válidos
    DefaultTableModel model = new DefaultTableModel(
            new Object[][]{
                    {"Juan", "Perez", "Lopez", "1234"},
                    {"Maria", "Garcia", "Sanchez", "5678"}
            },
            new String[]{"Nombre", "ApellidoP", "ApellidoM", "Tarjeta"}
    );

    List<Persona> personas = new ArrayList<>();
    List<Docente> docentes = new ArrayList<>();

    // El método validarDocente es static, no se puede mockear fácil.
    // Pero si tus datos cumplen el patrón, no debe tronar.

    boolean res = controladora.actualizarDesdeTabla(model, personas, docentes);

    assertTrue(res);
    assertEquals(2, personas.size());
    assertEquals(2, docentes.size());
    assertEquals("Juan", personas.get(0).getNombre());
    assertEquals("1234", docentes.get(0).getNoTarjeta());
}

    @Test
    void testActualizarDesdeTabla_invalido() {
        // Si un campo no es válido, retorna false y no rellena nada
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{
                        {"AAA", "BBB", "CCC", "mal"}
                },
                new Object[]{"Nombre", "ApellidoP", "ApellidoM", "Tarjeta"}
        );

        // Cambia temporalmente el validador si tienes acceso, aquí asume que no es válido el "mal"
        // Lo normal sería mockear el método, pero es estático
        List<Persona> personas = new ArrayList<>();
        List<Docente> docentes = new ArrayList<>();
        // Forzamos que el validador retorne false llamando a la implementación real

        boolean res = controladora.actualizarDesdeTabla(model, personas, docentes);

        assertFalse(res);
        assertEquals(0, personas.size());
        assertEquals(0, docentes.size());
    }
}
