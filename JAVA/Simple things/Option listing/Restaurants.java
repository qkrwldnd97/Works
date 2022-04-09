
import java.util.Scanner;
public class Restaurants {
    // On campus
    public static final String ON_CAMPUS_VEGAN = "Purdue Dining Courts\nFlatbreads";
    public static final String ON_CAMPUS_VEGETARIAN = ON_CAMPUS_VEGAN + "\nOasis Cafe\nAh*Z\nFreshens";
    public static final String ON_CAMPUS_GLUTEN_FREE = "Purdue Dining Courts\nFlatbreads\nOasis Cafe\nPappy's " +
            "Sweet Shop";
    public static final String ON_CAMPUS_BURGERS = "Pappy's Sweet Shop\nCary Knight Spot";
    public static final String ON_CAMPUS_SANDWICHES = "Flatbreads\nOasis Cafe\nErbert & Gerbert's";
    public static final String ON_CAMPUS_OTHERS = "Purdue Dining Courts\nAh*Z\nFreshens\nLemongrass";
    public static final String ON_CAMPUS_ALL = ON_CAMPUS_BURGERS + "\n" + ON_CAMPUS_SANDWICHES + "\n" +
            ON_CAMPUS_OTHERS;

    // Off campus
    public static final String OFF_CAMPUS_VEGAN = "Chipotle\nQdoba\nNine Irish Brothers\nFive Guys\n Puccini's " +
            "Smiling Teeth\nPanera Bread";
    public static final String OFF_CAMPUS_VEGETARIAN = OFF_CAMPUS_VEGAN + "\nWendy's\nBruno's Pizza\nJimmy " +
            "John's\nPotbelly Sandwich Shop\nBasil Thai\nIndia Mahal";
    public static final String OFF_CAMPUS_GLUTEN_FREE = "Chipotle\nQdoba\nNine Irish Brothers\nPuccini's Smiling " +
            "Teeth\nWendy's\nScotty's Brewhouse\nPanera Bread\nBasil Thai";
    public static final String OFF_CAMPUS_BURGERS = "Five Guys\nWendy's\nTriple XXX\nScotty's Brewhouse";
    public static final String OFF_CAMPUS_SANDWICHES ="Panera Bread\nJimmy John's\nPotbelly Sandwich Shop";
    public static final String OFF_CAMPUS_PIZZAS = "Puccini's Smiling Teeth\nMad Mushroom Pizza\nBruno's Pizza\n";
    public static final String OFF_CAMPUS_OTHERS = "Chipotle\nQdoba\nNine Irish Brothers\nFamous Frank's\n Von's " +
            "Dough Shack\nBuffalo Wild Wings\nBasil Thai\nMaru Sushi\nIndia Mahal\nHappy China\nYori";
    public static final String offCampusAll = OFF_CAMPUS_BURGERS + "\n" + OFF_CAMPUS_SANDWICHES + "\n" +
            OFF_CAMPUS_PIZZAS + OFF_CAMPUS_OTHERS;

    public static void main(String[] args){
        Scanner myScanner=new Scanner(System.in);
        System.out.println("On Campus=1, Off Campus=2");
        int campus= myScanner.nextInt();
        if(campus==1){
            System.out.println("Dietary restrictions? (Y/N)");
            char dietary=myScanner.next().charAt(0);

            if(dietary=='Y'){
                System.out.println("1-Vegan, 2-Vegetarian, 3-Gluten-free");
                int vegan=myScanner.nextInt();
                if(vegan==1){
                    System.out.println(ON_CAMPUS_VEGAN);
                }
                else if(vegan==2){
                    System.out.println(ON_CAMPUS_VEGETARIAN);
                }
            }
            else if(dietary=='N'){
                System.out.println("Food preference (Y/N)");
                char preference= myScanner.next().charAt(0);
                if(preference=='Y'){
                    System.out.println("1-Burgers, 2- Sandwiches, 3-Others");
                    int burgers=myScanner.nextInt();
                    if(burgers==1){
                        System.out.println(ON_CAMPUS_BURGERS);
                    }
                    else if(burgers==2){
                        System.out.println(ON_CAMPUS_SANDWICHES);
                    }
                    else if(burgers==3){
                        System.out.println(ON_CAMPUS_OTHERS);
                    }
                }
                else if(preference=='N'){
                    System.out.println(ON_CAMPUS_ALL);
                }
            }
        }
        else if(campus==2){
            System.out.println("Dietary restrictions? (Y/N)");
            char diet=myScanner.next().charAt(0);
            if(diet=='Y'){
                System.out.println("1-Vegan, 2-Vegetarian, 3-Gluten-free");
                int vegetarian=myScanner.nextInt();
                if(vegetarian==1){
                    System.out.println(OFF_CAMPUS_VEGAN);
                }
                else if(vegetarian==2){
                    System.out.println(OFF_CAMPUS_VEGETARIAN);
                }
                else if(vegetarian==3){
                    System.out.println(OFF_CAMPUS_GLUTEN_FREE);
                }
            }
            else if(diet=='N'){
                System.out.println("Food preference? (Y/N)");
                char food=myScanner.next().charAt(0);
                if(food=='Y'){
                    System.out.println("1-Burgers, 2-Sandwiches, 3-Pizzas, 4-Others");
                    int pizzas=myScanner.nextInt();
                    if(pizzas==1){
                        System.out.println(OFF_CAMPUS_BURGERS);
                    }
                    else if(pizzas==2){
                        System.out.println(OFF_CAMPUS_SANDWICHES);
                    }
                    else if(pizzas==3){
                        System.out.println(OFF_CAMPUS_PIZZAS);
                    }
                    else if(pizzas==4){
                        System.out.println(OFF_CAMPUS_OTHERS);
                    }
                }
                else if(food=='N'){
                    System.out.println(offCampusAll);
                }
            }
        }

    }
}