package com.biblio.virtual.dto;

import java.util.List;

public class DashboardResponseDTO {
    private DashboardMetricasDTO metricas;
    private List<DashboardLibroDTO> librosMasPrestados;
    private List<DashboardGeneroDTO> generosMasPopulares;

    public DashboardResponseDTO(
            DashboardMetricasDTO metricas,
            List<DashboardLibroDTO> librosMasPrestados,
            List<DashboardGeneroDTO> generosMasPopulares) {

        this.metricas = metricas;
        this.librosMasPrestados = librosMasPrestados;
        this.generosMasPopulares = generosMasPopulares;
    }

    public DashboardMetricasDTO getMetricas() {
        return metricas;
    }

    public List<DashboardLibroDTO> getLibrosMasPrestados() {
        return librosMasPrestados;
    }

    public List<DashboardGeneroDTO> getGenerosMasPopulares() {
        return generosMasPopulares;
    }
}
