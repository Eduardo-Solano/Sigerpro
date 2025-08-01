/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ExportadorPDF {

    public static void generarPDFDesdeTabla(JTable tabla, String nombreArchivo, String rutaLogoEscuela, String rutaLogoSistema) {
        if (!nombreArchivo.toLowerCase().endsWith(".pdf")) {
            nombreArchivo += ".pdf";
        }

        Document documento = new Document(PageSize.A4, 50, 50, 50, 50);

        try {
            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            // === ENCABEZADO ===
            PdfPTable encabezado = new PdfPTable(2);
            encabezado.setWidthPercentage(100);
            encabezado.setWidths(new int[]{1, 4});
            encabezado.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            // Logo de la escuela (izquierda)
            if (rutaLogoEscuela != null && !rutaLogoEscuela.isEmpty()) {
                try {
                    Image logoEscuela = Image.getInstance(rutaLogoEscuela);
                    logoEscuela.scaleToFit(60, 60);
                    PdfPCell celdaLogo = new PdfPCell(logoEscuela, false);
                    celdaLogo.setBorder(Rectangle.NO_BORDER);
                    celdaLogo.setRowspan(3);
                    celdaLogo.setHorizontalAlignment(Element.ALIGN_LEFT);
                    celdaLogo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    encabezado.addCell(celdaLogo);
                } catch (Exception e) {
                    encabezado.addCell(new PdfPCell(new Phrase("")));
                    System.err.println("Error cargando logo de escuela: " + e.getMessage());
                }
            } else {
                encabezado.addCell(new PdfPCell(new Phrase("")));
            }

            // Títulos compactos
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13);
            Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 11);
            Font fontDescripcion = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, BaseColor.DARK_GRAY);

            PdfPCell titulo = new PdfPCell(new Phrase("Instituto Tecnológico de Oaxaca", fontTitulo));
            titulo.setHorizontalAlignment(Element.ALIGN_LEFT);
            titulo.setBorder(Rectangle.NO_BORDER);
            encabezado.addCell(titulo);

            PdfPCell subtitulo = new PdfPCell(new Phrase("Sistema de Gestión de Residencias Profesionales (SIGERPRO)", fontSubtitulo));
            subtitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
            subtitulo.setBorder(Rectangle.NO_BORDER);
            encabezado.addCell(subtitulo);

            PdfPCell descripcion = new PdfPCell(new Phrase("Reporte detallado de expedientes registrados por los alumnos", fontDescripcion));
            descripcion.setHorizontalAlignment(Element.ALIGN_LEFT);
            descripcion.setBorder(Rectangle.NO_BORDER);
            encabezado.addCell(descripcion);

            documento.add(encabezado);
            documento.add(Chunk.NEWLINE);

            // === TABLA ===
            PdfPTable tablaPDF = new PdfPTable(tabla.getColumnCount());
            tablaPDF.setWidthPercentage(100);
            tablaPDF.setSpacingBefore(10f);

            TableModel modelo = tabla.getModel();

            // Encabezado de la tabla (colores personalizados)
            for (int col = 0; col < modelo.getColumnCount(); col++) {
                PdfPCell celdaEncabezado = new PdfPCell(
                        new Phrase(modelo.getColumnName(col),
                                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.WHITE))
                );
                celdaEncabezado.setBackgroundColor(new BaseColor(51, 51, 51));
                celdaEncabezado.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdaEncabezado.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celdaEncabezado.setPadding(5f);
                tablaPDF.addCell(celdaEncabezado);
            }

            // Datos de la tabla
            for (int row = 0; row < modelo.getRowCount(); row++) {
                for (int col = 0; col < modelo.getColumnCount(); col++) {
                    Object valor = modelo.getValueAt(row, col);
                    PdfPCell celda = new PdfPCell(new Phrase(valor != null ? valor.toString() : ""));
                    celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                    celda.setPadding(4f);
                    tablaPDF.addCell(celda);
                }
            }

            documento.add(tablaPDF);

            // === LOGO DEL SISTEMA (INFERIOR DERECHA) ===
            if (rutaLogoSistema != null && !rutaLogoSistema.isEmpty()) {
                try {
                    Image logoSistema = Image.getInstance(rutaLogoSistema);
                    logoSistema.scaleToFit(50, 50);
                    float x = documento.right() - 50;
                    float y = documento.bottom() + 10;
                    logoSistema.setAbsolutePosition(x, y);
                    writer.getDirectContent().addImage(logoSistema);
                } catch (Exception e) {
                    System.err.println("Error cargando logo del sistema: " + e.getMessage());
                }
            }

            documento.close();
            System.out.println("✅ PDF generado correctamente: " + nombreArchivo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void generarReporteProyectos(JTable tabla, String nombreArchivo, String rutaLogoEscuela, String rutaLogoSistema) {
    if (!nombreArchivo.toLowerCase().endsWith(".pdf")) {
        nombreArchivo += ".pdf";
    }

    Document documento = new Document(PageSize.A4, 50, 50, 50, 50);

    try {
        PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
        documento.open();

        // === ENCABEZADO ===
        PdfPTable encabezado = new PdfPTable(2);
        encabezado.setWidthPercentage(100);
        encabezado.setWidths(new int[]{1, 4});
        encabezado.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        if (rutaLogoEscuela != null && !rutaLogoEscuela.isEmpty()) {
            try {
                Image logoEscuela = Image.getInstance(rutaLogoEscuela);
                logoEscuela.scaleToFit(60, 60);
                PdfPCell celdaLogo = new PdfPCell(logoEscuela, false);
                celdaLogo.setBorder(Rectangle.NO_BORDER);
                celdaLogo.setRowspan(3);
                celdaLogo.setHorizontalAlignment(Element.ALIGN_LEFT);
                celdaLogo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                encabezado.addCell(celdaLogo);
            } catch (Exception e) {
                encabezado.addCell(new PdfPCell(new Phrase("")));
                System.err.println("Error cargando logo de escuela: " + e.getMessage());
            }
        } else {
            encabezado.addCell(new PdfPCell(new Phrase("")));
        }

        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13);
        Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 11);
        Font fontDescripcion = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, BaseColor.DARK_GRAY);

        PdfPCell titulo = new PdfPCell(new Phrase("Instituto Tecnológico de Oaxaca", fontTitulo));
        titulo.setHorizontalAlignment(Element.ALIGN_LEFT);
        titulo.setBorder(Rectangle.NO_BORDER);
        encabezado.addCell(titulo);

        PdfPCell subtitulo = new PdfPCell(new Phrase("SIGERPRO - Sistema de Gestión de Proyectos", fontSubtitulo));
        subtitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
        subtitulo.setBorder(Rectangle.NO_BORDER);
        encabezado.addCell(subtitulo);

        PdfPCell descripcion = new PdfPCell(new Phrase("Reporte general de los proyectos registrados por distintas empresas", fontDescripcion));
        descripcion.setHorizontalAlignment(Element.ALIGN_LEFT);
        descripcion.setBorder(Rectangle.NO_BORDER);
        encabezado.addCell(descripcion);

        documento.add(encabezado);
        documento.add(Chunk.NEWLINE);

        // === TABLA ===
        TableModel modelo = tabla.getModel();

        // Índices de columnas que se deben excluir
        Set<Integer> columnasExcluidas = new HashSet<>(Arrays.asList(2, 3, 4, 9)); // Columnas 3,4,5,10 base 0

        // Contar columnas visibles
        int columnasVisibles = 0;
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            if (!columnasExcluidas.contains(i)) columnasVisibles++;
        }

        PdfPTable tablaPDF = new PdfPTable(columnasVisibles);
        tablaPDF.setWidthPercentage(100);
        tablaPDF.setSpacingBefore(10f);

        // === HEADER CON FUENTE PEQUEÑA Y COLOR AZUL OSCURO ===
        BaseColor azulOscuro = new BaseColor(0, 70, 140);
        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, BaseColor.WHITE);

        for (int col = 0; col < modelo.getColumnCount(); col++) {
            if (columnasExcluidas.contains(col)) continue;

            PdfPCell celdaEncabezado = new PdfPCell(new Phrase(modelo.getColumnName(col), fontHeader));
            celdaEncabezado.setBackgroundColor(azulOscuro);
            celdaEncabezado.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaEncabezado.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celdaEncabezado.setPadding(5f);
            tablaPDF.addCell(celdaEncabezado);
        }

        // === DATOS DE LA TABLA ===
        for (int row = 0; row < modelo.getRowCount(); row++) {
            for (int col = 0; col < modelo.getColumnCount(); col++) {
                if (columnasExcluidas.contains(col)) continue;

                Object valor = modelo.getValueAt(row, col);
                PdfPCell celda = new PdfPCell(new Phrase(valor != null ? valor.toString() : ""));
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setPadding(4f);
                tablaPDF.addCell(celda);
            }
        }

        documento.add(tablaPDF);

        // === LOGO INFERIOR DERECHO ===
        if (rutaLogoSistema != null && !rutaLogoSistema.isEmpty()) {
            try {
                Image logoSistema = Image.getInstance(rutaLogoSistema);
                logoSistema.scaleToFit(50, 50);
                float x = documento.right() - 50;
                float y = documento.bottom() + 10;
                logoSistema.setAbsolutePosition(x, y);
                writer.getDirectContent().addImage(logoSistema);
            } catch (Exception e) {
                System.err.println("Error cargando logo del sistema: " + e.getMessage());
            }
        }

        documento.close();
        System.out.println("✅ PDF generado correctamente: " + nombreArchivo);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}


