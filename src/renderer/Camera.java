package renderer;

import static primitives.Util.isZero;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * camera prodacting rays thtough a view plane
 */
public class Camera {


    private final Point p0;//camera eye
    private final Vector vTo; //vector pointing towards the scene
    private final Vector vUp; //vector pointing upwards
    private final Vector vRight; //vector pointing towards the right
    private double distance;
    private double width; // width for view plane
    private double height;// height for view plane

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

        vRight = this.vTo.crossProduct(this.vUp);
    }

    /**
     * set view plane Distance
     * @param distance
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
     * Constructing a ray through a pixel.
     * @param Nx pixels of the width in the view plane
     * @param Ny pixels of the height in the view plane
     * @return ray from the camera to pixel[i, j]
     */
    public Ray constructRay(int Nx, int Ny, int j, int i) {
        //image center
        Point Pc = p0.add(vTo.scale(distance));

        //Ratio (pixel width and height)

        double Ry = height / Ny;
        double Rx = width / Nx;

        //pixel [i, j] center
        Point Pij = Pc;

        //delta values for going to pixel[i,j] from pc.

        double yI = -(i - (Ny - 1) / 2d) * Ry;
        double xJ = (j - (Nx - 1) / 2d) * Rx;

        if (!isZero(xJ)) {
            Pij = Pij.add(vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            Pij = Pij.add(vUp.scale(yI));
        }

        return new Ray(p0, Pij.subtract(p0));
    }
}


