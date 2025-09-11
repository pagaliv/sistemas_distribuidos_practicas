package ejercicio3;

import java.io.*;

public class ejercicio3_1 {
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

        try(BufferedReader fis=new BufferedReader(new InputStreamReader(new FileInputStream(fichero))); Writer dos= new OutputStreamWriter( new FileOutputStream(archivo));){
            String Linea;
            while((Linea= fis.readLine()) != null){
                dos.write(Linea + "\n");
            }
        }catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
}
