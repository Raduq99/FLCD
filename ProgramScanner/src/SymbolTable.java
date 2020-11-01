public class SymbolTable {
    private static int globalPos = 0;
    private class TreeNode {
        String value;
        int position;
        TreeNode left, right;
        public TreeNode(String value) {
            this.value = value;
            this.position = globalPos++;
            left = right = null;
        }
    }

    private TreeNode root;

    public SymbolTable() {
        root = null;
    }

    public int search(String item) {
        return this.searchRecursive(this.root, item);
    }

    private int searchRecursive(TreeNode current, String item) {
        if(current == null) {
            return -1;
        }
        if(item.compareTo(current.value) < 0) {
            return this.searchRecursive(current.left, item);
        }
        else if(item.compareTo(current.value) > 0) {
            return this.searchRecursive(current.right, item);
        }
        else return current.position;
    }

    public void add(String item) {
        this.root = this.addRecursive(this.root, item);
    }

    private TreeNode addRecursive(TreeNode current, String item) {
        if (current == null) {
            return new TreeNode(item);
        }
        if(this.searchRecursive(current, item) != -1) {
            return current;
        }
        if (item.compareTo(current.value) < 0) {
            current.left = addRecursive(current.left, item);
        }
        else if (item.compareTo(current.value) > 0) {
            current.right = addRecursive(current.right, item);
        }
        return current;
    }

    private void inorderRecursive(TreeNode current, StringBuilder res) {
        if (current != null) {
            inorderRecursive(current.left, res);
            //System.out.println(current.position + "->" + root.value);
            res.append(current.value).append("->").append(current.position).append('\n');
            inorderRecursive(current.right, res);
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        this.inorderRecursive(this.root, res);
        return res.toString();
    }

    //public void print() {
    //    this.inorderRecursive(this.root, res);
    //}
}
