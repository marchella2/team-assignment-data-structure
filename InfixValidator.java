import java.util.*;

public class InfixValidator {
  
  // Implementasi Stack menggunakan array
  static class ArrayStack {
    private char[] stack;
    private int top;
    private int capacity;
    
    public ArrayStack(int size) {
      capacity = size;
      stack = new char[capacity];
      top = -1;
    }
    
    public void push(char item) {
      if (top < capacity - 1) {
        stack[++top] = item;
      }
    }
    
    public char pop() {
      if (top >= 0) {
        return stack[top--];
      }
      return '\0';
    }
    
    public boolean isEmpty() {
      return top == -1;
    }
  }
  
  // Implementasi Stack menggunakan linked list
  static class LinkedListStack {
    private Node top;
    
    class Node {
      char data;
      Node next;
      
      Node(char data) {
        this.data = data;
        this.next = null;
      }
    }
    
    public void push(char item) {
      Node newNode = new Node(item);
      newNode.next = top;
      top = newNode;
    }
    
    public char pop() {
      if (top != null) {
        char data = top.data;
        top = top.next;
        return data;
      }
      return '\0';
    }
    
    public boolean isEmpty() {
      return top == null;
    }
  }
  
  // Method untuk mengecek apakah karakter adalah operator
  public static boolean isOperator(char c) {
    return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^';
  }
  
  // Method untuk mengecek apakah karakter adalah operand (angka atau variabel)
  public static boolean isOperand(char c) {
    return Character.isLetterOrDigit(c);
  }
  
  // Method untuk memvalidasi ekspresi infix menggunakan ArrayStack
  public static boolean isValidInfixArray(String expression) {
    if (expression == null || expression.trim().isEmpty()) {
      return false;
    }
    
    expression = expression.replaceAll("\\s+", ""); // Hapus spasi
    ArrayStack parenthesesStack = new ArrayStack(expression.length());
    
    boolean expectOperand = true; // Awalnya kita mengharapkan operand
    
    for (int i = 0; i < expression.length(); i++) {
      char c = expression.charAt(i);
      
      if (c == '(') {
        parenthesesStack.push(c);
        expectOperand = true; // Setelah '(' kita mengharapkan operand
      }
      else if (c == ')') {
        if (parenthesesStack.isEmpty()) {
          return false; // Kurung tutup yang tidak cocok
        }
        parenthesesStack.pop();
        expectOperand = false; // Setelah ')' kita mengharapkan operator
      }
      else if (isOperand(c)) {
        if (!expectOperand) {
          return false; // Dua operand berturut-turut
        }
        expectOperand = false; // Setelah operand kita mengharapkan operator
      }
      else if (isOperator(c)) {
        if (expectOperand) {
          // Tangani unary operator di awal atau setelah '('
          if ((c == '+' || c == '-') && (i == 0 || expression.charAt(i-1) == '(')) {
            expectOperand = true;
            continue;
          }
          return false; // Dua operator berturut-turut
        }
        expectOperand = true; // Setelah operator kita mengharapkan operand
      }
      else {
        return false; // Karakter tidak valid
      }
    }
    
    // Cek apakah kurung seimbang dan ekspresi tidak berakhir dengan operator
    return parenthesesStack.isEmpty() && !expectOperand;
  }
  
  // Method untuk memvalidasi ekspresi infix menggunakan LinkedListStack
  public static boolean isValidInfixLinkedList(String expression) {
    if (expression == null || expression.trim().isEmpty()) {
      return false;
    }
    
    expression = expression.replaceAll("\\s+", ""); // Hapus spasi
    LinkedListStack parenthesesStack = new LinkedListStack();
    
    boolean expectOperand = true; // Awalnya kita mengharapkan operand
    
    for (int i = 0; i < expression.length(); i++) {
      char c = expression.charAt(i);
      
      if (c == '(') {
        parenthesesStack.push(c);
        expectOperand = true; // Setelah '(' kita mengharapkan operand
      }
      else if (c == ')') {
        if (parenthesesStack.isEmpty()) {
          return false; // Kurung tutup yang tidak cocok
        }
        parenthesesStack.pop();
        expectOperand = false; // Setelah ')' kita mengharapkan operator
      }
      else if (isOperand(c)) {
        if (!expectOperand) {
          return false; // Dua operand berturut-turut
        }
        expectOperand = false; // Setelah operand kita mengharapkan operator
      }
      else if (isOperator(c)) {
        if (expectOperand) {
          // Tangani unary operator di awal atau setelah '('
          if ((c == '+' || c == '-') && (i == 0 || expression.charAt(i-1) == '(')) {
            expectOperand = true;
            continue;
          }
          return false; // Dua operator berturut-turut
        }
        expectOperand = true; // Setelah operator kita mengharapkan operand
      }
      else {
        return false; // Karakter tidak valid
      }
    }
    
    // Cek apakah kurung seimbang dan ekspresi tidak berakhir dengan operator
    return parenthesesStack.isEmpty() && !expectOperand;
  }
  
  // Method untuk memvalidasi ekspresi infix (default menggunakan ArrayStack)
  public static boolean isValidInfix(String expression) {
    return isValidInfixArray(expression);
  }
  
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("=== PROGRAM VALIDASI NOTASI INFIX ===");
    System.out.println();
    
    while (true) {
      System.out.print("Masukkan notasi infix (atau 'exit' untuk keluar): ");
      String input = scanner.nextLine();
      
      if (input.equalsIgnoreCase("exit")) {
        System.out.println("Terima kasih!");
        break;
      }
      
      boolean validArray = isValidInfixArray(input);
      boolean validLinkedList = isValidInfixLinkedList(input);
      
      System.out.println("Hasil validasi menggunakan ArrayStack: " + 
                        (validArray ? "VALID" : "TIDAK VALID"));
      System.out.println("Hasil validasi menggunakan LinkedListStack: " + 
                        (validLinkedList ? "VALID" : "TIDAK VALID"));
      
      if (validArray && validLinkedList) {
        System.out.println("'" + input + "' adalah notasi infix yang VALID");
      } else {
        System.out.println("'" + input + "' bukan merupakan notasi infix yang VALID");
      }
      
      System.out.println();
    }
    
    scanner.close();
  }
}
