package practica4.ejercicio1;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class Descargador implements Runnable {
    private final String urlRecurso;
    private final String rutaArchivo;
    private final long byteInicial;
    private final long byteFinal;
    private final CountDownLatch latch;
    private final int idHilo;

    public Descargador(String urlRecurso, String rutaArchivo,
                       long byteInicial, long byteFinal,
                       CountDownLatch latch, int idHilo) {
        this.urlRecurso = urlRecurso;
        this.rutaArchivo = rutaArchivo;
        this.byteInicial = byteInicial;
        this.byteFinal = byteFinal;
        this.latch = latch;
        this.idHilo = idHilo;
    }

    @Override
    public void run() {
        try {
            System.out.printf("Hilo %d: Descargando bytes %d-%d%n",
                    idHilo, byteInicial, byteFinal);

            descargarParte();
            System.out.printf("Hilo %d: Descarga completada%n", idHilo);

        } catch (Exception e) {
            System.err.printf("Error en hilo %d: %s%n", idHilo, e.getMessage());
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
    }

    private void descargarParte() throws IOException {
        URL url = new URL(urlRecurso);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

        // Configurar cabecera Range para descargar solo una parte
        String rango = String.format("bytes=%d-%d", byteInicial, byteFinal);
        conexion.setRequestProperty("Range", rango);

        int codigoRespuesta = conexion.getResponseCode();

        // Verificar que la respuesta es correcta (206 = Partial Content)
        if (codigoRespuesta != HttpURLConnection.HTTP_PARTIAL &&
                codigoRespuesta != HttpURLConnection.HTTP_OK) {
            throw new IOException("Error en la descarga parcial. Código: " + codigoRespuesta);
        }

        try (InputStream inputStream = conexion.getInputStream();
             RandomAccessFile archivo = new RandomAccessFile(rutaArchivo, "rw")) {

            // Posicionar el puntero en la posición correcta
            archivo.seek(byteInicial);

            byte[] buffer = new byte[8192]; // Buffer de 8KB
            int bytesLeidos;
            long totalBytesLeidos = 0;
            long tamañoParte = byteFinal - byteInicial + 1;

            while (totalBytesLeidos < tamañoParte &&
                    (bytesLeidos = inputStream.read(buffer)) != -1) {

                // Asegurarse de no escribir más bytes de los necesarios
                long bytesRestantes = tamañoParte - totalBytesLeidos;
                int bytesAEscribir = (int) Math.min(bytesLeidos, bytesRestantes);

                archivo.write(buffer, 0, bytesAEscribir);
                totalBytesLeidos += bytesAEscribir;
            }

            System.out.printf("Hilo %d: Descargados %d bytes%n", idHilo, totalBytesLeidos);
        }
    }
}
