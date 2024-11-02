package pe.edu.upeu.segundaunidadfx.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List; // Asegúrate de tener esta línea
import pe.edu.upeu.segundaunidadfx.modelo.Usuario;
import pe.edu.upeu.segundaunidadfx.repositorio.PermisoRepository;
import pe.edu.upeu.segundaunidadfx.repositorio.UsuariosRepository;

@Service
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;

    @Autowired
    private UsuariosRepository repo;

    @Autowired
    public UsuariosService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public Usuario validarCredenciales(String email, String contraseA) {
        return usuariosRepository.findByEmailAndContraseA(email, contraseA);
    }

    public void save(Usuario usuario) {
        usuariosRepository.save(usuario);
    }

    public List<Usuario> getAllUsuarios() {
        return usuariosRepository.findAll();
    }

    // R - Listar todos
    public List<Usuario> list() {
        return repo.findAll();
    }

    // U - Actualizar
    public Usuario update(Usuario usuario, Long id) {
        return repo.findById(id)
                .map(existingUsuario -> {
                    existingUsuario.setNombre(usuario.getNombre());
                    existingUsuario.setApellido(usuario.getApellido());
                    existingUsuario.setEmail(usuario.getEmail());
                    existingUsuario.setContraseA(usuario.getContraseA());
                    existingUsuario.setRol(usuario.getRol());
                    return repo.save(existingUsuario);
                })
                .orElse(null);
    }

    // D - Eliminar
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // B - Buscar por ID
    public Usuario searchById(Long id) {
        return repo.findById(id).orElse(null);
    }
}
