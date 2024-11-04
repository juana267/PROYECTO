package pe.edu.upeu.segundaunidadfx.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.segundaunidadfx.modelo.CursoIdioma;

@Repository
public interface CursoIdiomaRepository extends JpaRepository<CursoIdioma, Long> {
}

