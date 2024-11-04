package pe.edu.upeu.segundaunidadfx.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.segundaunidadfx.dto.ComboBoxOption;
import pe.edu.upeu.segundaunidadfx.modelo.MatriculaIdioma;
import pe.edu.upeu.segundaunidadfx.repositorio.MatriculaIdiomaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatriculaIdiomaService {

    @Autowired
    private MatriculaIdiomaRepository repo;

    public MatriculaIdioma save(MatriculaIdioma matricula) {
        return repo.save(matricula);
    }

    public List<MatriculaIdioma> list() {
        return repo.findAll();
    }

    public MatriculaIdioma update(MatriculaIdioma matricula, Long id) {
        try {
            Optional<MatriculaIdioma> existingMatricula = repo.findById(id);
            if (existingMatricula.isPresent()) {
                MatriculaIdioma matriculaToUpdate = existingMatricula.get();
                matriculaToUpdate.setEstudiante(matricula.getEstudiante());
                matriculaToUpdate.setCursoIdioma(matricula.getCursoIdioma());
                matriculaToUpdate.setFechaInicio(matricula.getFechaInicio());
                matriculaToUpdate.setFechaFin(matricula.getFechaFin());
                matriculaToUpdate.setNivel(matricula.getNivel());
                matriculaToUpdate.setEstado(matricula.getEstado());
                return repo.save(matriculaToUpdate);
            } else {
                System.out.println("Matrícula con ID " + id + " no encontrada para actualizar.");
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar la matrícula: " + e.getMessage());
        }
        return null;
    }

    public MatriculaIdioma update(MatriculaIdioma matricula) {
        return repo.save(matricula);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public MatriculaIdioma searchById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<ComboBoxOption> listarCombobox() {
        List<ComboBoxOption> listar = new ArrayList<>();
        for (MatriculaIdioma matricula : repo.findAll()) {
            ComboBoxOption cb = new ComboBoxOption();
            cb.setKey(String.valueOf(matricula.getIdMatricula()));
            cb.setValue(matricula.getEstudiante().getNombre() + " - " + matricula.getCursoIdioma().getNombre());
            listar.add(cb);
        }
        return listar;
    }
}
