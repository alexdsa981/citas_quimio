package com.ipor.quimioterapia.repository.dynamic;

import com.ipor.quimioterapia.model.dynamic.AtencionQuimioterapia;
import com.ipor.quimioterapia.model.dynamic.FichaPaciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtencionQuimioterapiaRepository extends JpaRepository<AtencionQuimioterapia, Long> {

}
