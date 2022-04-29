package primitives;

public class Material {

    public Double3 kD;
    public Double3 kS;
    public int nShininess;

    public Material() {
        kS = Double3.ZERO;
        kD = Double3.ZERO;
        nShininess = 0;
    }

    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }


    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }


    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }
}