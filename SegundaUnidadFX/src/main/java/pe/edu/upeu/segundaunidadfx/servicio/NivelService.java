package pe.edu.upeu.segundaunidadfx.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.segundaunidadfx.modelo.Nivel;
import pe.edu.upeu.segundaunidadfx.repositorio.NivelRepository;

import java.util.List;

@Service
public class NivelService {

    @Autowired
    private NivelRepository repo;

    // C - Crear
    public Nivel save(Nivel nivel) {
        return repo.save(nivel);
    }

    // R - Listar todos
    public List<Nivel> list() {
        return repo.findAll();
    }

    // U - Actualizar
    public Nivel update(Nivel nivel, Long id) {
        return repo.findById(id)
                .map(existingNivel -> {
                    existingNivel.setNombre(nivel.getNombre());
                    existingNivel.setPago(nivel.getPago());
                    existingNivel.setCurso(nivel.getCurso()); // Actualizar el curso si es necesario
                    return repo.save(existingNivel);
                })
                .orElse(null);
    }

    // D - Eliminar
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // B - Buscar por ID
    public Nivel searchById(Long id) {
        return repo.findById(id).orElse(null);
    }
}
