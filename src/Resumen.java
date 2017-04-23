
import java.io.*;
import java.security.*;
import java.util.*;

/*
 * Ejemplo de creación de resúmenes de mensajes mediante funciones resumen (SHA1 ? MD5)
 *
 * @author basado en la implementacion de jjramos
 */
public class Resumen {
    
    /** Creates a new instance of Resumen */
    public Resumen() {
    }
    
    /** Mostramos un array de bytes por pantalla en formato hexadecimal **/
    static void mostrarHexadecimal(byte []octetos){
        for(int i=0;i<octetos.length;i++)
            System.out.format("%02X ",octetos[i]);
    }
    
    
    //metodo que pasa de array de bytes a un String, donde los bytes estan representados de forma hexadecimal
    public static String byteToString(byte[] resumen){
        String secuencia = "";
        for(int i=0;i<resumen.length;i++){
            secuencia = secuencia + String.format("%02x", resumen[i]);
        }
        secuencia = secuencia.toLowerCase();
        return secuencia;
    }
    
    // generar el resumen de un string dado:
    public static byte[] generar(String mensaje){
        
        
        MessageDigest resumen;
        byte[] cifrado = null;
        
        try {
            // Obtenemos un motor de cálculo de funcionas Hash (resumen), 
            // especificando el algoritmo a utilizar (p.e. "SHA" o "MD5"):
            resumen = MessageDigest.getInstance("MD5");
           
            // Resumimos el mensaje
            //System.out.println("El mensaje: \""+mensaje+"\", se resume en "+
                    //resumen.getDigestLength()+" octetos con: "+
                    //resumen.getAlgorithm()+"");
            
            cifrado =resumen.digest(mensaje.getBytes());
            System.out.println("");
            
            
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Error: el algoritmo de resumen especificado no existe!");
        }
        return cifrado;
           
   }
}
