package gdx.math;

/**
 * Takes a linear value in the range of 0-1 and outputs a (usually) non-linear, interpolated value.
 */
public abstract class Interpolation {
    static public final gdx.math.Interpolation linear = new gdx.math.Interpolation() {
        public float apply(float a) {
            return a;
        }
    };
    /**
     * Aka "smoothstep".
     */
    static public final gdx.math.Interpolation smooth = new gdx.math.Interpolation() {
        public float apply(float a) {
            return a * a * (3 - 2 * a);
        }
    };

    //
    static public final gdx.math.Interpolation smooth2 = new gdx.math.Interpolation() {
        public float apply(float a) {
            a = a * a * (3 - 2 * a);
            return a * a * (3 - 2 * a);
        }
    };

    //
    /**
     * By Ken Perlin.
     */
    static public final gdx.math.Interpolation smoother = new gdx.math.Interpolation() {
        public float apply(float a) {
            return a * a * a * (a * (a * 6 - 15) + 10);
        }
    };
    static public final gdx.math.Interpolation fade = smoother;
    static public final gdx.math.Interpolation.Pow pow2 = new gdx.math.Interpolation.Pow(2);
    /**
     * Slow, then fast.
     */
    static public final gdx.math.Interpolation.PowIn pow2In = new gdx.math.Interpolation.PowIn(2);

    //
    static public final gdx.math.Interpolation.PowIn slowFast = pow2In;
    /**
     * Fast, then slow.
     */
    static public final gdx.math.Interpolation.PowOut pow2Out = new gdx.math.Interpolation.PowOut(2);
    static public final gdx.math.Interpolation.PowOut fastSlow = pow2Out;
    static public final gdx.math.Interpolation pow2InInverse = new gdx.math.Interpolation() {
        public float apply(float a) {
            if (a < MathUtils.FLOAT_ROUNDING_ERROR) return 0;
            return (float) Math.sqrt(a);
        }
    };
    static public final gdx.math.Interpolation pow2OutInverse = new gdx.math.Interpolation() {
        public float apply(float a) {
            if (a < MathUtils.FLOAT_ROUNDING_ERROR) return 0;
            if (a > 1) return 1;
            return 1 - (float) Math.sqrt(-(a - 1));
        }
    };
    static public final gdx.math.Interpolation.Pow pow3 = new gdx.math.Interpolation.Pow(3);
    static public final gdx.math.Interpolation.PowIn pow3In = new gdx.math.Interpolation.PowIn(3);
    static public final gdx.math.Interpolation.PowOut pow3Out = new gdx.math.Interpolation.PowOut(3);
    static public final gdx.math.Interpolation pow3InInverse = new gdx.math.Interpolation() {
        public float apply(float a) {
            return (float) Math.cbrt(a);
        }
    };
    static public final gdx.math.Interpolation pow3OutInverse = new gdx.math.Interpolation() {
        public float apply(float a) {
            return 1 - (float) Math.cbrt(-(a - 1));
        }
    };
    static public final gdx.math.Interpolation.Pow pow4 = new gdx.math.Interpolation.Pow(4);
    static public final gdx.math.Interpolation.PowIn pow4In = new gdx.math.Interpolation.PowIn(4);
    static public final gdx.math.Interpolation.PowOut pow4Out = new gdx.math.Interpolation.PowOut(4);
    static public final gdx.math.Interpolation.Pow pow5 = new gdx.math.Interpolation.Pow(5);
    static public final gdx.math.Interpolation.PowIn pow5In = new gdx.math.Interpolation.PowIn(5);
    static public final gdx.math.Interpolation.PowOut pow5Out = new gdx.math.Interpolation.PowOut(5);
    static public final gdx.math.Interpolation sine = new gdx.math.Interpolation() {
        public float apply(float a) {
            return (1 - MathUtils.cos(a * MathUtils.PI)) / 2;
        }
    };
    static public final gdx.math.Interpolation sineIn = new gdx.math.Interpolation() {
        public float apply(float a) {
            return 1 - MathUtils.cos(a * MathUtils.HALF_PI);
        }
    };
    static public final gdx.math.Interpolation sineOut = new gdx.math.Interpolation() {
        public float apply(float a) {
            return MathUtils.sin(a * MathUtils.HALF_PI);
        }
    };
    static public final gdx.math.Interpolation.Exp exp10 = new gdx.math.Interpolation.Exp(2, 10);
    static public final gdx.math.Interpolation.ExpIn exp10In = new gdx.math.Interpolation.ExpIn(2, 10);
    static public final gdx.math.Interpolation.ExpOut exp10Out = new gdx.math.Interpolation.ExpOut(2, 10);
    static public final gdx.math.Interpolation.Exp exp5 = new gdx.math.Interpolation.Exp(2, 5);
    static public final gdx.math.Interpolation.ExpIn exp5In = new gdx.math.Interpolation.ExpIn(2, 5);
    static public final gdx.math.Interpolation.ExpOut exp5Out = new gdx.math.Interpolation.ExpOut(2, 5);
    static public final gdx.math.Interpolation circle = new gdx.math.Interpolation() {
        public float apply(float a) {
            if (a <= 0.5f) {
                a *= 2;
                return (1 - (float) Math.sqrt(1 - a * a)) / 2;
            }
            a--;
            a *= 2;
            return ((float) Math.sqrt(1 - a * a) + 1) / 2;
        }
    };
    static public final gdx.math.Interpolation circleIn = new gdx.math.Interpolation() {
        public float apply(float a) {
            return 1 - (float) Math.sqrt(1 - a * a);
        }
    };
    static public final gdx.math.Interpolation circleOut = new gdx.math.Interpolation() {
        public float apply(float a) {
            a--;
            return (float) Math.sqrt(1 - a * a);
        }
    };
    static public final gdx.math.Interpolation.Elastic elastic = new gdx.math.Interpolation.Elastic(2, 10, 7, 1);
    static public final gdx.math.Interpolation.ElasticIn elasticIn = new gdx.math.Interpolation.ElasticIn(2, 10, 6, 1);
    static public final gdx.math.Interpolation.ElasticOut elasticOut = new gdx.math.Interpolation.ElasticOut(2, 10, 7, 1);
    static public final gdx.math.Interpolation.Swing swing = new gdx.math.Interpolation.Swing(1.5f);
    static public final gdx.math.Interpolation.SwingIn swingIn = new gdx.math.Interpolation.SwingIn(2f);
    static public final gdx.math.Interpolation.SwingOut swingOut = new gdx.math.Interpolation.SwingOut(2f);
    static public final gdx.math.Interpolation.Bounce bounce = new gdx.math.Interpolation.Bounce(4);
    static public final gdx.math.Interpolation.BounceIn bounceIn = new gdx.math.Interpolation.BounceIn(4);
    static public final gdx.math.Interpolation.BounceOut bounceOut = new gdx.math.Interpolation.BounceOut(4);

