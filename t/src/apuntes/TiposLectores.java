package apuntes;

import java.io.FileInputStream;
import java.io.InputStream;

public class TiposLectores {
    //IMPUTSTREAM
    // Lectura byte a byte
    /*try (InputStream is = new FileInputStream("archivo.bin")) {
        int byteLeido;
        while ((byteLeido = is.read()) != -1) {
            byte b = (byte) byteLeido;
            // Procesar cada byte
        }
    }

// Lectura en bloques de bytes
    try (InputStream is = new FileInputStream("archivo.bin")) {
        byte[] buffer = new byte[1024];
        int bytesLeidos;
        while ((bytesLeidos = is.read(buffer)) != -1) {
            // Procesar buffer[0] hasta buffer[bytesLeidos-1]
        }
    }
    */

}
