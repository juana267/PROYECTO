package pe.edu.upeu.segundaunidadfx.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.segundaunidadfx.modelo.Nota;
import pe.edu.upeu.segundaunidadfx.repositorio.NotaRepository;

import java.util.List;

@Service
public class NotasService {

    @Autowired
    NotaRepository repo;

    // C - Crear
    public Nota save(Nota nota) {
        return repo.save(nota);
    }

    // R - Listar todos
    public List<Nota> list() {
        return repo.findAll();
    }

    // U - Actualizar
    public Nota update(Nota nota, Long id) {
        return repo.findById(id)
                .map(existingNota -> {
                    existingNota.setNota1(nota.getNota1());
                    existingNota.setNota2(nota.getNota2());
                    existingNota.setNota3(nota.getNota3());
                    return repo.save(existingNota);
                })
                .orElse(null);
    }

    // D - Eliminar
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // B - Buscar por ID
    public Nota searchById(Long id) {
        return repo.findById(id).orElse(null);
    }
}



