package com.biblio.virtual.dto;

public class DashboardLibroDTO {
    private String titulo;
    private Long cantidad;

    public DashboardLibroDTO(String titulo, Long cantidad) {
        this.titulo = titulo;
        this.cantidad = cantidad;
    }

    // getters y setters

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

}
