package puppy.code.Patrones;

import puppy.code.Pantallas.PantallaJuego;
import puppy.code.Nave4;

public interface EstrategiaDisparo {

    /**
     * Genera las balas correspondientes según el tipo de power-up.
     */
    void disparar(PantallaJuego juego, Nave4 nave);

    int obtenerScore();
}
