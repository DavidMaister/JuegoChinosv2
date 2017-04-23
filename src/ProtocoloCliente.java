
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author David
 */
public class ProtocoloCliente {
    
    
    //variables que indican los diferentes estados
    private final int introducirAlias = 1;
    private final int autenticado = 2;
    private final int vsMaquina = 3;
    private final int negRondas = 4;
    private final int mandarChinos = 5;
    private final int mandarApuesta = 6;
    private final int ganador = 7;
    private final int finRonda = 8;
    private final int comprobarLogin = 9;
    private final int recibirChinos = 10;
    private final int recibirApuesta = 11;
    private final int revelar = 12;
    private int nRondas;
    private int chinos;
    private String chinosCifrado;
    private int chinosServidor;
    private String chinosServidorCifrado;
    private int apuesta;
    private int apuestaServidor;
    private int aleatorio;
    /**
     * Constructor del cliente.
     * @param direccionServidor Dirección o nombre del servidor.
     * @param puerto  Puerto donde escucha el servidor.
     */
    public ProtocoloCliente(String direccionServidor, int puerto) {
        Socket socketConexion;
        Random rand = new Random(); //para generar numeros aleatorios
        int estado = introducirAlias;
        boolean salir = false;
        
        
        try {
            // Abrimos la conexión.
            socketConexion=new Socket(direccionServidor, puerto);
            
                // Obtenemos los canales de entrada y salida:
                PrintWriter out= new PrintWriter(socketConexion.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socketConexion.getInputStream()));
        
                // Para leer de la línea de comandos:
                BufferedReader inConsola = new BufferedReader(new InputStreamReader(System.in));
        
                // Inicializamos variables para poder mandar mensajes
                Mensajes mensajeaEnviar = new Mensajes();
                String mensaje;
                String[] campos;
                String alias = "";
                
                
                
                while(!salir){
                    switch(estado){
                        //este estado corresponde al login
                        case introducirAlias:
                            //leemos el nombre de usuario
                            System.out.println("Introduce tu alias: ");
                            alias=inConsola.readLine();
                            alias=alias.toLowerCase();
                            
                            //Creamos el mensaje y lo enviamos
                            mensaje = mensajeaEnviar.mensajeLogin(alias);
                            //System.out.println("Original: "+mensaje);
                            
                            //convertimos el mensaje a base 64
                            mensaje = Base64.getEncoder().encodeToString(mensaje.getBytes());
                            //System.out.println("Codificado: "+mensaje);
                            
                            //enviamos el mensaje codificado en base 64
                            enviarMensaje(mensaje, out);
                            estado = comprobarLogin;
                            
                        break;
                        
                        //se comprueba si el login es correcto o no
                        case comprobarLogin:
                            campos = leerPeticion(in);

                            //comprobamos si el login es correcto y pasamos al siguiente estado
                            if(campos[0].compareTo(Mensajes.mLoginOk) == 0){
                                System.out.println("Login correcto. ");
                                estado = autenticado;
                            }
                            if(campos[0].compareTo(Mensajes.mLoginError) == 0){
                                System.out.println("Login incorrecto. Vuelve a intentarlo ");
                                estado = introducirAlias;
                            }
                        break;
                        

                        //se decide si se juega contra maquina o no, pero solo implementaremos que juega contra la maquian
                        //asi que no se toma ninguna decision aqui
                        case autenticado:
                            System.out.println("Jugar contra maquina o jugador");
                            mensaje = mensajeaEnviar.mensajeMaquina();
                            enviarMensaje(mensaje, out);
                            System.out.println("Has elegido jugar contra la maquina.");
                            estado = vsMaquina;
                        break;
                        
                        //estado donde se decide el numero de rondas
                        case vsMaquina:
                            //leemos numero de rondas
                            System.out.println("Numero de rondas: ");
                            nRondas = Integer.parseInt(inConsola.readLine());
                            if(nRondas >10 || nRondas <= 0){ //no se aceptan mas de 10 rondas ni negativos, por defecto tres rondas
                                nRondas = 3;
                            }
                            
                            //creamos el mensaje con nº rondas y lo enviamos
                            mensaje = mensajeaEnviar.mensajeRondas(nRondas);
                            enviarMensaje(mensaje, out);
                          
                            estado = negRondas;
                            
                        break;
                        
                        //se recibe la confirmacion de las rondas
                        case negRondas:
                            //leemos la confirmacion
                            campos = leerPeticion(in);
                       
                            
                            if(campos[0].compareTo(Mensajes.mRondasOk) == 0){
                                System.out.println("Rondas confirmadas, que empiece el juego.");
                                estado = mandarChinos;
                            }
                        break;
                        
                        //mandamos los chinos que elijamos
                        case mandarChinos:
                            System.out.println("Elige numero de chinos: ");
                            chinos = Integer.parseInt(inConsola.readLine());
                            
                            //generamos el resumen hash de nuestros chinos
                            aleatorio = rand.nextInt(1023);
                            int aux = aleatorio + chinos;
                            chinosCifrado = Resumen.byteToString(Resumen.generar(""+aux));
                            System.out.println("Hash: "+ chinosCifrado);
                            //enviamos el mensaje con nuestros chinos
                            mensaje = mensajeaEnviar.mensajeChinos(chinosCifrado);
                            enviarMensaje(mensaje, out);
                            estado = recibirChinos;
                        break;
                        
                        //recibimos los chinos del servidor
                        case recibirChinos:
                            campos = leerPeticion(in);
                            if(campos[0].compareTo(Mensajes.mChinos) == 0){
                                chinosServidorCifrado = campos[1];
                                System.out.println("Recibidos chinos del servidor");
                                estado = mandarApuesta;
                            }
                            
                        break;
                        //elegimos que apuesta se hace 
                        case mandarApuesta:
                            System.out.println("Elige tu apuesta sabiamente: ");
                            apuesta = Integer.parseInt(inConsola.readLine());
                            mensaje = mensajeaEnviar.mensajeApuesta(apuesta);
                            enviarMensaje(mensaje, out);
                            estado = recibirApuesta;
                        break;
                        
                        //recibimos la apuesta del servidor
                        case recibirApuesta:
                            campos = leerPeticion(in);
                            if(campos[0].compareTo(Mensajes.mApuesta) == 0){
                                apuestaServidor = Integer.parseInt(campos[1]);
                                System.out.println("Recibida apuesta del servidor");
                                estado = revelar;
                            }
                        break;
                        
                        //revelamos cual es el numero de chinos al servidor
                        case revelar:
                            mensaje = mensajeaEnviar.mensajeRevelar(chinos, aleatorio);
                            enviarMensaje(mensaje, out);
                            estado = ganador;
                        break;
                        
                        //estado donde se nos manda quien ha sido el ganador de la ronda
                        case ganador:
                            //leemos el resultado
                            campos = leerPeticion(in);
                            if(campos[0].compareTo(Mensajes.mGanadorRonda) == 0){
                                System.out.println("Ha habido un total de "+campos[2]+" chinos.");
                                
                                //no ha ganado nadie
                                if(Integer.parseInt(campos[1]) == 0){
                                    System.out.println("Ups no ha ganado nadie");
                                }
                                
                                //gana el servidor
                                else if(Integer.parseInt(campos[1]) == 1){
                                    System.out.println("Una pena, no has ganado esta ronda");
                                }
                                
                                //ganas tú
                                else if(Integer.parseInt(campos[1]) == 2){
                                    System.out.println("Sigue así, has ganado esta ronda!!");
                                }
                                estado = finRonda;
                            }
                            if(campos[0].compareTo(Mensajes.mFin) == 0){
                                System.out.println("Te han pillado haciendo trampas");
                                salir = true;
                            }
                        break;
                        
                        //depende del mensaje que recibamos se acabara el juego o inicaremos otra ronda
                        case finRonda:
                            campos = leerPeticion(in);
                            if(campos[0].compareTo(Mensajes.mNextRonda)== 0){
                                System.out.println("Pasamos a la siguiente ronda.");
                                estado = mandarChinos;
                            }
                            else if(campos[0].compareTo(Mensajes.mFin) == 0){
                                System.out.println("El resultado ha sido");
                                System.out.println("Servidor: "+campos[1]);
                                System.out.println(""+alias+": "+campos[2]);
                                //en caso de empate, gana el servidor
                                if(Integer.parseInt(campos[1]) >= Integer.parseInt(campos[2]))
                                    System.out.println("Has perdido la partida :(");
                                else
                                    System.out.println("Enhorabuena has ganado!!!!");
                                
                                salir = true;
                            }
                        break;
                    }
                    
                    
                }
                
            /*// Si es un error, lo comentamos:
            if(campos[0].compareTo("error")==0){
                System.out.println("Error! Has enviado una selección incorrecta.");
            } */      
                
            in.close();
            out.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ProtocoloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //clase para recibir los mensajes enviados por el servidor
    private String[] leerPeticion(BufferedReader in){
        // Leemos una petición:
        
        String linea=" ";
        try {
            linea = in.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ProtocoloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Interpretamos la petición. Para que no haya problema con las letras de la palabra
        // al compararlas con los comandos, las pasamos todas a minúsculas:
        linea = linea.toLowerCase();
        // los comandos vienen  separados por espacios,por lo que los separamos
        String[] campos = linea.split(" ");
          

        return campos;
    }
    
    // clase para enviar mensajes al servidor
    private void enviarMensaje(String mensaje, PrintWriter out){
        // Enviamos la respuesta:
        out.println(mensaje);
        out.flush();
    }
}
