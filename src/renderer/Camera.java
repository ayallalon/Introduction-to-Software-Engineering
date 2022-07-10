package renderer;

import java.util.stream.IntStream;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Random;

import static primitives.Util.isZero;

/**
 *
 * Class Camera represent a view of the geometric world through the view plane (which represent the picture),
 * Through the view plane the camera captures the geometric world.
 * it produces graphic views of the objects using the view plane and rays and object intersections.
 * The rays converge according to the location of the pixel centers in the view plane.
 * directions of the camera to the right, up and front  (compared to the original x,y,z axis),
 * all vectors orthogonal to each other
 */
public class Camera {
    
    /**
     * _P0 - the camera location
     */
    private Point p0;
    
    /**
     * X axis vector
     */
    private Vector vTo;
    
    /**
     * _Vup - Y axis vector
     */
    private Vector vUp;
    
     /**
     * Z axis vector
     */
    private Vector vRight;
    
    /**
     * object's actual distance from the camera center
     */
    private int distance;
    
    /**
     * object's actual width
     */
    private double width;
    
    /**
     * object's actual height
     */
    private double height;

    private ImageWriter imageWriter;
    private RayTracer rayTracer;
    private int superSampling;
    private double aperture;
    private double focus;
    private double dofSampling;

    /**
     * simple Camera constructor which get as input location point and two orthogonal vectors represent the direction
     *
     * @param p0  - the camera location
     * @param vTo - Y axis vector
     * @param vUp - X axis vector
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("vto  and vup are not orthogonal");
        }
        this.p0 = p0;

        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        superSampling = 1;
        vRight = this.vTo.crossProduct(this.vUp);
    }
    
     /**
     * setter - chaining method
     *
     * @param distance - the object's actual distance from the camera center
     * @return the camera with the configured distance
     */
    public Camera setVPDistance(int distance) {
        this.distance = distance;
        return this;
    }
    
