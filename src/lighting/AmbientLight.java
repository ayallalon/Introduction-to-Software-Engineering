package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * @author Ayala alon & Tehila Gabay
 */
public class AmbientLight extends Light {

    /**
     * constructor
     * @param Ia
     * @param Ka
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super( Ia.scale(Ka));
     }

    /**
     * constructor
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

}
