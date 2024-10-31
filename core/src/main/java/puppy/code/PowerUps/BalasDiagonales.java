package puppy.code.PowerUps;

import puppy.code.Pantallas.PantallaJuego;

public class BalasDiagonales implements PowerUp {
    @Override
    public void aplicarPowerUp(PantallaJuego pantalla){
        pantalla.getNave().setCantidadBalasDiagonales(60);
        pantalla.getNave().setEstadoBalasDiagonales(true);
    }
}
