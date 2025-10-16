package Practica3.ejercicio1;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ServidorPersona {
    private static final int PUERTO = 12345;
    private static final int MAX_HILOS = 5;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(MAX_HILOS);

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor de Personas iniciado en puerto " + PUERTO);
            System.out.println("Esperando conexiones de clientes...");

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("\nNuevo cliente conectado: " +
                        clienteSocket.getInetAddress().getHostAddress());

                // Crear un nuevo hilo para manejar el cliente
                pool.execute(new ManejadorClientePersona(clienteSocket));
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }
}

class ManejadorClientePersona implements Runnable {
    private Socket socket;

    public ManejadorClientePersona(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()))) {

            // Configurar timeouts
            socket.setSoTimeout(30000); // 30 segundos timeout

            // Leer primer mensaje de texto
            String mensajeInicial = reader.readLine();
            if (mensajeInicial != null) {
                System.out.println("Cliente dice: " + mensajeInicial);
            }

            // Primera recepción del objeto Persona
            Persona persona = (Persona) objectIn.readObject();
            System.out.println("Objeto Persona recibido: " + persona);

            // Enviar confirmación al cliente
            String respuesta1 = "He recibido el objeto Persona con nombre " +
                    persona.getNombre() + " y edad " + persona.getEdad();
            objectOut.writeObject(respuesta1);
            objectOut.flush();
            System.out.println("Respuesta enviada: " + respuesta1);

            // Segunda recepción del objeto Persona actualizado
            String mensajeActualizacion = reader.readLine();
            if (mensajeActualizacion != null) {
                System.out.println("Cliente dice: " + mensajeActualizacion);
            }

            Persona personaActualizada = (Persona) objectIn.readObject();
            System.out.println("Objeto Persona actualizado recibido: " + personaActualizada);

            // Enviar segunda confirmación
            String respuesta2 = "He recibido el objeto Persona con nombre " +
                    personaActualizada.getNombre() + " y edad " +
                    personaActualizada.getEdad();
            objectOut.writeObject(respuesta2);
            objectOut.flush();
            System.out.println("Respuesta enviada: " + respuesta2);

            System.out.println("Comunicación completada con cliente: " +
                    socket.getInetAddress().getHostAddress());

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error con cliente " +
                    socket.getInetAddress().getHostAddress() + ": " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar socket: " + e.getMessage());
            }
        }
    }
}