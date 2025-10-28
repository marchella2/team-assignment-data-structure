import java.util.*;

// Class Node untuk Binary Search Tree
class WordNode {
    String word;
    int frequency;
    WordNode left, right;

    public WordNode(String word) {
        this.word = word;
        this.frequency = 1;
        this.left = this.right = null;
    }
}

// Class Binary Search Tree untuk Indeks Kata Dokumen
class WordBST {
    private WordNode root;

    // Menambahkan kata ke BST (jika sudah ada, tambah frekuensi)
    public void insert(String word) {
        root = insertRec(root, word.toLowerCase());
    }

    private WordNode insertRec(WordNode root, String word) {
        if (root == null)
            return new WordNode(word);

        int cmp = word.compareTo(root.word);
        if (cmp < 0)
            root.left = insertRec(root.left, word);
        else if (cmp > 0)
            root.right = insertRec(root.right, word);
        else
            root.frequency++;

        return root;
    }

    // Cari kata dalam BST
    public WordNode search(String word) {
        return searchRec(root, word.toLowerCase());
    }

    private WordNode searchRec(WordNode root, String word) {
        if (root == null || root.word.equals(word))
            return root;

        if (word.compareTo(root.word) < 0)
            return searchRec(root.left, word);
        return searchRec(root.right, word);
    }

    // Traversal inorder: tampilkan semua kata dalam urutan abjad
    public void displayInOrder() {
        System.out.println("=== Daftar Kata dalam Dokumen (urut abjad) ===");
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(WordNode root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root.word + " : " + root.frequency + " kali");
            inorderRec(root.right);
        }
    }
}

// Class Node untuk Expression Tree
class ExprNode {
    String value;
    ExprNode left, right;

    public ExprNode(String value) {
        this.value = value;
    }
}

// Class Expression Tree
class ExpressionTree {
    // Membangun tree dari postfix expression
    public ExprNode buildTree(String postfix) {
        Stack<ExprNode> stack = new Stack<>();

        for (String token : postfix.split(" ")) {
            if (!isOperator(token)) {
                stack.push(new ExprNode(token));
            } else {
                ExprNode node = new ExprNode(token);
                node.right = stack.pop();
                node.left = stack.pop();
                stack.push(node);
            }
        }
        return stack.peek();
    }

    // Evaluasi Expression Tree
    public int evaluate(ExprNode root) {
        if (root == null)
            return 0;

        if (!isOperator(root.value))
            return Integer.parseInt(root.value);

        int leftVal = evaluate(root.left);
        int rightVal = evaluate(root.right);

        switch (root.value) {
            case "+": return leftVal + rightVal;
            case "-": return leftVal - rightVal;
            case "*": return leftVal * rightVal;
            case "/": return leftVal / rightVal;
        }
        return 0;
    }

    // Traversal inorder untuk menampilkan bentuk ekspresi
    public void inorder(ExprNode root) {
        if (root != null) {
            if (isOperator(root.value))
                System.out.print("(");
            inorder(root.left);
            System.out.print(root.value);
            inorder(root.right);
            if (isOperator(root.value))
                System.out.print(")");
        }
    }

    private boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
    }
}

// Main Program
public class SystemBST {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        WordBST bst = new WordBST();
        ExpressionTree exprTree = new ExpressionTree();

        while (true) {
            System.out.println("\n=== SISTEM MANAJEMEN DATA BERBASIS TREE ===");
            System.out.println("1. Indeks Pencarian Dokumen (BST)");
            System.out.println("2. Keluar");
            System.out.print("Pilih menu: ");
            int menu = sc.nextInt();
            sc.nextLine();

            switch (menu) {
                case 1:
                    System.out.println("\n--- SISTEM INDEKS DOKUMEN ---");
                    System.out.print("Masukkan teks dokumen: ");
                    String text = sc.nextLine();

                    // Pisahkan teks menjadi kata-kata dan masukkan ke BST
                    String[] words = text.replaceAll("[^a-zA-Z ]", "").split("\\s+");
                    for (String w : words) {
                        if (!w.isEmpty())
                            bst.insert(w);
                    }

                    while (true) {
                        System.out.println("\n1. Tampilkan semua kata (urut abjad)");
                        System.out.println("2. Cari kata");
                        System.out.println("3. Kembali ke menu utama");
                        System.out.print("Pilih: ");
                        int sub = sc.nextInt();
                        sc.nextLine();

                        if (sub == 1) {
                            bst.displayInOrder();
                        } else if (sub == 2) {
                            System.out.print("Masukkan kata yang ingin dicari: ");
                            String word = sc.nextLine();
                            WordNode node = bst.search(word);
                            if (node != null)
                                System.out.println("Kata \"" + word + "\" ditemukan sebanyak " + node.frequency + " kali.");
                            else
                                System.out.println("Kata \"" + word + "\" tidak ditemukan dalam dokumen.");
                        } else break;
                    }
                    break;
                case 2:
                    System.out.println("Program selesai. Terima kasih!");
                    sc.close();
                    return;

                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }
}
