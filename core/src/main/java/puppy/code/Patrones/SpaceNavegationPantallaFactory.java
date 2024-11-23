package puppy.code.Patrones;

import puppy.code.Pantallas.*;

import puppy.code.SpaceNavigation;

public class SpaceNavegationPantallaFactory implements PantallaFactory {
    private SpaceNavigation game;

    public SpaceNavegationPantallaFactory(SpaceNavigation game) {
        this.game = game;
    }

    @Override
    public PantallaMenu crearPantallaMenu(){
        return new PantallaMenu(game);
    }

    @Override
    public PantallaJuego crearPantallaJuego(){
        return new PantallaJuego(game);
    }
    @Override
    public PantallaInstrucciones crearPantallaInstrucciones(){
        return new PantallaInstrucciones(game);
    }
    @Override
    public PantallaGameOver crearPantallaGameOver(){
        return new PantallaGameOver(game);
    }
    @Override
    public PantallaPausa crearPantallaPausa(PantallaJuego pantallaJuego){
        return new PantallaPausa(game, pantallaJuego);
    }
}
