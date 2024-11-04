package pe.edu.upeu.segundaunidadfx.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.segundaunidadfx.dto.ComboBoxOption;
import pe.edu.upeu.segundaunidadfx.dto.ModeloDataAutocomplet;
import pe.edu.upeu.segundaunidadfx.modelo.CursoIdioma;
import pe.edu.upeu.segundaunidadfx.repositorio.CursoIdiomaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CursoIdiomaService {
    @Autowired
    private CursoIdiomaRepository repo;
    private final Logger logger = LoggerFactory.getLogger(CursoIdiomaService.class);

    public CursoIdioma save(CursoIdioma curso) {
        return repo.save(curso);
    }

    public List<CursoIdioma> list() {
        return repo.findAll();
    }

    public CursoIdioma update(CursoIdioma curso, Long id) {
        try {
            Optional<CursoIdioma> existingCurso = repo.findById(id);
            if (existingCurso.isPresent()) {
                CursoIdioma cursoToUpdate = existingCurso.get();
                cursoToUpdate.setNombre(curso.getNombre());
                cursoToUpdate.setNivel(curso.getNivel());
                cursoToUpdate.setDuracion(curso.getDuracion());
                cursoToUpdate.setCosto(curso.getCosto());
                return repo.save(cursoToUpdate);
            } else {
                logger.warn("Curso con ID " + id + " no encontrado para actualizar.");
            }
        } catch (Exception e) {
            logger.error("Error al actualizar el curso: ", e);
        }
        return null;
    }

    public CursoIdioma update(CursoIdioma curso) {
        return repo.save(curso);
    }

    public void delete(Long id) {
        try {
            if (repo.existsById(id)) {
                repo.deleteById(id);
            } else {
                logger.warn("Curso con ID " + id + " no encontrado para eliminar.");
            }
        } catch (Exception e) {
            logger.error("Error al eliminar el curso con ID " + id, e);
        }
    }

    public CursoIdioma searchById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<ModeloDataAutocomplet> listAutoCompletCurso() {
        List<ModeloDataAutocomplet> listarCursos = new ArrayList<>();
        try {
            for (CursoIdioma curso : repo.findAll()) {
                ModeloDataAutocomplet data = new ModeloDataAutocomplet();
                data.setIdx(String.valueOf(curso.getIdCursoIdioma()));
                data.setNameDysplay(curso.getNombre());
                data.setOtherData(curso.getNivel() + ":" + curso.getDuracion());
                listarCursos.add(data);
            }
        } catch (Exception e) {
            logger.error("Error al realizar la búsqueda de autocompletado", e);
        }
        return listarCursos;
    }


    public ComboBoxOption listarCombobox() {
        return null;
    }
}

