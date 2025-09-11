import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

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
        try(FileInputStream fis=new FileInputStream(fichero); FileOutputStream fos= new FileOutputStream(archivo);){
            while(fis.available()>0){
                fos.write(fis.read());
            }
        }catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

}