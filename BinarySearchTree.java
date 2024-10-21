package App;

public class BinarySearchTree {
    private BSTNode root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void insert(StudentData student) {
        root = insertRec(root, student);
    }

    private BSTNode insertRec(BSTNode root, StudentData student) {
        if (root == null) {
            root = new BSTNode(student);
            return root;
        }
        if (student.entryNumber.compareTo(root.student.entryNumber) < 0)
            root.left = insertRec(root.left, student);
        else if (student.entryNumber.compareTo(root.student.entryNumber) > 0)
            root.right = insertRec(root.right, student);
        return root;
    }

    public StudentData search(String entryNumber) {
        return searchRec(root, entryNumber);
    }

    private StudentData searchRec(BSTNode root, String entryNumber) {
        if (root == null || root.student.entryNumber.equals(entryNumber))
            return root != null ? root.student : null;
        if (entryNumber.compareTo(root.student.entryNumber) < 0)
            return searchRec(root.left, entryNumber);
        return searchRec(root.right, entryNumber);
    }

    public void update(StudentData updatedStudent) {
        root = updateRec(root, updatedStudent);
    }

    private BSTNode updateRec(BSTNode root, StudentData updatedStudent) {
        if (root == null) return null;
        if (root.student.entryNumber.equals(updatedStudent.entryNumber)) {
            root.student = updatedStudent;
        } else if (updatedStudent.entryNumber.compareTo(root.student.entryNumber) < 0) {
            root.left = updateRec(root.left, updatedStudent);
        } else {
            root.right = updateRec(root.right, updatedStudent);
        }
        return root;
    }
}
