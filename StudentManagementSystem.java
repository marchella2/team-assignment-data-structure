import java.util.*;

// Class untuk menyimpan data mahasiswa
class Student {
    private String nim;
    private String nama;
    private double ipk;
    private String jurusan;
    private int semester;

    // Constructor
    public Student(String nim, String nama, double ipk, String jurusan, int semester) {
        this.nim = nim;
        this.nama = nama;
        this.ipk = ipk;
        this.jurusan = jurusan;
        this.semester = semester;
    }

    // Getter methods
    public String getNim() { return nim; }
    public String getNama() { return nama; }
    public double getIpk() { return ipk; }
    public String getJurusan() { return jurusan; }
    public int getSemester() { return semester; }

    // Setter methods
    public void setNama(String nama) { this.nama = nama; }
    public void setIpk(double ipk) { this.ipk = ipk; }
    public void setJurusan(String jurusan) { this.jurusan = jurusan; }
    public void setSemester(int semester) { this.semester = semester; }

    @Override
    public String toString() {
        return String.format("NIM: %s | Nama: %s | IPK: %.2f | Jurusan: %s | Semester: %d", 
                           nim, nama, ipk, jurusan, semester);
    }
}

// Class untuk manajemen Hash Table mahasiswa
class StudentHashTable {
    private HashMap<String, Student> studentTable;
    private int operationCount; // Untuk analisis performa

    public StudentHashTable() {
        this.studentTable = new HashMap<>();
        this.operationCount = 0;
    }

    // Menambahkan mahasiswa baru
    public boolean addStudent(Student student) {
        operationCount++;
        if (studentTable.containsKey(student.getNim())) {
            System.out.println("Error: Mahasiswa dengan NIM " + student.getNim() + " sudah ada!");
            return false;
        }
        
        studentTable.put(student.getNim(), student);
        System.out.println("Berhasil menambahkan mahasiswa: " + student.getNama());
        return true;
    }

    // Mencari mahasiswa berdasarkan NIM
    public Student searchStudent(String nim) {
        operationCount++;
        long startTime = System.nanoTime();
        
        Student student = studentTable.get(nim);
        
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1000000.0; // Convert to milliseconds
        
        if (student != null) {
            System.out.println("Mahasiswa ditemukan dalam " + String.format("%.4f", duration) + " ms");
            System.out.println(student);
        } else {
            System.out.println("Mahasiswa dengan NIM " + nim + " tidak ditemukan!");
        }
        
        return student;
    }

    // Update data mahasiswa
    public boolean updateStudent(String nim, String nama, Double ipk, String jurusan, Integer semester) {
        operationCount++;
        Student student = studentTable.get(nim);
        
        if (student == null) {
            System.out.println("Mahasiswa dengan NIM " + nim + " tidak ditemukan!");
            return false;
        }

        // Update hanya field yang tidak null
        if (nama != null) student.setNama(nama);
        if (ipk != null) student.setIpk(ipk);
        if (jurusan != null) student.setJurusan(jurusan);
        if (semester != null) student.setSemester(semester);

        System.out.println("Data mahasiswa berhasil diupdate!");
        System.out.println(student);
        return true;
    }

    // Menghapus mahasiswa
    public boolean deleteStudent(String nim) {
        operationCount++;
        Student removedStudent = studentTable.remove(nim);
        
        if (removedStudent != null) {
            System.out.println("Mahasiswa " + removedStudent.getNama() + " berhasil dihapus!");
            return true;
        } else {
            System.out.println("Mahasiswa dengan NIM " + nim + " tidak ditemukan!");
            return false;
        }
    }

    // Menampilkan semua mahasiswa
    public void displayAllStudents() {
        operationCount++;
        if (studentTable.isEmpty()) {
            System.out.println("Tidak ada data mahasiswa.");
            return;
        }

        System.out.println("\n=== DAFTAR SEMUA MAHASISWA ===");
        System.out.println("Total mahasiswa: " + studentTable.size());
        System.out.println("─".repeat(80));
        
        // Sort berdasarkan NIM untuk tampilan yang teratur
        studentTable.entrySet().stream()
                   .sorted(Map.Entry.comparingByKey())
                   .forEach(entry -> System.out.println(entry.getValue()));
    }

