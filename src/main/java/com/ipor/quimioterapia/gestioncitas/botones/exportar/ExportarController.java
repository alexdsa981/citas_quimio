package com.ipor.quimioterapia.gestioncitas.botones.exportar;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteDTO;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.atencionquimioterapia.AtencionQuimioterapia;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.cita.Cita;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.detallequimioterapia.DetalleQuimioterapia;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.detallecie.DetalleCie;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.funcionesvitales.FuncionesVitales;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.paciente.Paciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.registrosantiguos.RegistrosAntiguosService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
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
    @Autowired
    RegistrosAntiguosService registrosAntiguosService;

    @ResponseBody
    @PostMapping("/")
    public void exportarFichas(@RequestBody List<Long> idsFichas, HttpServletResponse response) throws IOException {
        List<FichaPacienteDTO> listaDTO = new ArrayList<>();

        for (Long idFicha : idsFichas) {
            if (idFicha > 0){
                listaDTO.add(new FichaPacienteDTO(fichaPacienteService.getPorID(idFicha)));
            } else{
                listaDTO.add(new FichaPacienteDTO(registrosAntiguosService.getPorID(-idFicha)));
            }
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
        for (FichaPacienteDTO dto : listaDTO) {
            Row fila = sheet.createRow(filaIdx++);
            crearCeldaSeguro(fila, 0, str(dto.getFicha_id()), estiloRojo);
            crearCeldaSeguro(fila, 1, str(dto.getCita_fechaRegistro()), estiloRojo);
            crearCeldaSeguro(fila, 2, str(dto.getFicha_horaCreacion()), estiloRojo);
            crearCeldaSeguro(fila, 3, str(dto.getCita_usuarioCreacion()), estiloRojo);

            crearCeldaSeguro(fila, 4, str(dto.getCita_fecha()), estiloRojo);
            crearCeldaSeguro(fila, 5, str(dto.getCita_horaProgramada()), estiloRojo);

            crearCeldaSeguro(
                    fila,
                    6,
                    (dto.getCita_horaProgramada() != null && dto.getCita_duracionMinutosProtocolo() != null)
                            ? dto.getCita_horaProgramada().plusMinutes(dto.getCita_duracionMinutosProtocolo()).toString()
                            : null,
                    estiloRojo
            );

            crearCeldaSeguro(fila, 7, str(dto.getPaciente_nombreCompleto()), estiloRojo);
            crearCeldaSeguro(fila, 8, str(dto.getPaciente_tipoDocumentoNombre()), estiloRojo);
            crearCeldaSeguro(fila, 9, str(dto.getPaciente_numDocIdentidad()), estiloRojo);
            crearCeldaSeguro(fila, 10, str(dto.getPaciente_sexo()), estiloRojo);
            crearCeldaSeguro(fila, 11, str(dto.getPaciente_fechaNacimiento()), estiloRojo);
            crearCeldaSeguro(fila, 12, str(dto.getPaciente_edad()), estiloRojo);
            crearCeldaSeguro(fila, 13, str(dto.getPaciente_telefono()), estiloRojo);
            crearCeldaSeguro(fila, 14, str(dto.getPaciente_numCelular()), estiloRojo);
            crearCeldaSeguro(fila, 15, str(dto.getCita_aseguradora()), estiloRojo);
            crearCeldaSeguro(fila, 16, str(dto.getCita_medico()), estiloRojo);
            crearCeldaSeguro(fila, 17, str(dto.getAtencion_medico()), estiloRojo);
            crearCeldaSeguro(fila, 18, str(dto.getAtencion_enfermera()), estiloRojo);
            crearCeldaSeguro(fila, 19, str(dto.getAtencion_cubiculo()), estiloRojo);

            LocalTime horaInicio = dto.getAtencion_horaInicio();
            LocalTime horaFin = dto.getAtencion_horaFin();

            crearCeldaSeguro(fila, 20, str(horaInicio), estiloRojo);
            crearCeldaSeguro(fila, 21, str(horaFin), estiloRojo);
            crearCeldaSeguro(fila, 22, (horaInicio != null && horaFin != null)
                    ? String.valueOf(calcularDuracion(horaInicio, horaFin))
                    : null, estiloRojo);

            crearCeldaSeguro(fila, 23, str(dto.getCita_estado()), estiloRojo);
            crearCeldaSeguro(fila, 24, str(dto.getDetalle_medicinas()), estiloRojo);
            crearCeldaSeguro(fila, 25, str(dto.getDetalle_observaciones()), estiloRojo);
            crearCeldaSeguro(fila, 26, str(dto.getDetalle_examenesAuxiliares()), estiloRojo);
            crearCeldaSeguro(fila, 27, str(dto.getDetalle_tratamiento()), estiloRojo);

            crearCeldaSeguro(fila, 28,
                    (dto.getFv_presionSistolica() != null && dto.getFv_presionDiastolica() != null)
                            ? dto.getFv_presionSistolica() + "/" + dto.getFv_presionDiastolica()
                            : null,
                    estiloRojo
            );

            crearCeldaSeguro(fila, 29, str(dto.getFv_frecuenciaCardiaca()), estiloRojo);
            crearCeldaSeguro(fila, 30, str(dto.getFv_frecuenciaRespiratoria()), estiloRojo);
            crearCeldaSeguro(fila, 31, str(dto.getFv_saturacionOxigeno()), estiloRojo);
            crearCeldaSeguro(fila, 32, str(dto.getFv_temperatura()), estiloRojo);
            crearCeldaSeguro(fila, 33, str(dto.getFv_pesoKg()), estiloRojo);
            crearCeldaSeguro(fila, 34, str(dto.getFv_tallaCm()), estiloRojo);
            crearCeldaSeguro(fila, 35, str(dto.getFv_superficieCorporal()), estiloRojo);

            String codigosCie = (dto.getListaCIE() != null && !dto.getListaCIE().isEmpty())
                    ? dto.getListaCIE().stream()
                    .map(dc -> dc.getCodigo() + " - " + dc.getDescripcion())
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
                new CellReference(listaDTO.size(), columnas.length - 1),
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
    private static String str(Object val) {
        return val != null ? val.toString() : null;
    }


}
