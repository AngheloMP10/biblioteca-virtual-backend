package com.biblio.virtual.service;

import com.biblio.virtual.model.Libro;
import com.biblio.virtual.model.Prestamo;
import com.biblio.virtual.model.Usuario;
import com.biblio.virtual.model.enums.EstadoPrestamo;
import com.biblio.virtual.repository.ILibroRepository;
import com.biblio.virtual.repository.IPrestamoRepository;
import com.biblio.virtual.repository.IUsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PrestamoServiceTest {

    @Mock
    private IPrestamoRepository prestamoRepo;

    @Mock
    private IUsuarioRepository usuarioRepo;

    @Mock
    private ILibroRepository libroRepo;

    @Mock
    private NotificacionService notificacionService;

    @InjectMocks
    private PrestamoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deberiaSolicitarPrestamoExitosamente() {
        String username = "anghelo";
        Long libroId = 1L;

        Usuario usuario = new Usuario();
        usuario.setUsername(username);

        Libro libro = new Libro();
        libro.setId(libroId);
        libro.setTitulo("Clean Code");
        libro.setStock(3);

        Prestamo prestamoGuardado = new Prestamo();
        prestamoGuardado.setId(10L);
        prestamoGuardado.setUsuario(usuario);
        prestamoGuardado.setLibro(libro);
        prestamoGuardado.setEstado(EstadoPrestamo.PENDIENTE);

        when(prestamoRepo.countByUsuarioUsernameAndEstadoNotIn(
                eq(username),
                any(List.class))).thenReturn(0L);

        when(usuarioRepo.findByUsername(username))
                .thenReturn(Optional.of(usuario));

        when(libroRepo.findById(libroId))
                .thenReturn(Optional.of(libro));

        when(prestamoRepo.save(any(Prestamo.class)))
                .thenReturn(prestamoGuardado);

        Prestamo resultado = service.solicitarPrestamo(libroId, username);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getEstado()).isEqualTo(EstadoPrestamo.PENDIENTE);
        assertThat(resultado.getUsuario().getUsername()).isEqualTo(username);
        assertThat(resultado.getLibro().getTitulo()).isEqualTo("Clean Code");

        verify(prestamoRepo).save(any(Prestamo.class));
        verify(notificacionService).enviarNotificacion(any());
    }

    @Test
    void deberiaAprobarPrestamoYReducirStock() {
        Long prestamoId = 1L;

        Libro libro = new Libro();
        libro.setId(1L);
        libro.setStock(5);

        Prestamo prestamo = new Prestamo();
        prestamo.setId(prestamoId);
        prestamo.setLibro(libro);
        prestamo.setEstado(EstadoPrestamo.PENDIENTE);

        when(prestamoRepo.findById(prestamoId))
                .thenReturn(Optional.of(prestamo));

        service.aprobarPrestamo(prestamoId);

        assertThat(prestamo.getEstado())
                .isEqualTo(EstadoPrestamo.APROBADO);

        assertThat(libro.getStock())
                .isEqualTo(4);

        verify(libroRepo).save(libro);
        verify(prestamoRepo).save(prestamo);
        verify(notificacionService).enviarNotificacion(any());
    }

    @Test
    void deberiaRechazarPrestamoCuandoUsuarioAlcanzaLimiteMaximo() {
        String username = "anghelo";
        Long libroId = 1L;

        when(prestamoRepo.countByUsuarioUsernameAndEstadoNotIn(
                eq(username),
                any(List.class))).thenReturn(3L);

        assertThatThrownBy(() -> service.solicitarPrestamo(libroId, username))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("límite de 3 libros");

        verify(usuarioRepo, never()).findByUsername(anyString());
        verify(libroRepo, never()).findById(anyLong());
        verify(prestamoRepo, never()).save(any());
    }

    @Test
    void deberiaRechazarPrestamoCuandoLibroNoTieneStock() {
        String username = "anghelo";
        Long libroId = 1L;

        Usuario usuario = new Usuario();
        usuario.setUsername(username);

        Libro libro = new Libro();
        libro.setId(libroId);
        libro.setStock(0);

        when(prestamoRepo.countByUsuarioUsernameAndEstadoNotIn(
                eq(username),
                any(List.class))).thenReturn(0L);

        when(usuarioRepo.findByUsername(username))
                .thenReturn(Optional.of(usuario));

        when(libroRepo.findById(libroId))
                .thenReturn(Optional.of(libro));

        assertThatThrownBy(() -> service.solicitarPrestamo(libroId, username))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no está disponible");

        verify(prestamoRepo, never()).save(any());
    }

    @Test
    void deberiaLanzarErrorCuandoUsuarioNoExiste() {
        String username = "usuarioFake";
        Long libroId = 1L;

        when(prestamoRepo.countByUsuarioUsernameAndEstadoNotIn(
                eq(username),
                any(List.class))).thenReturn(0L);

        when(usuarioRepo.findByUsername(username))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.solicitarPrestamo(libroId, username))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuario no encontrado");

        verify(libroRepo, never()).findById(anyLong());
        verify(prestamoRepo, never()).save(any());
    }

    @Test
    void deberiaLanzarErrorCuandoLibroNoExiste() {
        String username = "anghelo";
        Long libroId = 999L;

        Usuario usuario = new Usuario();
        usuario.setUsername(username);

        when(prestamoRepo.countByUsuarioUsernameAndEstadoNotIn(
                eq(username),
                any(List.class))).thenReturn(0L);

        when(usuarioRepo.findByUsername(username))
                .thenReturn(Optional.of(usuario));

        when(libroRepo.findById(libroId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.solicitarPrestamo(libroId, username))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Libro no encontrado");

        verify(prestamoRepo, never()).save(any());
    }

    @Test
    void deberiaEntregarPrestamoAprobado() {
        Long prestamoId = 1L;

        Prestamo prestamo = new Prestamo();
        prestamo.setId(prestamoId);
        prestamo.setEstado(EstadoPrestamo.APROBADO);

        when(prestamoRepo.findById(prestamoId))
                .thenReturn(Optional.of(prestamo));

        service.entregarPrestamo(prestamoId);

        assertThat(prestamo.getEstado())
                .isEqualTo(EstadoPrestamo.EN_PRESTAMO);

        assertThat(prestamo.getFechaDevolucion())
                .isEqualTo(LocalDate.now().plusDays(7));

        verify(prestamoRepo).save(prestamo);
    }

    @Test
    void deberiaRechazarEntregaSiPrestamoNoEstaAprobado() {
        Long prestamoId = 1L;

        Prestamo prestamo = new Prestamo();
        prestamo.setId(prestamoId);
        prestamo.setEstado(EstadoPrestamo.PENDIENTE);

        when(prestamoRepo.findById(prestamoId))
                .thenReturn(Optional.of(prestamo));

        assertThatThrownBy(() -> service.entregarPrestamo(prestamoId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("APROBADO");

        verify(prestamoRepo, never()).save(any());
    }

    @Test
    void deberiaFinalizarPrestamoYRecuperarStock() {
        Long prestamoId = 1L;

        Libro libro = new Libro();
        libro.setId(1L);
        libro.setStock(2);

        Prestamo prestamo = new Prestamo();
        prestamo.setId(prestamoId);
        prestamo.setLibro(libro);
        prestamo.setEstado(EstadoPrestamo.EN_PRESTAMO);

        when(prestamoRepo.findById(prestamoId))
                .thenReturn(Optional.of(prestamo));

        service.finalizarPrestamo(prestamoId);

        assertThat(prestamo.getEstado())
                .isEqualTo(EstadoPrestamo.FINALIZADO);

        assertThat(prestamo.getFechaDevolucion())
                .isEqualTo(LocalDate.now());

        assertThat(libro.getStock())
                .isEqualTo(3);

        verify(libroRepo).save(libro);
        verify(prestamoRepo).save(prestamo);
        verify(notificacionService).enviarNotificacion(any());
    }

    @Test
    void deberiaRechazarFinalizacionSiNoEstaEnPrestamo() {
        Long prestamoId = 1L;

        Prestamo prestamo = new Prestamo();
        prestamo.setId(prestamoId);
        prestamo.setEstado(EstadoPrestamo.APROBADO);

        when(prestamoRepo.findById(prestamoId))
                .thenReturn(Optional.of(prestamo));

        assertThatThrownBy(() -> service.finalizarPrestamo(prestamoId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("EN_PRESTAMO");

        verify(libroRepo, never()).save(any());
        verify(prestamoRepo, never()).save(any());
    }
}