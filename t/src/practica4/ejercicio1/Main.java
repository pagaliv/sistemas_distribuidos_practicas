package practica4.ejercicio1;

// Main.java
public class Main {
    public static void main(String[] args) {
        // Configuración básica
        String url = "https://www.ejemplo.com/archivo-grande.pdf";
        String directorioDestino = "./descargas";
        int numHilos = 3;

        DescargadorConcurrente descargador = new DescargadorConcurrente(
                url, directorioDestino, numHilos
        );

        try {
            descargador.descargar();
        } catch (Exception e) {
            System.err.println("Error durante la descarga: " + e.getMessage());
            e.printStackTrace();
        }
    }
}