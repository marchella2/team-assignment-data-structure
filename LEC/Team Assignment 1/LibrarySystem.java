// Class Book
class Book {
    String title;
    String author;
    boolean available;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public void displayInfo() {
        System.out.println(title + " | " + author + " | " + (available ? "Tersedia" : "Dipinjam"));
    }
}

// Parent Class: User
class User {
    protected String name;
    protected String id;

    public User(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public void showInfo() {
        System.out.println("User: " + name + " (ID: " + id + ")");
    }

    // Polymorphic method
    public void interactWithSystem() {
        System.out.println(name + " berinteraksi dengan sistem.");
    }

    // Mencari buku berdasarkan judul - dapat digunakan oleh Admin dan Member
    public void searchBook(Book[] books, int count, String title) {
        System.out.println("\n== " + name + " mencari buku \"" + title + "\" ==");
        boolean found = false;
        
        for (int i = 0; i < count; i++) {
            if (books[i].title.toLowerCase().contains(title.toLowerCase())) {
                books[i].displayInfo();
                found = true;
            }
        }
        
        if (!found) {
            System.out.println(name + " tidak menemukan buku dengan judul \"" + title + "\".");
        }
        System.out.println();
    }
}

// Child Class: Admin
class Admin extends User {
    public Admin(String name, String id) {
        super(name, id);
    }

    @Override
    public void showInfo() {
        System.out.println("Admin: " + name + " (ID: " + id + ")");
    }

    @Override
    public void interactWithSystem() {
        System.out.println(name + " dapat menambah atau menghapus buku.");
    }

    // Kembalikan count baru setelah menambah buku
    public int addBook(Book[] books, int count, String title, String author) {
        if (count < books.length) {
            books[count++] = new Book(title, author);
            System.out.println("Buku \"" + title + "\" berhasil ditambahkan.");
        } else {
            System.out.println("Koleksi buku penuh, tidak bisa menambah buku.");
        }
        return count;
    }

    // Kembalikan count baru setelah menghapus buku
    public int removeBook(Book[] books, int count, String title) {
        for (int i = 0; i < count; i++) {
            if (books[i].title.equalsIgnoreCase(title)) {
                System.out.println("Buku \"" + title + "\" dihapus dari koleksi.");
                // Geser elemen array ke kiri
                for (int j = i; j < count - 1; j++) {
                    books[j] = books[j + 1];
                }
                books[count - 1] = null;
                return count - 1; // kurangi jumlah buku
            }
        }
        System.out.println("Buku \"" + title + "\" tidak ditemukan.");
        return count;
    }
}

// Child Class: Member
class Member extends User {
    public Member(String name, String id) {
        super(name, id);
    }

    @Override
    public void showInfo() {
        System.out.println("Member: " + name + " (ID: " + id + ")");
    }

    @Override
    public void interactWithSystem() {
        System.out.println(name + " dapat meminjam atau mengembalikan buku.");
    }

    public void borrowBook(Book[] books, int count, String title) {
        for (int i = 0; i < count; i++) {
            if (books[i].title.equalsIgnoreCase(title)) {
                if (books[i].available) {
                    books[i].available = false;
                    System.out.println(name + " berhasil meminjam buku: " + title);
                } else {
                    System.out.println("Buku \"" + title + "\" sedang dipinjam.");
                }
                return;
            }
        }
        System.out.println("Buku \"" + title + "\" tidak ditemukan.");
    }

    public void returnBook(Book[] books, int count, String title) {
        for (int i = 0; i < count; i++) {
            if (books[i].title.equalsIgnoreCase(title)) {
                if (!books[i].available) {
                    books[i].available = true;
                    System.out.println(name + " berhasil mengembalikan buku: " + title);
                } else {
                    System.out.println("Buku \"" + title + "\" sudah tersedia.");
                }
                return;
            }
        }
        System.out.println("Buku \"" + title + "\" tidak ditemukan.");
    }
}

// Main Program
public class LibrarySystem {
    public static void main(String[] args) {
        // Array buku
        Book[] books = new Book[10];
        int count = 0; // jumlah buku saat ini

        // Tambah buku awal
        books[count++] = new Book("Pemrograman Java", "James Gosling");
        books[count++] = new Book("Struktur Data", "Robert Lafore");
        books[count++] = new Book("Algoritma", "Donald Knuth");

        // Buat pengguna
        User[] users = new User[2];
        users[0] = new Admin("Ahmad", "A001");
        users[1] = new Member("Rizal", "M101");

        System.out.println("== Data Pengguna ==");
        for (User u : users) {
            u.showInfo();
            u.interactWithSystem(); // polymorphism
            System.out.println();
        }

        System.out.println("== Daftar Buku ==");
        for (int i = 0; i < count; i++) books[i].displayInfo();
        System.out.println();

        // Aksi admin
        Admin admin = (Admin) users[0];
        count = admin.addBook(books, count, "Basis Data", "Elmasri & Navathe");
        count = admin.removeBook(books, count, "Struktur Data");

        System.out.println("\n== Daftar Buku Setelah Admin Mengelola ==");
        for (int i = 0; i < count; i++) books[i].displayInfo();
        admin.searchBook(books, count, "Data");

        // Aksi member
        Member member = (Member) users[1];

        System.out.println("\n=== Member melakukan pencarian ===");
        member.searchBook(books, count, "Java");

        member.borrowBook(books, count, "Pemrograman Java");
        member.returnBook(books, count, "Pemrograman Java");

        System.out.println("\n== Daftar Buku Akhir ==");
        for (int i = 0; i < count; i++) books[i].displayInfo();
    }
}