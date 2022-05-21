package primitives;

public class Material {

    public Double3 kD;
    public Double3 kS;
    public Double3 kT;//Transparency
    public Double3 kR;//Reflection
    public int nShininess; //the strong of the shininess

    /**
     * constructor
     */
    public Material() {
        kS = Double3.ZERO;
        kD = Double3.ZERO;
        kR = Double3.ZERO;
        kT = Double3.ZERO;
        nShininess = 0;
    }

    /**
     * set Kd that get Double3
     * @return Kd
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * set Kd that get double
     * @return kd
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }


    /**
     * set Ks that get Double3
     * @return Ks
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * set Ks that get double
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }


    /**
     * set Shininess
     * @return Shininess
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    public Material setkT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    public Material setkR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }
}