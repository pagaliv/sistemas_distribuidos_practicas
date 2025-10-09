package Practica2.ejercicio5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ejercicio5Servidor {
    public static void main(String[] args) {
        try(ServerSocket servidor = new ServerSocket(23405);){
            while(true){
                try(Socket cliente = servidor.accept()) {
                    (new ClientHandler(cliente)).start();
                }catch (IOException E){
                    System.out.println(E.getMessage());
                }
            }
        }catch (IOException e){
            System.out.println("Error en el servidor");
        }
    }
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        @Override
        public void run() {
            try (DataInputStream in = new DataInputStream((clientSocket.getInputStream()));
                 DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                 ) {
                 String request = in.readLine();
                 String nombre = request.split(" ")[0];
                 int tam=Integer.parseInt(request.split(" ")[1]);
                 int leidos=0;
                  int m=0;
                  byte[] buffer = new byte[1024];
                 try(FileOutputStream in2 = new FileOutputStream(nombre)){
                     while(leidos<tam/2){
                         leidos++;
                         m=in.read();
                         in2.write(m);
                     }
                     request = in.readLine();
                     int n;
                     n=in.read(buffer);
                     while(n!=-1){
                         in2.write(buffer,0,n);
                         n=in.read(buffer);
                     }

                 }




            }catch (IOException E){
                System.out.println(E.getMessage());
            }
        }
    }
}
