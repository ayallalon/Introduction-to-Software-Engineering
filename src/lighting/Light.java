package lighting;

import primitives.Color;

public abstract class Light {

    protected final Color intensity;

    /**
     * Light
     * @param intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * getter for light intensity
     * @return light intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
