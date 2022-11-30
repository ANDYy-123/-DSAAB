import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Astar {

    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("src/data/sample3.in");
        Scanner in = new Scanner(input);
        int n = in.nextInt();
        int m = in.nextInt();
        int[] initial = new int[n * m];
        int o = 0;
        for (int i = 0; i < n * m; i++) {
            initial[i] = in.nextInt();
            if (initial[i] == 0){
                o++;
            }
        }

        Stack<Node> output = solve(initial, o, n, m);
        if (output.isEmpty()){
            System.out.println(-1);
        }else {
            System.out.println(output.size());
            while(!output.isEmpty()) {
                int[] a = output.pop().getNum();
                for (int k : a) {
                    System.out.print(k+" ");
                }
                System.out.println("");
            }
        }
//        int[] a = {5, 1, 2, 4, 9, 6, 3, 8, 13, 15, 10, 11, 14, 0, 7, 12};
//        int[] o = {13};
//        Node node = new Node(a, o);
//        System.out.println(score(node, 4, 4));
    }

    public static Stack<Node> solve(int[] initial, int o, int n, int m){
        MinPQ<Node> open = new MinPQ<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.getF() - o2.getF();
            }
        });
        Node root = new Node(initial, findO(initial, o));
        open.insert(root);

        ArrayList<Node> close = new ArrayList<>();

        int[] answer = new int[n*m];
        for (int i = 0; i < answer.length - o; i++) {
            answer[i] = i + 1;
        }
        Node an = new Node(answer, findO(answer, o));

        while (!open.isEmpty()){

            Node doing = open.delMin();
            close.add(doing);

            int[] zero = doing.getZero();

            if (same(answer, doing.getNum())){//end
                //output stack
                an.setParent(doing);
                Stack<Node> stack = new Stack<>();
                Node node0 = an;
                stack.push(node0);
                while(node0.getParent() != null){
                    stack.push(node0.getParent());
                    node0 = node0.getParent();
                }
                return stack;

            }else {

                for (int i = 0; i < zero.length; i++) {
                    if (canMoveL(n, m, zero[i])) {
                        int[] a = moveL(doing.getNum(), n, m, zero[i]);
                        addNode(doing, a, o, n, m, open, close);
                    }
                    if (canMoveR(n, m, zero[i])) {
                        int[] a = moveR(doing.getNum(), n, m, zero[i]);
                        addNode(doing, a, o, n, m, open, close);
                    }
                    if (canMoveU(n, m, zero[i])) {
                        int[] a = moveU(doing.getNum(), n, m, zero[i]);
                        addNode(doing, a, o, n, m, open, close);
                    }
                    if (canMoveD(n, m, zero[i])) {
                        int[] a = moveD(doing.getNum(), n, m, zero[i]);
                        addNode(doing, a, o, n, m, open, close);
                    }
                }

            }
        }
        return new Stack<>();
    }

    public static void print(int[] ini){
        for (int i = 0; i < ini.length; i++) {
            System.out.print(ini[i]);
            System.out.print(" ");
        }
        System.out.println("");
    }

    public static void addNode(Node parent, int[] child, int o, int n, int m,MinPQ<Node> open, ArrayList<Node> close){
        Node node = new Node(child, findO(child, o));
        node.setParent(parent);
        node.setF(score(node, n, m));
        node.setG(node.getG() + 1);

        if (!isInClose(close, node)){
            if (!isInOpen(open, node)){
                print(node.getNum());
                open.insert(node);
            }
        }

    }

    public static boolean isInOpen(MinPQ<Node> open, Node node){
        for (Node value : open) {
            if (same(value.getNum(), node.getNum())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInClose(ArrayList<Node> close, Node node){//can be improved
        for (int i = 0; i < close.size(); i++) {
            if (same(close.get(i).getNum(), node.getNum())){
                return true;
            }
        }
        return false;
    }

    public static boolean same(int[] o1, int[] o2){//can be improved
        for (int i = 0; i < o1.length; i++) {
            if (o1[i] != o2[i]){
                return false;
            }
        }
        return true;
    }

    public static int[] findO(int[] doing, int o){
        int[] zero = new int[o];
        int j = 0;
        for (int i = 0; i < doing.length; i++) {
            if (doing[i] == 0){
                zero[j] = i;
                j++;
            }
        }
        return zero;
    }

    public static int score(Node node, int n, int m){
        int score = 0;
        for (int i = 0; i < node.getNum().length; i++) {
            if (node.getNum()[i] != 0 && node.getNum()[i] != i + 1) {
                int Tn = node.getNum()[i] / m;
                int Tm = node.getNum()[i] % m - 1;
                if (Tm == -1){
                    Tn++;
                    Tm = 3;
                }

                int Nn = i / m;
                int Nm = i % m;
                score += Math.abs(Nn - Tn) + Math.abs(Nm - Tm);
            }
        }
        return node.getG() + score;
    }

    public static boolean canMoveL(int n, int m, int i){
        if (i % m == 0 || i <= 0) {
            return false;
        }else return true;
    }
    public static boolean canMoveR(int n, int m, int i){
        if ((i + 1) % m == 0 || i < 0 || i >= n * m - 1) {
            return false;
        }else return true;
    }
    public static boolean canMoveU(int n, int m, int i){
        if (i / m == 0) {
            return false;
        }else return true;
    }
    public static boolean canMoveD(int n, int m, int i){
        if (i / m == n - 1) {
            return false;
        }else return true;
    }

    public static int[] moveL(int[] initial, int n, int m, int i){
        int[] aux = Arrays.copyOfRange(initial, 0, n*m);
        int tmp = aux[i];
        aux[i] = aux[i-1];
        aux[i-1] = tmp;
        return aux;
    }
    public static int[] moveR(int[] initial, int n, int m, int i){
        int[] aux = Arrays.copyOfRange(initial, 0, n*m);
        int tmp = aux[i];
        aux[i] = aux[i+1];
        aux[i+1] = tmp;
        return aux;
    }
    public static int[] moveU(int[] initial, int n, int m, int i){
        int[] aux = Arrays.copyOfRange(initial, 0, n*m);
        int tmp = aux[i];
        aux[i] = aux[i-m];
        aux[i-m] = tmp;
        return aux;
    }
    public static int[] moveD(int[] initial, int n, int m, int i){
        int[] aux = Arrays.copyOfRange(initial, 0, n*m);
        int tmp = aux[i];
        aux[i] = aux[i+m];
        aux[i+m] = tmp;
        return aux;
    }

}
