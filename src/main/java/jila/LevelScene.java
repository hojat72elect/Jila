package jila;

/**
 * Created by Hojat Ghasemi on 2022-02-16.
 * The author could be contacted at "https://twitter.com/hojat_93"
 */
public class LevelScene extends Scene {
    public LevelScene() {
        System.out.println("and inside level scene right now!");
        Window.get().r = 1;
        Window.get().g = 1;
        Window.get().b = 1;
    }

    @Override
    public void update(float dt) {

    }
}
