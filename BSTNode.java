package App;

class BSTNode {
    StudentData student;
    BSTNode left, right;

    public BSTNode(StudentData student) {
        this.student = student;
        this.left = this.right = null;
    }
}
