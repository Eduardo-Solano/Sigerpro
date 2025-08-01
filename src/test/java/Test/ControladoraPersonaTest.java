package Test;

import controladores.ControladoraPersona;
import entidad.Persona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistencia.ControladoraPersistencia;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControladoraPersonaTest {

    private ControladoraPersona controladora;
    private ControladoraPersistencia mockPersis;

    @BeforeEach
    void setUp() {
        mockPersis = mock(ControladoraPersistencia.class);
        controladora = new ControladoraPersona();
        // Hacemos "inject" del mock usando reflexión (ya que es default y no por constructor)
        try {
            var field = ControladoraPersona.class.getDeclaredField("controlPersis");
            field.setAccessible(true);
            field.set(controladora, mockPersis);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCrearPersona() {
        Persona p = new Persona();
        controladora.crearPersona(p);
        verify(mockPersis).crearPersona(p);
    }

    @Test
    void testEliminarPersona() {
        controladora.eliminarPersona(42);
        verify(mockPersis).eliminarPersona(42);
    }

    @Test
    void testEditarPersona() {
        Persona p = new Persona();
        controladora.editarPersona(p);
        verify(mockPersis).editarPersona(p);
    }

    @Test
    void testBuscarPorId() {
        Persona p = new Persona();
        when(mockPersis.buscarPersonaPorId(1)).thenReturn(p);

        Persona result = controladora.buscarPorId(1);

        assertSame(p, result);
        verify(mockPersis).buscarPersonaPorId(1);
    }

    @Test
    void testListarPersonas() {
        ArrayList<Persona> lista = new ArrayList<>();
        lista.add(new Persona());
        when(mockPersis.listarPersonas()).thenReturn(lista);

        ArrayList<Persona> resultado = controladora.listarPersonas();

        assertEquals(1, resultado.size());
        verify(mockPersis).listarPersonas();
    }

    @Test
    void testExistePersona_True() {
        Persona p = new Persona();
        p.setNombre("Juan");
        p.setApellidoP("Perez");
        p.setApellidoM("Lopez");

        Persona igual = new Persona();
        igual.setNombre("Juan");
        igual.setApellidoP("Perez");
        igual.setApellidoM("Lopez");

        ArrayList<Persona> lista = new ArrayList<>();
        lista.add(igual);

        when(mockPersis.listarPersonas()).thenReturn(lista);

        boolean existe = controladora.existePersona(p);

        assertTrue(existe);
    }

    @Test
    void testExistePersona_False() {
        Persona p = new Persona();
        p.setNombre("Juan");
        p.setApellidoP("Perez");
        p.setApellidoM("Lopez");

        Persona diferente = new Persona();
        diferente.setNombre("Maria");
        diferente.setApellidoP("Garcia");
        diferente.setApellidoM("Sanchez");

        ArrayList<Persona> lista = new ArrayList<>();
        lista.add(diferente);

        when(mockPersis.listarPersonas()).thenReturn(lista);

        boolean existe = controladora.existePersona(p);

        assertFalse(existe);
    }
}
