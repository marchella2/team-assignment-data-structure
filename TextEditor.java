import java.util.Stack;

public class TextEditor {
    private String currentText;
    private Stack<String> undoStack; // Stack untuk riwayat yang bisa di-undo
    private Stack<String> redoStack; // Stack untuk riwayat yang telah di-undo

    public TextEditor(String initialText) {
        this.currentText = initialText;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        // Inisialisasi: Teks awal tidak perlu dimasukkan ke undoStack
    }

    // Method untuk menambah atau mengubah teks
    public void addText(String newText) {
        // Simpan kondisi teks saat ini ke undoStack
        undoStack.push(currentText);

        // Perbarui teks saat ini
        currentText = newText;

        // Kosongkan redoStack karena riwayat redo tidak valid lagi
        redoStack.clear();

        System.out.println("Teks saat ini: \"" + currentText + "\"");
    }

    // Method untuk melakukan Undo
    public void undo() {
        if (!undoStack.isEmpty()) {
            // Pindahkan kondisi teks saat ini ke redoStack (untuk kemungkinan redo)
            redoStack.push(currentText);

            // Ambil kondisi sebelumnya dari undoStack dan jadikan teks saat ini
            currentText = undoStack.pop();

            System.out.println("Undo: \"" + currentText + "\"");
        } else {
            System.out.println("Tidak ada lagi yang bisa di-undo.");
        }
    }

    // Method untuk melakukan Redo
    public void redo() {
        if (!redoStack.isEmpty()) {
            // Pindahkan kondisi teks saat ini ke undoStack (setelah redo, ini jadi kondisi sebelumnya)
            undoStack.push(currentText);

            // Ambil kondisi dari redoStack dan jadikan teks saat ini
            currentText = redoStack.pop();

            System.out.println("Redo: \"" + currentText + "\"");
        } else {
            System.out.println("Tidak ada lagi yang bisa di-redo.");
        }
    }

    public String getCurrentText() {
        return currentText;
    }
}