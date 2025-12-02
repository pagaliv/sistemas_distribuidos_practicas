package ejercicios_practicar_examen.Modelo_B;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Clienthandler extends Thread {
    private Socket cliente = null;
    private String nombreCLiente = "";
    ArrayList<String> listaFicheros = null;
    boolean conexion = false;

    public Clienthandler(Socket s, ArrayList<String> sus){
        this.cliente = s;
        listaFicheros = sus;
        this.conexion = true;
    }
    @Override
    public void run(){
        String msgServer = "";
        String respuestaCliente="";
        int responseIntCliente=-1;
        boolean respuestaValida = false;
        String peticion = "¿Que deseas hacer ? Salir del servidor(0), ver los ficheros (1), anyadir un fichero nuevo (2)";
        try(PrintWriter pw = new PrintWriter(cliente.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(cliente.getInputStream()))){
                System.out.println("Se inicia conexión con cliente");
                pw.println("Como te llamas?");
                nombreCLiente= br.readLine();
                while(conexion){
                    System.out.println("Server:" + peticion);
                    pw.println(peticion);
                    respuestaCliente=br.readLine();
                    System.out.println("Cliente: "+ responseIntCliente);
                    try{
                        responseIntCliente = Integer.parseInt(respuestaCliente);
                        respuestaValida = true;
                    }catch(NumberFormatException ex){
                        respuestaValida=false;
                        ex.printStackTrace();
                        pw.println("Respuesta invalida, ¿lo entiendes?");
                        System.out.println("Server:" + "Respuesta invalida, ¿lo entiendes?" );
                        respuestaCliente=br.readLine();
                        System.out.println("Cliente: "+ responseIntCliente);
                    }
                    if(respuestaValida){
                        respuestaValida=false;
                        if(responseIntCliente==0){
                            pw.println("Desconectado crrectamente, ha sido un placer");
                            System.out.println("Server:" + "Desonexión" );
                        }if(responseIntCliente==1){
                            pw.println("Estos son los archivos");
                            System.out.println("Server: LOS ARCHIVOS");
                            for( int i=0; i< listaFicheros.size(); i ++){
                                pw.println(listaFicheros.get(i));
                                System.out.println("Server:" + listaFicheros.get(i));
                            }
                            pw.println("FIN");
                        }if(responseIntCliente==2){
                            msgServer= "Como se llama el archivo que desea enviar";
                            pw.println(msgServer);
                            String nombreArchivo= br.readLine();
                            System.out.println("Cliente : " + nombreArchivo);
                            String tamArchivoString= br.readLine();
                             try{
                                responseIntCliente = Integer.parseInt(tamArchivoString);
                                 respuestaValida = true;
                            }catch(NumberFormatException exN){
                                exN.printStackTrace();
                                respuestaValida=false;
                            }
                            if( respuestaValida){
                                byte[] buffer = new byte[8192];
                                int bytesLeidos;
                                int total_leido=0;
                                try(FileOutputStream fos = new FileOutputStream(nombreArchivo)){
                                    
                                    
                                }
                            }

                            
                            

                        }
                    }


                }
        }catch(IOException e){
            e.printStackTrace();
        }


    }
    
    
}
