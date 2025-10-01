package Practica2.ejercicio1;

import java.util.Scanner;

public class ejercicio1_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la ip o el dominio ");
        String ip = sc.nextLine();

    }
    public static boolean itIsIP(String input) {
        if (input.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")) {
            return true;
        }
        // IPv6 pattern (basic check)
        if (input.matches("(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}|::(?:[0-9a-fA-F]{1,4}:){0,6}[0-9a-fA-F]{1,4}|[0-9a-fA-F]{1,4}::(?:[0-9a-fA-F]{1,4}:){0,5}[0-9a-fA-F]{1,4}|(?:[0-9a-fA-F]{1,4}:){1,7}:|(?:[0-9a-fA-F]{1,4}:){0,6}:[0-9a-fA-F]{1,4}|::")) {
            return true;
        }
        return false;
    }
}