    /**
     * @param a Alpha value between 0 and 1.
     */
    abstract public float apply(float a);

    /**
     * @param a Alpha value between 0 and 1.
     */
    public float apply(float start, float end, float a) {
        return start + (end - start) * apply(a);
    }

    //

    static public class Pow extends gdx.math.Interpolation {
        final int power;

        public Pow(int power) {
            this.power = power;
        }

        public float apply(float a) {
            if (a <= 0.5f) return (float) Math.pow(a * 2, power) / 2;
            return (float) Math.pow((a - 1) * 2, power) / (power % 2 == 0 ? -2 : 2) + 1;
        }
    }

    static public class PowIn extends gdx.math.Interpolation.Pow {
        public PowIn(int power) {
            super(power);
        }

        public float apply(float a) {
            return (float) Math.pow(a, power);
        }
    }

    static public class PowOut extends gdx.math.Interpolation.Pow {
        public PowOut(int power) {
            super(power);
        }

        public float apply(float a) {
            return (float) Math.pow(a - 1, power) * (power % 2 == 0 ? -1 : 1) + 1;
        }
    }

    //

    static public class Exp extends gdx.math.Interpolation {
        final float value, power, min, scale;

        public Exp(float value, float power) {
            this.value = value;
            this.power = power;
            min = (float) Math.pow(value, -power);
            scale = 1 / (1 - min);
        }

        public float apply(float a) {
            if (a <= 0.5f) return ((float) Math.pow(value, power * (a * 2 - 1)) - min) * scale / 2;
            return (2 - ((float) Math.pow(value, -power * (a * 2 - 1)) - min) * scale) / 2;
        }
    }

    ;

    static public class ExpIn extends gdx.math.Interpolation.Exp {
        public ExpIn(float value, float power) {
            super(value, power);
        }

        public float apply(float a) {
            return ((float) Math.pow(value, power * (a - 1)) - min) * scale;
        }
    }

    static public class ExpOut extends gdx.math.Interpolation.Exp {
        public ExpOut(float value, float power) {
            super(value, power);
        }

        public float apply(float a) {
            return 1 - ((float) Math.pow(value, -power * a) - min) * scale;
        }
    }

    //

    static public class Elastic extends gdx.math.Interpolation {
        final float value, power, scale, bounces;

        public Elastic(float value, float power, int bounces, float scale) {
            this.value = value;
            this.power = power;
            this.scale = scale;
            this.bounces = bounces * MathUtils.PI * (bounces % 2 == 0 ? 1 : -1);
        }

        public float apply(float a) {
            if (a <= 0.5f) {
                a *= 2;
                return (float) Math.pow(value, power * (a - 1)) * MathUtils.sin(a * bounces) * scale / 2;
            }
            a = 1 - a;
            a *= 2;
            return 1 - (float) Math.pow(value, power * (a - 1)) * MathUtils.sin((a) * bounces) * scale / 2;
        }
    }

