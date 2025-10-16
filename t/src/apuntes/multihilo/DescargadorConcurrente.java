package apuntes.multihilo;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;

public class DescargadorConcurrente {
    private static final int NUM_DESCARGAS_CONCURRENTES = 3;

    public static void main(String[] args) {
        List<String> urls = Arrays.asList(
                "https://www.example.com",
                "https://httpbin.org/json",
                "https://jsonplaceholder.typicode.com/posts/1"
        );

        ExecutorService executor = Executors.newFixedThreadPool(NUM_DESCARGAS_CONCURRENTES);
        List<Future<ResultadoDescarga>> resultados = new ArrayList<>();

        for (String url : urls) {
            Future<ResultadoDescarga> futuro =
                    executor.submit(new DescargadorURL(url));
            resultados.add(futuro);
        }

        // Mostrar resultados
        for (Future<ResultadoDescarga> futuro : resultados) {
            try {
                ResultadoDescarga resultado = futuro.get();
                System.out.println(resultado);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }
}

class DescargadorURL implements Callable<ResultadoDescarga> {
    private String url;

    public DescargadorURL(String url) {
        this.url = url;
    }

    @Override
    public ResultadoDescarga call() throws Exception {
        long inicio = System.currentTimeMillis();
        int codigoRespuesta = -1;
        long tamano = 0;

        try {
            URL urlObj = new URL(url);
            HttpURLConnection conexion = (HttpURLConnection) urlObj.openConnection();
            conexion.setRequestMethod("GET");

            codigoRespuesta = conexion.getResponseCode();

            if (codigoRespuesta == 200) {
                try (InputStream inputStream = conexion.getInputStream();
                     ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                    byte[] buffer = new byte[1024];
                    int bytesLeidos;
                    while ((bytesLeidos = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesLeidos);
                        tamano += bytesLeidos;
                    }

                    // Guardar en archivo (opcional)
                    String nombreArchivo = "descarga_" +
                            System.currentTimeMillis() + ".txt";
                    Files.write(Paths.get(nombreArchivo), outputStream.toByteArray());
                }
            }

        } catch (IOException e) {
            return new ResultadoDescarga(url, -1, 0,
                    System.currentTimeMillis() - inicio, "Error: " + e.getMessage());
        }

        long tiempo = System.currentTimeMillis() - inicio;
        return new ResultadoDescarga(url, codigoRespuesta, tamano, tiempo, "Éxito");
    }
}

class ResultadoDescarga {
    private String url;
    private int codigoRespuesta;
    private long tamano;
    private long tiempoMs;
    private String estado;

    public ResultadoDescarga(String url, int codigoRespuesta, long tamano,
                             long tiempoMs, String estado) {
        this.url = url;
        this.codigoRespuesta = codigoRespuesta;
        this.tamano = tamano;
        this.tiempoMs = tiempoMs;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return String.format("URL: %s | Código: %d | Tamaño: %d bytes | Tiempo: %d ms | Estado: %s",
                url, codigoRespuesta, tamano, tiempoMs, estado);
    }
}