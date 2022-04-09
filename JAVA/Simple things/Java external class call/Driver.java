import java.util.Scanner;


public class Driver {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Circle's radius: ");
        double radius=myScanner.nextDouble();
        System.out.println("the length of the side of the Triangle: ");
        double tri=myScanner.nextDouble();
        System.out.println("length of Rectangle: ");
        double length=myScanner.nextDouble();
        System.out.println("width of Rectangle: ");
        double width=myScanner.nextDouble();

        Circle c=new Circle(radius);
        Triangle t=new Triangle(tri);
        Rectangle r= new Rectangle(width,length);

        System.out.println("Area of Circle: "+c.getArea()+"\n"+
                            "Circumference of Circle: "+c.getCircumference()+"\n"+
                            "Area of Triangle: "+ t.getArea()+"\n"+
                            "Perimeter of Triangle: "+ t.getPerimeter()+"\n"+
                            "Perimeter of Rectangle: "+r.getPerimeter()+"\n"+
                            "Area of Rectangle: "+r.getArea());
    }
}
