import java.util.*;

public class PostfixEvaluator {
    
    // Implementasi Stack untuk evaluasi postfix
    static class Stack {
        private int[] stack;
        private int top;
        private int capacity;
        
        public Stack(int size) {
            capacity = size;
            stack = new int[capacity];
            top = -1;
        }
        
        public void push(int item) {
            if (top < capacity - 1) {
                stack[++top] = item;
            } else {
                System.out.println("Stack overflow!");
            }
        }
        
        public int pop() {
            if (top >= 0) {
                return stack[top--];
            } else {
                System.out.println("Stack underflow!");
                return -1;
            }
        }
        
        public boolean isEmpty() {
            return top == -1;
        }
        
        public int peek() {
            if (top >= 0) {
                return stack[top];
            }
            return -1;
        }
        
        public int size() {
            return top + 1;
        }
    }
    
    // Method untuk mengecek apakah karakter adalah operator
    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^';
    }
    
    // Method untuk mengecek apakah karakter adalah digit
    public static boolean isDigit(char c) {
        return Character.isDigit(c);
    }
    
    // Method untuk mengevaluasi ekspresi postfix
    public static int evaluatePostfix(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Ekspresi tidak boleh kosong");
        }
        
        expression = expression.replaceAll("\\s+", ""); // Hapus spasi
        Stack stack = new Stack(expression.length());
        
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            
            if (isDigit(c)) {
                // Jika karakter adalah digit, push ke stack
                stack.push(c - '0');
            } else if (isOperator(c)) {
                // Jika karakter adalah operator, pop dua operand dan lakukan operasi
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Ekspresi postfix tidak valid: tidak cukup operand");
                }
                
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                int result = performOperation(operand1, operand2, c);
                stack.push(result);
            } else {
                throw new IllegalArgumentException("Karakter tidak valid: " + c);
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Ekspresi postfix tidak valid: hasil akhir tidak tepat");
        }
        
        return stack.pop();
    }
    
    // Method untuk melakukan operasi aritmatika
    private static int performOperation(int operand1, int operand2, char operator) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                if (operand2 == 0) {
                    throw new ArithmeticException("Pembagian dengan nol tidak diperbolehkan");
                }
                return operand1 / operand2;
            case '%':
                if (operand2 == 0) {
                    throw new ArithmeticException("Modulo dengan nol tidak diperbolehkan");
                }
                return operand1 % operand2;
            case '^':
                return (int) Math.pow(operand1, operand2);
            default:
                throw new IllegalArgumentException("Operator tidak didukung: " + operator);
        }
    }
    
    // Method untuk mengkonversi infix ke postfix
    public static String infixToPostfix(String infix) {
        if (infix == null || infix.trim().isEmpty()) {
            throw new IllegalArgumentException("Ekspresi infix tidak boleh kosong");
        }
        
        infix = infix.replaceAll("\\s+", ""); // Hapus spasi
        StringBuilder postfix = new StringBuilder();
        Stack operatorStack = new Stack(infix.length());
        
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            
            if (isDigit(c)) {
                postfix.append(c);
            } else if (c == '(') {
                operatorStack.push(c);
            } else if (c == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfix.append((char) operatorStack.pop());
                }
                if (!operatorStack.isEmpty()) {
                    operatorStack.pop(); // Hapus '('
                }
            } else if (isOperator(c)) {
                while (!operatorStack.isEmpty() && 
                       operatorStack.peek() != '(' && 
                       getPrecedence((char) operatorStack.peek()) >= getPrecedence(c)) {
                    postfix.append((char) operatorStack.pop());
                }
                operatorStack.push(c);
            }
        }
        
        while (!operatorStack.isEmpty()) {
            postfix.append((char) operatorStack.pop());
        }
        
        return postfix.toString();
    }
    
    // Method untuk mendapatkan precedence operator
    private static int getPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            case '^':
                return 3;
            default:
                return 0;
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== PROGRAM EVALUASI NOTASI POSTFIX ===");
        System.out.println();
        
        while (true) {
            System.out.println("Pilih operasi:");
            System.out.println("1. Evaluasi ekspresi postfix");
            System.out.println("2. Konversi infix ke postfix");
            System.out.println("3. Keluar");
            System.out.print("Masukkan pilihan (1-3): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer
            
            switch (choice) {
                case 1:
                    System.out.print("Masukkan ekspresi postfix: ");
                    String postfixExpression = scanner.nextLine();
                    try {
                        int result = evaluatePostfix(postfixExpression);
                        System.out.println("Hasil evaluasi: " + result);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                    
                case 2:
                    System.out.print("Masukkan ekspresi infix: ");
                    String infixExpression = scanner.nextLine();
                    try {
                        String postfix = infixToPostfix(infixExpression);
                        System.out.println("Notasi postfix: " + postfix);
                        
                        // Evaluasi hasil konversi
                        int result = evaluatePostfix(postfix);
                        System.out.println("Hasil evaluasi: " + result);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                    
                case 3:
                    System.out.println("Terima kasih!");
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("Pilihan tidak valid!");
            }
            
            System.out.println();
        }
    }
}
