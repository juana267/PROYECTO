package pe.edu.upeu.segundaunidadfx.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.segundaunidadfx.modelo.Usuario;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmailAndContraseA(String email, String contraseA); // Ajuste aquí
}


