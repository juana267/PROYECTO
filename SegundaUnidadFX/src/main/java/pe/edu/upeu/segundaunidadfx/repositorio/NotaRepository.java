package pe.edu.upeu.segundaunidadfx.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.segundaunidadfx.modelo.Nivel;
import pe.edu.upeu.segundaunidadfx.modelo.Nota;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Long> {
}
