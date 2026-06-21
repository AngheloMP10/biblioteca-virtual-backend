package com.biblio.virtual.dto;

import java.time.LocalDateTime;

public class NotificacionDTO {

    private String tipo;
    private String mensaje;
    private Long prestamoId;
    private LocalDateTime fecha;

    public NotificacionDTO() {
    }

    public NotificacionDTO(String tipo, String mensaje, Long prestamoId, LocalDateTime fecha) {
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.prestamoId = prestamoId;
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Long prestamoId) {
        this.prestamoId = prestamoId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "NotificacionDTO{" +
                "tipo='" + tipo + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", prestamoId=" + prestamoId +
                ", fecha=" + fecha +
                '}';
    }
}
