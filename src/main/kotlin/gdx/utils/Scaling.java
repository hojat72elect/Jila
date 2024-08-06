package gdx.utils;

import gdx.math.Vector2;

/**
 * Various scaling types for fitting one rectangle into another.
 */
public abstract class Scaling {
    protected static final Vector2 temp = new Vector2();
    /**
     * Scales the source to fit the target while keeping the same aspect ratio. This may cause the source to be smaller than the
     * target in one direction.
     */
    public static final gdx.utils.Scaling fit = new gdx.utils.Scaling() {
        public Vector2 apply(float sourceWidth, float sourceHeight, float targetWidth, float targetHeight) {
            float targetRatio = targetHeight / targetWidth;
            float sourceRatio = sourceHeight / sourceWidth;
            float scale = targetRatio > sourceRatio ? targetWidth / sourceWidth : targetHeight / sourceHeight;
            temp.x = sourceWidth * scale;
            temp.y = sourceHeight * scale;
            return temp;
        }
    };
    /**
     * Scales the source to fit the target while keeping the same aspect ratio, but the source is not scaled at all if smaller in
     * both directions. This may cause the source to be smaller than the target in one or both directions.
     */
    public static final gdx.utils.Scaling contain = new gdx.utils.Scaling() {
        public Vector2 apply(float sourceWidth, float sourceHeight, float targetWidth, float targetHeight) {
            float targetRatio = targetHeight / targetWidth;
            float sourceRatio = sourceHeight / sourceWidth;
            float scale = targetRatio > sourceRatio ? targetWidth / sourceWidth : targetHeight / sourceHeight;
            if (scale > 1) scale = 1;
            temp.x = sourceWidth * scale;
            temp.y = sourceHeight * scale;
            return temp;
        }
    };
    /**
     * Scales the source to fill the target while keeping the same aspect ratio. This may cause the source to be larger than the
     * target in one direction.
     */
    public static final gdx.utils.Scaling fill = new gdx.utils.Scaling() {
        public Vector2 apply(float sourceWidth, float sourceHeight, float targetWidth, float targetHeight) {
            float targetRatio = targetHeight / targetWidth;
            float sourceRatio = sourceHeight / sourceWidth;
            float scale = targetRatio < sourceRatio ? targetWidth / sourceWidth : targetHeight / sourceHeight;
            temp.x = sourceWidth * scale;
            temp.y = sourceHeight * scale;
            return temp;
        }
    };
    /**
     * Scales the source to fill the target in the x direction while keeping the same aspect ratio. This may cause the source to
     * be smaller or larger than the target in the y direction.
     */
    public static final gdx.utils.Scaling fillX = new gdx.utils.Scaling() {
        public Vector2 apply(float sourceWidth, float sourceHeight, float targetWidth, float targetHeight) {
            float scale = targetWidth / sourceWidth;
            temp.x = sourceWidth * scale;
            temp.y = sourceHeight * scale;
            return temp;
        }
    };
    /**
     * Scales the source to fill the target in the y direction while keeping the same aspect ratio. This may cause the source to
     * be smaller or larger than the target in the x direction.
     */
    public static final gdx.utils.Scaling fillY = new gdx.utils.Scaling() {
        public Vector2 apply(float sourceWidth, float sourceHeight, float targetWidth, float targetHeight) {
            float scale = targetHeight / sourceHeight;
            temp.x = sourceWidth * scale;
            temp.y = sourceHeight * scale;
            return temp;
        }
    };
    /**
     * Scales the source to fill the target. This may cause the source to not keep the same aspect ratio.
     */
    public static final gdx.utils.Scaling stretch = new gdx.utils.Scaling() {
        public Vector2 apply(float sourceWidth, float sourceHeight, float targetWidth, float targetHeight) {
            temp.x = targetWidth;
            temp.y = targetHeight;
            return temp;
        }
    };
    /**
     * Scales the source to fill the target in the x direction, without changing the y direction. This may cause the source to not
     * keep the same aspect ratio.
     */
    public static final gdx.utils.Scaling stretchX = new gdx.utils.Scaling() {
        public Vector2 apply(float sourceWidth, float sourceHeight, float targetWidth, float targetHeight) {
            temp.x = targetWidth;
            temp.y = sourceHeight;
            return temp;
        }
    };
    /**
     * Scales the source to fill the target in the y direction, without changing the x direction. This may cause the source to not
     * keep the same aspect ratio.
     */
    public static final gdx.utils.Scaling stretchY = new gdx.utils.Scaling() {
        public Vector2 apply(float sourceWidth, float sourceHeight, float targetWidth, float targetHeight) {
            temp.x = sourceWidth;
            temp.y = targetHeight;
            return temp;
        }
    };
    /**
     * The source is not scaled.
     */
    public static final gdx.utils.Scaling none = new gdx.utils.Scaling() {
        public Vector2 apply(float sourceWidth, float sourceHeight, float targetWidth, float targetHeight) {
            temp.x = sourceWidth;
            temp.y = sourceHeight;
            return temp;
        }
    };

    /**
     * Returns the size of the source scaled to the target. Note the same Vector2 instance is always returned and should never be
     * cached.
     */
    public abstract Vector2 apply(float sourceWidth, float sourceHeight, float targetWidth, float targetHeight);
}
