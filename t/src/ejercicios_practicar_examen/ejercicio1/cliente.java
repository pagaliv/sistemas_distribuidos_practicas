package ejercicios_practicar_examen.ejercicio1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class cliente {
    public static void main(String args[]) throws IOException{
        try (Socket socket = new Socket("localhost", 5000);
         BufferedReader br =new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        Scanner s = new Scanner(System.in)){
            System.out.println("Escribe tu nombre");
            String nombre =s.nextLine();
            System.out.println("Me conecte al servidor");
            pw.println(nombre) ;
            pw.flush();
            String m= br.readLine();
            System.out.println(m);
            
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
