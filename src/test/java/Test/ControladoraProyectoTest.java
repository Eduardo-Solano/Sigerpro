package Test;

import controladores.ControladoraEmpresa;
import controladores.ControladoraEstudiante;
import controladores.ControladoraPersona;
import controladores.ControladoraProyecto;
import entidad.Empresa;
import entidad.Estudiante;
import entidad.Persona;
import entidad.Proyecto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistencia.ControladoraPersistencia;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControladoraProyectoTest {

    private ControladoraProyecto controladora;
    private ControladoraPersistencia mockPersis;
    private ControladoraEmpresa mockEmp;
    private ControladoraEstudiante mockEst;
   private ControladoraPersona mockPersona;

    @BeforeEach
    void setUp() throws Exception {
        mockPersis = mock(ControladoraPersistencia.class);
        mockEmp = mock(ControladoraEmpresa.class);
        mockEst = mock(ControladoraEstudiante.class);
        mockPersona = mock(ControladoraPersona.class);

        controladora = new ControladoraProyecto();

        // Inyectar mocks usando reflexión porque los campos son atributos, no por constructor
        var cpField = ControladoraProyecto.class.getDeclaredField("controlPersis");
        cpField.setAccessible(true);
        cpField.set(controladora, mockPersis);

        var empField = ControladoraProyecto.class.getDeclaredField("controlEmp");
        empField.setAccessible(true);
        empField.set(controladora, mockEmp);

        var estField = ControladoraProyecto.class.getDeclaredField("controlE");
        estField.setAccessible(true);
        estField.set(controladora, mockEst);

        var persField = ControladoraProyecto.class.getDeclaredField("controlP");
        persField.setAccessible(true);
        persField.set(controladora, mockPersona);
    }

    @Test
    void testGuardarProyecto() {
        Proyecto p = new Proyecto();
        controladora.guardarProyecto(p);
        verify(mockPersis).guardarProyecto(p);
    }

    @Test
    void testEditarProyecto() {
        Proyecto p = new Proyecto();
        controladora.editarProyecto(p);
        verify(mockPersis).editarProyecto(p);
    }

    @Test
    void testEliminarProyecto() {
        controladora.eliminiarProyecto(7);
        verify(mockPersis).eliminarProyecto(7);
    }

    @Test
    void testBuscarProyectoPorId() {
        Proyecto proy = new Proyecto();
        when(mockPersis.buscarProyectoPorId(99)).thenReturn(proy);

        Proyecto resultado = controladora.buscarProyectoPorId(99);

        assertSame(proy, resultado);
        verify(mockPersis).buscarProyectoPorId(99);
    }

    @Test
    void testListarProyectos() {
        ArrayList<Proyecto> lista = new ArrayList<>();
        lista.add(new Proyecto());
        when(mockPersis.listarProyectos()).thenReturn(lista);

        ArrayList<Proyecto> resultado = controladora.listarProyectos();

        assertEquals(1, resultado.size());
        verify(mockPersis).listarProyectos();
    }

    @Test
    void testObtenerTodasLasEmpresas() {
        List<Empresa> empresas = new ArrayList<>();
        empresas.add(new Empresa());
        when(mockEmp.listarEmpresas()).thenReturn((ArrayList<Empresa>) empresas);

        List<Empresa> resultado = controladora.obtenerTodasLasEmpresas();

        assertEquals(1, resultado.size());
        verify(mockEmp).listarEmpresas();
    }

    @Test
    void testVerificarLimiteAlumno_true() {
        // Proyecto con límite 2 alumnos
        Proyecto proy = new Proyecto();
        proy.setNumAlumnosSug(2);

        // Simular estudiantes: solo 1 ya asignado a este proyecto
        Estudiante e1 = new Estudiante();
        e1.setIdProyecto(proy);
        Estudiante e2 = new Estudiante();
        e2.setIdProyecto(null);
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(e1);
        estudiantes.add(e2);

        when(mockPersis.buscarProyectoPorId(1)).thenReturn(proy);
        when(mockEst.listarEstudiantes()).thenReturn(estudiantes);

        boolean res = controladora.verificarLimiteAlumno(1);
        assertTrue(res);
    }

    @Test
    void testVerificarLimiteAlumno_false() {
        // Proyecto con límite 1 alumno
        Proyecto proy = new Proyecto();
        proy.setNumAlumnosSug(1);

        Estudiante e1 = new Estudiante();
        e1.setIdProyecto(proy);
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(e1);

        when(mockPersis.buscarProyectoPorId(77)).thenReturn(proy);
        when(mockEst.listarEstudiantes()).thenReturn(estudiantes);

        boolean res = controladora.verificarLimiteAlumno(77);
        assertFalse(res);
    }
}
