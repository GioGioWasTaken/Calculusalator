package Calculusalator;// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Derivative or integral?\nEnter D/I.");
        String mode = scanner.next();
        Calculus calculus = new Calculus();
        if(mode.equalsIgnoreCase("D")) {
            calculus.setMode("Derivative");
            System.out.println("Please enter your expression: ");
            String expr=scanner.next();
            calculus.calculate(expr,calculus.getMode());
        }
        else if(mode.equalsIgnoreCase("I")){
            calculus.setMode("Integral");
            System.out.println("Please enter your expression: ");
            String expr=scanner.next();
            calculus.calculate(expr, calculus.getMode()); // for now always assume we will be given a variable
        }
        else{
            System.out.println('"'+mode+'"'+ " is not a valid input.");
        }
    }
}