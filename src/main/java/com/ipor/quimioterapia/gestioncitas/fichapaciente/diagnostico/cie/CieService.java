package com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.cie;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.dto.CieCrearDTO;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.detallecie.DetalleCie;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.detallecie.DetalleCieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CieService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CieRepository cieRepository;
    @Autowired
    private DetalleCieRepository detalleCieRepository;

    public void crearOActualizar(CieCrearDTO dto) {
        Optional<Cie> optionalCie = cieRepository.findById(dto.getId());

        Cie entidad;
        if (optionalCie.isPresent()) {
            entidad = optionalCie.get();
        } else {
            entidad = new Cie();
            entidad.setId(dto.getId());
        }

        entidad.setCodigo(dto.getCodigo());
        entidad.setDescripcion(dto.getDescripcion());

        cieRepository.save(entidad);
    }


    public Cie getPorID(Long id) {
        return cieRepository.findById(id).get();
    }


    @Transactional
    public List<DetalleCie> guardarListaDetalleCie(FichaPaciente fichaPaciente, List<Long> listaCie) {
        List<DetalleCie> actuales = detalleCieRepository.findByFichaPacienteId(fichaPaciente.getId());

        Set<Long> idsActuales = actuales.stream()
                .map(dc -> dc.getCie().getId())
                .collect(Collectors.toSet());

        Set<Long> idsNuevos = new HashSet<>(listaCie);

        // Agregar nuevos que no estén aún
        for (Long idCie : idsNuevos) {
            if (!idsActuales.contains(idCie)) {
                Cie cie = getPorID(idCie);
                DetalleCie nuevoDetalle = new DetalleCie();
                nuevoDetalle.setCie(cie);
                nuevoDetalle.setFichaPaciente(fichaPaciente);
                detalleCieRepository.save(nuevoDetalle);
            }
        }

        // Eliminar los que ya no están
        if (!idsNuevos.isEmpty()) {
            detalleCieRepository.deleteByFichaPacienteIdAndCieIdNotIn(fichaPaciente.getId(), listaCie);
        } else {
            // Si no se envió ninguno, borra todos los existentes
            detalleCieRepository.deleteAll(actuales);
        }
        return fichaPaciente.getDetalleCies();
    }



    public List<DetalleCie> getPorIDFichaPaciente(Long id) {
        return detalleCieRepository.findByFichaPacienteId(id);
    }

}
