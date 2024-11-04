package pe.edu.upeu.segundaunidadfx.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.segundaunidadfx.modelo.Estudiante;
import pe.edu.upeu.segundaunidadfx.modelo.MatriculaIdioma;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaIdiomaRepository extends JpaRepository<MatriculaIdioma, Long> {
    MatriculaIdioma save(MatriculaIdioma matricula);

    List<MatriculaIdioma> findAll();

    Optional<MatriculaIdioma> findById(Long id);
}
