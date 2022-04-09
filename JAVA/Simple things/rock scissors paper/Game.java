import java.util.Scanner;
import java.util.Random;
public class Game {
    private final static int ROCK=1;
    private final static int PAPER=2;
    private final static int SCISSORS=3;
    private final static int EXIT=4;

    //game driver
    public void runGame(){
        boolean done;
        done=false;
        Scanner myScanner= new Scanner(System.in);
        while(!done){
            System.out.println("Welcome\nPlease enter an option:\n1.Rock\n2.Paper\n3.Scissors\n4.Exit");
            int num=myScanner.nextInt();
            Game game=new Game();
            int computer=game.simulateComputerMove();
            int check=game.checkWinner(num, computer);
            if(num==4){
                done=true;
            }
            else if(num==computer){
                if(num==1){
                    System.out.println("You played Rock !");
                    System.out.println("Computer played Rock !");
                }
                else if(num==2){
                    System.out.println("You played Paper !");
                    System.out.println("Computer played Paper !");
                }
                else{
                    System.out.println("You played Scissors !");
                    System.out.println("Computer played Scissors !");
                }
                System.out.println("Draw!");
            }
            else if(computer==check){
                if(num==1){
                    System.out.println("You played Rock !");
                    System.out.println("Computer played Paper !");
                }
                else if(num==3){
                    System.out.println("You played Scissors !");
                    System.out.println("Computer played Rock !");
                }
                else{
                    System.out.println("You played Paper !");
                    System.out.println("Computer played Scissors !");
                }
                System.out.println("You Lose!");
            }
            else if(check==num){
                if(num==1){
                    System.out.println("You played Rock !");
                    System.out.println("Computer played Scissors !");
                }
                else if(num==3){
                    System.out.println("You played Scissors !");
                    System.out.println("Computer played Paper !");
                }
                else{
                    System.out.println("You played Paper !");
                    System.out.println("Computer played Rock !");
                }
                System.out.println("You Win!");
            }

            else{
                System.out.println("Out of boundary!");
            }
        }

    }

    //checking winner
    private int checkWinner(int move1, int move2){
        if(move1==ROCK && move2==PAPER){
            return move2;
        }
        else if(move1==PAPER && move2==SCISSORS){
            return move2;
        }
        else if(move1==SCISSORS && move2==ROCK){
            return move2;
        }
        else if(move2==ROCK && move1==PAPER){
            return move1;
        }
        else if(move2==PAPER && move1==SCISSORS){
            return move1;
        }
        else if(move2==SCISSORS && move1==ROCK){
            return move1;
        }
        else if(move2==move1){
            return move2=move1;
        }
        else{
            return EXIT;
        }


    }

    //generates random number
    private int simulateComputerMove(){
        Random rand=new Random();
        int a= rand.nextInt(3)+1;
        return a;
    }

    //start the game
    public static void main(String args[]){
        Game game=new Game();
        game.runGame();

    }
}



