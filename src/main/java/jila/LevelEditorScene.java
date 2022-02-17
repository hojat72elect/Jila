package jila;

import java.awt.event.KeyEvent;

/**
 * Created by Hojat Ghasemi on 2022-02-16.
 * The author could be contacted at "https://twitter.com/hojat_93"
 */
public class LevelEditorScene extends Scene {

    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f;

    public LevelEditorScene() {
        System.out.println("we're inside level editor scene now!");
    }

    @Override
    public void update(float dt) {
        System.out.println("" + (1.0f / dt) + "FPS"); // prints out the refresh rate of this scene.

        if (!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            changingScene = true;
        }

        if (changingScene && timeToChangeScene > 0) {

            Window.get().r -= dt * 5.0f;
            Window.get().g -= dt * 5.0f;
            Window.get().b -= dt * 5.0f;
            timeToChangeScene -= dt;

        } else if (changingScene) {

            Window.changeScene(1);

        }
    }
}
