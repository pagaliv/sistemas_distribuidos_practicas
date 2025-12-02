package ejercicios_practicar_examen.Modelo_B;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class serverPrincipal {
    public static void main(String args[]){
        try(ServerSocket ss = new ServerSocket(5000)){
            while(true){
                try{
                    Socket s = ss.accept();
                    
                    
                }catch(){

                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