    // Mencari mahasiswa berdasarkan nama (partial match)
    public void searchByName(String nama) {
        operationCount++;
        System.out.println("\nMencari mahasiswa dengan nama mengandung: \"" + nama + "\"");
        
        List<Student> results = new ArrayList<>();
        for (Student student : studentTable.values()) {
            if (student.getNama().toLowerCase().contains(nama.toLowerCase())) {
                results.add(student);
            }
        }

        if (results.isEmpty()) {
            System.out.println("Tidak ada mahasiswa dengan nama mengandung \"" + nama + "\"");
        } else {
            System.out.println("Ditemukan " + results.size() + " mahasiswa:");
            for (Student student : results) {
                System.out.println(student);
            }
        }
    }

    // Generate sample data untuk testing
    public void generateSampleData() {
        String[] namaList = {
            "Andi Pratama", "Budi Santoso", "Citra Dewi", "Dina Marlina", "Eko Wijaya",
            "Fitri Handayani", "Gita Sari", "Hadi Nugroho", "Indira Putri", "Joko Susilo",
            "Kartika Sari", "Lina Wati", "Maya Sari", "Nanda Pratama", "Oki Setiawan",
            "Putri Ayu", "Qori Rahman", "Rina Sari", "Sari Dewi", "Tono Wijaya"
        };
        
        String[] jurusanList = {
            "Teknik Informatika", "Sistem Informasi", "Teknik Elektro", 
            "Manajemen", "Akuntansi", "Teknik Mesin"
        };
        
        Random random = new Random();
        
        for (int i = 0; i < namaList.length; i++) {
            String nim = String.format("2023%04d", 1001 + i);
            String nama = namaList[i];
            double ipk = 2.0 + (random.nextDouble() * 2.0); // IPK 2.0 - 4.0
            String jurusan = jurusanList[random.nextInt(jurusanList.length)];
            int semester = random.nextInt(8) + 1; // Semester 1-8
            
            Student student = new Student(nim, nama, ipk, jurusan, semester);
            studentTable.put(nim, student);
        }
        
        System.out.println("Berhasil generate " + namaList.length + " data sample mahasiswa!");
    }

    public int getSize() {
        return studentTable.size();
    }
}

// Main Program
public class StudentManagementSystem {
    private static StudentHashTable hashTable = new StudentHashTable();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("SISTEM MANAJEMEN DATA MAHASISWA DENGAN HASH TABLE");
        System.out.println("=" .repeat(60));
        
