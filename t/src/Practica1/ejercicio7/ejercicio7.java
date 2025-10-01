package Practica1.ejercicio7;

import java.io.*;

public class ejercicio7 {

    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Argumentos incorrectos");
            return; // Importante: salir si los argumentos son incorrectos
        }

        try{
             mostrarDirectorio(args[0]);

        } catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void mostrarDirectorio(String fichero) throws IOException {
        File dir = new File(fichero);
        if (!dir.exists()) {
            System.out.println("Directorio " + fichero + " no existe");
            return;
        }if (!dir.isDirectory()) {
            System.out.println("Directorio " + fichero + " no es una directorio");
            return;
        }
        File[] lista = dir.listFiles();
        for (int i = 0; i < lista.length; i++) {
            if (lista[i].isFile()) {
                System.out.println(lista[i].getName() +"   " + lista[i].length());
            }
            if (lista[i].isDirectory()) {
                System.out.println(lista[i].getName() + "   <DIR>");
            }
        }


    }


}