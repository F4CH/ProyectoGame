package puppy.code.Proyectiles;

public class Bullet extends Proyectil {

    public Bullet(){
        super();
    }

    @Override
    public void update() {
        getSpr().setPosition(getSpr().getX() + getXSpeed(), getSpr().getY() + getYSpeed());
        checkBounds();
    }

    public void setDestroyed() {
        super.setDestroyed(true);
    }
}
