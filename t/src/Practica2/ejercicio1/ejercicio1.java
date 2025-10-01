package Practica2.ejercicio1;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ejercicio1 {

    public static void main(String[] args) {
        // Verify that exactly one parameter was provided
        if (args.length != 1) {
            System.err.println("Usage: java NSLookup <hostname_or_ip>");
            System.exit(1);
        }

        String input = args[0];

        try {
            // Check if input is an IP address or hostname
            if (isIPAddress(input)) {
                // Reverse DNS lookup: IP to hostname
                performReverseLookup(input);
            } else {
                // Forward DNS lookup: hostname to IP
                performForwardLookup(input);
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + input);
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Checks if the input string is a valid IP address
     */
    private static boolean isIPAddress(String input) {
        // IPv4 pattern
        if (input.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")) {
            return true;
        }
        // IPv6 pattern (basic check)
        if (input.contains(":")) {
            return true;
        }
        return false;
    }

    /**
     * Performs forward lookup: hostname to IP address(es)
     */
    private static void performForwardLookup(String hostname) throws UnknownHostException {
        InetAddress[] addresses = InetAddress.getAllByName(hostname);

        for (InetAddress addr : addresses) {
            System.out.println(addr.getHostAddress());
        }
    }

    /**
     * Performs reverse lookup: IP address to hostname
     */
    private static void performReverseLookup(String ipAddress) throws UnknownHostException {
        InetAddress address = InetAddress.getByName(ipAddress);
        System.out.println(address.getHostName());
    }
}