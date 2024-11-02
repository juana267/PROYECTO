package pe.edu.upeu.segundaunidadfx.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.segundaunidadfx.modelo.Matricula;
import pe.edu.upeu.segundaunidadfx.repositorio.MatriculaRepository;

import java.util.List;

@Service
public class MatriculasService {

    @Autowired
    MatriculaRepository repo;

    // C - Crear
    public Matricula save(Matricula matricula) {
        return repo.save(matricula);
    }

    // R - Listar todos
    public List<Matricula> list() {
        return repo.findAll();
    }

    // U - Actualizar
    public Matricula update(Matricula matricula, Long id) {
        return repo.findById(id)
                .map(existingMatricula -> {
                    existingMatricula.setEstudianteNombre(matricula.getEstudianteNombre());
                    existingMatricula.setEstudianteApellido(matricula.getEstudianteApellido());
                    existingMatricula.setCurso(matricula.getCurso());
                    existingMatricula.setNivel(matricula.getNivel());
                    return repo.save(existingMatricula);
                })
                .orElse(null);
    }

    // D - Eliminar
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // B - Buscar por ID
    public Matricula searchById(Long id) {
        return repo.findById(id).orElse(null);
    }
}
