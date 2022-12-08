import java.util.ArrayList;
import java.util.Arrays;

public class Node {

    private int[] num;

    private int[] zero;

    private int f;

//    private int g;

    private Node parent;

    private ArrayList<Block> block;

    public Node(int[] num, int[] zero) {
        this.num = num;
        this.zero = zero;
        block = new ArrayList<>();
    }

    @Override
    public int hashCode(){
        return Arrays.hashCode(num);//f ^= ( f >>> 20) ^ ( f >>> 12) ^ (f >>> 7) ^ (f >>> 4) ;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Node){
            return Arrays.equals(((Node) obj).getNum(), this.num);
        }else return false;
    }


    public Node(int[] num, int[] zero, ArrayList<Block> block){
        this.num = num;
        this.zero = zero;
        this.block = block;
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

//    public int getG() {
//        return g;
//    }

    public Node getParent() {
        return parent;
    }

    public ArrayList<Block> getBlock() {
        return block;
    }

    public void setBlock(ArrayList<Block> block) {
        this.block = block;
    }

    public void addBlock(Block block){
        this.block.add(block);
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

//    public void setG(int g) {
//        this.g = g;
//    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
