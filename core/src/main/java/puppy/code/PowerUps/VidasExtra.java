package puppy.code.PowerUps;

import puppy.code.Pantallas.PantallaJuego;

public class VidasExtra implements PowerUp {

    @Override
    public void aplicarPowerUp(PantallaJuego pantalla){
        pantalla.getNave().setVidas(pantalla.getNave().getVidas() + 1);
    }
}
