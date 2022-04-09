
import java.lang.Math;
public class Circle {
    private double radius;
    public Circle(double radius){
        this.radius= radius;
    }
    public double getCircumference(){
        return radius*2*Math.PI;
    }
    public double getArea(){
        return Math.pow(radius,2)*Math.PI;
    }

}
