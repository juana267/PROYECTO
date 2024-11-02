package pe.edu.upeu.segundaunidadfx.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.segundaunidadfx.modelo.Permiso;
import pe.edu.upeu.segundaunidadfx.repositorio.PermisoRepository;
import pe.edu.upeu.segundaunidadfx.repositorio.RolesRepository;

import java.util.List;

@Service
public class PermisosService {

    @Autowired
    private PermisoRepository repo;

    // C - Crear
    public Permiso save(Permiso permiso) {
        return repo.save(permiso);
    }

    // R - Listar todos
    public List<Permiso> list() {
        return (List<Permiso>) repo.findAll();
    }

    // U - Actualizar
    public Permiso update(Permiso permiso, Long id) {
        return repo.findById(id)
                .map(existingPermiso -> {
                    existingPermiso.setNombre(permiso.getNombre());
                    return repo.save(existingPermiso);
                })
                .orElse(null);
    }

    // D - Eliminar
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // B - Buscar por ID
    public Permiso searchById(Long id) {
        return repo.findById(id).orElse(null);
    }
}
