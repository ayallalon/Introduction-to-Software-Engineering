package renderer;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.MissingResourceException;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * camera prodacting rays thtough a view plane
 */
public class Camera {


    private final Point _p0;//camera eye
    private final Vector _vTo; //vector pointing towards the scene
    private final Vector _vUp; //vector pointing upwards
    private final Vector _vRight; //vector pointing towards the right
    private double _distance;
    private double _width;
    private double _height;

    private ImageWriter imageWriter;
    private RayTracer rayTracer;

    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo))) {
            throw new IllegalArgumentException("vTo and vUp should be orthogonal.");
        }
        _p0 = p0;
        _vTo = vTo.normalize();
        _vUp = vUp.normalize();

        _vRight = _vTo.crossProduct(_vUp);
    }


    public Camera setVPDistance(double distance) {
        _distance = distance;
        return this;
    }

    public Camera setVPSize(double width, double height) {
        _width = width;
        _height = height;
        return this;
    }

    /**
     * Constructing a ray through a pixel.
     * @param Nx pixels of the width in the view plane
     * @param Ny pixels of the height in the view plane
     * @return ray from the camera to pixel[i, j]
     */
    public Ray constructRay(int Nx, int Ny, int j, int i) {
        //image center
        Point Pc = _p0.add(_vTo.scale(_distance));

        //Ratio (pixel width and height)

        double Ry = alignZero(_height / Ny);
        double Rx = alignZero(_width / Nx);

        //pixel [i, j] center
        Point Pij = Pc;

        //delta values for going to pixel[i,j] from pc.

        double yI = alignZero(-(i - (Ny - 1) / 2d) * Ry);
        double xJ = alignZero((j - (Nx - 1) / 2d) * Rx);

        if (!isZero(xJ)) {
            Pij = Pij.add(_vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            Pij = Pij.add(_vUp.scale(yI));
        }

        return new Ray(_p0, Pij.subtract(_p0));
    }

    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracer rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Function writeToImage produces unoptimized png file of the image according to
     * pixel color matrix in the directory of the project
     */
    public void writeToImage() {
        if (imageWriter == null) {
            throw new MissingResourceException("missing imagewriter", "Camera", "in print Grid");
        }
        imageWriter.writeToImage();
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
    public void renderImage() {
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
            Ray ray;
            Color pixelColor;
            for (int i = 0; i < nX; i++) {
                for (int j = 0; j < nY; j++) {
                    ray = constructRay(nX, nY, i, j);
                    pixelColor = rayTracer.traceRay(ray);
                    imageWriter.writePixel(i, j, pixelColor);
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
    }
}


