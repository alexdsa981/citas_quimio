package com.ipor.quimioterapia.gestioncitas.fichapaciente;

import com.ipor.quimioterapia.gestioncitas.dto.DetalleCieDTO;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.detallecie.DetalleCie;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.registrosantiguos.RegistrosAntiguos;
import com.ipor.quimioterapia.spring.cie.CieSpringDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FichaPacienteDTO {
    //FICHA PACIENTE
    private Long ficha_id;
    private Boolean ficha_isNuevo;
    private LocalDate ficha_fechaCreacion;
    private LocalTime ficha_horaCreacion;
    private Boolean ficha_isActive;

    //PACIENTE
    private Long paciente_id;
    private String paciente_nombre;
    private String paciente_apellidoP;
    private String paciente_apellidoM;
    private String paciente_nombreCompleto;
    private String paciente_fechaNacimiento;
    private String paciente_sexo;
    private String paciente_numCelular;
    private String paciente_tipoDocumentoNombre;
    private String paciente_numDocIdentidad;
    private String paciente_telefono;
    private Integer paciente_edad;


    //CITA
    private LocalDate cita_fecha;
    private LocalTime cita_horaProgramada;
    private String cita_estado;
    private String cita_aseguradora;
    private String cita_medico;
    private Integer cita_duracionMinutosProtocolo;
    private String cita_usuarioCreacion;
    private LocalDate cita_fechaRegistro;

    public Integer getHorasProtocolo() {
        if (cita_duracionMinutosProtocolo == null) return 0;
        return cita_duracionMinutosProtocolo / 60;
    }

    public Integer getMinutosRestantesProtocolo() {
        if (cita_duracionMinutosProtocolo == null) return 0;
        return cita_duracionMinutosProtocolo % 60;
    }

    //DETALLE
    private String detalle_medicinas;
    private String detalle_observaciones;
    private String detalle_examenesAuxiliares;
    private String detalle_tratamiento;

    //ATENCION
    private String atencion_cubiculo;
    private String atencion_enfermera;
    private String atencion_medico;
    private LocalTime atencion_horaInicio;
    private LocalTime atencion_horaFin;

    //FUNCIONES VITALES
    private Integer fv_presionSistolica;
    private Integer fv_presionDiastolica;
    private Integer fv_frecuenciaCardiaca;
    private Integer fv_frecuenciaRespiratoria;
    private Integer fv_saturacionOxigeno;
    private Double fv_temperatura;
    private Double fv_pesoKg;
    private Double fv_tallaCm;
    private Double fv_superficieCorporal;

    //LISTA CIE
    private List<DetalleCieDTO> listaCIE = new ArrayList<>();

    public FichaPacienteDTO(FichaPaciente fichaPaciente){
        //FICHA
        if(fichaPaciente.getCita() != null) {
            this.ficha_id = fichaPaciente.getId();
            this.ficha_isActive = fichaPaciente.getIsActive();
            this.ficha_horaCreacion = fichaPaciente.getHoraCreacion();
            this.ficha_fechaCreacion = fichaPaciente.getFechaCreacion();
        }

        //CITA
        if(fichaPaciente.getCita() != null) {
            this.cita_duracionMinutosProtocolo = fichaPaciente.getCita().getDuracionMinutosProtocolo();
            this.cita_medico = fichaPaciente.getCita().getMedicoConsulta().getNombreCompleto();
            this.cita_aseguradora = fichaPaciente.getCita().getAseguradora();
            this.cita_estado = fichaPaciente.getCita().getEstado().toString();
            this.cita_horaProgramada = fichaPaciente.getCita().getHoraProgramada();
            this.cita_fecha = fichaPaciente.getCita().getFecha();
            this.cita_usuarioCreacion = fichaPaciente.getCita().getUsuarioCreacion().getUsername();
            this.cita_fechaRegistro = fichaPaciente.getFechaCreacion();
        }


        //PACIENTE
        if(fichaPaciente.getCita() != null) {
            this.paciente_id = fichaPaciente.getPaciente().getIdPaciente();
            this.paciente_edad = fichaPaciente.getPaciente().getEdad();
            this.paciente_numDocIdentidad = fichaPaciente.getPaciente().getNumDocIdentidad();
            this.paciente_tipoDocumentoNombre = fichaPaciente.getPaciente().getTipoDocumentoNombre();
            this.paciente_numCelular = fichaPaciente.getPaciente().getNumCelular();
            this.paciente_telefono = fichaPaciente.getPaciente().getTelefono();
            this.paciente_sexo = fichaPaciente.getPaciente().getSexo();
            this.paciente_fechaNacimiento = fichaPaciente.getPaciente().getFechaNacimiento();
            this.paciente_nombreCompleto = fichaPaciente.getPaciente().getNombreCompleto();
            this.paciente_apellidoM = fichaPaciente.getPaciente().getApellidoM();
            this.paciente_apellidoP = fichaPaciente.getPaciente().getApellidoP();
            this.paciente_nombre = fichaPaciente.getPaciente().getNombre();
        }


        //FUNCIONES VITALES
        if(fichaPaciente.getFuncionesVitales() != null){
            this.fv_superficieCorporal = fichaPaciente.getFuncionesVitales().getSuperficieCorporal();
            this.fv_tallaCm = fichaPaciente.getFuncionesVitales().getTallaCm();
            this.fv_pesoKg = fichaPaciente.getFuncionesVitales().getPesoKg();
            this.fv_temperatura = fichaPaciente.getFuncionesVitales().getTemperatura();
            this.fv_saturacionOxigeno = fichaPaciente.getFuncionesVitales().getSaturacionOxigeno();
            this.fv_frecuenciaRespiratoria = fichaPaciente.getFuncionesVitales().getFrecuenciaRespiratoria();
            this.fv_frecuenciaCardiaca = fichaPaciente.getFuncionesVitales().getFrecuenciaCardiaca();
            this.fv_presionDiastolica = fichaPaciente.getFuncionesVitales().getPresionDiastolica();
            this.fv_presionSistolica = fichaPaciente.getFuncionesVitales().getPresionSistolica();
        }

        //LISTA DETALLES CIE
        if(fichaPaciente.getDetalleCies() != null) {
            for (DetalleCie detalleCie : fichaPaciente.getDetalleCies()){
                DetalleCieDTO dtocie = new DetalleCieDTO(detalleCie);
                this.listaCIE.add(dtocie);
            }
        }

        //ATENCION
        if(fichaPaciente.getAtencionQuimioterapia() != null) {
            this.atencion_horaFin = fichaPaciente.getAtencionQuimioterapia().getHoraFin();
            this.atencion_horaInicio = fichaPaciente.getAtencionQuimioterapia().getHoraInicio();
            this.atencion_medico = fichaPaciente.getAtencionQuimioterapia().getMedico().getNombreCompleto();
            this.atencion_enfermera = fichaPaciente.getAtencionQuimioterapia().getEnfermera().getNombreCompleto();
            this.atencion_cubiculo = fichaPaciente.getAtencionQuimioterapia().getCubiculo().getCodigo();
        }

        //DETALLE
        if(fichaPaciente.getDetalleQuimioterapia() != null) {
            this.detalle_tratamiento = fichaPaciente.getDetalleQuimioterapia().getTratamiento();
            this.detalle_observaciones = fichaPaciente.getDetalleQuimioterapia().getObservaciones();
            this.detalle_medicinas = fichaPaciente.getDetalleQuimioterapia().getMedicinas();
        }

        //EXTRA
        this.ficha_isNuevo = true;
    }


    public FichaPacienteDTO(RegistrosAntiguos registrosAntiguos){
        //FUNCIONES VITALES
        this.fv_superficieCorporal = registrosAntiguos.getSupCorporal();
        this.fv_tallaCm = registrosAntiguos.getTalla();
        this.fv_pesoKg = registrosAntiguos.getPeso();
        this.fv_temperatura = registrosAntiguos.getTemperatura();
        this.fv_saturacionOxigeno = registrosAntiguos.getSaturacion();
        this.fv_frecuenciaRespiratoria = registrosAntiguos.getFrecRespiratoria();
        this.fv_frecuenciaCardiaca = registrosAntiguos.getFrecCardiaca();
        this.fv_presionDiastolica = registrosAntiguos.getPresionMax();
        this.fv_presionSistolica = registrosAntiguos.getPresArterial();

        //LISTA DETALLES CIE
        if (registrosAntiguos.getCie1() != null && !registrosAntiguos.getCie1().isEmpty()){
            DetalleCieDTO ciedto1 = new DetalleCieDTO();
            ciedto1.setId(-1L);
            ciedto1.setCodigo(registrosAntiguos.getCodcie1());
            ciedto1.setDescripcion(registrosAntiguos.getCodcie1());
            this.getListaCIE().add(ciedto1);
        }
        if (registrosAntiguos.getCie2() != null && !registrosAntiguos.getCie2().isEmpty()){
            DetalleCieDTO ciedto2 = new DetalleCieDTO();
            ciedto2.setId(-2L);
            ciedto2.setCodigo(registrosAntiguos.getCodcie2());
            ciedto2.setDescripcion(registrosAntiguos.getCodcie2());
            this.getListaCIE().add(ciedto2);
        }

        //ATENCION
        this.atencion_horaFin = registrosAntiguos.getHoraFin();
        this.atencion_horaInicio = registrosAntiguos.getHoraReal();
        this.atencion_medico = registrosAntiguos.getMedico();
        this.atencion_enfermera = registrosAntiguos.getEnfermera();
        this.atencion_cubiculo = registrosAntiguos.getCama();

        //DETALLE
        this.detalle_tratamiento = registrosAntiguos.getTratamiento();
        this.detalle_observaciones = registrosAntiguos.getObservaciones();
        this.detalle_medicinas = registrosAntiguos.getMedicinas();

        //CITA
        this.cita_duracionMinutosProtocolo = registrosAntiguos.getHoraProtocolo().getHour() * 60 + registrosAntiguos.getHoraProtocolo().getMinute();
        this.cita_medico = registrosAntiguos.getMedico();
        this.cita_aseguradora = registrosAntiguos.getAseguradora();
        this.cita_estado = registrosAntiguos.getEstado();
        this.cita_horaProgramada = registrosAntiguos.getHoraInicio();
        this.cita_fecha = registrosAntiguos.getFecIngreso();
        this.cita_usuarioCreacion = registrosAntiguos.getUsuarioUp();
        this.cita_fechaRegistro = registrosAntiguos.getFecUp();

        //PACIENTE
        this.paciente_edad = registrosAntiguos.getEdad();
        this.paciente_numDocIdentidad = registrosAntiguos.getNoDocIdentidad();
        this.paciente_tipoDocumentoNombre = registrosAntiguos.getDocIdentidad();
        this.paciente_numCelular = registrosAntiguos.getTelefono();
        this.paciente_sexo = registrosAntiguos.getSexo();
        this.paciente_fechaNacimiento = registrosAntiguos.getFecNacimiento().toString();
        this.paciente_nombreCompleto = registrosAntiguos.getApellido_p() + " " + registrosAntiguos.getApellido_m() + " " + registrosAntiguos.getNombres();
        this.paciente_apellidoM = registrosAntiguos.getApellido_m();
        this.paciente_apellidoP = registrosAntiguos.getApellido_p();
        this.paciente_nombre = registrosAntiguos.getNombres();

        //FICHA
        this.ficha_id = -registrosAntiguos.getIdQuimioterapia();
        this.ficha_isActive = true;
        this.ficha_horaCreacion = null;
        this.ficha_fechaCreacion = null;

        //EXTRA
        this.ficha_isNuevo = false;
    }
}
