package Practica1.ejercicio3;
import java.io.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ejercicio3 {

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        if(args.length!=2){
            System.out.println("Argumentos incorrectos");
        }

        try{
            leerficheros(args[0],args[1]);
        }catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }

    }
    public static void leerficheros(String fichero, String archivo) throws IOException{
        byte buff[]=new byte[1024];

        try(DataInputStream fis=new DataInputStream( new FileInputStream(fichero)); DataOutputStream dos= new DataOutputStream( new FileOutputStream(archivo)) ;){
            String Linea;
            while((Linea= fis.readLine()) != null){
                dos.writeBytes(Linea + "\n");
            }
        }catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    static class Innerejercicio3 {
    
        
    }

}
