package Practica2.ejercicio2;

import java.io.*;
import java.net.*;

public class ejercicio2ClienteGET {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java GetClient <name>");
            System.exit(1);
        }

        String nombre = args[0];

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()))) {

            // Send GET request
            String request = "GET " + nombre;
            out.println(request);

            // Read response
            String response = in.readLine();
            if ("ERROR".equals(response)) {
                System.err.println("Error in request format");
            } else if ("Desconocido".equals(response)) {
                System.out.println("Unknown name: " + nombre);
            } else {
                System.out.println("Phone number for " + nombre + ": " + response);
            }

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + SERVER_HOST);
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}
