package a4;

import java.util.ArrayDeque;

/** 
 * Class to interpret and compute the result of arithmetic expressions 
 * in POSTFIX format - 
 */
public class Postfix {

    public static Double postfix(ArrayDeque<Object> tokens) {
        ArrayDeque<Double> stack = new ArrayDeque<>();
    
        for (Object token : tokens) {
            if (token instanceof Double) {
                stack.push((Double) token);
            } else if (token instanceof Character) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid postfix expression");
                }
                double b = stack.pop();
                double a = stack.pop();
                switch ((Character) token) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/':
                        if (b == 0) throw new ArithmeticException("Division by zero");
                        stack.push(a / b);
                        break;
                    case '^': stack.push(Math.pow(a, b)); break;
                    default: throw new IllegalArgumentException("Unknown operator: " + token);
                }
            }
        }
    
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid postfix expression: extra operands remain");
        }
    
        return stack.pop();
    }
    
}
