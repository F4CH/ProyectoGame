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

    // Actualiza la posición del proyectil y verifica los límites
    @Override
    public void update() {
        // Actualiza el ángulo en cada fotograma para crear la trayectoria en espiral
        angle += deltaAngle;
        xSpeed = (float)(MathUtils.cosDeg(angle) * Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed));
        ySpeed = (float)(MathUtils.sinDeg(angle) * Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed));

        // Mueve el proyectil
        spr.setPosition(spr.getX() + xSpeed, spr.getY() + ySpeed);

        // Verifica si el proyectil está fuera de los límites de la pantalla
        checkBounds();
    }
}
