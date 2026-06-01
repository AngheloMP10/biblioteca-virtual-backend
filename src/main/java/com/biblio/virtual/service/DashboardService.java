package com.biblio.virtual.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.biblio.virtual.dto.*;
import com.biblio.virtual.model.enums.EstadoPrestamo;
import com.biblio.virtual.repository.ILibroRepository;
import com.biblio.virtual.repository.IPrestamoRepository;
import com.biblio.virtual.repository.IUsuarioRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class DashboardService implements IDashboardService {

    private final ILibroRepository libroRepo;
    private final IUsuarioRepository usuarioRepo;
    private final IPrestamoRepository prestamoRepo;

    public DashboardService(
            ILibroRepository libroRepo,
            IUsuarioRepository usuarioRepo,
            IPrestamoRepository prestamoRepo) {

        this.libroRepo = libroRepo;
        this.usuarioRepo = usuarioRepo;
        this.prestamoRepo = prestamoRepo;
    }

    @Override
    public DashboardResponseDTO obtenerDashboard() {

        DashboardMetricasDTO metricas = new DashboardMetricasDTO(
                libroRepo.count(),
                usuarioRepo.count(),
                prestamoRepo.countByEstadoIn(
                        List.of(
                                EstadoPrestamo.APROBADO,
                                EstadoPrestamo.EN_PRESTAMO)),
                prestamoRepo.countByEstado(
                        EstadoPrestamo.FINALIZADO));

        return new DashboardResponseDTO(
                metricas,
                prestamoRepo.obtenerLibrosMasPrestados(),
                prestamoRepo.obtenerGenerosMasPopulares());
    }

    @Override
    public DashboardResponseDTO obtenerDashboardPorFecha(
            LocalDate fechaInicio,
            LocalDate fechaFin) {

        DashboardMetricasDTO metricas = new DashboardMetricasDTO(
                libroRepo.count(),
                usuarioRepo.count(),
                prestamoRepo.countByEstadoIn(
                        List.of(
                                EstadoPrestamo.APROBADO,
                                EstadoPrestamo.EN_PRESTAMO)),
                prestamoRepo.countByEstado(
                        EstadoPrestamo.FINALIZADO));

        return new DashboardResponseDTO(
                metricas,
                prestamoRepo.obtenerLibrosMasPrestadosPorFecha(
                        fechaInicio,
                        fechaFin),
                prestamoRepo.obtenerGenerosMasPopularesPorFecha(
                        fechaInicio,
                        fechaFin));
    }

    @Override
    public ByteArrayInputStream exportarExcel() {

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Dashboard");

            int rowNum = 0;

            DashboardResponseDTO dashboard = obtenerDashboard();

            // TÍTULO
            Row tituloRow = sheet.createRow(rowNum++);

            tituloRow.createCell(0)
                    .setCellValue("REPORTE DASHBOARD BIBLIOTECA");

            rowNum++;

            // MÉTRICAS
            Row encabezadoMetricas = sheet.createRow(rowNum++);
            encabezadoMetricas.createCell(0).setCellValue("Indicador");
            encabezadoMetricas.createCell(1).setCellValue("Valor");

            DashboardMetricasDTO m = dashboard.getMetricas();

            Row r1 = sheet.createRow(rowNum++);
            r1.createCell(0).setCellValue("Total Libros");
            r1.createCell(1).setCellValue(m.getTotalLibros());

            Row r2 = sheet.createRow(rowNum++);
            r2.createCell(0).setCellValue("Total Usuarios");
            r2.createCell(1).setCellValue(m.getTotalUsuarios());

            Row r3 = sheet.createRow(rowNum++);
            r3.createCell(0).setCellValue("Préstamos Activos");
            r3.createCell(1).setCellValue(m.getPrestamosActivos());

            Row r4 = sheet.createRow(rowNum++);
            r4.createCell(0).setCellValue("Préstamos Finalizados");
            r4.createCell(1).setCellValue(m.getPrestamosFinalizados());

            rowNum += 2;

            // LIBROS MÁS PRESTADOS
            Row librosTitulo = sheet.createRow(rowNum++);
            librosTitulo.createCell(0).setCellValue("LIBROS MÁS PRESTADOS");

            Row librosHeader = sheet.createRow(rowNum++);
            librosHeader.createCell(0).setCellValue("Libro");
            librosHeader.createCell(1).setCellValue("Cantidad");

            for (DashboardLibroDTO libro : dashboard.getLibrosMasPrestados()) {

                Row row = sheet.createRow(rowNum++);

                row.createCell(0)
                        .setCellValue(libro.getTitulo());

                row.createCell(1)
                        .setCellValue(libro.getCantidad());
            }

            rowNum += 2;

            // GÉNEROS
            Row generoTitulo = sheet.createRow(rowNum++);
            generoTitulo.createCell(0).setCellValue("GÉNEROS MÁS POPULARES");

            Row generoHeader = sheet.createRow(rowNum++);
            generoHeader.createCell(0).setCellValue("Género");
            generoHeader.createCell(1).setCellValue("Cantidad");

            for (DashboardGeneroDTO genero : dashboard.getGenerosMasPopulares()) {

                Row row = sheet.createRow(rowNum++);

                row.createCell(0)
                        .setCellValue(genero.getGenero());

                row.createCell(1)
                        .setCellValue(genero.getCantidad());
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            workbook.write(out);

            return new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error al generar Excel",
                    e);
        }
    }
}