
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Informacion de un usuario
 * implementado por jjramos
 * @author David
 * 
 */
public class Usuario {
    
    public String usuario;
    
    
    
    
    UsuarioContenedor usuarios;
    
    /** Creates a new instance of Usuario */
    public Usuario(String login_) {
        usuario=login_;
    }
    
    public Usuario(UsuarioContenedor uc_) {
        usuarios=uc_;
    }
    
    
}
