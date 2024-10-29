package puppy.code.PowerUps;

import puppy.code.PantallaJuego;

public class BalasExtra implements PowerUp {
    @Override
    public void aplicarPowerUp(PantallaJuego pantalla){
        pantalla.getNave().setDisparosPowerUp(40);
        pantalla.getNave().setPowerUpDisparo(true);
    }
}
