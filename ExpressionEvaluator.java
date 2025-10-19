import java.util.*;

public class ExpressionEvaluator {

    static class Stack {
        private final double[] stack;
        private int top;

        public Stack(int size) {
            stack = new double[size];
            top = -1;
        }

        public void push(double val) {
            stack[++top] = val;
        }

        public double pop() {
            return stack[top--];
        }

        public boolean isEmpty() {
            return top == -1;
        }

        public double peek() {
            return stack[top];
        }
    }

    public static boolean isValidInfix(String exp) {
        exp = exp.replaceAll("\\s+", "");
        if (exp.isEmpty()) return false;
        if ("+-*/".indexOf(exp.charAt(0)) != -1 || "+-*/".indexOf(exp.charAt(exp.length() - 1)) != -1)
            return false;

        final Stack stack = new Stack(exp.length());
        boolean expectOperand = true;

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            if (c == '(') {
                stack.push(c);
                expectOperand = true;
            } else if (c == ')') {
                if (stack.isEmpty()) return false;
                stack.pop();
                expectOperand = false;
            } else if (Character.isDigit(c)) {
                if (!expectOperand) return false;
                expectOperand = false;
            } else if ("+-*/".indexOf(c) != -1) {
                if (expectOperand) return false;
                expectOperand = true;
            } else return false;
        }

        return stack.isEmpty() && !expectOperand;
    }

    static int precedence(char ch) {
        return switch (ch) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> -1;
        };
    }

    static String infixToPostfix(String exp) {
        StringBuilder result = new StringBuilder();
        final Stack stack = new Stack(exp.length());

        for (char c : exp.toCharArray()) {
            if (Character.isDigit(c)) {
                result.append(c);
            } else if (c == '(') {
                stack.push((double) c);
            } else if (c == ')') {
                while (!stack.isEmpty() && (char) stack.peek() != '(') {
                    result.append((char) stack.pop());
                }
                if (!stack.isEmpty()) stack.pop();
            } else if ("+-*/".indexOf(c) != -1) {
                while (!stack.isEmpty() && precedence((char) stack.peek()) >= precedence(c)) {
                    result.append((char) stack.pop());
                }
                stack.push((double) c);
            }
        }

        while (!stack.isEmpty()) {
            result.append((char) stack.pop());
        }
        return result.toString();
    }

    static String infixToPrefix(String exp) {
        StringBuilder rev = new StringBuilder(exp).reverse();
        for (int i = 0; i < rev.length(); i++) {
            if (rev.charAt(i) == '(') rev.setCharAt(i, ')');
            else if (rev.charAt(i) == ')') rev.setCharAt(i, '(');
        }

        String postfix = infixToPostfix(rev.toString());
        return new StringBuilder(postfix).reverse().toString();
    }

    static double evalPostfix(String exp) {
        final Stack stack = new Stack(exp.length());
        for (char c : exp.toCharArray()) {
            if (Character.isDigit(c)) {
                stack.push(c - '0');
            } else {
                double b = stack.pop();
                double a = stack.pop();
                switch (c) {
                    case '+' -> stack.push(a + b);
                    case '-' -> stack.push(a - b);
                    case '*' -> stack.push(a * b);
                    case '/' -> stack.push(a / b);
                }
            }
        }
        return stack.pop();
    }

    static double evalPrefix(String exp) {
        final Stack stack = new Stack(exp.length());
        for (int i = exp.length() - 1; i >= 0; i--) {
            char c = exp.charAt(i);
            if (Character.isDigit(c)) {
                stack.push(c - '0');
            } else {
                double a = stack.pop();
                double b = stack.pop();
                switch (c) {
                    case '+' -> stack.push(a + b);
                    case '-' -> stack.push(a - b);
                    case '*' -> stack.push(a * b);
                    case '/' -> stack.push(a / b);
                }
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) { 
            System.out.println("=== PROGRAM INFIX → POSTFIX → PREFIX & EVALUASI ===\n");

            System.out.print("Masukkan notasi infix: ");
            String infix = sc.nextLine().replaceAll("\\s+", "");

            if (!isValidInfix(infix)) {
                System.out.println("Notasi infix tidak valid!");
                return;
            }

            String postfix = infixToPostfix(infix);
            String prefix = infixToPrefix(infix);

            double postfixResult = evalPostfix(postfix);
            double prefixResult = evalPrefix(prefix);

            System.out.println("\n=== HASIL OPERASI ===");
            System.out.println("Infix   : " + infix);
            System.out.println("Postfix : " + postfix);
            System.out.println("Prefix  : " + prefix);
            System.out.println("------------------------");
            System.out.printf("Hasil Evaluasi Postfix : %.2f%n", postfixResult);
            System.out.printf("Hasil Evaluasi Prefix  : %.2f%n", prefixResult);
        }
    }
}
