package renderer;

import static primitives.Util.isZero;

import java.util.MissingResourceException;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * camera protracting rays thought a view plane
 */
public class Camera {

    private final Point p0;//camera eye
    private final Vector vTo; //vector pointing towards the scene
    private final Vector vUp; //vector pointing upwards
    private final Vector vRight; //vector pointing towards the right

    private double distance;
    private double width; // width for view plane
    private double height;// height for view plane

    private ImageWriter imageWriter;
    private RayTracer rayTracer;

    /**
     * constructor
     * @param p0 center of the camera
     * @param vTo dir camera
     * @param vUp Camera location
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo))) {
            throw new IllegalArgumentException("vTo and vUp should be orthogonal.");
        }
        this.p0 = p0;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();

        this.vRight = this.vTo.crossProduct(this.vUp);
    }

    /**
     * set view plane Distance
     * @return the distance
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * set view plane Size
     * @param width for view plane
     * @param height for view plane
     * @return width and height
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }


    /**
     * this function gets the view plane size and a selected pixel,
     * and return the ray from the camera which intersects this pixel
     * @param nX - amount of rows in view plane (number of pixels)
     * @param nY - amount of columns in view plane (number of pixels)
     * @param j - X's index
     * @param i - Y's index
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
     * set ImageWriter
     * @return imageWriter
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * set RayTracer
     * @return RayTracer
     */
    public Camera setRayTracer(RayTracer rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Function writeToImage produces unoptimized png file of the image according to
     * pixel color matrix in the directory of the project
     */
    public Camera writeToImage() {
        if (imageWriter == null) {
            throw new MissingResourceException("missing imagewriter", "Camera", "in print Grid");
        }
        imageWriter.writeToImage();
        return this;
    }

    /**
     * paints the image as a grid according to the wanted interval and color of grid lines
     * @param interval length of wanted interval
     * @param color wanted color for grid lines
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null) {
            throw new MissingResourceException("missing imagewriter", "Camera", "in print Grid");
        }

        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(i, j, color);
                }
            }
            imageWriter.writeToImage();
        }
    }


    /**
     * The actual rendering function , according to data received from the ray tracer - colours each
     * pixel appropriately thus
     * rendering the image
     */
    public Camera renderImage() {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(),
                                                   "");
            }
            if (rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracer.class.getName(),
                                                   "");
            }

            //rendering the image
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();

            for (int row = 0; row < nY; row++) {
                for (int col = 0; col < nX; col++) {
                    castRay(nX, nY, row, col);//check the parameter
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
        return this;
    }


    /**
     * Cast ray from camera in order to color a pixel
     * @param nX - resolution on X axis (number of pixels in row)
     * @param nY - resolution on Y axis (number of pixels in column)
     * @param icol - pixel's column number (pixel index in row)
     * @param jrow - pixel's row number (pixel index in column)
     */
    private void castRay(int nX, int nY, int icol, int jrow) {
        Ray ray = constructRay(nX, nY, jrow, icol);
        Color pixelColor = rayTracer.traceRay(ray);
        imageWriter.writePixel(jrow, icol, pixelColor);
    }
}
