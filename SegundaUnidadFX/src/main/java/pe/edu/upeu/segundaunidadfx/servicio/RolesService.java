package pe.edu.upeu.segundaunidadfx.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.segundaunidadfx.modelo.Roles;
import pe.edu.upeu.segundaunidadfx.repositorio.RolesRepository;

import java.util.List;

@Service
public class RolesService {

    @Autowired
    private RolesRepository repo;

    // Método para buscar un rol por nombre
    public Roles getRoleByName(String nombre) {
        return repo.findByNombre(nombre);
    }

    // Métodos CRUD (crear, listar, actualizar, eliminar)

    public Roles save(Roles rol) {
        return repo.save(rol);
    }

    public List<Roles> list() {
        return repo.findAll();
    }

    public Roles update(Roles rol, Long id) {
        return repo.findById(id)
                .map(existingRol -> {
                    existingRol.setNombre(rol.getNombre());
                    return repo.save(existingRol);
                })
                .orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Roles searchById(Long id) {
        return repo.findById(id).orElse(null);
    }
}


