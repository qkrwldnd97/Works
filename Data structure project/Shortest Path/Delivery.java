import java.util.*;


public class Delivery {
    private DijGraph westLafayette;//The graph
    private Node restaurant;//The vertex that the driver start
    private Node[] customer;//The vertices that the driver need to pass through
    private double slope;//Tip percentage function slope
    private double intercept;//Tip percentage function intercept
    private double [] order;//The order amount from each customer
    public Delivery (DijGraph graph,Node restaurant, Node[] customer, double slope, double intercept, double[] order){
        this.westLafayette = graph;
        this.restaurant = restaurant;
        this.customer = customer;
        this.slope = slope;
        this.intercept  = intercept;
        this.order = order;
    }

    //Finding the best path that the driver can earn most tips
    //Each time the driver only picks up three orders
    //Picking up N orders and find the maximum tips will be NP-hard
    public double bestPath(){
        double max = 0.0;
        double result =0.0;
        double tip = 0.0;
        double distance =0.0;
        Dist[][] cst = { westLafayette.dijkstra(restaurant.getNodeNumber()),westLafayette.dijkstra(customer[0].getNodeNumber()), westLafayette.dijkstra(customer[1].getNodeNumber()), westLafayette.dijkstra(customer[2].getNodeNumber())};

                        distance += cst[0][customer[0].getNodeNumber()].getDist();
                        tip = slope*distance+intercept;
                        result += tip/100.0 * order[0];
                        distance += cst[1][customer[1].getNodeNumber()].getDist();
                        tip = slope*distance+intercept;
                        result += tip/100.0 * order[1];
                        distance  += cst[2][customer[2].getNodeNumber()].getDist();
                        tip = slope*distance+intercept;
                        result += tip/100.0 * order[2];
                        if(Double.compare(result, max) > 0){
                            max = result;
                        }
                        distance =0.0;
                        result =0.0;

                        distance += cst[0][customer[0].getNodeNumber()].getDist();
                        tip = slope*distance+intercept;
                        result += tip/100.0 * order[0];
                        distance += cst[1][customer[2].getNodeNumber()].getDist();
                        tip = slope*distance+intercept;
                        result += tip/100.0 * order[2];
                        distance += cst[3][customer[1].getNodeNumber()].getDist();
                        tip = slope*distance+intercept;
                        result += tip/100.0 * order[1];
                        if(Double.compare(result, max) > 0){
                            max = result;
                        }
                        distance = 0.0;
                        result = 0.0;

                        distance += cst[0][customer[1].getNodeNumber()].getDist();
                        tip = slope*distance+intercept;
                        result += tip/100.0 * order[1];
                        distance += cst[2][customer[0].getNodeNumber()].getDist();
                        tip = slope*distance+intercept;
                        result += tip/100.0 * order[0];
                        distance += cst[2][customer[2].getNodeNumber()].getDist();
                        tip= slope*distance+intercept;
                        result += tip/100.0 * order[2];
                        if(Double.compare(result, max) > 0){
                            max = result;
                        }
                        distance = 0.0;
                        result = 0.0;

                        distance += cst[0][customer[1].getNodeNumber()].getDist();
                        tip = slope*distance+intercept;
                        result += tip/100.0 * order[1];
                        distance += cst[2][customer[2].getNodeNumber()].getDist();
                        tip = slope*distance+intercept;
                        result += tip/100.0 * order[2];
                        distance += cst[3][customer[0].getNodeNumber()].getDist();
                        tip = slope*distance+intercept;
                        result += tip/100.0 * order[0];
                        if(Double.compare(result, max) > 0){
                            max = result;
                        }
                        distance = 0.0;
                        result = 0.0;

                        distance += cst[0][customer[2].getNodeNumber()].getDist();
                        tip = slope * distance + intercept;
                        result += tip/100.0 * order[2];
                        distance += cst[3][customer[0].getNodeNumber()].getDist();
                        tip = slope * distance + intercept;
                        result += tip/100.0 * order[0];
                        distance += cst[1][customer[1].getNodeNumber()].getDist();
                        tip = slope * distance + intercept;
                        result += tip/100.0 * order[1];
                        if(Double.compare(result, max) > 0){
                            max = result;
                        }

                        distance = 0.0;
                        result = 0.0;
                        distance += cst[0][customer[2].getNodeNumber()].getDist();
                        tip = slope * distance + intercept;
                        result += tip/100.0 * order[2];
                        distance += cst[3][customer[1].getNodeNumber()].getDist();
                        tip = slope * distance + intercept;
                        result += tip/100.0 * order[1];
                        distance += cst[2][customer[0].getNodeNumber()].getDist();
                        tip = slope * distance + intercept;
                        result += tip/100.0 * order[0];
                        if(Double.compare(result, max) > 0){
                            max = result;
                        }

        return max;
    }

}
