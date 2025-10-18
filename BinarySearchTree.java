import java.util.*;

public class BinarySearchTree {
    
    // Node class untuk BST
    static class Node {
        int data;
        Node left, right;
        
        public Node(int data) {
            this.data = data;
            this.left = this.right = null;
        }
    }
    
    private Node root;
    
    public BinarySearchTree() {
        this.root = null;
    }
    
    // Method untuk menambahkan node baru
    public void insert(int data) {
        root = insertRecursive(root, data);
    }
    
    private Node insertRecursive(Node root, int data) {
        // Jika tree kosong, buat node baru sebagai root
        if (root == null) {
            root = new Node(data);
            return root;
        }
        
        // Jika data lebih kecil dari root, masukkan ke subtree kiri
        if (data < root.data) {
            root.left = insertRecursive(root.left, data);
        }
        // Jika data lebih besar dari root, masukkan ke subtree kanan
        else if (data > root.data) {
            root.right = insertRecursive(root.right, data);
        }
        // Jika data sama, tidak masukkan (BST tidak mengizinkan duplikasi)
        
        return root;
    }
    
    // Method untuk mencari data dalam BST
    public boolean search(int data) {
        return searchRecursive(root, data);
    }
    
    private boolean searchRecursive(Node root, int data) {
        // Base case: jika root null atau data ditemukan
        if (root == null) {
            return false;
        }
        
        if (root.data == data) {
            return true;
        }
        
        // Jika data lebih kecil dari root, cari di subtree kiri
        if (data < root.data) {
            return searchRecursive(root.left, data);
        }
        // Jika data lebih besar dari root, cari di subtree kanan
        else {
            return searchRecursive(root.right, data);
        }
    }
    
    // Method untuk menghapus node
    public void delete(int data) {
        root = deleteRecursive(root, data);
    }
    
    private Node deleteRecursive(Node root, int data) {
        // Base case: jika tree kosong
        if (root == null) {
            return root;
        }
        
        // Jika data lebih kecil dari root, hapus dari subtree kiri
        if (data < root.data) {
            root.left = deleteRecursive(root.left, data);
        }
        // Jika data lebih besar dari root, hapus dari subtree kanan
        else if (data > root.data) {
            root.right = deleteRecursive(root.right, data);
        }
        // Jika data sama dengan root, ini adalah node yang akan dihapus
        else {
            // Node dengan satu child atau tidak ada child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            
            // Node dengan dua children: dapatkan inorder successor (node terkecil di subtree kanan)
            root.data = minValue(root.right);
            
            // Hapus inorder successor
            root.right = deleteRecursive(root.right, root.data);
        }
        
        return root;
    }
    
    // Method untuk mendapatkan nilai minimum dalam subtree
    private int minValue(Node root) {
        int minValue = root.data;
        while (root.left != null) {
            minValue = root.left.data;
            root = root.left;
        }
        return minValue;
    }
    
    // Method untuk mendapatkan nilai maksimum dalam subtree
    private int maxValue(Node root) {
        int maxValue = root.data;
        while (root.right != null) {
            maxValue = root.right.data;
            root = root.right;
        }
        return maxValue;
    }
    
    // Method untuk traversal inorder (Left, Root, Right)
    public void inorderTraversal() {
        System.out.print("Inorder Traversal: ");
        inorderRecursive(root);
        System.out.println();
    }
    
    private void inorderRecursive(Node root) {
        if (root != null) {
            inorderRecursive(root.left);
            System.out.print(root.data + " ");
            inorderRecursive(root.right);
        }
    }
    
    // Method untuk traversal preorder (Root, Left, Right)
    public void preorderTraversal() {
        System.out.print("Preorder Traversal: ");
        preorderRecursive(root);
        System.out.println();
    }
    
