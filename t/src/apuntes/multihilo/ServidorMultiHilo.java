package apuntes.multihilo;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorMultiHilo {
    private static final int PUERTO = 8080;
    private static final int MAX_HILOS = 10;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(MAX_HILOS);

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en puerto " + PUERTO);

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado: " +
                        clienteSocket.getInetAddress().getHostAddress());

                // Crear un nuevo hilo para manejar el cliente
                pool.execute(new ManejadorCliente(clienteSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}

class ManejadorCliente implements Runnable {
    private Socket socket;

    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String mensaje;
            while ((mensaje = in.readLine()) != null) {
                System.out.println("Cliente " +
                        socket.getInetAddress().getHostAddress() +
                        " dice: " + mensaje);

                // Responder al cliente
                String respuesta = "Eco: " + mensaje.toUpperCase();
                out.println(respuesta);

                if (mensaje.equalsIgnoreCase("quit")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error con cliente: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
