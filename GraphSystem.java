import java.util.*;

// Class untuk menyimpan data vertex dengan informasi tambahan
class Vertex {
    private String label;
    private Map<String, Integer> neighbors; // Map<neighbor, weight>
    
    public Vertex(String label) {
        this.label = label;
        this.neighbors = new HashMap<>();
    }
    
    public String getLabel() {
        return label;
    }
    
    public Map<String, Integer> getNeighbors() {
        return neighbors;
    }
    
    public void addNeighbor(String neighbor, int weight) {
        neighbors.put(neighbor, weight);
    }
    
    public void removeNeighbor(String neighbor) {
        neighbors.remove(neighbor);
    }
    
    public boolean hasNeighbor(String neighbor) {
        return neighbors.containsKey(neighbor);
    }
    
    public int getDegree() {
        return neighbors.size();
    }
}

// Class untuk Graph menggunakan Adjacency List
class Graph {
    private Map<String, Vertex> vertices;
    private boolean directed;
    private int edgeCount;
    
    public Graph(boolean directed) {
        this.vertices = new HashMap<>();
        this.directed = directed;
        this.edgeCount = 0;
    }
    
    // Menambahkan vertex baru
    public boolean addVertex(String label) {
        if (vertices.containsKey(label)) {
            System.out.println("Vertex dengan label '" + label + "' sudah ada!");
            return false;
        }
        
        vertices.put(label, new Vertex(label));
        System.out.println("Vertex '" + label + "' berhasil ditambahkan!");
        return true;
    }
    
    // Menambahkan edge (menghubungkan dua vertex)
    public boolean addEdge(String from, String to, int weight) {
        if (!vertices.containsKey(from)) {
            System.out.println("Vertex '" + from + "' tidak ditemukan!");
            return false;
        }
        
        if (!vertices.containsKey(to)) {
            System.out.println("Vertex '" + to + "' tidak ditemukan!");
            return false;
        }
        
        if (from.equals(to)) {
            System.out.println("Tidak dapat membuat edge dari vertex ke dirinya sendiri!");
            return false;
        }
        
        // Tambahkan edge dari -> to
        vertices.get(from).addNeighbor(to, weight);
        edgeCount++;
        
        // Jika graph tidak directed, tambahkan edge balik
        if (!directed) {
            vertices.get(to).addNeighbor(from, weight);
        }
        
        System.out.println("Edge dari '" + from + "' ke '" + to + "' (weight: " + weight + ") berhasil ditambahkan!");
        return true;
    }
    
    // Overloaded method untuk edge dengan weight default = 1
    public boolean addEdge(String from, String to) {
        return addEdge(from, to, 1);
    }
    
    // Menghapus vertex
    public boolean removeVertex(String label) {
        if (!vertices.containsKey(label)) {
            System.out.println("Vertex '" + label + "' tidak ditemukan!");
            return false;
        }
        
        Vertex vertex = vertices.get(label);
        
        // Hapus semua edge yang menghubungkan ke vertex ini
        for (String neighbor : vertex.getNeighbors().keySet()) {
            if (vertices.containsKey(neighbor)) {
                vertices.get(neighbor).removeNeighbor(label);
                if (!directed) {
                    edgeCount--;
                }
            }
        }
        
        edgeCount -= vertex.getDegree();
        vertices.remove(label);
        System.out.println("Vertex '" + label + "' dan semua edge yang terhubung berhasil dihapus!");
        return true;
    }
    
    // Menghapus edge
    public boolean removeEdge(String from, String to) {
        if (!vertices.containsKey(from) || !vertices.containsKey(to)) {
            System.out.println("Vertex tidak ditemukan!");
            return false;
        }
        
        if (!vertices.get(from).hasNeighbor(to)) {
            System.out.println("Edge dari '" + from + "' ke '" + to + "' tidak ditemukan!");
            return false;
        }
        
        vertices.get(from).removeNeighbor(to);
        edgeCount--;
        
        if (!directed) {
            vertices.get(to).removeNeighbor(from);
        }
        
        System.out.println("Edge dari '" + from + "' ke '" + to + "' berhasil dihapus!");
        return true;
    }
    
    // Mencari path menggunakan DFS (Depth First Search)
    public List<String> dfsPath(String start, String end) {
        if (!vertices.containsKey(start) || !vertices.containsKey(end)) {
            return null;
        }
        
        Set<String> visited = new HashSet<>();
        List<String> path = new ArrayList<>();
        Map<String, String> parent = new HashMap<>();
        
        dfsRecursive(start, end, visited, parent);
        
        if (!visited.contains(end)) {
            return null; // Tidak ada path
        }
        
        // Reconstruct path
        String current = end;
        while (current != null) {
            path.add(0, current);
            current = parent.get(current);
        }
        
        return path;
    }
    
