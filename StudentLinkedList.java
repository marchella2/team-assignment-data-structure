public class StudentLinkedList {
    
    private static class Node {
        private String nim;
        private String name;
        private int grade;
        private Node next;

        Node(String nim, String name, int grade) {
            this.nim = nim;
            this.name = name;
            this.grade = grade;
            this.next = null;
        }

        public String getNim() { return nim; }
        public void setNim(String nim) { this.nim = nim; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public int getGrade() { return grade; }
        public void setGrade(int grade) { this.grade = grade; }

        public Node getNext() { return next; }
        public void setNext(Node next) { this.next = next; }
    }

    private Node head;
    private Node tail; 
    private int size;

    public StudentLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean addStudent(String nim, String name, int grade) {
        if (nim == null) return false;
        if (findNodeByNim(nim) != null) {
            
            return false;
        }
        Node newNode = new Node(nim, name, grade);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
        return true;
    }

    public boolean removeByNim(String nim) {
        if (head == null || nim == null) return false;

        if (head.getNim().equals(nim)) {
            head = head.getNext();
           
            if (head == null) tail = null;
            size--;
            return true;
        }

        Node prev = head;
        Node cur = head.getNext();
        while (cur != null) {
            if (cur.getNim().equals(nim)) {
                prev.setNext(cur.getNext());
                
                if (cur == tail) tail = prev;
                size--;
                return true;
            }
            prev = cur;
            cur = cur.getNext();
        }
        return false; 
    }

    public boolean updateGrade(String nim, int newGrade) {
        Node cur = findNodeByNim(nim);
        if (cur == null) return false;
        cur.setGrade(newGrade);
        return true;
    }

    private Node findNodeByNim(String nim) {
        Node cur = head;
        while (cur != null) {
            if (cur.getNim().equals(nim)) return cur;
            cur = cur.getNext();
        }
        return null;
    }

    public String getStudentInfo(String nim) {
        Node node = findNodeByNim(nim);
        if (node == null) return null;
        return String.format("NIM: %s, Nama: %s, Nilai: %d",
                node.getNim(), node.getName(), node.getGrade());
    }

    // 
    public void printList() {
        System.out.println("Daftar Mahasiswa:");
        if (head == null) {
            System.out.println("[kosong]");
            return;
        }
        Node cur = head;
        int idx = 1;
        while (cur != null) {
            System.out.printf("%d. NIM: %s, Nama: %s, Nilai: %d%n",
                    idx++, cur.getNim(), cur.getName(), cur.getGrade());
            cur = cur.getNext();
        }
    }

    public int size() { return size; }

    public static void main(String[] args) {
        StudentLinkedList db = new StudentLinkedList();

        db.addStudent("12345", "Andi", 85);
        db.addStudent("67890", "Budi", 90);

        db.printList();
        System.out.println();

        System.out.println("Mengupdate nilai mahasiswa (Budi -> 95)");
        db.updateGrade("67890", 95);

        System.out.println("Daftar Mahasiswa setelah update:");
        db.printList();
    }
}
