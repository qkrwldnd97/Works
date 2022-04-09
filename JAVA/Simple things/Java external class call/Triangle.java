
import java.lang.Math;
public class Triangle {
    private double tri;
    public Triangle(double tri){
        this.tri=tri;
    }
    public double getPerimeter(){
        return tri*3;
    }
    public double getArea(){
        return (Math.sqrt(3)/4)*tri;
    }
}
