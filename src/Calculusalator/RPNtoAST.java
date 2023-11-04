package Calculusalator;

import java.util.Queue;
import java.util.Stack;

import static Calculusalator.Token_type.*;
public class RPNtoAST {
    public static ASTNode buildASTFromRPN(Queue<Token> rpnTokens) {
        Stack<ASTNode> stack = new Stack<>();

        for (Token token : rpnTokens) {
            if (token.type == NUMBER || token.type == VARIABLE) {
                stack.push(new ASTNode(token));
            } else if (token.type.isOperator()) {
                ASTNode right = stack.pop();
                ASTNode left = stack.pop();
                ASTNode node = new ASTNode(token);
                node.left = left;
                node.right = right;
                stack.push(node);
            }
        }

        return stack.pop();
    }

    public static void printTree(ASTNode node, String indent) {
        if (node != null) {
            System.out.println(indent + node.value);
            printTree(node.left, indent + "  ");
            printTree(node.right, indent + "  ");
        }
    }

}
