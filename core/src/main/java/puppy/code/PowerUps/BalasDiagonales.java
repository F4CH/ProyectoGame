package puppy.code.PowerUps;

import puppy.code.Pantallas.PantallaJuego;
import puppy.code.Nave4;

public class BalasDiagonales implements PowerUp {
    @Override
    public void aplicarPowerUp(PantallaJuego pantalla, Nave4 nave){
        nave.agregarEfecto("BalasDiagonales", 30);
    }
}
