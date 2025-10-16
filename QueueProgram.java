import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class QueueProgram {
    public static void main(String[] args) {
        Queue<String> antrian = new LinkedList<>();
        Scanner input = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\n=== PROGRAM ANTRIAN SEDERHANA ===");
            System.out.println("1. Tambah item ke antrian");
            System.out.println("2. Hapus item dari antrian");
            System.out.println("3. Lihat jumlah item dalam antrian");
            System.out.println("4. Lihat semua item dalam antrian");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu (1-5): ");
            pilihan = input.nextInt();
            input.nextLine(); // membersihkan buffer

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan item yang ingin ditambahkan: ");
                    String item = input.nextLine();
                    antrian.add(item);
                    System.out.println(item + " telah ditambahkan ke antrian.");
                    break;

                case 2:
                    if (!antrian.isEmpty()) {
                        String removed = antrian.remove();
                        System.out.println(removed + " telah dihapus dari antrian.");
                    } else {
                        System.out.println("Antrian kosong!");
                    }
                    break;

                case 3:
                    System.out.println("Jumlah item dalam antrian: " + antrian.size());
                    break;

                case 4:
                    if (!antrian.isEmpty()) {
                        System.out.println("Isi antrian: " + antrian);
                    } else {
                        System.out.println("Antrian kosong!");
                    }
                    break;

                case 5:
                    System.out.println("Terima kasih! Program selesai.");
                    break;

                default:
                    System.out.println("Pilihan tidak valid, coba lagi!");
            }

        } while (pilihan != 5);

        input.close();
    }
}
