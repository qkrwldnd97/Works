import java.io.File;
import java.io.IOException;
import java.util.*;

//for testing
public class DeliverySystem {

    public static void main(String[] args)throws IOException {
        DijGraph westLafayetteMap = new DijGraph("./src/localmap.txt", 1);
        File diliveryFile = new File("./src/localdelivery.txt");
        Scanner sc = new Scanner(diliveryFile);
        while(sc.hasNextLine()){
            String restaurantName = sc.next();
            Node restaurant = westLafayetteMap.findByName(restaurantName);
            double slope = sc.nextDouble();
            double intercept = sc.nextDouble();
            Node[] customer = new Node[3];
            double[] order = new double[3];
            for(int x =0; x <3; x++){
                customer[x] = westLafayetteMap.findByName(sc.next());
                order[x] = sc.nextDouble();
            }
            Delivery driver = new Delivery(westLafayetteMap,restaurant,customer,slope,intercept,order);
            System.out.println("The Order from " + restaurantName + ": "+ String.format("%.2f", driver.bestPath()));
        }

    }

}
