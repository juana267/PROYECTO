package pe.edu.upeu.segundaunidadfx.dto;

import lombok.Data;

@Data
public class SessionManager {
    static SessionManager instance;
    Integer userId;  // Cambia a Integer si originalmente estaba como Long
    String userName;
    String nombrePerfil;

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
}
