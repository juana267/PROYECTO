package pe.edu.upeu.segundaunidadfx.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.segundaunidadfx.modelo.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findByNombre(String nombre); // Agregar este método
}
