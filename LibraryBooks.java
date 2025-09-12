// class Book
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

public class LibraryBooks {
    public static void main(String[] args) {
        // array untuk menyimpan data buku
        Book[] books = new Book[10];
        int count = 0;

        // tambah beberapa buku ke perpustakaan
        books[count++] = new Book("Pemrograman Java", "James Gosling");
        books[count++] = new Book("Struktur Data", "Robert Lafore");
        books[count++] = new Book("Algoritma", "Donald Knuth");

        // tampilkan semua buku
        System.out.println("=== Daftar Buku ===");
        for (int i = 0; i < count; i++) {
            books[i].displayInfo();
        }

        // pinjam buku
        String judulPinjam = "Pemrograman Java";
        for (int i = 0; i < count; i++) {
            if (books[i].title.equalsIgnoreCase(judulPinjam)) {
                if (books[i].available) {
                    books[i].available = false;
                    System.out.println("\nBuku \"" + judulPinjam + "\" berhasil dipinjam.");
                } else {
                    System.out.println("\nBuku \"" + judulPinjam + "\" sedang dipinjam.");
                }
            }
        }

        // tampilkan daftar buku setelah dipinjam
        System.out.println("\n=== Setelah Peminjaman ===");
        for (int i = 0; i < count; i++) {
            books[i].displayInfo();
        }

        // kembalikan buku
        String judulKembali = "Pemrograman Java";
        for (int i = 0; i < count; i++) {
            if (books[i].title.equalsIgnoreCase(judulKembali)) {
                if (!books[i].available) {
                    books[i].available = true;
                    System.out.println("\nBuku \"" + judulKembali + "\" berhasil dikembalikan.");
                }
            }
        }

        // tampilkan daftar buku terakhir
        System.out.println("\n=== Daftar Buku Akhir ===");
        for (int i = 0; i < count; i++) {
            books[i].displayInfo();
        }
    }
}
