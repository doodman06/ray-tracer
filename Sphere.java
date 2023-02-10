public class Sphere {
    
    public Vector center;
    public double radius;
    public Vector colour;

    public Sphere(double x, double y, double z, double r, Vector c){
        center = new Vector(x ,y, z);
        radius = r;
        colour = c;
    }

    public double intersectionPoint(Vector o, Vector d){
        Vector v = o.sub(center);
        double a = d.dot(d);
        double b = v.dot(d) * 2;
        double c = v.dot(v) - (radius * radius);
        double disc = b*b - 4*a*c;
        return (-b - Math.sqrt(disc))/(2*a);
    }
 
    public Vector getAmbientColour() {
        return new Vector(colour.x / 10, colour.y / 10, colour.z / 10);
    }


}
