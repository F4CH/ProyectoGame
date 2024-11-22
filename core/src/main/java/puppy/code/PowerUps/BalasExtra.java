package puppy.code.PowerUps;

import com.badlogic.gdx.graphics.Texture;
import puppy.code.Pantallas.PantallaJuego;
import puppy.code.Nave4;
import puppy.code.Proyectiles.Bullet;

import java.util.ArrayList;
import java.util.List;

public class BalasExtra implements PowerUpDisparos {
    private int disparosRestantes;

    public BalasExtra(){
        this.disparosRestantes = 60;
    }

    public void aplicarPowerUp(PantallaJuego pantalla, Nave4 nave){
        nave.agregarPowerUp(this);
    }

    public List<Bullet> generarBalas(Nave4 nave){
        List<Bullet> balas = new ArrayList<>();
        if(disparosRestantes > 0){
            Texture txBala = nave.getTexture();
            float x = nave.getSprite().getX() + nave.getSprite().getWidth() / 2;
            float y = nave.getSprite().getY() + nave.getSprite().getHeight();
            balas.add(new Bullet(x - 25, y - 5, 0 , 3, txBala));
            balas.add(new Bullet(x + 15, y - 5, 0 , 3, txBala));
            disparosRestantes--;
        }
        return balas;
    }

    public boolean isActivo(){
        return disparosRestantes > 0;
    }
}
