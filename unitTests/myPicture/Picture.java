package myPicture;

import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;

import geometries.Plane;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

public class Picture {


    @Test
    public void OurPicture() {
        Camera camera = new Camera(new Point(-90, -100, -3000), new Vector(0, 0, 1),
                                   new Vector(0, -1, 0)).setVPSize(200, 200).setVPDistance(1000);

        Scene scene = new Scene.SceneBuilder("Test scene")
            .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)))
            .build();


        scene.getGeometries()
             .add(
                 new Plane(new Point(0, 100, 500), new Vector(new Double3(0, -1, 0)))
                     .setEmission(new Color(BLACK)).setMaterial(new Material()
                                                                    .setKd(0.5)
                                                                    .setKs(0.5)
                                                                    .setKt(0)
                                                                    .setKr(0.2)
                                                                    .setShininess(
                                                                        60)

                                                               ),
                 new Sphere(new Point(50, 50, 2000), 50).setEmission(new Color(WHITE))
                                                        .setMaterial(new Material()
                                                                         .setKd(0.2)
                                                                         .setKs(0.2)
                                                                         .setKt(0.8)
                                                                         .setKr(0)
                                                                         .setShininess(10)),
                 new Sphere(new Point(150, 0, 2100), 100).setEmission(new Color(0, 255, 0))
                                                         .setMaterial(new Material().setKd(0.2)
                                                                                    .setKs(0.2)
                                                                                    .setKt(0.5)
                                                                                    .setKr(0)
                                                                                    .setShininess(
                                                                                        100)),
                 new Sphere(new Point(-250, -50, 2200), 150).setEmission(new Color(32, 178, 170))
                                                            .setMaterial(new Material()
                                                                             .setKd(0.2)
                                                                             .setKs(0.2)
                                                                             .setKt(0)
                                                                             .setKr(0.2)
                                                                             .setShininess(100)),
                 new Sphere(new Point(-50, 25, 2100), 75).setEmission(new Color(220, 20, 60))
                                                         .setMaterial(new Material()
                                                                          .setKd(0.2)
                                                                          .setKs(0.2)
                                                                          .setKt(0.3)
                                                                          .setKr(0)
                                                                          .setShininess(20))

                 );


        scene.getLights().add(
            new PointLight(new Color(255, 255, 224), new Point(0, -270, 2000))
                .setKc(1).setKl(0.0001).setKq(0.00000001));

        scene.getLights().add(
            new SpotLight(new Color(500, 300, 0), new Point(250, -200, 1900),
                          new Vector(-1, 1, 0.5)).setKc(1).setKl(4E-7).setKq(2E-10));

        camera.setImageWriter(new ImageWriter("Picture", 1000, 1000)) //
              .setRayTracer(new RayTracerBasic(scene)) //
              .renderImage() //
              .writeToImage();
    }
}
