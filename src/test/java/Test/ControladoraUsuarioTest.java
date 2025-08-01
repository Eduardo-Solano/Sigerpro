package Test;

import controladores.ControladoraUsuario;
import entidad.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistencia.ControladoraPersistencia;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControladoraUsuarioTest {

    private ControladoraUsuario controladora;
    private ControladoraPersistencia mockPersis;

    @BeforeEach
    void setUp() throws Exception {
        mockPersis = mock(ControladoraPersistencia.class);
        controladora = new ControladoraUsuario();
        // Inyecta el mock usando reflexión porque es atributo (no constructor ni setter)
        var field = ControladoraUsuario.class.getDeclaredField("controlPersis");
        field.setAccessible(true);
        field.set(controladora, mockPersis);
    }

    @Test
    void testCrearUsuario() {
        Usuario u = new Usuario();
        controladora.crearUsuario(u);
        verify(mockPersis).crearUsuario(u);
    }

    @Test
    void testEliminarUsuario() {
        controladora.eliminarUsuario(10);
        verify(mockPersis).eliminarUsuario(10);
    }

    @Test
    void testEditarUsuario() {
        Usuario u = new Usuario();
        controladora.editarUsuario(u);
        verify(mockPersis).editarUsuario(u);
    }

    @Test
    void testBuscarPorId() {
        Usuario u = new Usuario();
        when(mockPersis.buscarUsuarioPorId(123)).thenReturn(u);
        Usuario resultado = controladora.buscarPorId(123);
        assertSame(u, resultado);
        verify(mockPersis).buscarUsuarioPorId(123);
    }

    @Test
    void testListarUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>();
        lista.add(new Usuario());
        when(mockPersis.listarUsuario()).thenReturn(lista);

        ArrayList<Usuario> resultado = controladora.listarUsuarios();

        assertEquals(1, resultado.size());
        verify(mockPersis).listarUsuario();
    }

    @Test
    void testBuscarCorreoOUser_CorreoExiste() {
        Usuario u1 = new Usuario();
        u1.setCorreo("mail@dominio.com");
        u1.setNombreUsuario("usuario1");

        ArrayList<Usuario> lista = new ArrayList<>();
        lista.add(u1);

        when(mockPersis.listarUsuario()).thenReturn(lista);

        String encontrado = controladora.buscarCorreoOUser("mail@dominio.com");

        assertEquals("mail@dominio.com", encontrado);
    }

    @Test
    void testBuscarCorreoOUser_UsuarioExiste() {
        Usuario u1 = new Usuario();
        u1.setCorreo("otro@mail.com");
        u1.setNombreUsuario("usuario1");

        ArrayList<Usuario> lista = new ArrayList<>();
        lista.add(u1);

        when(mockPersis.listarUsuario()).thenReturn(lista);

        String encontrado = controladora.buscarCorreoOUser("usuario1");

        assertEquals("otro@mail.com", encontrado);
    }

    @Test
    void testBuscarCorreoOUser_NoExiste() {
        Usuario u1 = new Usuario();
        u1.setCorreo("algo@mail.com");
        u1.setNombreUsuario("alguien");

        ArrayList<Usuario> lista = new ArrayList<>();
        lista.add(u1);

        when(mockPersis.listarUsuario()).thenReturn(lista);

        String encontrado = controladora.buscarCorreoOUser("noexiste");

        assertEquals("", encontrado);
    }

    @Test
    void testBuscarCorreoOUser_ListaVacia() {
        when(mockPersis.listarUsuario()).thenReturn(new ArrayList<>());

        String encontrado = controladora.buscarCorreoOUser("cualquiera");

        assertEquals("", encontrado);
    }
}
