package puppy.code.PowerUps;
import puppy.code.Nave4;

public class VidasExtra extends VidasPowerUp{

    private int cantidadVidas;

    public VidasExtra() {
        this.cantidadVidas = 1;
    }

    public VidasExtra(int cantidadVidas){
        this.cantidadVidas = cantidadVidas;
    }

    @Override
    public void modificarVidas(Nave4 nave){
        nave.incrementarVidas(cantidadVidas);
    }



}
