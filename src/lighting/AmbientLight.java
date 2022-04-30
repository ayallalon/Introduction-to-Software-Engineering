package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * AmbientLight for all 3D objects
 */
public class AmbientLight extends Light {

    /**
     * default constructor
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * constructor
     * @param Ia illumination color for light
     * @param Ka attenuation factor
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }
}
