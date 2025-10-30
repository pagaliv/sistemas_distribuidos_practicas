package practica4.ejercicio1;

public class PruebaDescargador {
    public static void main(String[] args) {
        // Ejemplo de uso
        String url = "https://ejemplo.com/archivo-grande.zip";
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
