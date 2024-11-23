package puppy.code.Patrones;

import puppy.code.Pantallas.*;

public interface PantallaFactory {
    PantallaMenu crearPantallaMenu();
    PantallaJuego crearPantallaJuego();
    PantallaInstrucciones crearPantallaInstrucciones();
    PantallaGameOver crearPantallaGameOver();
    PantallaPausa crearPantallaPausa(PantallaJuego pantallaJuego);
}
