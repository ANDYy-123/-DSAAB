
public class Node {

    private int[] num;

    private int[] zero;

    private int f;

    private int g;

    private Node parent;

    public Node(int[] num, int[] zero) {
        this.num = num;
        this.zero = zero;
    }

    public int[] getNum() {
        return num;
    }

    public int[] getZero() {
        return zero;
    }

    public int getF() {
        return f;
    }

    public int getG() {
        return g;
    }

    public Node getParent() {
        return parent;
    }

    public void setNum(int[] num) {
        this.num = num;
    }

    public void setZero(int[] zero) {
        this.zero = zero;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
