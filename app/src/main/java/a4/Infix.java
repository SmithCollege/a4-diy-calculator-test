package a4;

import java.util.*;

public class Infix {

    public static Double infixToPostfix(ArrayDeque<Object> tokens) {
        ArrayDeque<Object> outputQueue = new ArrayDeque<>();
        Deque<Character> operatorStack = new ArrayDeque<>();

        // Operator precedence and associativity
        Map<Character, Integer> precedence = new HashMap<>();
        precedence.put('+', 1);
        precedence.put('-', 1);
        precedence.put('*', 2);
        precedence.put('/', 2);
        precedence.put('^', 3); // Exponentiation has the highest precedence

        Map<Character, Boolean> rightAssociative = new HashMap<>();
        rightAssociative.put('^', true); // Only exponentiation is right-associative

        for (Object token : tokens) {
            if (token instanceof Double) {
                outputQueue.add(token); // Numbers go directly to output
            } else if (token instanceof Character) {
                Character operator = (Character) token;

                if (operator.equals('(')) {
                    operatorStack.push(operator);
                } else if (operator.equals(')')) {
                    // Pop operators until '(' is found
                    while (!operatorStack.isEmpty() && !operatorStack.peek().equals('(')) {
                        outputQueue.add(operatorStack.pop());
                    }
                    if (!operatorStack.isEmpty() && operatorStack.peek().equals('(')) {
                        operatorStack.pop(); // Remove '(' from the stack
                    }
                } else { // Operator case
                    while (!operatorStack.isEmpty() && !operatorStack.peek().equals('(')) {
                        Character topOp = operatorStack.peek();

                        if ((rightAssociative.getOrDefault(operator, false) && precedence.get(operator) < precedence.get(topOp)) ||
                            (!rightAssociative.getOrDefault(operator, false) && precedence.get(operator) <= precedence.get(topOp))) {
                            outputQueue.add(operatorStack.pop());
                        } else {
                            break;
                        }
                    }
                    operatorStack.push(operator);
                }
            } else {
                throw new IllegalArgumentException("Invalid token type: " + token);
            }
        }

        // Pop remaining operators
        while (!operatorStack.isEmpty()) {
            outputQueue.add(operatorStack.pop());
        }

        return Postfix.postfix(outputQueue);
    }

    public static ArrayDeque<Object> parseExpression(String expression) {
        ArrayDeque<Object> tokens = new ArrayDeque<>();
        Scanner scanner = new Scanner(expression);
        while (scanner.hasNext()) {
            if (scanner.hasNextDouble()) {
                tokens.add(scanner.nextDouble());
            } else {
                tokens.add(scanner.next());
            }
        }
        return tokens;
    }

}


