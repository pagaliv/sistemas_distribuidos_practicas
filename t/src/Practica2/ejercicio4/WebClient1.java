package Practica2.ejercicio4;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class WebClient1 {

    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            System.err.println("Usage: java WebClient1 <server> <resource> [port]");
            System.err.println("Example: java WebClient1 i1.wp.com /hipertextual.com/wp-content/uploads/2015/09/googles-new-logo-.gif");
            System.exit(1);
        }

        String server = args[0];
        String resource = args[1];
        int port = (args.length == 3) ? Integer.parseInt(args[2]) : 80;

        try {
            downloadResource(server, port, resource);
            System.out.println("Resource downloaded successfully: " + getFileName(resource));
        } catch (Exception e) {
            System.err.println("Error downloading resource: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void downloadResource(String server, int port, String resource) throws IOException {
        // Establish connection to the web server
        try (Socket socket = new Socket(server, port);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Send HTTP GET request
            String httpRequest = buildHttpRequest(server, resource);
            out.println(httpRequest);
            out.flush();

            System.out.println("Request sent:");
            System.out.println(httpRequest);

            // Read HTTP response
            String responseLine = in.readLine();
            System.out.println("Response: " + responseLine);

            if (responseLine == null || !responseLine.contains("200")) {
                throw new IOException("HTTP request failed: " + responseLine);
            }

            // Parse headers to find content length and skip to body
            long contentLength = parseHeadersAndGetContentLength(in);

            // Extract filename from resource
            String fileName = getFileName(resource);

            // Read and save the content
            saveContent(in, fileName, contentLength);
        }
    }

    private static String buildHttpRequest(String server, String resource) {
        StringBuilder request = new StringBuilder();
        request.append("GET ").append(resource).append(" HTTP/1.1\r\n");
        request.append("Host: ").append(server).append("\r\n");
        request.append("Connection: close\r\n"); // Use close to avoid socket caching issues
        request.append("User-Agent: WebClient1/1.0\r\n");
        request.append("\r\n");
        return request.toString();
    }

    private static long parseHeadersAndGetContentLength(DataInputStream in) throws IOException {
        long contentLength = -1;
        String line;

        System.out.println("Reading headers...");
        while ((line = in.readLine()) != null) {
            System.out.println("Header: " + line);

            // Empty line indicates end of headers
            if (line.isEmpty()) {
                break;
            }

            // Look for Content-Length header
            if (line.toLowerCase().startsWith("content-length:")) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    try {
                        contentLength = Long.parseLong(parts[1].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid Content-Length: " + parts[1]);
                    }
                }
            }
        }

        System.out.println("Content-Length: " + contentLength);
        return contentLength;
    }

    private static void saveContent(DataInputStream in, String fileName, long contentLength) throws IOException {
        System.out.println("Saving content to: " + fileName);

        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            if (contentLength > 0) {
                // Read exact number of bytes specified by Content-Length
                readExactBytes(in, fileOut, contentLength);
            } else {
                // Fallback: read until end of stream (may hang with HTTP/1.1 keep-alive)
                System.out.println("No Content-Length header, reading until end of stream...");
                readUntilEnd(in, fileOut);
            }
        }

        System.out.println("File saved successfully: " + fileName);
    }

    private static void readExactBytes(DataInputStream in, FileOutputStream fileOut, long contentLength) throws IOException {
        byte[] buffer = new byte[4096];
        long bytesRead = 0;

        while (bytesRead < contentLength) {
            int bytesToRead = (int) Math.min(buffer.length, contentLength - bytesRead);
            int read = in.read(buffer, 0, bytesToRead);

            if (read == -1) {
                throw new IOException("Unexpected end of stream. Expected: " + contentLength + ", got: " + bytesRead);
            }

            fileOut.write(buffer, 0, read);
            bytesRead += read;

            // Progress indicator for large files
            if (contentLength > 100000) { // Only show for files > 100KB
                int percent = (int) ((bytesRead * 100) / contentLength);
                System.out.print("\rDownloading: " + percent + "% (" + bytesRead + "/" + contentLength + " bytes)");
            }
        }

        if (contentLength > 100000) {
            System.out.println(); // New line after progress indicator
        }
    }

    private static void readUntilEnd(DataInputStream in, FileOutputStream fileOut) throws IOException {
        byte[] buffer = new byte[4096];
        int bytesRead;
        long totalBytes = 0;

        while ((bytesRead = in.read(buffer)) != -1) {
            fileOut.write(buffer, 0, bytesRead);
            totalBytes += bytesRead;
        }

        System.out.println("Downloaded " + totalBytes + " bytes (no Content-Length header)");
    }

    private static String getFileName(String resource) {
        // Extract filename from resource path
        String[] parts = resource.split("/");
        String fileName = parts[parts.length - 1];

        // If no filename in path, use default
        if (fileName.isEmpty() || fileName.equals(resource)) {
            return "downloaded_resource.html";
        }

        return fileName;
    }
}