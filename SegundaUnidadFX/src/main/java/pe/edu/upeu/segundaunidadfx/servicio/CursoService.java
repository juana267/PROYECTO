package pe.edu.upeu.segundaunidadfx.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.segundaunidadfx.modelo.Curso;
import pe.edu.upeu.segundaunidadfx.repositorio.CursoRepository;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository repo;

    // C - Crear
    public Curso save(Curso curso) {
        return repo.save(curso);
    }

    // R - Listar todos
    public List<Curso> list() {
        return repo.findAll();
    }

    // U - Actualizar
    public Curso update(Curso curso, Long id) {
        return repo.findById(id)
                .map(existingCurso -> {
                    existingCurso.setNombre(curso.getNombre());
                    return repo.save(existingCurso);
                })
                .orElse(null);
    }

    // D - Eliminar
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // B - Buscar por ID
    public Curso searchById(Long id) {
        return repo.findById(id).orElse(null);
    }
}