    private void dfsRecursive(String current, String end, Set<String> visited, Map<String, String> parent) {
        visited.add(current);
        
        if (current.equals(end)) {
            return;
        }
        
        for (String neighbor : vertices.get(current).getNeighbors().keySet()) {
            if (!visited.contains(neighbor)) {
                parent.put(neighbor, current);
                dfsRecursive(neighbor, end, visited, parent);
            }
        }
    }
    
    // Mencari path menggunakan BFS (Breadth First Search)
    public List<String> bfsPath(String start, String end) {
        if (!vertices.containsKey(start) || !vertices.containsKey(end)) {
            return null;
        }
        
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>();
        
        queue.offer(start);
        visited.add(start);
        parent.put(start, null);
        
        while (!queue.isEmpty()) {
            String current = queue.poll();
            
            if (current.equals(end)) {
                // Reconstruct path
                List<String> path = new ArrayList<>();
                String node = end;
                while (node != null) {
                    path.add(0, node);
                    node = parent.get(node);
                }
                return path;
            }
            
            for (String neighbor : vertices.get(current).getNeighbors().keySet()) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }
        
        return null; // Tidak ada path
    }
    
    // DFS traversal untuk menampilkan semua vertex yang bisa dijangkau
    public void dfsTraversal(String start) {
        if (!vertices.containsKey(start)) {
            System.out.println("Vertex '" + start + "' tidak ditemukan!");
            return;
        }
        
        Set<String> visited = new HashSet<>();
        System.out.print("DFS Traversal mulai dari '" + start + "': ");
        dfsTraversalRecursive(start, visited);
        System.out.println();
    }
    
    private void dfsTraversalRecursive(String current, Set<String> visited) {
        visited.add(current);
        System.out.print(current + " ");
        
        for (String neighbor : vertices.get(current).getNeighbors().keySet()) {
            if (!visited.contains(neighbor)) {
                dfsTraversalRecursive(neighbor, visited);
            }
        }
    }
    
    // BFS traversal untuk menampilkan semua vertex yang bisa dijangkau
    public void bfsTraversal(String start) {
        if (!vertices.containsKey(start)) {
            System.out.println("Vertex '" + start + "' tidak ditemukan!");
            return;
        }
        
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        queue.offer(start);
        visited.add(start);
        
        System.out.print("BFS Traversal mulai dari '" + start + "': ");
        
        while (!queue.isEmpty()) {
            String current = queue.poll();
            System.out.print(current + " ");
            
            for (String neighbor : vertices.get(current).getNeighbors().keySet()) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        System.out.println();
    }
    
    // Mengecek apakah graph connected (untuk undirected graph)
    public boolean isConnected() {
        if (vertices.isEmpty()) {
            return true;
        }
        
        String firstVertex = vertices.keySet().iterator().next();
        Set<String> visited = new HashSet<>();
        
        dfsCheckConnected(firstVertex, visited);
        
        return visited.size() == vertices.size();
    }
    
    // Helper method untuk DFS checking connectivity
    private void dfsCheckConnected(String current, Set<String> visited) {
        visited.add(current);
        
        for (String neighbor : vertices.get(current).getNeighbors().keySet()) {
            if (!visited.contains(neighbor)) {
                dfsCheckConnected(neighbor, visited);
            }
        }
    }
    
    // Menghitung derajat vertex
    public int getDegree(String label) {
        if (!vertices.containsKey(label)) {
            System.out.println("Vertex '" + label + "' tidak ditemukan!");
            return -1;
        }
        
        return vertices.get(label).getDegree();
    }
    
    // Menampilkan semua vertex dan edge
    public void displayGraph() {
        if (vertices.isEmpty()) {
            System.out.println("Graph kosong!");
            return;
        }
        
        System.out.println("\n=== STRUKTUR GRAPH ===");
        System.out.println("Tipe Graph: " + (directed ? "Directed" : "Undirected"));
        System.out.println("Jumlah Vertex: " + vertices.size());
        System.out.println("Jumlah Edge: " + edgeCount);
        System.out.println("─".repeat(60));
        
        for (Vertex vertex : vertices.values()) {
            System.out.print("Vertex '" + vertex.getLabel() + "' (degree: " + vertex.getDegree() + ") -> ");
            
            if (vertex.getNeighbors().isEmpty()) {
                System.out.println("Tidak ada edge");
            } else {
                List<String> edges = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : vertex.getNeighbors().entrySet()) {
                    edges.add(entry.getKey() + "(weight: " + entry.getValue() + ")");
                }
                System.out.println(String.join(", ", edges));
            }
        }
    }
    
