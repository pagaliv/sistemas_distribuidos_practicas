package Practica2.ejercicio3;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ejercicio3Cliente {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner scanner;

    public static void main(String[] args) {
        ejercicio3Cliente client = new ejercicio3Cliente();
        client.start();
    }

    public void start() {
        try {
            // Connect to server
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            scanner = new Scanner(System.in);

            System.out.println("Connected to phone directory server");

            // Main interaction loop
            boolean running = true;
            while (running) {
                showMenu();
                int option = getMenuOption();

                switch (option) {
                    case 1:
                        addPhoneNumber();
                        break;
                    case 2:
                        getPhoneNumber();
                        break;
                    case 3:
                        running = false;
                        disconnect();
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }

                if (running) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + SERVER_HOST);
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    private void showMenu() {
        System.out.println("\n=== PHONE DIRECTORY CLIENT ===");
        System.out.println("1. Add phone number");
        System.out.println("2. Get phone number");
        System.out.println("3. Exit");
        System.out.print("Choose an option (1-3): ");
    }

    private int getMenuOption() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void addPhoneNumber() {
        try {
            System.out.print("Enter name: ");
            String nombre = scanner.nextLine().trim();

            System.out.print("Enter phone number: ");
            String tfno = scanner.nextLine().trim();

            if (nombre.isEmpty() || tfno.isEmpty()) {
                System.out.println("Error: Name and phone number cannot be empty");
                return;
            }

            // Send PUT request
            String request = "PUT " + nombre + " " + tfno;
            out.println(request);

            // Read response
            String response = in.readLine();
            if ("OK".equals(response)) {
                System.out.println("✓ Phone number stored successfully for: " + nombre);
            } else {
                System.out.println("✗ Error storing phone number");
            }

        } catch (IOException e) {
            System.err.println("Communication error: " + e.getMessage());
        }
    }

    private void getPhoneNumber() {
        try {
            System.out.print("Enter name to search: ");
            String nombre = scanner.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println("Error: Name cannot be empty");
                return;
            }

            // Send GET request
            String request = "GET " + nombre;
            out.println(request);

            // Read response
            String response = in.readLine();
            if ("ERROR".equals(response)) {
                System.out.println("✗ Error in request format");
            } else if ("Desconocido".equals(response)) {
                System.out.println("✗ Unknown name: " + nombre);
            } else {
                System.out.println("✓ Phone number for " + nombre + ": " + response);
            }

        } catch (IOException e) {
            System.err.println("Communication error: " + e.getMessage());
        }
    }

    private void disconnect() {
        try {
            // Send QUIT message to server
            out.println("QUIT");
            System.out.println("Disconnected from server");
        } catch (Exception e) {
            System.err.println("Error during disconnection: " + e.getMessage());
        }
    }

    private void closeResources() {
        try {
            if (scanner != null) scanner.close();
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}