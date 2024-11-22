package puppy.code.PowerUps;

import puppy.code.Nave4;
import puppy.code.Pantallas.PantallaJuego;

/**
 * Interfaz para representar un PowerUp.
 * Define el comportamiento que debe implementar cualquier tipo de PowerUp.
 */
public interface PowerUp {

    /**
     * Aplica el efecto del PowerUp a la nave y al juego.
     *
     * @param pantalla Instancia de PantallaJuego donde se aplica el PowerUp.
     * @param nave Instancia de Nave4 afectada por el PowerUp.
     */
    void aplicarPowerUp(PantallaJuego pantalla, Nave4 nave);
}
