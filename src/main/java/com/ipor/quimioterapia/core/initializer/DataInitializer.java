package com.ipor.quimioterapia.core.initializer;

import com.ipor.quimioterapia.gestioncitas.botones.reprogramar.MotivoReprogramacionRepository;
import com.ipor.quimioterapia.gestioncitas.botones.reprogramar.MotivoReprogramacion;
import com.ipor.quimioterapia.usuario.Usuario;
import com.ipor.quimioterapia.usuario.UsuarioRepository;
import com.ipor.quimioterapia.usuario.rol.RolUsuario;
import com.ipor.quimioterapia.usuario.rol.RolUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

     //
     //CLASIFICADORES
     //

    @Autowired
    RolUsuarioRepository rolUsuarioRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    MotivoReprogramacionRepository motivoReprogramacionRepository;


    @Override
    public void run(String... args) {

        if (rolUsuarioRepository.count() == 0) {
            rolUsuarioRepository.save(new RolUsuario("General"));
            rolUsuarioRepository.save(new RolUsuario("Supervisor"));
            rolUsuarioRepository.save(new RolUsuario("Admin"));
        }

        if (usuarioRepository.count() == 0) {
            RolUsuario rolAdmin = rolUsuarioRepository.findByNombre("Admin");
            Usuario admin = new Usuario();
            admin.setNombre("ADMINISTRADOR");
            admin.setUsername("ADMIN");
            admin.setPassword("$2a$12$7SW6dd16qcrYSdV0L4Uzp.qzCEe6ricYOH9fdr1r/bGlF2ItBun4a");
            admin.setRolUsuario(rolAdmin);
            admin.setIsActive(true);
            admin.setChangedPass(false);
            admin.setIsSpringUser(false);
            usuarioRepository.save(admin);
        }

        if (motivoReprogramacionRepository.count() == 0) {
            MotivoReprogramacion motivo1 = new MotivoReprogramacion();
            motivo1.setNombre("Efectos adversos");
            motivo1.setIsActive(Boolean.TRUE);
            motivoReprogramacionRepository.save(motivo1);
            MotivoReprogramacion motivo2= new MotivoReprogramacion();
            motivo2.setNombre("Otros");
            motivo2.setIsActive(Boolean.TRUE);
            motivoReprogramacionRepository.save(motivo2);
        }




    }
}