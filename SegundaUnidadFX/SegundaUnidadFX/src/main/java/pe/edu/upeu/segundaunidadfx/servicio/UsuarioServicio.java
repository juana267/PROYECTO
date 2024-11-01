package pe.edu.upeu.segundaunidadfx.servicio;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import pe.edu.upeu.segundaunidadfx.dto.UsuarioRegistroDTO;
import pe.edu.upeu.segundaunidadfx.modelo.Usuario;

public interface UsuarioServicio extends UserDetailsService {

    Usuario guardar(UsuarioRegistroDTO registroDTO);

    List<Usuario> listarUsuarios();
}