    static public class ElasticIn extends gdx.math.Interpolation.Elastic {
        public ElasticIn(float value, float power, int bounces, float scale) {
            super(value, power, bounces, scale);
        }

        public float apply(float a) {
            if (a >= 0.99) return 1;
            return (float) Math.pow(value, power * (a - 1)) * MathUtils.sin(a * bounces) * scale;
        }
    }

    static public class ElasticOut extends gdx.math.Interpolation.Elastic {
        public ElasticOut(float value, float power, int bounces, float scale) {
            super(value, power, bounces, scale);
        }

        public float apply(float a) {
            if (a == 0) return 0;
            a = 1 - a;
            return (1 - (float) Math.pow(value, power * (a - 1)) * MathUtils.sin(a * bounces) * scale);
        }
    }

    //

    static public class Bounce extends gdx.math.Interpolation.BounceOut {
        public Bounce(float[] widths, float[] heights) {
            super(widths, heights);
        }

        public Bounce(int bounces) {
            super(bounces);
        }

        private float out(float a) {
            float test = a + widths[0] / 2;
            if (test < widths[0]) return test / (widths[0] / 2) - 1;
            return super.apply(a);
        }

        public float apply(float a) {
            if (a <= 0.5f) return (1 - out(1 - a * 2)) / 2;
            return out(a * 2 - 1) / 2 + 0.5f;
        }
    }

    static public class BounceOut extends gdx.math.Interpolation {
        final float[] widths, heights;

        public BounceOut(float[] widths, float[] heights) {
            if (widths.length != heights.length)
                throw new IllegalArgumentException("Must be the same number of widths and heights.");
            this.widths = widths;
            this.heights = heights;
        }

        public BounceOut(int bounces) {
            if (bounces < 2 || bounces > 5)
                throw new IllegalArgumentException("bounces cannot be < 2 or > 5: " + bounces);
            widths = new float[bounces];
            heights = new float[bounces];
            heights[0] = 1;
            switch (bounces) {
                case 2:
                    widths[0] = 0.6f;
                    widths[1] = 0.4f;
                    heights[1] = 0.33f;
                    break;
                case 3:
                    widths[0] = 0.4f;
                    widths[1] = 0.4f;
                    widths[2] = 0.2f;
                    heights[1] = 0.33f;
                    heights[2] = 0.1f;
                    break;
                case 4:
                    widths[0] = 0.34f;
                    widths[1] = 0.34f;
                    widths[2] = 0.2f;
                    widths[3] = 0.15f;
                    heights[1] = 0.26f;
                    heights[2] = 0.11f;
                    heights[3] = 0.03f;
                    break;
                case 5:
                    widths[0] = 0.3f;
                    widths[1] = 0.3f;
                    widths[2] = 0.2f;
                    widths[3] = 0.1f;
                    widths[4] = 0.1f;
                    heights[1] = 0.45f;
                    heights[2] = 0.3f;
                    heights[3] = 0.15f;
                    heights[4] = 0.06f;
                    break;
            }
            widths[0] *= 2;
        }

        public float apply(float a) {
            if (a == 1) return 1;
            a += widths[0] / 2;
            float width = 0, height = 0;
            for (int i = 0, n = widths.length; i < n; i++) {
                width = widths[i];
                if (a <= width) {
                    height = heights[i];
                    break;
                }
                a -= width;
            }
            a /= width;
            float z = 4 / width * height * a;
            return 1 - (z - z * a) * width;
        }
    }

    static public class BounceIn extends gdx.math.Interpolation.BounceOut {
        public BounceIn(float[] widths, float[] heights) {
            super(widths, heights);
        }

        public BounceIn(int bounces) {
            super(bounces);
        }

        public float apply(float a) {
            return 1 - super.apply(1 - a);
        }
    }

    //

    static public class Swing extends gdx.math.Interpolation {
        private final float scale;

        public Swing(float scale) {
            this.scale = scale * 2;
        }

        public float apply(float a) {
            if (a <= 0.5f) {
                a *= 2;
                return a * a * ((scale + 1) * a - scale) / 2;
            }
            a--;
            a *= 2;
            return a * a * ((scale + 1) * a + scale) / 2 + 1;
        }
    }

    static public class SwingOut extends gdx.math.Interpolation {
        private final float scale;

        public SwingOut(float scale) {
            this.scale = scale;
        }

        public float apply(float a) {
            a--;
            return a * a * ((scale + 1) * a + scale) + 1;
        }
    }

    static public class SwingIn extends gdx.math.Interpolation {
        private final float scale;

        public SwingIn(float scale) {
            this.scale = scale;
        }

        public float apply(float a) {
            return a * a * ((scale + 1) * a - scale);
        }
    }
}
