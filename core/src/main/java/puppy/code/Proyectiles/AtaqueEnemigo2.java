package puppy.code.Proyectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class AtaqueEnemigo2 extends Proyectil {
    private float deltaAngle;
    private float angle;

    public AtaqueEnemigo2(float x, float y, int speed, float angle, float deltaAngle, Texture tx) {
        super(x, y, speed, angle, tx);
        this.deltaAngle = deltaAngle;
        this.angle = angle;
    }

    @Override
    public void update() {
        // Actualiza el ángulo para crear la trayectoria en espiral
        angle += deltaAngle;

        // Calcula las nuevas velocidades basadas en el ángulo
        float speed = (float) Math.sqrt(getXSpeed() * getXSpeed() + getYSpeed() * getYSpeed());
        setXSpeed((float) (MathUtils.cosDeg(angle) * speed));
        setYSpeed((float) (MathUtils.sinDeg(angle) * speed));

        // Mueve el proyectil
        getSpr().setPosition(getSpr().getX() + getXSpeed(), getSpr().getY() + getYSpeed());
        checkBounds();
    }
}
