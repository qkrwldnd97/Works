import java.util.Scanner;
import java.lang.Math;

public class ExpressionEvaluator{
    public static void main(String[] args){
	Scanner myScanner= new Scanner(System.in);
	String input;
	input= myScanner.nextLine();
	int start=input.indexOf('-');
	int end=input.indexOf('+');
	String a=input.substring(start+1,end);
	int aa= Integer.parseInt(a);


	String exp=input.substring(end);
	int start2=exp.indexOf('(');
	int end2=exp.indexOf('*');
	String b=exp.substring(start2+1,end2);
	int ab= Integer.parseInt(b);


	String exp2=exp.substring(end2);
	int start3=exp2.indexOf('*');
	int end3=exp2.indexOf('-');
	String c=exp2.substring(start3+1,end3);
	int ac= Integer.parseInt(c);

	String exp3=exp2.substring(end3);
	int start4=exp3.indexOf('*');
	int end4=exp3.indexOf('*');
	String d=exp3.substring(start4+1,end4);
	int ad= Integer.parseInt(d);

	String exp4=exp3.substring(end4);
	int start5=exp4.indexOf('*');
	int end5=exp4.indexOf(')');
	String e=exp4.substring((start5)+1,end5);
	int ae= Integer.parseInt(e);

	String exp5=exp4.substring(end5);
	int start6=exp5.indexOf('*');
	int end6=exp5.indexOf(')');
	String f=exp5.substring((start6)+1,end6);
	int af= Integer.parseInt(f);

	System.out.println((-aa+Math.sqrt(ab*ac-4*ad*ae))/(2*af));
	
    }
}
