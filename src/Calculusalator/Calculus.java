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

    public String Normalize(String expr){ // I'm familiar with this term with ML inputs, so I guess it applies here too.
        String res = expr.replace(" ", ""); // ignore whitespace
        res = res.toLowerCase();
        return res;
    }


    public void calculate(String expr, int mode) {
        String exprNorm = Normalize(expr);
        Lexer lexer = new Lexer(exprNorm);
        if(mode == DERIVATIVE_MODE){
            lexer.outputDerivative(expr);
        }
        if(mode == INTEGRAL_MODE){
            lexer.outputIntegral(expr);
        }

        System.out.println(exprNorm + " Calculated with mode: " + mode);
    }
}
