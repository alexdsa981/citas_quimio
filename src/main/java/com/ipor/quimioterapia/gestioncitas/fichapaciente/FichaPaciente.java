package com.ipor.quimioterapia.gestioncitas.fichapaciente;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.atencionquimioterapia.AtencionQuimioterapia;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.cita.Cita;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.detallequimioterapia.DetalleQuimioterapia;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.detallecie.DetalleCie;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.funcionesvitales.FuncionesVitales;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class FichaPaciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaCreacion;
    private LocalTime horaCreacion;

    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "id_cita")
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "id_atencion_quimioterapia")
    private AtencionQuimioterapia atencionQuimioterapia;
    @ManyToOne
    @JoinColumn(name = "id_detalle_quimioterapia")
    private DetalleQuimioterapia detalleQuimioterapia;

    @OneToMany(mappedBy = "fichaPaciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FuncionesVitales> funcionesVitales;

    @OneToMany(mappedBy = "fichaPaciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleCie> detalleCies;



}
