package lighting;

import primitives.Color;

public abstract class Light {
    protected final Color intensity;

    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * getter for light intensity
     * @return
     */
    public Color getIntensity() {
        return intensity;
    }
}
