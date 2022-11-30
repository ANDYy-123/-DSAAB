import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Tree {
    private ArrayList<Node> nodes;
    private int n;

    static class Node{
        int[] num;
        int score;

//        Node left = null;
//        Node right = null;
        Node parent = null;

        Node(int[] num, int score){
            this.num = num;
            this.score = score;
        }
        public int getScore(){
            return score;
        }
        public int[] getNum(){
            return num;
        }
    }

    public Tree(){
        n = 0;
        nodes = new ArrayList<>();
//        int[] a0 = null;
        Node a = new Node(null, -1);
        nodes.add(a);
    }


    private void exchange(int i, int j){
        Node tmp = nodes.get(i);
        nodes.set(i, nodes.get(j));
        nodes.set(j, tmp);
    }

    public void insert(Node number){

        nodes.add(number);
        number.parent = min();
        n++;
        swim(n);

    }

    private void swim(int i){
        while(i > 1 && nodes.get(i/2).getScore() > nodes.get(i).getScore()) {
            exchange(i/2,i);
            i = i/2;
        }
    }

    public Node min(){
        if (n <= 0)
            throw new NoSuchElementException("In Max.PQ: pq is empty");
        return nodes.get(1);
    }

    public Node delMin(){
        if (nodes.size() <= 0)
            throw new NoSuchElementException("In Min.PQ: pq is empty");
        Node result = nodes.get(1);

        nodes.set(1, nodes.get(n--));
        sink(1);


        return result;
    }

    private void sink(int i){
        while(i*2 <= n){
            int j = i*2;
            if (j+1 <= n && nodes.get(j).getScore() > nodes.get(j + 1).getScore())
                j++;
            if (nodes.get(i).getScore() <= nodes.get(j).getScore()) {
                break;
            }
            exchange(i, j);
            i = j;
        }
    }

    public Node[] getNode(){
        Node[] a = new Node[nodes.size() - 1];
        for (int i = 1; i < nodes.size(); i++) {
            a[i - 1] = nodes.get(i);
        }
        return a;
    }

    public boolean isEmpty() {
        return n == 0;
    }




    public static void main(String[] args) {
        int[] a = {1,2,3,4};
        int[] b = {1,3,2,4};
        int[] c = {4,3,2,1};
        int a0 = 1;
        int b0 = 2;
        int c0 = 3;
        Node a1 = new Node(a, a0);
        Node b1 = new Node(b, b0);
        Node c1 = new Node(c, c0);
        Node d1 = new Node(null,5);
        Node e1 = new Node(null, 6);
        Tree tree = new Tree();
        tree.insert(c1);
        tree.insert(b1);
        tree.insert(a1);
        tree.insert(e1);
        tree.insert(d1);

//        Node[] d = tree.getNode();
//        for (int i = 0; i < d.length; i++) {
//            for (int j = 0; j < d[i].getNum().length; j++) {
//                System.out.print(d[i].getNum()[j]);
//                System.out.print(" ");
//            }
//            System.out.println(d[i].getScore());
//        }

//        System.out.println(tree.delMin().getScore());
//        System.out.println(tree.delMin().getScore());
//        System.out.println(tree.delMin().getScore());
//        System.out.println(tree.delMin().getScore());
//        System.out.println(tree.delMin().getScore());



//        for (int i = 0; i < d.length; i++) {
//            for (int j = 0; j < d[i].getNum().length; j++) {
//                System.out.print(d[i].getNum()[j]);
//                System.out.print(" ");
//            }
//            System.out.println(d[i].getScore());
//        }
    }


}
