package pe.edu.upeu.segundaunidadfx.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.segundaunidadfx.dto.ComboBoxOption;
import pe.edu.upeu.segundaunidadfx.modelo.Estudiante;
import pe.edu.upeu.segundaunidadfx.repositorio.EstudianteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository repo;

    public Estudiante save(Estudiante estudiante) {
        return repo.save(estudiante);
    }

    public List<Estudiante> list() {
        return repo.findAll();
    }

    public Estudiante update(Estudiante estudiante, Long id) {
        try {
            Optional<Estudiante> existingEstudiante = repo.findById(id);
            if (existingEstudiante.isPresent()) {
                Estudiante estudianteToUpdate = existingEstudiante.get();
                estudianteToUpdate.setNombre(estudiante.getNombre());
                estudianteToUpdate.setApellido(estudiante.getApellido());
                estudianteToUpdate.setDocumentoIdentidad(estudiante.getDocumentoIdentidad());
                estudianteToUpdate.setDireccion(estudiante.getDireccion());
                estudianteToUpdate.setEmail(estudiante.getEmail());
                estudianteToUpdate.setTelefono(estudiante.getTelefono());
                return repo.save(estudianteToUpdate);
            } else {
                System.out.println("Estudiante con ID " + id + " no encontrado para actualizar.");
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar el estudiante: " + e.getMessage());
        }
        return null;
    }

    public Estudiante update(Estudiante estudiante) {
        return repo.save(estudiante);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Estudiante searchById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<ComboBoxOption> listarCombobox() {
        List<ComboBoxOption> listar = new ArrayList<>();
        for (Estudiante estudiante : repo.findAll()) {
            ComboBoxOption cb = new ComboBoxOption();
            cb.setKey(String.valueOf(estudiante.getIdEstudiante()));
            cb.setValue(estudiante.getNombre() + " " + estudiante.getApellido());
            listar.add(cb);
        }
        return listar;
    }
}