    // Menampilkan adjacency matrix representation
    public void displayAdjacencyMatrix() {
        if (vertices.isEmpty()) {
            System.out.println("Graph kosong!");
            return;
        }
        
        List<String> vertexList = new ArrayList<>(vertices.keySet());
        Collections.sort(vertexList);
        int n = vertexList.size();
        
        System.out.println("\n=== ADJACENCY MATRIX ===");
        
        // Header
        System.out.print("    ");
        for (String vertex : vertexList) {
            System.out.printf("%-6s", vertex);
        }
        System.out.println();
        
        // Matrix
        for (String from : vertexList) {
            System.out.printf("%-4s", from);
            for (String to : vertexList) {
                if (vertices.get(from).hasNeighbor(to)) {
                    int weight = vertices.get(from).getNeighbors().get(to);
                    System.out.printf("%-6d", weight);
                } else {
                    System.out.printf("%-6d", 0);
                }
            }
            System.out.println();
        }
    }
    
    // Menampilkan semua vertex
    public void displayVertices() {
        if (vertices.isEmpty()) {
            System.out.println("Graph kosong!");
            return;
        }
        
        System.out.println("\n=== DAFTAR VERTEX ===");
        System.out.println("Total vertex: " + vertices.size());
        System.out.println("─".repeat(40));
        
        List<String> vertexList = new ArrayList<>(vertices.keySet());
        Collections.sort(vertexList);
        
        for (String vertex : vertexList) {
            System.out.println("• " + vertex + " (degree: " + vertices.get(vertex).getDegree() + ")");
        }
    }
    
    // Generate sample data untuk testing
    public void generateSampleData() {
        // Hapus data lama jika ada
        if (!vertices.isEmpty()) {
            System.out.println("Data sudah ada. Menghapus data lama...");
            vertices.clear();
            edgeCount = 0;
        }
        
        // Tambahkan vertex
        String[] cities = {"Jakarta", "Bandung", "Surabaya", "Yogyakarta", "Semarang", 
                          "Medan", "Palembang", "Denpasar", "Makassar", "Malang"};
        
        for (String city : cities) {
            addVertex(city);
        }
        
        // Tambahkan edge (representasi jaringan transportasi antar kota)
        addEdge("Jakarta", "Bandung", 150);
        addEdge("Jakarta", "Surabaya", 780);
        addEdge("Jakarta", "Yogyakarta", 550);
        addEdge("Bandung", "Yogyakarta", 420);
        addEdge("Bandung", "Semarang", 380);
        addEdge("Surabaya", "Malang", 90);
        addEdge("Surabaya", "Denpasar", 310);
        addEdge("Yogyakarta", "Semarang", 120);
        addEdge("Yogyakarta", "Surabaya", 330);
        addEdge("Semarang", "Surabaya", 320);
        addEdge("Medan", "Jakarta", 1400);
        addEdge("Palembang", "Jakarta", 580);
        addEdge("Denpasar", "Makassar", 620);
        addEdge("Makassar", "Surabaya", 950);
        
        System.out.println("\nBerhasil generate sample data: Jaringan transportasi antar kota (10 kota, 14 edge)!");
    }
    
    public int getVertexCount() {
        return vertices.size();
    }
    
    public int getEdgeCount() {
        return edgeCount;
    }
    
    public boolean hasVertex(String label) {
        return vertices.containsKey(label);
    }
    
    public boolean isDirected() {
        return directed;
    }
}

// Main Program
public class GraphSystem {
    private static Graph graph = new Graph(false); // Default: undirected graph
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("SISTEM MANAJEMEN GRAPH");
        System.out.println("=".repeat(60));
        
