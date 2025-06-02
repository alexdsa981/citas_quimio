package com.ipor.quimioterapia.model.dynamic;

import com.ipor.quimioterapia.model.fixed.Aseguradora;
import com.ipor.quimioterapia.model.fixed.Contratante;
import com.ipor.quimioterapia.model.fixed.TipoDocIdentidad;
import com.ipor.quimioterapia.model.fixed.TipoPaciente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_tipo_doc_identidad", "num_doc_identidad"})
)
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(nullable = false, unique = true)
    private String historia;
    //@Column(nullable = false)
    private String nombre;
    //@Column(nullable = false)
    private String apellidoP;
    //@Column(nullable = false)
    private String apellidoM;
    //@Column(nullable = false)
    private LocalDate fechaNacimiento;
    //@Column(nullable = false)
    private String sexo;
    //@Column(nullable = false)
    private String numCelular;
    //@Column(nullable = false)
    private String numDocIdentidad;

    @ManyToOne
    @JoinColumn(name = "id_tipo_doc_identidad")
    private TipoDocIdentidad tipoDocIdentidad;

    @ManyToOne
    @JoinColumn(name = "id_contratante")
    private Contratante contratante;

}
