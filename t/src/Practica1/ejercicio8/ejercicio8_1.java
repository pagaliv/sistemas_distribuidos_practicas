package Practica1.ejercicio8;
import java.io.*;
import java.nio.charset.StandardCharsets;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ejercicio8_1 {

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        if(args.length!=1){
            System.out.println("Argumentos incorrectos");
        }

        try{
            leerficheros(args[0]);
        }catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }

    }
    public static void leerficheros(String fichero) throws IOException{


        try(DataInputStream fis=new DataInputStream( new FileInputStream(fichero))){
            String Linea;
            while((Linea= fis.readLine()) != null){
               System.out.println(Linea);
            }
        }catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }
        try(BufferedReader ris=new BufferedReader( new InputStreamReader( new FileInputStream(fichero), StandardCharsets.UTF_8))){
            String Linea = "";
            while((Linea= ris.readLine()) != null){
                System.out.println(Linea);
            }
        }catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }
        try(BufferedReader ris=new BufferedReader( new InputStreamReader( new FileInputStream(fichero), "Cp1252"))){
            String Linea = "";
            while((Linea= ris.readLine()) != null){
                System.out.println(Linea);
            }
        }catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

}

