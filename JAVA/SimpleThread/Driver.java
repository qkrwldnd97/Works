public class Driver{
    public static void main(String[] args) {

        Thread T = new A();
        T.start();


        Runnable r = new B();
        Thread TT = new Thread(r);
        TT.start();


    }
}