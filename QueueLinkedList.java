// Kelas untuk merepresentasikan pelanggan
class Customer {
  String name;
  int queueNumber;
  Customer next;
  
  public Customer(String name, int queueNumber) {
    this.name = name;
    this.queueNumber = queueNumber;
    this.next = null;
  }
}

// Implementasi Queue menggunakan Linked List
class CustomerQueue {
  private Customer front; // Pelanggan di depan antrian
  private Customer rear;  // Pelanggan di belakang antrian
  private int size;
  private int nextQueueNumber;
  
  public CustomerQueue() {
    this.front = null;
    this.rear = null;
    this.size = 0;
    this.nextQueueNumber = 1;
  }
  
  // Menambah pelanggan baru ke antrian
  public void addCustomer(String name) {
    Customer newCustomer = new Customer(name, nextQueueNumber++);
    
    if (rear == null) {
      // Antrian kosong
      front = rear = newCustomer;
    } else {
      // Tambah di belakang antrian
      rear.next = newCustomer;
      rear = newCustomer;
    }
    size++;
    System.out.println("Pelanggan " + name + " ditambahkan ke antrian dengan nomor " + newCustomer.queueNumber);
  }
  
  // Melayani pelanggan (menghapus dari depan antrian)
  public void serveCustomer() {
    if (front == null) {
      System.out.println("Antrian kosong, tidak ada pelanggan untuk dilayani.");
      return;
    }
    
    Customer servedCustomer = front;
    front = front.next;
    
    // Jika antrian menjadi kosong setelah melayani
    if (front == null) {
      rear = null;
    }
    
    size--;
    System.out.println("Melayani pelanggan: " + servedCustomer.name);
  }
  
  // Menampilkan daftar pelanggan dalam antrian
  public void displayQueue() {
    if (front == null) {
      System.out.println("Antrian kosong.");
      return;
    }
    
    System.out.println("Pelanggan dalam antrean:");
    Customer current = front;
    int position = 1;
    
    while (current != null) {
      System.out.println(position + ". " + current.name);
      current = current.next;
      position++;
    }
  }
  
  // Mengecek apakah antrian kosong
  public boolean isEmpty() {
    return front == null;
  }
  
  // Mendapatkan ukuran antrian
  public int getSize() {
    return size;
  }
}

// Kelas utama untuk simulasi sistem manajemen antrian
public class QueueLinkedList {
  public static void main(String[] args) {
    System.out.println("=== SIMULASI SISTEM MANAJEMEN ANTRIAN CUSTOMER SERVICE ===");
    
    CustomerQueue queue = new CustomerQueue();
    
    // Simulasi penambahan pelanggan
    System.out.println("\n--- Menambahkan pelanggan ke antrian ---");
    queue.addCustomer("Budi");
    queue.addCustomer("Sari");
    queue.addCustomer("Ahmad");
    queue.addCustomer("Dewi");
    
    // Menampilkan antrian
    System.out.println("\n--- Status antrian ---");
    queue.displayQueue();
    System.out.println("Jumlah pelanggan dalam antrian: " + queue.getSize());
    
    // Simulasi melayani beberapa pelanggan
    System.out.println("\n--- Melayani pelanggan ---");
    queue.serveCustomer();
    queue.serveCustomer();
    
    // Menampilkan antrian setelah dilayani
    System.out.println("\n--- Status antrian setelah dilayani ---");
    queue.displayQueue();
    System.out.println("Jumlah pelanggan dalam antrian: " + queue.getSize());
    
    // Menambah pelanggan baru
    System.out.println("\n--- Menambahkan pelanggan baru ---");
    queue.addCustomer("Rina");
    
    // Status akhir antrian
    System.out.println("\n--- Status akhir antrian ---");
    queue.displayQueue();
    System.out.println("Jumlah pelanggan dalam antrian: " + queue.getSize());
    
    // Melayani semua pelanggan yang tersisa
    System.out.println("\n--- Melayani semua pelanggan yang tersisa ---");
    while (!queue.isEmpty()) {
      queue.serveCustomer();
    }
    
    // Cek antrian kosong
    System.out.println("\n--- Status akhir ---");
    queue.displayQueue();
    System.out.println("Antrian kosong: " + queue.isEmpty());
  }
}
