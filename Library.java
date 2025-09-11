// Parent Class
class User {
    protected String name;
    protected String id;

    public User(String name, String id) {
        this.name = name;
        this.id = id;
    }

    // Polymorphic method
    public void showInfo() {
        System.out.println("User: " + name + " (ID: " + id + ")");
    }
}

// Child Class: Admin
class Admin extends User {
    public Admin(String name, String id) {
        super(name, id);
    }

    // Override method (Polymorphism)
    @Override
    public void showInfo() {
        System.out.println("Admin: " + name + " (ID: " + id + ")");
    }

    public void manageBooks() {
        System.out.println(name + " mengelola koleksi buku.");
    }
}

class Member extends User {
    public Member(String name, String id) {
        super(name, id);
    }

    @Override
    public void showInfo() {
        System.out.println("Member: " + name + " (ID: " + id + ")");
    }

    public void borrowBook(String title) {
        System.out.println(name + " meminjam buku: " + title);
    }
}

public class LibrarySystem {
    public static void main(String[] args) {
        User[] users = new User[3];
        users[0] = new Admin("Ahmad", "A001");
        users[1] = new Member("Rizal", "M101");
        users[2] = new Member("Budi", "M102");

        System.out.println("== Data Pengguna ==");
        for (User u : users) {
            u.showInfo(); 
        }

        ((Admin) users[0]).manageBooks();
        ((Member) users[1]).borrowBook("Pemrograman Java");
    }
}
