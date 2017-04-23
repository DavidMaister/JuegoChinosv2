/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author David
 */
public class Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int puerto=1234;
        int nHebras =3; //10 hebras por defecto
        
        // Por si quisiéramos pasarle argumentos por la línea de comandos:
        System.out.println("Servidor para juego de los chinos a su servicio");
        if(args.length>0){
            puerto=Integer.parseInt(args[0]);
        }
        
        ProtocoloServidor.iniciar(puerto);
        // Creamos las 50 hebras:
        for(int i=0;i<nHebras;i++){
            // Creamos una nueva instancia de hebra:
            System.out.println("Creando hebra");
            ProtocoloServidor protocolo = new ProtocoloServidor(puerto);
            System.out.println("Iniciamos hebra");
            // Para inicializarla, llamamos a .start():
            protocolo.start();
        }
        
    }
    
}
