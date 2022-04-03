package renderer;

import static primitives.Util.isZero;

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

        double Ry = _height / Ny;
        double Rx = _width / Nx;

        //pixel [i, j] center
        Point Pij = Pc;

        //delta values for going to pixel[i,j] from pc.

        double yI = -(i - (Ny - 1) / 2d) * Ry;
        double xJ = (j - (Nx - 1) / 2d) * Rx;

        if (!isZero(xJ)) {
            Pij = Pij.add(_vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            Pij = Pij.add(_vUp.scale(yI));
        }

        return new Ray(_p0, Pij.subtract(_p0));
    }
}


