package ejercicio6;

import java.io.*;

public class ejer6 {

    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Argumentos incorrectos");
            return; // Importante: salir si los argumentos son incorrectos
        }

        try{
            int n = contarIniciales(args[0]);
            System.out.println(n + " valores conectados");
        } catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static int contarIniciales(String fichero) throws IOException {
        int contadorTotal = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(fichero))) {
            String linea;

            while((linea = reader.readLine()) != null) {
                contadorTotal += contarOcurrenciasEnLinea(linea);
            }
        }

        return contadorTotal;
    }

    private static int contarOcurrenciasEnLinea(String linea) {
        int contadorLinea = 0;
        int posicion = 0;
        String texto = linea.toUpperCase(); // Para búsqueda case-insensitive

        while((posicion = texto.indexOf("PGV", posicion)) != -1) {
            contadorLinea++;
            posicion += 3; // Avanzar la longitud de "PGV" (3 caracteres)
        }

        return contadorLinea;
    }
}