        while (true) {
            showMenu();
            int choice = getIntInput("Pilih menu (1-7): ");
            
            switch (choice) {
                case 1: addStudentMenu(); break;
                case 2: searchStudentMenu(); break;
                case 3: updateStudentMenu(); break;
                case 4: deleteStudentMenu(); break;
                case 5: hashTable.displayAllStudents(); break;
                case 6: searchByNameMenu(); break;
                case 7: generateSampleDataMenu(); break;
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
        System.out.println("MENU UTAMA - HASH TABLE MAHASISWA");
        System.out.println("=".repeat(60));
        System.out.println("1. Tambah Mahasiswa");
        System.out.println("2. Cari Mahasiswa (berdasarkan NIM)");
        System.out.println("3. Update Data Mahasiswa");
        System.out.println("4. Hapus Mahasiswa");
        System.out.println("5. Tampilkan Semua Mahasiswa");
        System.out.println("6. Cari Berdasarkan Nama");
        System.out.println("7. Generate Sample Data");
        System.out.println("0. Keluar");
        System.out.println("=".repeat(60));
    }

    private static void addStudentMenu() {
        System.out.println("\nTAMBAH MAHASISWA BARU");
        System.out.println("─".repeat(30));
        
        System.out.print("Masukkan NIM: ");
        String nim = scanner.nextLine().trim();
        
        if (nim.isEmpty()) {
            System.out.println("NIM tidak boleh kosong!");
            return;
        }
        
        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine().trim();
        
        if (nama.isEmpty()) {
            System.out.println("Nama tidak boleh kosong!");
            return;
        }
        
        // Loop untuk validasi IPK
        double ipk;
        while (true) {
            ipk = getDoubleInput("Masukkan IPK (0.0-4.0): ");
            if (ipk >= 0.0 && ipk <= 4.0) {
                break;
            }
            System.out.println("IPK harus antara 0.0 - 4.0!");
        }
        
        System.out.print("Masukkan Jurusan: ");
        String jurusan = scanner.nextLine().trim();
        
        // Loop untuk validasi Semester
        int semester;
        while (true) {
            semester = getIntInput("Masukkan Semester (1-14): ");
            if (semester >= 1 && semester <= 14) {
                break;
            }
            System.out.println("Semester harus antara 1-14!");
        }
        
        Student student = new Student(nim, nama, ipk, jurusan, semester);
        hashTable.addStudent(student);
    }

    private static void searchStudentMenu() {
        System.out.println("\nCARI MAHASISWA");
        System.out.println("─".repeat(20));
        
        System.out.print("Masukkan NIM yang dicari: ");
        String nim = scanner.nextLine().trim();
        
        if (nim.isEmpty()) {
            System.out.println("NIM tidak boleh kosong!");
            return;
        }
        
        hashTable.searchStudent(nim);
    }

    private static void updateStudentMenu() {
        System.out.println("\nUPDATE DATA MAHASISWA");
        System.out.println("─".repeat(25));
        
        System.out.print("Masukkan NIM mahasiswa yang akan diupdate: ");
        String nim = scanner.nextLine().trim();
        
        if (nim.isEmpty()) {
            System.out.println("NIM tidak boleh kosong!");
            return;
        }
        
        // Cek apakah mahasiswa ada
        Student existing = hashTable.searchStudent(nim);
        if (existing == null) {
            return;
        }
        
        System.out.println("\nMasukkan data baru (kosongkan jika tidak ingin mengubah):");
        
        System.out.print("Nama baru (sekarang: " + existing.getNama() + "): ");
        String nama = scanner.nextLine().trim();
        if (nama.isEmpty()) nama = null;
        
        // Loop untuk validasi IPK
        Double ipk = null;
        while (true) {
            System.out.print("IPK baru (sekarang: " + existing.getIpk() + "): ");
            String ipkStr = scanner.nextLine().trim();
            if (ipkStr.isEmpty()) {
                break;
            }
            try {
                ipk = Double.parseDouble(ipkStr);
                if (ipk >= 0.0 && ipk <= 4.0) {
                    break;
                }
                System.out.println("IPK harus antara 0.0 - 4.0!");
            } catch (NumberFormatException e) {
                System.out.println("Format IPK tidak valid!");
            }
        }
        
        System.out.print("Jurusan baru (sekarang: " + existing.getJurusan() + "): ");
        String jurusan = scanner.nextLine().trim();
        if (jurusan.isEmpty()) jurusan = null;
        
        // Loop untuk validasi Semester
        Integer semester = null;
        while (true) {
            System.out.print("Semester baru (sekarang: " + existing.getSemester() + "): ");
            String semesterStr = scanner.nextLine().trim();
            if (semesterStr.isEmpty()) {
                break;
            }
            try {
                semester = Integer.parseInt(semesterStr);
                if (semester >= 1 && semester <= 14) {
                    break;
                }
                System.out.println("Semester harus antara 1-14!");
            } catch (NumberFormatException e) {
                System.out.println("Format semester tidak valid!");
            }
        }
        
        hashTable.updateStudent(nim, nama, ipk, jurusan, semester);
    }

    private static void deleteStudentMenu() {
        System.out.println("\nHAPUS MAHASISWA");
        System.out.println("─".repeat(20));
        
        System.out.print("Masukkan NIM mahasiswa yang akan dihapus: ");
        String nim = scanner.nextLine().trim();
        
        if (nim.isEmpty()) {
            System.out.println("NIM tidak boleh kosong!");
            return;
        }
        
        // Konfirmasi penghapusan
        Student student = hashTable.searchStudent(nim);
        if (student != null) {
            System.out.print("Yakin ingin menghapus mahasiswa " + student.getNama() + "? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            
            if (confirm.equals("y") || confirm.equals("yes")) {
                hashTable.deleteStudent(nim);
            } else {
                System.out.println("Penghapusan dibatalkan.");
            }
        }
    }

    private static void searchByNameMenu() {
        System.out.println("\nCARI BERDASARKAN NAMA");
        System.out.println("─".repeat(25));
        
        System.out.print("Masukkan nama atau sebagian nama: ");
        String nama = scanner.nextLine().trim();
        
        if (nama.isEmpty()) {
            System.out.println("Nama tidak boleh kosong!");
            return;
        }
        
        hashTable.searchByName(nama);
    }

    private static void generateSampleDataMenu() {
        System.out.println("\nGENERATE SAMPLE DATA");
        System.out.println("─".repeat(25));
        
        if (hashTable.getSize() > 0) {
            System.out.print("Data sudah ada (" + hashTable.getSize() + " mahasiswa). Tetap generate? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            
            if (!confirm.equals("y") && !confirm.equals("yes")) {
                System.out.println("Generate data dibatalkan.");
                return;
            }
        }
        
        hashTable.generateSampleData();
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

    // Helper method untuk input double
    private static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid!");
            }
        }
    }
}
