public class Main {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor("Selamat");

        // Perubahan 1
        editor.addText("Selamat datang");

        // Perubahan 2
        editor.addText("Selamat datang di editor");

        System.out.println("\n--- Melakukan Undo ---");

        // Undo 1
        editor.undo();

        // Undo 2
        editor.undo();

        System.out.println("\n--- Melakukan Redo ---");

        // Redo 1
        editor.redo();

        System.out.println("\n--- Melakukan Perubahan Baru ---");

        // Perubahan Baru
        editor.addText("Selamat datang semuanya");
    }
}