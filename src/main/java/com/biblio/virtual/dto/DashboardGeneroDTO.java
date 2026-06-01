package com.biblio.virtual.dto;

public class DashboardGeneroDTO {
    private String genero;
    private Long cantidad;

    public DashboardGeneroDTO(String genero, Long cantidad) {
        this.genero = genero;
        this.cantidad = cantidad;
    }

    // getters y setters

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

}
