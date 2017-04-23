/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */
public class Mensajes {
    
    String mensaje;
    
    //cabecera de los mensajes
    public static String mLogin = "log";
    public static String mLoginOk = "lok";
    public static String mLoginError = "lerr";
    public static String mMaquina = "maq";
    public static String mRondas = "ron";
    public static String mRondasOk = "rok";
    public static String mChinos = "chi";
    public static String mApuesta = "apu";
    public static String mGanadorRonda = "gan";
    public static String mNextRonda= "next";
    public static String mFin= "fin";
    public static String mRevelar= "rev";
    
    public Mensajes(){
        mensaje = "";
    }
    //mensaje de login, este contiene el alias del usuario
    public String mensajeLogin(String alias){
        mensaje = mLogin+" "+alias+"\n";
        
        return mensaje;
    }
    
    public String mensajeLoginOk(){
        mensaje = mLoginOk+" "+"LoginOk"+" "+"\n";
        return mensaje;
    }
    
    public String mensajeLoginError(){
        mensaje = mLoginError+" "+"\n";
        return mensaje;
    }
    
    public String mensajeMaquina(){
        mensaje = mMaquina+" "+"\n";
        return mensaje;
    }
    
    //n es el numero de rondas a elefir
    public String mensajeRondas(int n){
        mensaje = mRondas+" "+n+" "+"\n";
        return mensaje;
    }
    
    public String mensajeRondasOk(){
        mensaje = mRondasOk+" "+"\n";
        return mensaje;
    }
    
    //hash es un resumen que se manda del numero de chinos
    public String mensajeChinos(String hash){
        mensaje = mChinos+" "+hash+" "+"\n";
        return mensaje;
    }
    
    //n es la apuesta elegida
    public String mensajeApuesta(int n){
        mensaje = mApuesta+" "+n+" "+"\n";
        return mensaje;
    }
    
    // resultado puede tener tres valores:
    // 0- significa que no ha ganado nadie, o que ambos apuestan lo mismo
    // 1- gana el servidor, si se jugara contra otro jugador, seria que gana el contrincante
    // 2- gana el cliente
    //nTotal es la suma de chinos del cliente y del servidor
    public String mensajeGanadorRonda(int resultado, int nTotal){
        mensaje = mGanadorRonda+" "+resultado+" "+nTotal+" "+"\n";
        return mensaje;
    }
    
    public String mensajeNextRonda(){
        mensaje = mNextRonda+" "+"\n";
        return mensaje;
    }
    
    //rondasServidor es las rondas ganadas por el servidor
    //rondasCliente es las rondsa ganadas por el cliente
    public String mensajeFin (int rondasServidor, int rondasCliente){
        mensaje = mFin+" "+rondasServidor+" "+rondasCliente+" "+"\n";
        return mensaje;
    }
    
    //n es el numero de chinos
    //aleatorio es el numero aleatorio usado para generar el hash
    public String mensajeRevelar(int n, int aleatorio){
        mensaje = mRevelar+" "+n+" "+aleatorio+" "+"\n";
        return mensaje;
    }
}