    private void preorderRecursive(Node root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preorderRecursive(root.left);
            preorderRecursive(root.right);
        }
    }
    
    // Method untuk traversal postorder (Left, Right, Root)
    public void postorderTraversal() {
        System.out.print("Postorder Traversal: ");
        postorderRecursive(root);
        System.out.println();
    }
    
    private void postorderRecursive(Node root) {
        if (root != null) {
            postorderRecursive(root.left);
            postorderRecursive(root.right);
            System.out.print(root.data + " ");
        }
    }
    
    // Method untuk mendapatkan tinggi tree
    public int getHeight() {
        return getHeightRecursive(root);
    }
    
    private int getHeightRecursive(Node root) {
        if (root == null) {
            return 0;
        }
        
        int leftHeight = getHeightRecursive(root.left);
        int rightHeight = getHeightRecursive(root.right);
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    // Method untuk menghitung jumlah node
    public int countNodes() {
        return countNodesRecursive(root);
    }
    
    private int countNodesRecursive(Node root) {
        if (root == null) {
            return 0;
        }
        
        return 1 + countNodesRecursive(root.left) + countNodesRecursive(root.right);
    }
    
    // Method untuk mengecek apakah tree kosong
    public boolean isEmpty() {
        return root == null;
    }
    
    // Method untuk mendapatkan nilai minimum dalam tree
    public int getMin() {
        if (root == null) {
            throw new RuntimeException("Tree kosong");
        }
        return minValue(root);
    }
    
    // Method untuk mendapatkan nilai maksimum dalam tree
    public int getMax() {
        if (root == null) {
            throw new RuntimeException("Tree kosong");
        }
        return maxValue(root);
    }
    
    // Method untuk menampilkan tree dalam bentuk visual sederhana
    public void displayTree() {
        if (root == null) {
            System.out.println("Tree kosong");
            return;
        }
        
        System.out.println("Struktur Tree:");
        displayTreeRecursive(root, 0);
    }
    
    private void displayTreeRecursive(Node root, int level) {
        if (root != null) {
            displayTreeRecursive(root.right, level + 1);
            
            for (int i = 0; i < level; i++) {
                System.out.print("    ");
            }
            System.out.println(root.data);
            
            displayTreeRecursive(root.left, level + 1);
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BinarySearchTree bst = new BinarySearchTree();
        
        System.out.println("=== PROGRAM BINARY SEARCH TREE ===");
        System.out.println();
        
        while (true) {
            System.out.println("Pilih operasi:");
            System.out.println("1. Tambah data");
            System.out.println("2. Cari data");
            System.out.println("3. Hapus data");
            System.out.println("4. Tampilkan traversal inorder");
            System.out.println("5. Tampilkan traversal preorder");
            System.out.println("6. Tampilkan traversal postorder");
            System.out.println("7. Tampilkan tinggi tree");
            System.out.println("8. Tampilkan jumlah node");
            System.out.println("9. Tampilkan nilai minimum");
            System.out.println("10. Tampilkan nilai maksimum");
            System.out.println("11. Tampilkan struktur tree");
            System.out.println("12. Keluar");
            System.out.print("Masukkan pilihan (1-12): ");
            
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.print("Masukkan data yang akan ditambahkan: ");
                    int insertData = scanner.nextInt();
                    bst.insert(insertData);
                    System.out.println("Data " + insertData + " berhasil ditambahkan");
                    break;
                    
                case 2:
                    System.out.print("Masukkan data yang akan dicari: ");
                    int searchData = scanner.nextInt();
                    if (bst.search(searchData)) {
                        System.out.println("Data " + searchData + " ditemukan dalam tree");
                    } else {
                        System.out.println("Data " + searchData + " tidak ditemukan dalam tree");
                    }
                    break;
                    
                case 3:
                    System.out.print("Masukkan data yang akan dihapus: ");
                    int deleteData = scanner.nextInt();
                    if (bst.search(deleteData)) {
                        bst.delete(deleteData);
                        System.out.println("Data " + deleteData + " berhasil dihapus");
                    } else {
                        System.out.println("Data " + deleteData + " tidak ditemukan dalam tree");
                    }
                    break;
                    
                case 4:
                    if (bst.isEmpty()) {
                        System.out.println("Tree kosong");
                    } else {
                        bst.inorderTraversal();
                    }
                    break;
                    
                case 5:
                    if (bst.isEmpty()) {
                        System.out.println("Tree kosong");
                    } else {
                        bst.preorderTraversal();
                    }
                    break;
                    
                case 6:
                    if (bst.isEmpty()) {
                        System.out.println("Tree kosong");
                    } else {
                        bst.postorderTraversal();
                    }
                    break;
                    
                case 7:
                    System.out.println("Tinggi tree: " + bst.getHeight());
                    break;
                    
                case 8:
                    System.out.println("Jumlah node: " + bst.countNodes());
                    break;
                    
                case 9:
                    try {
                        System.out.println("Nilai minimum: " + bst.getMin());
                    } catch (RuntimeException e) {
                        System.out.println("Tree kosong");
                    }
                    break;
                    
                case 10:
                    try {
                        System.out.println("Nilai maksimum: " + bst.getMax());
                    } catch (RuntimeException e) {
                        System.out.println("Tree kosong");
                    }
                    break;
                    
                case 11:
                    bst.displayTree();
                    break;
                    
                case 12:
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
