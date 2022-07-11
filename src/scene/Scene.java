package scene;

import lighting.AmbientLight;
import geometries.Geometries;
import lighting.Light;
import lighting.LightSource;
import primitives.Color;
import primitives.Point;

import java.util.LinkedList;
import java.util.List;

public class Scene {

    private final String name;
    private final Color background;
    private final Geometries geometries;
    private final List<LightSource> lights;
    private AmbientLight ambientLight;

    /**
     * constructor
     * @param builder
     */
    private Scene(SceneBuilder builder) {
        name = builder.name;
        background = builder.background;
        ambientLight = builder.ambientLight;
        geometries = builder.geometries;
        lights = builder.lights;
    }

    /**
     * getName
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * getBackground
     * @return Background
     */
    public Color getBackground() {
        return background;
    }

    /**
     * getAmbientLight
     * @return ambientLight
     */
    public AmbientLight getAmbientLight() {
        return ambientLight;
    }


    /**
     * setAmbientLight
     * @param ambientLight
     * @return AmbientLight
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }


    /**
     * getGeometries
     * @return Geometries
     */
    public Geometries getGeometries() {
        return geometries;
    }

    /**
     * getLights
     * @return Lights
     */
    public List<LightSource> getLights() {
        return lights;
    }

    public static class SceneBuilder {

        private final String name; //name of the scane
        private List<LightSource> lights = new LinkedList<>();
        private Color background = Color.BLACK;
        private AmbientLight ambientLight = new AmbientLight();
        private Geometries geometries = new Geometries();

        /**
         * SceneBuilder
         * @param name of the scane
         */
        public SceneBuilder(String name) {
            this.name = name;
        }

        /**
         * setBackground
         * @param background
         * @return background
         */
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        /**
         * setLights
         * @param lights
         * @return lights
         */
        public SceneBuilder setLights(List<LightSource> lights) {
            this.lights = lights;
            return this;
        }

        /**
         * setAmbientLight
         * @param ambientLight
         * @return ambientLight
         */
        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        /**
         * setGeometries
         * @param geometries
         * @return geometries
         */
        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        /**
         * build
         * @return new Scene
         */
        public Scene build() {
            //            validateObject(scene);
            return new Scene(this);
        }

        private void validateObject(Scene scene) {
            //nothing to do
        }

        public SceneBuilder readXmlFile(String filename) {
            //to do
            return this;
        }
    }
}
