package Calculusalator;



public class ASTNode {
    Token value;
    ASTNode left;
    ASTNode right;

    public ASTNode(Token value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}

