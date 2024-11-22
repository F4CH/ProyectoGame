package puppy.code.PowerUps;

import puppy.code.Pantallas.PantallaJuego;
import puppy.code.Nave4;
import puppy.code.Proyectiles.Bullet;

import java.util.List;

public interface PowerUpDisparos{
    void aplicarPowerUp(PantallaJuego pantalla, Nave4 nave);
    /**
     * Genera las balas correspondientes según el tipo de power-up.
     * @param nave Nave que dispara.
     * @return Lista de balas generadas.
     */
    List<Bullet> generarBalas(Nave4 nave);

    boolean isActivo();
}
