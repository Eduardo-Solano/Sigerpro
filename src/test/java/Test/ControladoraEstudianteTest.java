package Test;

import controladores.ControladoraDepartamento;
import controladores.ControladoraEstudiante;
import controladores.ControladoraPersona;
import controladores.ValidadorCampos;
import entidad.Departamento;
import entidad.Estudiante;
import entidad.Persona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import persistencia.ControladoraPersistencia;

import javax.swing.table.DefaultTableModel;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControladoraEstudianteTest {

    @InjectMocks
    private ControladoraEstudiante controladoraEstudiante;

    @Mock
    private ControladoraPersistencia controlPersis;
    @Mock
    private ControladoraPersona controlP;
    @Mock
    private ControladoraDepartamento controlDp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarEstudiante() {
        Estudiante e = new Estudiante();
        controladoraEstudiante.guardarEstudiante(e);
        verify(controlPersis).guardarEstudiante(e);
    }

    @Test
    void testEliminarEstudiante() {
        controladoraEstudiante.eliminarEstudiante("C12345678");
        verify(controlPersis).eliminarEstudiante("C12345678");
    }

    @Test
    void testEditarEstudiante() {
        Estudiante e = new Estudiante();
        controladoraEstudiante.editarEstudiante(e);
        verify(controlPersis).editarEstudiante(e);
    }

    @Test
    void testBuscarPorNumeroControl() {
        Estudiante e = new Estudiante();
        when(controlPersis.buscarPorNumeroControl("C123")).thenReturn(e);
        assertEquals(e, controladoraEstudiante.buscarPorNumeroControl("C123"));
    }

    @Test
    void testListarEstudiantes() {
        ArrayList<Estudiante> lista = new ArrayList<>();
        when(controlPersis.listarEstudiantes()).thenReturn(lista);
        assertEquals(lista, controladoraEstudiante.listarEstudiantes());
    }

    @Test
    void testExisteEstudiante_True() {
        Estudiante e = new Estudiante();
        e.setNumControl("C1");
        ArrayList<Estudiante> lista = new ArrayList<>(List.of(e));
        when(controlPersis.listarEstudiantes()).thenReturn(lista);
        assertTrue(controladoraEstudiante.existeEstudiante("C1"));
    }

    @Test
    void testExisteEstudiante_False() {
        ArrayList<Estudiante> lista = new ArrayList<>();
        when(controlPersis.listarEstudiantes()).thenReturn(lista);
        assertFalse(controladoraEstudiante.existeEstudiante("C9"));
    }

    @Test
    void testTieneTresCaracteresRepetidos() {
        assertTrue(ControladoraEstudiante.tieneTresCaracteresRepetidos("aaabc"));
        assertTrue(ControladoraEstudiante.tieneTresCaracteresRepetidos("aaa"));
        assertFalse(ControladoraEstudiante.tieneTresCaracteresRepetidos("abc"));
    }

    @Test
void testActualizarDesdeTabla_TodoValido() {
    DefaultTableModel model = new DefaultTableModel(new Object[][]{
            {"Juan", "Perez", "Lopez", "C123", "Ing", "2"},
            {"Ana", "Ruiz", "Garcia", "C124", "Ing", "3"}
    }, new Object[]{"Nombre", "ApP", "ApM", "NumControl", "Carrera", "Semestre"});
    List<Persona> personas = new ArrayList<>();
    List<Estudiante> estudiantes = new ArrayList<>();

    try (MockedStatic<ValidadorCampos> mockValidador = mockStatic(ValidadorCampos.class)) {
        mockValidador.when(() -> ValidadorCampos.validarEstudiante(
                any(), any(), any(), any(), any(), any()
        )).thenReturn(true);

        boolean result = controladoraEstudiante.actualizarDesdeTabla(model, personas, estudiantes);
        assertTrue(result);
        assertEquals(2, personas.size());
        assertEquals(2, estudiantes.size());
    }
}

@Test
void testActualizarDesdeTabla_ErrorEnValidacion() {
    DefaultTableModel model = new DefaultTableModel(new Object[][]{
            {"Juan", "Perez", "Lopez", "C123", "Ing", "2"},
            {"Ana", "Ruiz", "", "C124", "Ing", "3"}
    }, new Object[]{"Nombre", "ApP", "ApM", "NumControl", "Carrera", "Semestre"});
    List<Persona> personas = new ArrayList<>();
    List<Estudiante> estudiantes = new ArrayList<>();

    try (MockedStatic<ValidadorCampos> mockValidador = mockStatic(ValidadorCampos.class)) {
        // Valida la primera fila como true, la segunda como false
        mockValidador.when(() -> ValidadorCampos.validarEstudiante(
                any(), any(), any(), any(), any(), any()
        )).thenAnswer(invocation -> {
            String apMaterno = invocation.getArgument(2);
            return !apMaterno.isEmpty();
        });

        boolean result = controladoraEstudiante.actualizarDesdeTabla(model, personas, estudiantes);
        assertFalse(result);
    }
}

}

    // Puedes agregar más tests de lógica según tus necesidades, por ejemplo,
    // test para p
