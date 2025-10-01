package Practica1.ejercicio8;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ejercicio8 {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        if(args.length!=1){
            System.out.println("Argumentos incorrectos");
        }

        try{
            escribirFichero(args[0]);
        }catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }

    }
    public static void escribirFichero(String fichero) throws IOException{
        byte buff[]=new byte[1024];

        try(Writer dos= new OutputStreamWriter( new FileOutputStream(fichero), StandardCharsets.UTF_8);){
            String Linea="Lapiz 1€";
                dos.write(Linea);
        }catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
}
