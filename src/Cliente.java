

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */
public class Cliente {
    
    /**
     * Método principal de la clase:
     * @param argumentos 
     */
    public static void main(String []argumentos){
        int puerto=1234;
        String direccionServidor="127.0.0.1";
        
        // Por si quisiéramos pasarle argumentos por la línea de comandos:
        if(argumentos.length==2){
            direccionServidor=argumentos[0];
            puerto=Integer.parseInt(argumentos[1]);
        }
        
        // Creamos un cliente:
        new ProtocoloCliente(direccionServidor,puerto);
        
    }
    
}
