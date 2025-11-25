package ejercicios_practicar_examen.ejercicio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static void main(String args[]){
        try(ServerSocket ss = new ServerSocket(55555)){
            while(true){
                try{
                    Socket s = ss.accept();
                    new Thread(()-> manejarCliente(s)).start();
                }catch(IOException e){
                    e.printStackTrace();
                }
                

            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void manejarCliente(Socket s){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter pw = new PrintWriter(s.getOutputStream(),true)){
            Boolean conexion= true;
            String msgCLiente="";
            System.out.println("Cliente conectado");
            String msg = "Buenas, que desea hacer decirme su nombre (1) o salir del  servidor (2)" ;
            while (conexion) {
                System.out.println("CLIENTE CONECTADO");
                pw.println(msg);
                msgCLiente = br.readLine();
                int opcion=0;
                System.out.println(msgCLiente);
                try{
                      opcion = Integer.parseInt(msgCLiente);   
                } catch(NumberFormatException nfe){
                    pw.println("ERROR EN LA RESPUESTA");
                }
                
                if (opcion==2){
                    pw.println("0");
                    conexion= false;

                } else if (opcion==1){
                    pw.println("Hola, bienvienido al server, como te llamas ");
                    msgCLiente = br.readLine();
                    System.out.println("El cliente ha mandado: " + msgCLiente);
                    pw.println("Hola, bienvienido al server" + msgCLiente);
                } else {
                    pw.println("No existe esa opción");
                }
                
                
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