        while (true) {
            showMenu();
            int choice = getIntInput("Pilih menu (0-12): ");
            
            switch (choice) {
                case 1: addVertexMenu(); break;
                case 2: addEdgeMenu(); break;
                case 3: removeVertexMenu(); break;
                case 4: removeEdgeMenu(); break;
                case 5: displayGraphMenu(); break;
                case 6: dfsTraversalMenu(); break;
                case 7: bfsTraversalMenu(); break;
                case 8: findPathMenu(); break;
                case 9: getDegreeMenu(); break;
                case 10: checkConnectedMenu(); break;
                case 11: generateSampleDataMenu(); break;
                case 12: toggleGraphTypeMenu(); break;
                case 0:
                    System.out.println("Terima kasih telah menggunakan sistem ini!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Pilihan tidak valid! Silakan coba lagi.");
            }
            
            System.out.println("\nTekan Enter untuk melanjutkan...");
            scanner.nextLine();
        }
    }
    
    private static void showMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("MENU UTAMA - SISTEM GRAPH");
        System.out.println("=".repeat(60));
        System.out.println("1. Tambah Vertex");
        System.out.println("2. Tambah Edge");
        System.out.println("3. Hapus Vertex");
        System.out.println("4. Hapus Edge");
        System.out.println("5. Tampilkan Graph");
        System.out.println("6. DFS Traversal");
        System.out.println("7. BFS Traversal");
        System.out.println("8. Cari Path antara Dua Vertex");
        System.out.println("9. Lihat Derajat Vertex");
        System.out.println("10. Cek Graph Connected");
        System.out.println("11. Generate Sample Data");
        System.out.println("12. Toggle Graph Type (Directed/Undirected)");
        System.out.println("0. Keluar");
        System.out.println("=".repeat(60));
        System.out.println("Status: " + (graph.getVertexCount() == 0 ? "Graph kosong" : 
                          graph.getVertexCount() + " vertex, " + graph.getEdgeCount() + " edge"));
    }
    
    private static void addVertexMenu() {
        System.out.println("\nTAMBAH VERTEX");
        System.out.println("─".repeat(30));
        
        System.out.print("Masukkan label vertex: ");
        String label = scanner.nextLine().trim();
        
        if (label.isEmpty()) {
            System.out.println("Label tidak boleh kosong!");
            return;
        }
        
        graph.addVertex(label);
    }
    
    private static void addEdgeMenu() {
        System.out.println("\nTAMBAH EDGE");
        System.out.println("─".repeat(30));
        
        System.out.print("Masukkan vertex asal: ");
        String from = scanner.nextLine().trim();
        
        System.out.print("Masukkan vertex tujuan: ");
        String to = scanner.nextLine().trim();
        
        System.out.print("Masukkan weight edge (atau Enter untuk weight = 1): ");
        String weightStr = scanner.nextLine().trim();
        
        if (weightStr.isEmpty()) {
            graph.addEdge(from, to);
        } else {
            try {
                int weight = Integer.parseInt(weightStr);
                graph.addEdge(from, to, weight);
            } catch (NumberFormatException e) {
                System.out.println("Weight harus berupa angka!");
            }
        }
    }
    
    private static void removeVertexMenu() {
        System.out.println("\nHAPUS VERTEX");
        System.out.println("─".repeat(30));
        
        if (graph.getVertexCount() == 0) {
            System.out.println("Graph kosong!");
            return;
        }
        
        graph.displayVertices();
        
        System.out.print("\nMasukkan label vertex yang akan dihapus: ");
        String label = scanner.nextLine().trim();
        
        System.out.print("Yakin ingin menghapus vertex '" + label + "' dan semua edge yang terhubung? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            graph.removeVertex(label);
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }
    
    private static void removeEdgeMenu() {
        System.out.println("\nHAPUS EDGE");
        System.out.println("─".repeat(30));
        
        if (graph.getEdgeCount() == 0) {
            System.out.println("Graph tidak memiliki edge!");
            return;
        }
        
        System.out.print("Masukkan vertex asal: ");
        String from = scanner.nextLine().trim();
        
        System.out.print("Masukkan vertex tujuan: ");
        String to = scanner.nextLine().trim();
        
        graph.removeEdge(from, to);
    }
    
    private static void displayGraphMenu() {
        System.out.println("\nTAMPILKAN GRAPH");
        System.out.println("─".repeat(30));
        
        System.out.println("Pilih format:");
        System.out.println("1. Adjacency List");
        System.out.println("2. Adjacency Matrix");
        System.out.print("Pilihan: ");
        
        int choice = getIntInput("");
        
        if (choice == 1) {
            graph.displayGraph();
        } else if (choice == 2) {
            graph.displayAdjacencyMatrix();
        } else {
            System.out.println("Pilihan tidak valid!");
        }
    }
    
    private static void dfsTraversalMenu() {
        System.out.println("\nDFS TRAVERSAL");
        System.out.println("─".repeat(30));
        
        if (graph.getVertexCount() == 0) {
            System.out.println("Graph kosong!");
            return;
        }
        
        graph.displayVertices();
        System.out.print("\nMasukkan vertex awal: ");
        String start = scanner.nextLine().trim();
        
        graph.dfsTraversal(start);
    }
    
    private static void bfsTraversalMenu() {
        System.out.println("\nBFS TRAVERSAL");
        System.out.println("─".repeat(30));
        
        if (graph.getVertexCount() == 0) {
            System.out.println("Graph kosong!");
            return;
        }
        
        graph.displayVertices();
        System.out.print("\nMasukkan vertex awal: ");
        String start = scanner.nextLine().trim();
        
        graph.bfsTraversal(start);
    }
    
    private static void findPathMenu() {
        System.out.println("\nCARI PATH ANTARA DUA VERTEX");
        System.out.println("─".repeat(30));
        
        if (graph.getVertexCount() == 0) {
            System.out.println("Graph kosong!");
            return;
        }
        
        graph.displayVertices();
        
        System.out.print("\nMasukkan vertex asal: ");
        String from = scanner.nextLine().trim();
        
        System.out.print("Masukkan vertex tujuan: ");
        String to = scanner.nextLine().trim();
        
        System.out.println("\nMencari path menggunakan BFS (shortest path)...");
        List<String> bfsPath = graph.bfsPath(from, to);
        
        if (bfsPath != null) {
            System.out.println("Path ditemukan (BFS): " + String.join(" -> ", bfsPath));
            System.out.println("Jarak: " + (bfsPath.size() - 1) + " edge");
        } else {
            System.out.println("Tidak ada path dari '" + from + "' ke '" + to + "'");
        }
        
        System.out.println("\nMencari path menggunakan DFS...");
        List<String> dfsPath = graph.dfsPath(from, to);
        
        if (dfsPath != null) {
            System.out.println("Path ditemukan (DFS): " + String.join(" -> ", dfsPath));
            System.out.println("Jarak: " + (dfsPath.size() - 1) + " edge");
        } else {
            System.out.println("Tidak ada path dari '" + from + "' ke '" + to + "'");
        }
    }
    
    private static void getDegreeMenu() {
        System.out.println("\nLIHAT DERAJAT VERTEX");
        System.out.println("─".repeat(30));
        
        if (graph.getVertexCount() == 0) {
            System.out.println("Graph kosong!");
            return;
        }
        
        graph.displayVertices();
        
        System.out.print("\nMasukkan label vertex: ");
        String label = scanner.nextLine().trim();
        
        int degree = graph.getDegree(label);
        if (degree >= 0) {
            System.out.println("Derajat vertex '" + label + "': " + degree);
        }
    }
    
    private static void checkConnectedMenu() {
        System.out.println("\nCEK GRAPH CONNECTED");
        System.out.println("─".repeat(30));
        
        if (graph.getVertexCount() == 0) {
            System.out.println("Graph kosong!");
            return;
        }
        
        boolean connected = graph.isConnected();
        
        if (connected) {
            System.out.println("✓ Graph adalah connected graph (semua vertex terhubung)");
        } else {
            System.out.println("✗ Graph adalah disconnected graph (ada vertex yang tidak terhubung)");
        }
    }
    
    private static void generateSampleDataMenu() {
        System.out.println("\nGENERATE SAMPLE DATA");
        System.out.println("─".repeat(30));
        
        if (graph.getVertexCount() > 0) {
            System.out.print("Data sudah ada (" + graph.getVertexCount() + " vertex). Tetap generate? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            
            if (!confirm.equals("y") && !confirm.equals("yes")) {
                System.out.println("Generate data dibatalkan.");
                return;
            }
        }
        
        graph.generateSampleData();
    }
    
    private static void toggleGraphTypeMenu() {
        System.out.println("\nTOGGLE GRAPH TYPE");
        System.out.println("─".repeat(30));
        
        System.out.println("Tipe graph saat ini: " + (graph.isDirected() ? "Directed" : "Undirected"));
        System.out.println("Catatan: Mengubah tipe graph akan menghapus semua data yang ada.");
        System.out.print("Yakin ingin mengubah tipe graph? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            System.out.println("1. Undirected Graph");
            System.out.println("2. Directed Graph");
            
            int choice = getIntInput("Pilih tipe graph (1-2): ");
            
            if (choice == 1 || choice == 2) {
                graph = new Graph(choice == 2);
                System.out.println("Graph type diubah menjadi: " + (choice == 2 ? "Directed" : "Undirected"));
                System.out.println("Semua data graph telah dihapus.");
            } else {
                System.out.println("Pilihan tidak valid!");
            }
        } else {
            System.out.println("Perubahan dibatalkan.");
        }
    }
    
    // Helper method untuk input integer
    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid!");
            }
        }
    }
}

