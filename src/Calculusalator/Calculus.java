package Calculusalator;

public class Calculus {
    private static final int DERIVATIVE_MODE = 0; // arbitrary values.
    private static final int INTEGRAL_MODE = 1;
    private static int mode;

    public void setMode(String mode) {
        if (mode.equalsIgnoreCase("Derivative")) {
            Calculus.mode = DERIVATIVE_MODE;
            System.out.println("Set to derivative mode.");
        } else if (mode.equalsIgnoreCase("Integral")) {
            Calculus.mode = INTEGRAL_MODE;
            System.out.println("Set to integral mode.");
        } else {
            System.out.println("Invalid mode.");
        }
    }
    public int getMode(){
        return mode;
    }

    public void calculate(String expr, int mode) {
        System.out.println(expr + " Calculated with mode: " + mode);
    }
}
