
public class Rectangle {
    private double width, length;
    public Rectangle(double width, double length){
        this.width=width;
        this.length=length;
    }
    public double getPerimeter(){
        return 2*(width+length);
    }
    public double getArea(){
        return width*length;
    }
}
