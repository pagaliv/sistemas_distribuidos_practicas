package Practica1.ejercicio9;

import java.io.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ejercicio9 {

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        if(args.length!=1){
            System.out.println("Argumentos incorrectos");
        }

        try{
            int i= leerficheros(args[0]);
        }catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }

    }
    //Pre.
    //Post devuelve 7 si es cod ASCII, 23 si es UTF-8 y 8 si no es UTF-8
    public static int leerficheros(String fichero) throws IOException{

        File dir = new File(fichero);
        int cadena[]= new int[Math.toIntExact(dir.length())];
        byte buff[]=new byte[1];

        try(FileInputStream fis=new FileInputStream(fichero)){
            int n;
            int m=0;
            n=fis.read(buff);
            while(n!=-1){
               cadena[m]=buff[0];
                n=fis.read(buff);
                m++;
            }
            for(int i=0;i<cadena.length;i++){
                if(cadena[i]>128 || cadena[i]<191){
                    return 8;
                }
                if(cadena[i]<0 || cadena[i]>127){

                }
            }

        }catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }
        return -1;
    }

}