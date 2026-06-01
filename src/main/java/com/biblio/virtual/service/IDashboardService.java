package com.biblio.virtual.service;

import com.biblio.virtual.dto.DashboardResponseDTO;
import java.time.LocalDate;
import java.io.ByteArrayInputStream;

public interface IDashboardService {

    DashboardResponseDTO obtenerDashboard();

    DashboardResponseDTO obtenerDashboardPorFecha(
            LocalDate fechaInicio,
            LocalDate fechaFin);

    ByteArrayInputStream exportarExcel();
}