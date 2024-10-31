package puppy.code.PowerUps;

import puppy.code.Pantallas.PantallaJuego;

public class BalasExtra implements PowerUp {
    @Override
    public void aplicarPowerUp(PantallaJuego pantalla){
        pantalla.getNave().setCantidadDisparosExtra(60);
        pantalla.getNave().setEstadoBalasExtra(true);
    }
}