    /**
     * setter - chaining method
     * @param width   - the width of the view plane
     * @param height- the height of the view plane
     * @return the camera with the configured view plane
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }
    /**
    * setter for imageWriter
    * @param imageWriter
    * @return the image writer for the camera
    */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracer rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }
    /**
     * Enable Super Sampling feature, providing superSampling density
     *
     * @param density amount of rays per pixel width or height
     * @return the camera itself - for chaining
     */
    public Camera enableSuperSampling(int density) {
        this.superSampling = density;
        return this;
    }

    /**
     * Enable Depthe of Field feature, providing aperture and focus distance
     *
     * @param aperture at the view plane
     * @param distance from view plane to focal plane
     * @param density  amount of rays per aperture window width or height
     * @return the camera itself - for chaining
     */
    public Camera enableDepthOfField(double aperture, double distance, int density) {
        this.aperture = aperture;
        this.focus = distance;
        this.dofSampling = density;
        return this;
    }
    // ***************** Operations ******************** //
    
    /**
     * this function gets the view plane size and a selected pixel,
     * and return the ray from the camera which intersects this pixel
     *
     * @param nX - amount of rows in view plane (number of pixels)
     * @param nY - amount of columns in view plane (number of pixels)
     * @param j  - X's index
     * @param i  - Y's index
     * @return - the ray which goes through the pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {

        //view plane center Point
        Point Pc = p0.add(vTo.scale(distance));

        //pixels ratios
        double Rx = width / nX;
        double Ry = height / nY;

        //Pij point[i,j] in view-plane coordinates
        Point Pij = Pc;

        //delta values for moving on the view=plane
        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;

        if (!isZero(Xj)) {
            Pij = Pij.add(vRight.scale(Xj));
        }
        if (!isZero(Yi)) {
            Pij = Pij.add(vUp.scale(Yi));
        }

        // vector from camera's eye in the direction of point(i,j) in the viewplane
        Vector Vij = Pij.subtract(p0);

        return new Ray(p0, Vij);

    }

    /**
     * The function constructs a beam of rays from Camera location throw the pixel
     * (i,j) in the view plane - the ray starts at the pixel if Depth of Field is
     * activated!!!
     *
     * @param nX             number of pixels in a row of view plane
     * @param nY             number of pixels in a column of view plane
     * @param j              number of the pixel in a row
     * @param i              number of the pixel in a column
     * @param dist distance from the camera to the view plane
     * @param width    view plane width
     * @param height   view plane height
     * @return the beam of rays from pixel (if DoF is active) or from camera
     */
    public List<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i, double dist, double width, double height) {
        if (superSampling == 1) {
            return List.of(constructRay(nX, nY, j, i));
        }
        double rX = width / nX;
        double rY = height / nY;
        double xJ = (j - (nX - 1) / 2d) * rX;
        double yI = (i - (nY - 1) / 2d) * rY;
        Point pIJ = p0.add(vTo.scale(dist)); // the view plane center point
        if (xJ != 0)
            pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0)
            pIJ = pIJ.add(vUp.scale(-yI)); // it's possible pIJ.subtract(_vUp.scale(yI));

        if (superSampling == 0)
            return constructFocalRays(pIJ);

        List<Ray> rays = new LinkedList<>();
        double y = -rY / 2d;
        double dY = rY / superSampling;
        double xStart = -rX / 2d;
        double dX = rX / superSampling;
        for (double row = superSampling; row >= 1; --row, y += dY) {
            double x = xStart;
            for (double col = superSampling; col >= 1; --col, x += dX) {
                Point p = pIJ;
                if (!isZero(x))
                    p = pIJ.add(vRight.scale(x));
                if (!isZero(y))
                    p = p.add(vUp.scale(y));
                rays.addAll(constructFocalRays(p));
            }
        }
        return rays;
    }

    /**
     * Cast ray from camera in order to color a pixel
     *
     * @param nX  - resolution on X axis (number of pixels in row)
     * @param nY  - resolution on Y axis (number of pixels in column)
     * @param icol - pixel's column number (pixel index in row)
     * @param jrow - pixel's row number (pixel index in column)
     */
    private void castBeamRay(int nX, int nY, int icol, int jrow) {
        List<Ray> rays = constructRaysThroughPixel(nX, nY, jrow, icol, distance, width, height);
        Color pixelColor = rayTracer.traceRays(rays);
        imageWriter.writePixel(jrow, icol, pixelColor);
    }
    
     /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public Camera renderImage() {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracer.class.getName(), "");
            }
            //rendering the image
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            IntStream.range(0, nY).parallel().forEach(i -> {
                IntStream.range(0, nX).parallel().forEach(j -> {
                    castBeamRay(nX, nY,i,j);
                });
            });
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
        return this;
    }

    /**
     * Cast ray from camera in order to color a pixel
     *
     * @param nX  - resolution on X axis (number of pixels in row)
     * @param nY  - resolution on Y axis (number of pixels in column)
     * @param icol - pixel's column number (pixel index in row)
     * @param jrow - pixel's row number (pixel index in column)
     */
    private void castRay(int nX, int nY, int icol, int jrow) {
        Ray ray = constructRay(nX, nY, jrow, icol);
        Color pixelColor = rayTracer.traceRay(ray);
        imageWriter.writePixel(jrow, icol, pixelColor);
    }

    /**
     * chaining functios
     */
    public void printGrid(int interval, Color color) {
        imageWriter.printGrid(interval, color);
    }

    private static Random rnd = new Random();

    /**
     * Create beam of rays from view plane aperture hole through focal point
     *
     * @param pnt point at View Plane
     * @return beam of rays
     */
    private List<Ray> constructFocalRays(Point pnt) {
        Vector v = pnt.subtract(p0);
        if (dofSampling == 0)
            return List.of(new Ray(p0, v));

        v.normalize();
        Point f = pnt.add(v.scale(focus / vTo.dotProduct(v)));

        List<Ray> rays = new LinkedList<>();
        for (double i = dofSampling; i > 0; --i) {
            double x = rnd.nextDouble() * 2 - 1;
            double y = Math.sqrt(1 - x * x);
            Point p = pnt;
            double mult = (rnd.nextDouble() - 0.5) * aperture;
            if (!isZero(x))
                p.add(vRight.scale(x * mult));
            if (!isZero(y))
                p.add(vUp.scale(y * mult));
            rays.add(new Ray(p, f.subtract(p)));
        }
        return rays;
    }

    public Camera writeToImage() {
        imageWriter.writeToImage();
        return this;
    }


    //moving and rotating camera
    /**
     * Adds the given amount to the camera's position
     *
     * @return the current camera
     */
    public Camera move(Double3 amount) {
        p0 = p0.add(new Vector(amount));
        return this;
    }

    /**
     * Rotates the camera around the axes with the given angles
     *
     * @param x angles to rotate around the x axis
     * @param y angles to rotate around the y axis
     * @param z angles to rotate around the z axis
     * @return the current camera
     */
    public Camera rotate(double x, double y, double z) {
        vTo = vTo.rotateX(x).rotateY(y).rotateZ(z);
        vUp = vUp.rotateX(x).rotateY(y).rotateZ(z);
        vRight = vTo.crossProduct(vUp);

        return this;
    }



}
