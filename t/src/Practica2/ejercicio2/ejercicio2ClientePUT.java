package Practica2.ejercicio2;

import java.io.*;
import java.net.*;

public class ejercicio2ClientePUT {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java PutClient <name> <phone_number>");
            System.exit(1);
        }

        String nombre = args[0];
        String tfno = args[1];

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()))) {

            // Send PUT request
            String request = "PUT " + nombre + " " + tfno;
            out.println(request);

            // Read response
            String response = in.readLine();
            if ("OK".equals(response)) {
                System.out.println("Phone number stored successfully for: " + nombre);
            } else {
                System.err.println("Error storing phone number");
            }

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + SERVER_HOST);
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}