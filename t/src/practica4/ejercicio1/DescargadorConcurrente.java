package practica4.ejercicio1;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.*;

public class DescargadorConcurrente {
    private final String urlRecurso;
    private final String directorioDestino;
    private final int numHilos;
    private final ExecutorService executor;

    public DescargadorConcurrente(String urlRecurso, String directorioDestino, int numHilos) {
        this.urlRecurso = urlRecurso;
        this.directorioDestino = directorioDestino;
        this.numHilos = numHilos;
        this.executor = Executors.newFixedThreadPool(numHilos);
    }

    public void descargar() throws Exception {
        System.out.println("Iniciando descarga concurrente de: " + urlRecurso);

        // 1. Obtener el tamaño del recurso
        long tamañoTotal = obtenerTamañoRecurso();
        System.out.println("Tamaño total del recurso: " + tamañoTotal + " bytes");

        if (tamañoTotal <= 0) {
            throw new IOException("No se pudo determinar el tamaño del recurso");
        }

        // 2. Dividir el recurso en partes
        long tamañoParte = tamañoTotal / numHilos;
        System.out.println("Tamaño por parte: " + tamañoParte + " bytes");

        // 3. Crear y ejecutar los descargadores
        String nombreArchivo = obtenerNombreArchivo(urlRecurso);
        String rutaCompleta = directorioDestino + File.separator + nombreArchivo;

        // Crear el archivo vacío con el tamaño total
        crearArchivoVacio(rutaCompleta, tamañoTotal);

        CountDownLatch latch = new CountDownLatch(numHilos);

        for (int i = 0; i < numHilos; i++) {
            long inicio = i * tamañoParte;
            long fin;

            if (i == numHilos - 1) {
                // Última parte - incluir los bytes restantes
                fin = tamañoTotal - 1;
            } else {
                fin = inicio + tamañoParte - 1;
            }

            Descargador descargador = new Descargador(
                    urlRecurso, rutaCompleta, inicio, fin, latch, i + 1
            );
            executor.execute(descargador);
        }

        // 4. Esperar a que todos los hilos terminen
        System.out.println("Esperando a que terminen las descargas...");
        latch.await();

        executor.shutdown();
        System.out.println("¡Descarga completada! Archivo guardado en: " + rutaCompleta);
    }

    private long obtenerTamañoRecurso() throws IOException {
        URL url = new URL(urlRecurso);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("HEAD");

        int codigoRespuesta = conexion.getResponseCode();
        if (codigoRespuesta != HttpURLConnection.HTTP_OK) {
            throw new IOException("Error al obtener información del recurso. Código: " + codigoRespuesta);
        }

        return conexion.getContentLengthLong();
    }

    private String obtenerNombreArchivo(String url) {
        String nombre = url.substring(url.lastIndexOf('/') + 1);
        if (nombre.isEmpty() || !nombre.contains(".")) {
            nombre = "descarga_" + System.currentTimeMillis() + ".dat";
        }
        return nombre;
    }

    private void crearArchivoVacio(String ruta, long tamaño) throws IOException {
        File archivo = new File(ruta);
        archivo.getParentFile().mkdirs(); // Crear directorios si no existen

        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
            raf.setLength(tamaño);
        }
    }
}
