
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *  Simula una base de datos de usuarios
 * @author David
 */
public class UsuarioContenedor {
    Map usuarios;
    
    /**
     * Creates a new instance of UsuarioContenedor 
     */
    public UsuarioContenedor() {
       usuarios = new HashMap();  
    }
    
    boolean anadir(Usuario u){
        boolean error=false;
        
        if(usuarios.get(u.usuario)==null)
            usuarios.put(u.usuario,u);
        else    
            error=true;

        return error;
    }
    
    Usuario obtener(String login){
        Usuario u=null;
        
        u=(Usuario)usuarios.get(login);
        
        return u;
    }
}
