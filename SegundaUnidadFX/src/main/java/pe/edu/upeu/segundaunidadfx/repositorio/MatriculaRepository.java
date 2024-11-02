package pe.edu.upeu.segundaunidadfx.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.segundaunidadfx.modelo.Matricula;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
}