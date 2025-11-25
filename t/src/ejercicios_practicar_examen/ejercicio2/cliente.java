package ejercicios_practicar_examen.ejercicio2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class cliente {
    public static void main(String args[]){
        String msgServer="";
        String msg="";
        boolean conection= true;
        int exit=0;
        try(Socket s= new Socket("localhost",55555);
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter pr = new PrintWriter(s.getOutputStream(),true);
            Scanner scanner= new Scanner(System.in)){
                System.out.println("Servidor conectado");
               while(conection){
                    msgServer = br.readLine();
                    System.out.println("Servidor: " + msgServer); 
                    if(msgServer.contains("Buenas, que desea hacer") || msgServer.contains("como te llamas") ){
                        msg = scanner.nextLine();
                        
                    }
                    
                    pr.println(msg);
                    msgServer = br.readLine();
                    System.out.println("Servidor: " + msgServer); 
                    if(esNumerico(msgServer) && Integer.parseInt(msgServer)==0){
                        conection =false;
                    }

                    
               }
               

            

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static boolean esNumerico(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
}

    
}
