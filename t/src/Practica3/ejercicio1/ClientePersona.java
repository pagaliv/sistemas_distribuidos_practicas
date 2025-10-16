package Practica3.ejercicio1;

import java.io.*;
import java.net.*;

public class ClientePersona {
    private static final String SERVIDOR = "localhost";
    private static final int PUERTO = 12345;

    public static void main(String[] args) {
        System.out.println("Iniciando cliente...");

        try (Socket socket = new Socket(SERVIDOR, PUERTO);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
             BufferedReader consoleReader = new BufferedReader(
                     new InputStreamReader(System.in))) {

            System.out.println("Conectado al servidor: " + SERVIDOR + ":" + PUERTO);

            // Crear objeto Persona inicial
            Persona persona = new Persona("Juan Pérez", 25);
            System.out.println("Persona creada: " + persona);

            // Enviar mensaje inicial
            writer.println("Hola, soy un cliente y te voy a enviar una persona");
            System.out.println("Mensaje enviado: Hola, soy un cliente y te voy a enviar una persona");

            // Enviar objeto Persona
            objectOut.writeObject(persona);
            objectOut.flush();
            System.out.println("Objeto Persona enviado: " + persona);

            // Recibir primera respuesta del servidor
            String respuesta1 = (String) objectIn.readObject();
            System.out.println("Servidor responde: " + respuesta1);

            // Actualizar la persona
            System.out.println("\n--- Actualizando persona ---");
            persona.setNombre("María García");
            persona.setEdad(30);
            System.out.println("Persona actualizada: " + persona);

            // Enviar mensaje de actualización
            writer.println("Actualizo la persona");
            System.out.println("Mensaje enviado: Actualizo la persona");

            // Enviar objeto Persona actualizado
            objectOut.writeObject(persona);
            objectOut.flush();
            System.out.println("Objeto Persona actualizado enviado: " + persona);

            // Recibir segunda respuesta del servidor
            String respuesta2 = (String) objectIn.readObject();
            System.out.println("Servidor responde: " + respuesta2);

            System.out.println("\nComunicación completada. Cerrando conexión...");

        } catch (UnknownHostException e) {
            System.err.println("Servidor no encontrado: " + SERVIDOR);
        } catch (ConnectException e) {
            System.err.println("No se pudo conectar al servidor. Asegúrate de que el servidor esté ejecutándose.");
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Clase no encontrada - " + e.getMessage());
        }
    }
}
