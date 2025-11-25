package ejercicios_practicar_examen.ejercicio1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static void main(String[] args) throws IOException{
        try( ServerSocket servidor = new ServerSocket(5000)){
            while (true) {
                try {
                    Socket cliente = servidor.accept();
                    new Thread(() -> manejarCliente(cliente)).start();
                } catch( IOException e){
                     e.printStackTrace();
                }
            }
        }
           
    }
    static void manejarCliente(Socket socket){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream())){
            System.out.println("Cliente manejado en hilo: " + Thread.currentThread().getName());
            String m = br.readLine();
            System.out.println(m);
            String res = "Hola " + m + "Te has conectado";
            pw.println(res);
            pw.flush();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
