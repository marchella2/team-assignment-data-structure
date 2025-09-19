// Sistem Antrian Restoran menggunakan Single Linked List

// Customer class - represent data customer
class Customer {
  private String name;
  private int tableNumber;
  private String orderType;
  
  public Customer(String name, int tableNumber, String orderType) {
    this.name = name;
    this.tableNumber = tableNumber;
    this.orderType = orderType;
  }
  
  public String getName() { return name; }
  public int getTableNumber() { return tableNumber; }
  public String getOrderType() { return orderType; }
  
  @Override
  public String toString() {
    return String.format("[%s - Meja %d - %s]", name, tableNumber, orderType);
  }
}

// Node Class
class Node {
  Customer customer;  // Data pelanggan
  Node next;         // Pointer ke node selanjutnya
  
  public Node(Customer customer) {
    this.customer = customer;
    this.next = null;
  }
}

// Class untuk Antrian Restaurant
class RestaurantQueue {
  private Node front;  // Pointer ke pelanggan pertama dalam antrian (yang akan dilayani)
  private Node rear;   // Pointer ke pelanggan terakhir dalam antrian (yang baru masuk)
  private int size;    // Jumlah pelanggan dalam antrian
  
  public RestaurantQueue() {
    this.front = null;
    this.rear = null;
    this.size = 0;
  }
    
  // Push operation - Tambah pelanggan baru ke belakang antrian
  public void push(Customer customer) {
    Node newNode = new Node(customer);
    
    if (rear == null) {
      front = rear = newNode;
    } else {
      rear.next = newNode;
      rear = newNode;
    }
    
    size++;
    System.out.println("Pelanggan " + customer.getName() + " berhasil ditambahkan ke antrian");
    displayQueue();
  }
    
  // Pop operation - Layani pelanggan pertama dalam antrian
  public Customer pop() {
    if (front == null) {
      System.out.println("Antrian kosong! Tidak ada pelanggan untuk dilayani.");
      return null;
    }
    
    Customer servedCustomer = front.customer;
    front = front.next;
    
    if (front == null) {
      rear = null;
    }
    
    size--;
    System.out.println("Pelanggan " + servedCustomer.getName() + " sedang dilayani");
    displayQueue();
    return servedCustomer;
  }
    
  // Display queue sesuai urutan
  public void displayQueue() {
    System.out.println("\nStatus Antrian Restoran:");
    System.out.println("═══════════════════════════════");
    
    if (front == null) {
      System.out.println("   Antrian kosong");
    } else {
      System.out.println("   Jumlah pelanggan menunggu: " + size);
      System.out.print("   Urutan antrian: ");
      
      Node current = front;
      int position = 1;
      
      while (current != null) {
        System.out.print(position + ". " + current.customer);
        if (current.next != null) {
          System.out.print(" -> ");
        }
        current = current.next;
        position++;
      }
      System.out.println();
    }
    System.out.println("═══════════════════════════════\n");
  }
    
  // Cek Queue kosong
  public boolean isEmpty() {
    return front == null;
  }
    
  // Jumlah size queue
  public int getSize() {
    return size;
  }
    
  // Display customer pertama tanpa menghapusnya dari queue
  public Customer peek() {
    if (front == null) {
      return null;
    }
    return front.customer;
  }
}

// Main Program
public class RestaurantQueueSystem {
  public static void main(String[] args) {
    System.out.println("SISTEM ANTRIAN RESTORAN");
    
    RestaurantQueue queue = new RestaurantQueue();
    
    System.out.println("SIMULASI KEDATANGAN PELANGGAN\n");
    
    queue.push(new Customer("Budi Santoso", 5, "Dine-in"));
    queue.push(new Customer("Sari Dewi", 12, "Take-away"));
    queue.push(new Customer("Ahmad Rahman", 8, "Dine-in"));
    queue.push(new Customer("Lisa Chen", 3, "Delivery"));
    queue.push(new Customer("Muhammad Ali", 15, "Dine-in"));
    
    System.out.println("\nSIMULASI PELAYANAN PELANGGAN\n");
    
    // Melayani 3 pelanggan pertama
    for (int i = 0; i < 3; i++) {
      Customer served = queue.pop();
      if (served != null) {
        System.out.println("   -> " + served.getName() + " telah selesai dilayani\n");
      }
    }
    
    // Tambah pelanggan baru di tengah-tengah pelayanan
    System.out.println("PELANGGAN BARU DATANG\n");
    queue.push(new Customer("Rina Sari", 9, "Take-away"));
    queue.push(new Customer("David Wong", 4, "Dine-in"));
    
    // Lanjutkan pelayanan
    System.out.println("\nLANJUTAN PELAYANAN\n");
    
    while (!queue.isEmpty()) {
      Customer served = queue.pop();
      if (served != null) {
        System.out.println("   -> " + served.getName() + " telah selesai dilayani\n");
      }
    }
    
    // Coba pop dari antrian kosong
    System.out.println("MENCOBA MELAYANI DARI ANTRIAN KOSONG\n");
    queue.pop();
    
    System.out.println("\nDEMO SELESAI - Semua pelanggan telah dilayani!");
  }
}
