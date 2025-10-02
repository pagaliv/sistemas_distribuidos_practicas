package Practica2.ejercicio2;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class ejercicio2Server {
    private static final int PORT = 12345;
    private static AgendaTfno agenda = new AgendaTfno();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Phone Directory Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String request = in.readLine();
                if (request == null) {
                    out.println("ERROR");
                    return;
                }

                System.out.println("Received: " + request);
                String response = processRequest(request);
                out.println(response);

            } catch (IOException e) {
                System.err.println("Client handling error: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing client socket: " + e.getMessage());
                }
            }
        }

        private String processRequest(String request) {
            if (request.startsWith("PUT ")) {
                return handlePutRequest(request);
            } else if (request.startsWith("GET ")) {
                return handleGetRequest(request);
            } else {
                return "ERROR";
            }
        }

        private String handlePutRequest(String request) {
            try {
                // Remove "PUT " prefix and split the remaining string
                String data = request.substring(4);
                String[] parts = data.split(" ", 2);

                if (parts.length != 2) {
                    return "ERROR";
                }

                String nombre = parts[0].trim();
                String tfno = parts[1].trim();

                if (nombre.isEmpty() || tfno.isEmpty()) {
                    return "ERROR";
                }

                agenda.añadeTelefono(nombre, tfno);
                System.out.println("Stored: " + nombre + " -> " + tfno);
                return "OK";

            } catch (Exception e) {
                return "ERROR";
            }
        }

        private String handleGetRequest(String request) {
            try {
                // Remove "GET " prefix
                String nombre = request.substring(4).trim();

                if (nombre.isEmpty()) {
                    return "ERROR";
                }

                String tfno = agenda.getTfno(nombre);
                if (tfno == null) {
                    return "Desconocido";
                }

                return tfno;

            } catch (Exception e) {
                return "ERROR";
            }
        }
    }
}

class AgendaTfno {
    private Map<String, String> agenda = new HashMap<>();

    public void añadeTelefono(String nombre, String tfno) {
        agenda.put(nombre, tfno);
    }

    public String getTfno(String nombre) {
        return agenda.get(nombre);
    }
}
