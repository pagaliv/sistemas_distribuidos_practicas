package Practica2.ejercicio5;

import Practica2.ejercicio3.ejercicio3Cliente;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ejercicio5Cliente {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 23405;

    private Socket socket;
    private OutputStream out;
    private InputStream in;
    private Scanner scanner;
    public static void main(String[] args) throws IOException {
            ejercicio5Cliente client = new ejercicio5Cliente();
            client.start();
    }
    public void start()  {
        try(Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            ) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Escriba al ruta completa del atchivo que desea enviar");
            String ruta = sc.nextLine();
            File dir = new File(ruta);
            if (!dir.exists()) {
                System.out.println("Directorio " + ruta + " no existe");
                return;
            }
            int tam = (int)dir.length();

            String msg= ruta+" " +tam;
            out.writeBytes(msg);
            try(FileInputStream fis = new FileInputStream(ruta);){
                int enviados=0;
                int b;
                byte[] buffer = new byte[1024];
                while(enviados<tam/2){
                    b=fis.read();
                    enviados++;
                    out.write(b);
                }
                out.flush();
                out.writeBytes("He mandao la mitad");
                out.flush();
                int n;
                n=in.read(buffer);
                while(n!=-1){
                    out.write(buffer,0,n);
                    n=in.read(buffer);
                }
                out.flush();

            }

        }catch (IOException e){
            System.err.println("Error en el servidor");
        }
    }
}
