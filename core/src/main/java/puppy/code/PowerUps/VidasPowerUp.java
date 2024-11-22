package puppy.code.PowerUps;

import puppy.code.Pantallas.PantallaJuego;
import puppy.code.Nave4;

public abstract class VidasPowerUp implements PowerUp {
    public abstract void modificarVidas(Nave4 nave);

    public void aplicarPowerUp(PantallaJuego juego, Nave4 nave){
        modificarVidas(nave);
    }
}
