package ejercicios_practicar_examen.Modelo_B;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class cliente {
    public static void main(String args[]){
        String msgServer = "";
        String msgCliente="";
        boolean conexion= true;
        boolean bucleMandarMuchaInfo = true;
        try(Socket s= new Socket("localhost", 55555);
            Scanner scanner = new Scanner(System.in); 
            PrintWriter pw = new PrintWriter(s.getOutputStream(), true );
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()))
        ){
            msgServer = br.readLine();
            System.out.println("Server: " +  msgServer);
            msgCliente = scanner.nextLine();
            pw.println(msgCliente);
            System.out.println("Cliente: "+ msgCliente);
                while(conexion){
                    msgServer = br.readLine();
                    System.out.println("Server: " +  msgServer);
                    msgCliente = scanner.nextLine();
                    pw.println(msgCliente);
                    System.out.println("Cliente: "+ msgCliente);
                    if(msgCliente.contains("0")){
                        msgServer = br.readLine();
                        System.out.println("Server: " +  msgServer);
                        break;
                    }if (msgCliente.contains("1")){
                        while (bucleMandarMuchaInfo){
                            msgServer = br.readLine();
                            System.out.println("Server: " +  msgServer);
                            if(msgServer.contains("FIN")){
                                bucleMandarMuchaInfo =false;
                            }
                        }
                        bucleMandarMuchaInfo=true;
                    }if (msgCliente.contains("2")){
                        
                    }

                    


                }


            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
