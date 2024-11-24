package puppy.code;

import puppy.code.Pantallas.PantallaJuego;
import puppy.code.Patrones.EstrategiaDisparo;

public class ControladorDisparo {
    private EstrategiaDisparo estrategiaDisparo;

    public ControladorDisparo(EstrategiaDisparo estrategiaDisparo){
        this.estrategiaDisparo = estrategiaDisparo;
    }

    public void disparar(PantallaJuego juego, Nave4 nave){
        estrategiaDisparo.disparar(juego, nave);
    }

    public void setEstrategia(EstrategiaDisparo nuevaEstrategia){
        this.estrategiaDisparo = nuevaEstrategia;
    }

    public EstrategiaDisparo getEstrategia(){
        return this.estrategiaDisparo;
    }
}
