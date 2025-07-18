package com.ipor.quimioterapia.gestioncitas.botones.exportar;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.atencionquimioterapia.AtencionQuimioterapia;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.cita.Cita;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.detallequimioterapia.DetalleQuimioterapia;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.detallecie.DetalleCie;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.funcionesvitales.FuncionesVitales;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.paciente.Paciente;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFTableStyleInfo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app/exportar-fichas")
public class ExportarController {

    @Autowired
    FichaPacienteService fichaPacienteService;

    @ResponseBody
    @PostMapping("/")
    public void exportarFichas(@RequestBody List<Long> idsFichas, HttpServletResponse response) throws IOException {
        List<FichaPaciente> listaFichas = new ArrayList<>();
        for (Long idFicha : idsFichas) {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(idFicha);
            listaFichas.add(fichaPaciente);
        }

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Fichas");

        // Estilo para celdas nulas (fondo rojo suave)
        CellStyle estiloRojo = workbook.createCellStyle();
        estiloRojo.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        estiloRojo.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Encabezados
        String[] columnas = {
                "ID Ficha", "Fecha Creación", "Hora Creación", "Usuario", "Fecha Cita", "Hora Programada Inicio","Hora Programada Fin",
                "Paciente", "Tipo Doc", "Doc Identidad", "Sexo", "Fecha Nacimiento", "Edad", "Teléfono", "Celular", "Aseguradora",
                "Médico Tratante", "Médico de Turno", "Enfermera", "Cubículo", "Hora Inicio", "Hora Fin", "Duración (min)", "Estado",
                "Medicinas", "Observaciones", "Exámenes Aux.", "Tratamiento",
                "Presión", "FC", "FR", "Sat O2", "Temp", "Peso", "Talla", "SC", "CIEs"
        };

        Row header = sheet.createRow(0);
        for (int i = 0; i < columnas.length; i++) {
            header.createCell(i).setCellValue(columnas[i]);
        }

        int filaIdx = 1;
        for (FichaPaciente ficha : listaFichas) {
            Row fila = sheet.createRow(filaIdx++);
            Cita cita = ficha.getCita();
            Paciente paciente = ficha.getPaciente();
            AtencionQuimioterapia atencion = ficha.getAtencionQuimioterapia();
            DetalleQuimioterapia detalle = ficha.getDetalleQuimioterapia();
            FuncionesVitales vitals = ficha.getFuncionesVitales();
            List<DetalleCie> cies = ficha.getDetalleCies();

            crearCeldaSeguro(fila, 0, String.valueOf(ficha.getId()), estiloRojo);
            crearCeldaSeguro(fila, 1, ficha.getFechaCreacion() != null ? ficha.getFechaCreacion().toString() : null, estiloRojo);
            crearCeldaSeguro(fila, 2, ficha.getHoraCreacion() != null ? ficha.getHoraCreacion().toString() : null, estiloRojo);
            crearCeldaSeguro(fila, 3, cita.getUsuarioCreacion() != null ? cita.getUsuarioCreacion().getUsername() : null, estiloRojo);

            crearCeldaSeguro(fila, 4, cita.getFecha() != null ? cita.getFecha().toString() : null, estiloRojo);
            crearCeldaSeguro(fila, 5, cita.getHoraProgramada() != null ? cita.getHoraProgramada().toString() : null, estiloRojo);

            if (cita.getHoraProgramada() != null && cita.getDuracionMinutosProtocolo() != null) {
                LocalTime horaFinProtocolo = cita.getHoraProgramada().plusMinutes(cita.getDuracionMinutosProtocolo());
                crearCeldaSeguro(fila, 6, horaFinProtocolo.toString(), estiloRojo);
            } else {
                crearCeldaSeguro(fila, 6, null, estiloRojo);
            }



            crearCeldaSeguro(fila, 7, paciente.getNombreCompleto(), estiloRojo);
            crearCeldaSeguro(fila, 8, paciente.getTipoDocumentoNombre(), estiloRojo);
            crearCeldaSeguro(fila, 9, paciente.getNumDocIdentidad(), estiloRojo);
            crearCeldaSeguro(fila, 10, paciente.getSexo(), estiloRojo);
            crearCeldaSeguro(fila, 11, String.valueOf(paciente.getFechaNacimiento()), estiloRojo);
            crearCeldaSeguro(fila, 12, String.valueOf(paciente.getEdad()), estiloRojo);
            crearCeldaSeguro(fila, 13, paciente.getTelefono(), estiloRojo);
            crearCeldaSeguro(fila, 14, paciente.getNumCelular(), estiloRojo);
            crearCeldaSeguro(fila, 15, cita.getAseguradora(), estiloRojo);
            crearCeldaSeguro(fila, 16, cita.getMedicoConsulta() != null ? cita.getMedicoConsulta().getNombreCompleto() : null, estiloRojo);

            crearCeldaSeguro(fila, 17, atencion != null && atencion.getMedico() != null ? atencion.getMedico().getNombreCompleto() : null, estiloRojo);
            crearCeldaSeguro(fila, 18, atencion != null && atencion.getEnfermera() != null ? atencion.getEnfermera().getNombreCompleto() : null, estiloRojo);

            crearCeldaSeguro(fila, 19, atencion != null && atencion.getCubiculo() != null ? atencion.getCubiculo().getCodigo() : null, estiloRojo);

            LocalTime horaInicio = (atencion != null) ? atencion.getHoraInicio() : null;
            LocalTime horaFin = (atencion != null) ? atencion.getHoraFin() : null;

            crearCeldaSeguro(fila, 20, horaInicio != null ? horaInicio.toString() : null, estiloRojo);

            crearCeldaSeguro(fila, 21, horaFin != null ? horaFin.toString() : null, estiloRojo);

            crearCeldaSeguro(fila, 22,
                    (horaInicio != null && horaFin != null) ?
                            String.valueOf(calcularDuracion(horaInicio, horaFin)) :
                            null,
                    estiloRojo);

            crearCeldaSeguro(fila, 23, cita.getEstado().toString(), estiloRojo);


            crearCeldaSeguro(fila, 24, detalle != null ? detalle.getMedicinas() : null, estiloRojo);
            crearCeldaSeguro(fila, 25, detalle != null ? detalle.getObservaciones() : null, estiloRojo);
            crearCeldaSeguro(fila, 26, detalle != null ? detalle.getExamenesAuxiliares() : null, estiloRojo);
            crearCeldaSeguro(fila, 27, detalle != null ? detalle.getTratamiento() : null, estiloRojo);

            crearCeldaSeguro(fila, 28, vitals != null ? vitals.getPresionSistolica() + "/" + vitals.getPresionDiastolica() : null, estiloRojo);
            crearCeldaSeguro(fila, 29, vitals != null ? String.valueOf(vitals.getFrecuenciaCardiaca()) : null, estiloRojo);
            crearCeldaSeguro(fila, 30, vitals != null ? String.valueOf(vitals.getFrecuenciaRespiratoria()) : null, estiloRojo);
            crearCeldaSeguro(fila, 31, vitals != null ? String.valueOf(vitals.getSaturacionOxigeno()) : null, estiloRojo);
            crearCeldaSeguro(fila, 32, vitals != null ? String.valueOf(vitals.getTemperatura()) : null, estiloRojo);
            crearCeldaSeguro(fila, 33, vitals != null ? String.valueOf(vitals.getPesoKg()) : null, estiloRojo);
            crearCeldaSeguro(fila, 34, vitals != null ? String.valueOf(vitals.getTallaCm()) : null, estiloRojo);
            crearCeldaSeguro(fila, 35, vitals != null ? String.valueOf(vitals.getSuperficieCorporal()) : null, estiloRojo);

            String codigosCie = (cies != null && !cies.isEmpty())
                    ? cies.stream().map(dc -> dc.getCie().getCodigo() + " - " + dc.getCie().getDescripcion())
                    .collect(Collectors.joining(", "))
                    : null;
            crearCeldaSeguro(fila, 36, codigosCie, estiloRojo);
        }

        // Autoajuste
        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

// Crear tabla Excel (filtros, orden, estilo)
        AreaReference ref = new AreaReference(
                new CellReference(0, 0),
                new CellReference(listaFichas.size(), columnas.length - 1),
                SpreadsheetVersion.EXCEL2007
        );
        XSSFTable tabla = sheet.createTable(ref);
        tabla.setName("FichasTabla");
        tabla.setDisplayName("FichasTabla");


// Solo aplica autofiltro y define que es una tabla (sin estilo visual personalizado)
        tabla.getCTTable().addNewTableStyleInfo().setName("TableStyleMedium9");
        tabla.getCTTable().addNewAutoFilter().setRef(ref.formatAsString());


        // Respuesta HTTP
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=fichas.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private void crearCeldaSeguro(Row fila, int col, String valor, CellStyle estiloRojo) {
        Cell celda = fila.createCell(col);
        if (valor != null && !valor.equals("null")) {
            celda.setCellValue(valor);
        } else {
            celda.setCellValue("");
            celda.setCellStyle(estiloRojo);
        }
    }

    private String toStringOrNull(Time hora) {
        return (hora != null) ? hora.toString() : null;
    }

    public long calcularDuracion(LocalTime inicio, LocalTime fin) {
        if (inicio == null || fin == null) return 0;
        return Duration.between(inicio, fin).toMinutes();
    }

}
