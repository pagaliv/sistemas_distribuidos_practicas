package Practica1.ejercicio4;

import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

public class revisorCambioDir extends TimerTask {
    //Atributes
    private File dir;
    private long lastTime;
    public revisorCambioDir(String dir) {
        this.dir = new File(dir);
        this.lastTime = this.dir.lastModified();

    }

    @Override
    public void run() {
        if(dir.isDirectory()){
            if(this.lastTime != this.dir.lastModified()){
                this.lastTime = this.dir.lastModified();
                try {
                    mostrarDirectorio(dir);
                } catch (IOException e) {
                    System.out.println("Error al mostrar el directorio");
                }
            }
        }

    }
    public static void mostrarDirectorio(File dir) throws IOException {
        if (!dir.exists()) {
            System.out.println("Directorio " + dir.toString() + " no existe");
            return;
        }if (!dir.isDirectory()) {
            System.out.println("Directorio " + dir.toString() + " no es una directorio");
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
        System.out.println();


    }
}
