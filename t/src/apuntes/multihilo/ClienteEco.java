package apuntes.multihilo;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteEco {
    private static final String SERVIDOR = "localhost";
    private static final int PUERTO = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVIDOR, PUERTO);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado al servidor. Escribe 'quit' para salir.");

            // Hilo para recibir mensajes del servidor
            Thread receptor = new Thread(() -> {
                try {
                    String respuesta;
                    while ((respuesta = in.readLine()) != null) {
                        System.out.println("Servidor: " + respuesta);
                    }
                } catch (IOException e) {
                    System.out.println("Conexión cerrada");
                }
            });
            receptor.start();

            // Enviar mensajes
            String mensaje;
            while (!(mensaje = scanner.nextLine()).equalsIgnoreCase("quit")) {
                out.println(mensaje);
            }

        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }
}