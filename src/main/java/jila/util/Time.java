package jila.util;

/**
 * Created by Hojat Ghasemi on 2022-02-16.
 * The author could be contacted at "https://twitter.com/hojat_93"
 */
public class Time {
    public static float timeStarted = System.nanoTime();

    /**
     *
     * @return a simple delta time; time that has passed since the app started.
     */
    public static float getTime() {
        return (float) ((System.nanoTime() - timeStarted) * 1E-9);
    }

}
