package puppy.code.Patrones;

import puppy.code.CargaYGuardado.HighScoreGuardadoCarga;

public class ManejoHighScore {
    private static ManejoHighScore instance;
    private int highScore;

    private ManejoHighScore(){
        this.highScore = HighScoreGuardadoCarga.cargarHighScore();
    }

    public static ManejoHighScore getInstance(){
        if(instance == null){
            instance = new ManejoHighScore();
        }
        return instance;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int score) {
        if(score > highScore){
            highScore = score;

            HighScoreGuardadoCarga.guardarHighScore(highScore);
        }
    }
}
