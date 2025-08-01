
package Test;

import controladores.ControladoraEmpresa;
import entidad.Empresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import persistencia.ControladoraPersistencia;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControladoraEmpresaTest {

    @InjectMocks
    private ControladoraEmpresa controladoraEmpresa;

    @Mock
    private ControladoraPersistencia controlPersis;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void testGuardarEmpresa() {
        Empresa empresa = new Empresa();
        controladoraEmpresa.guardarEmpresa(empresa);
        verify(controlPersis).guardarEmpresa(empresa);
    }

    @Test
    void testBuscarEmpresaPorNombre() {
        Empresa empresa = new Empresa();
        when(controlPersis.buscarEmpresaPorNombre("Bimbo")).thenReturn(empresa);
        Empresa resultado = controladoraEmpresa.buscarEmpresaPorNombre("Bimbo");
        assertEquals(empresa, resultado);
    }

    @Test
    void testEliminarEmpresa() {
        Integer id = 1;
        controladoraEmpresa.eliminarEmpresa(id);
        verify(controlPersis).eliminarEmpresa(id);
    }

    @Test
    void testEditarEmpresa() {
        Empresa empresa = new Empresa();
        controladoraEmpresa.editarEmpresa(empresa);
        verify(controlPersis).editarEmpresa(empresa);
    }

    @Test
    void testBuscarEmpresaPorId() {
        Empresa empresa = new Empresa();
        when(controlPersis.buscarEmpresaPorId(1)).thenReturn(empresa);
        Empresa resultado = controladoraEmpresa.buscarEmpresaPorId(1);
        assertEquals(empresa, resultado);
    }

    @Test
    void testListarEmpresas() {
        ArrayList<Empresa> lista = new ArrayList<>();
        when(controlPersis.listarEmpresas()).thenReturn(lista);
        ArrayList<Empresa> resultado = controladoraEmpresa.listarEmpresas();
        assertEquals(lista, resultado);
    }

    @Test
    void testExisteEmpresa_true() {
        Empresa empresa1 = new Empresa();
        empresa1.setRfc("RFC123");

        Empresa empresa2 = new Empresa();
        empresa2.setRfc("RFC123");

        ArrayList<Empresa> lista = new ArrayList<>();
        lista.add(empresa1);

        when(controlPersis.listarEmpresas()).thenReturn(lista);

        boolean existe = controladoraEmpresa.existeEmpresa(empresa2);
        assertTrue(existe);
    }

    @Test
    void testExisteEmpresa_false() {
        Empresa empresa1 = new Empresa();
        empresa1.setRfc("RFC999");

        Empresa empresa2 = new Empresa();
        empresa2.setRfc("RFC123");

        ArrayList<Empresa> lista = new ArrayList<>();
        lista.add(empresa1);

        when(controlPersis.listarEmpresas()).thenReturn(lista);

        boolean existe = controladoraEmpresa.existeEmpresa(empresa2);
        assertFalse(existe);
    }
}
