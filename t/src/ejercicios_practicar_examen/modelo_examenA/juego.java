package ejercicios_practicar_examen.modelo_examenA;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Juego {
    private List<manejadorDeHilos> jugadores;
    private CountDownLatch latch;
    public Juego(){
        jugadores = new ArrayList<manejadorDeHilos>();
        latch = new CountDownLatch(3);
    }
    public void addPlayer(manejadorDeHilos e){
        jugadores.add(e);
        latch.countDown();
    }
    public void jugar(){
        try {
            latch.await(); //se espera hasta iniciar la partida
        } catch (InterruptedException e) {
            e.printStackTrace();
        } 

    }
    
}
