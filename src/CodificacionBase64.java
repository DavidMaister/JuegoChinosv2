/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */


import org.apache.commons.codec.binary.Base64;



public class CodificacionBase64 {
    
    //clase que codifica en base64 el mensaje introducido
    public static String codificar(String mensaje) {
        
        byte[] bitstream=new byte[128];
        
        for(int i=0;i<bitstream.length;i++){
            bitstream[i]=(byte) (255-i);
        }  
        
        bitstream=mensaje.getBytes();

        // Codificamos en base 64:
        String codificadoBase64 = Base64.encodeBase64String(bitstream);
        return codificadoBase64;
    }
    
    public static String decodificar(String codificado){
        Base64 coder=new Base64();
        String decodificado = "";
        byte[] decodificadoByte = coder.decode(codificado);
    
        decodificado = Resumen.byteToString(decodificadoByte);
        return decodificado;
    }
}
