class Node {
    int data;
    Node left, right;
    int size;

    Node(int data) {
        this.data = data;
        left = right = null;
        this.size = 0;
    }
}

class BinaryTree {
    Node root;

    public void insert(int data) {
        root = insertNode(root, data);
    }

    public void inorder() {
        inOrderPrint(root);
    }

    public void perorder() {
        preOrderPrint(root);
    }

    public void postorder() {
        postOrderPrint(root);
    }

    public int getSize() {
        return root.size;
    }

    public void search(int val) {
        System.out.println(searchTree(root, val));
    }

    private Node insertNode(Node root, int data) {
        if(root == null) {
            root = new Node(data);
            root.size++;
        } else if(data < root.data) {
            root.size++;
            root.left = insertNode(root.left, data);
        } else {
            root.size++;
            root.right = insertNode(root.right, data);
        }

        return root;
    }

    private void inOrderPrint(Node root) {
        if(root == null) return;

        inOrderPrint(root.left);
        System.out.print(root.data + " ");
        inOrderPrint(root.right);
    }

    private void preOrderPrint(Node root) {
        if(root == null) return;

        System.out.print(root.data + " ");
        preOrderPrint(root.left);
        preOrderPrint(root.right);
    }

    private void postOrderPrint(Node root) {
        if(root == null) return;

        preOrderPrint(root.left);
        preOrderPrint(root.right);
        System.out.print(root.data + " ");
    }

    private boolean searchTree(Node root, int val) {
        if(root == null) return false;

        if(root.data == val) return true; 

        boolean leftSub = searchTree(root.left, val);
        boolean rightSub = searchTree(root.right, val);

        return leftSub || rightSub;
    }
}



public class tree {

    public static void main(String[] args) {
        BinaryTree btree = new BinaryTree();

        btree.insert(5);
        btree.insert(6);
        btree.insert(2);
        btree.insert(1);
        btree.insert(8);
        btree.insert(4);

        btree.search(0);
        System.out.println(btree.getSize());
        
    }
}