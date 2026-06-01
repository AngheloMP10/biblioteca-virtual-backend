package com.biblio.virtual.dto;

public class DashboardMetricasDTO {
    private long totalLibros;
    private long totalUsuarios;
    private long prestamosActivos;
    private long prestamosFinalizados;

    public DashboardMetricasDTO() {
    }

    public DashboardMetricasDTO(
            long totalLibros,
            long totalUsuarios,
            long prestamosActivos,
            long prestamosFinalizados) {

        this.totalLibros = totalLibros;
        this.totalUsuarios = totalUsuarios;
        this.prestamosActivos = prestamosActivos;
        this.prestamosFinalizados = prestamosFinalizados;
    }

    // getters y setters

    public long getTotalLibros() {
        return totalLibros;
    }

    public void setTotalLibros(long totalLibros) {
        this.totalLibros = totalLibros;
    }

    public long getTotalUsuarios() {
        return totalUsuarios;
    }

    public void setTotalUsuarios(long totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }

    public long getPrestamosActivos() {
        return prestamosActivos;
    }

    public void setPrestamosActivos(long prestamosActivos) {
        this.prestamosActivos = prestamosActivos;
    }

    public long getPrestamosFinalizados() {
        return prestamosFinalizados;
    }

    public void setPrestamosFinalizados(long prestamosFinalizados) {
        this.prestamosFinalizados = prestamosFinalizados;
    }

}
