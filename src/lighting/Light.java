package lighting;

import primitives.Color;


public abstract class Light {

    protected Color intensity;


    /**
     * constructor
     * @param intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * get Intensity -> reset intensity
     * @return intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
