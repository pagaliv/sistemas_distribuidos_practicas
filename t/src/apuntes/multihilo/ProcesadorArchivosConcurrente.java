package apuntes.multihilo;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class ProcesadorArchivosConcurrente {
    private static final int NUM_HILOS = 4;

    public static void main(String[] args) {
        String directorio = "archivos";
        crearArchivosEjemplo(directorio);

        ExecutorService executor = Executors.newFixedThreadPool(NUM_HILOS);
        List<Future<ResultadoProcesamiento>> resultados = new ArrayList<>();

        try {
            Files.list(Paths.get(directorio))
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        Future<ResultadoProcesamiento> futuro =
                                executor.submit(new ProcesadorArchivo(path));
                        resultados.add(futuro);
                    });

            // Recoger resultados
            for (Future<ResultadoProcesamiento> futuro : resultados) {
                try {
                    ResultadoProcesamiento resultado = futuro.get();
                    System.out.println(resultado);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    private static void crearArchivosEjemplo(String directorio) {
        try {
            Files.createDirectories(Paths.get(directorio));
            for (int i = 1; i <= 5; i++) {
                String contenido = "Línea 1 del archivo " + i + "\n" +
                        "Línea 2 del archivo " + i + "\n" +
                        "Línea 3 del archivo " + i;
                Files.write(Paths.get(directorio, "archivo" + i + ".txt"),
                        contenido.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ProcesadorArchivo implements Callable<ResultadoProcesamiento> {
    private Path rutaArchivo;

    public ProcesadorArchivo(Path rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public ResultadoProcesamiento call() throws Exception {
        String nombreArchivo = rutaArchivo.getFileName().toString();
        long tamano = Files.size(rutaArchivo);
        int lineas = 0;
        int palabras = 0;

        try (BufferedReader reader = Files.newBufferedReader(rutaArchivo)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                lineas++;
                palabras += linea.split("\\s+").length;
            }
        }

        // Simular procesamiento pesado
        Thread.sleep(1000);

        return new ResultadoProcesamiento(nombreArchivo, tamano, lineas, palabras);
    }
}

class ResultadoProcesamiento {
    private String nombreArchivo;
    private long tamano;
    private int lineas;
    private int palabras;

    public ResultadoProcesamiento(String nombreArchivo, long tamano,
                                  int lineas, int palabras) {
        this.nombreArchivo = nombreArchivo;
        this.tamano = tamano;
        this.lineas = lineas;
        this.palabras = palabras;
    }

    @Override
    public String toString() {
        return String.format("Archivo: %s | Tamaño: %d bytes | Líneas: %d | Palabras: %d",
                nombreArchivo, tamano, lineas, palabras);
    }
}