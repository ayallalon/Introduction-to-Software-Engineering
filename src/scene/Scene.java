package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

public class Scene {

    public final String name;                // name of the scene
    public final Color background;           // the color of background
    public final AmbientLight ambientLight;  // the ambientLight
    public final Geometries geometries;      // the geometries in the scene


    /***
     * constructor
     * @param builder that build the scene
     */
    private Scene(SceneBuilder builder) {
        name = builder.name;
        background = builder.background;
        ambientLight = builder.ambientLight;
        geometries = builder.geometries;
    }

    /**
     * get Name
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * get Background
     * @return Background
     */
    public Color getBackground() {
        return background;
    }

    /**
     * get AmbientLight
     * @return AmbientLight
     */
    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    /**
     * get Geometries
     * @return Geometries
     */
    public Geometries getGeometries() {
        return geometries;
    }

    /**
     * SceneBuilder that build the scene
     */
    public static class SceneBuilder {
        private final String name;
        private Color background = Color.BLACK;
        private AmbientLight ambientLight = new AmbientLight();
        private Geometries geometries = new Geometries();

        /**
         * constructor
         * @param name of scene
         */
        public SceneBuilder(String name) {
            this.name = name;
        }
       //caning methods

        /**
         * set AmbientLight
         * @param ambientLight of the geometries
         * @return AmbientLight
         */
        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        /**
         * set Geometries
         * @param geometries in the scene
         * @return Geometries
         */
        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        /**
         * set Background
         * @param background of thr scene
         * @return Background
         */
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        public Scene Build() {
            Scene scene = new Scene(this);
            //...
            return scene;
        }
    }
}
