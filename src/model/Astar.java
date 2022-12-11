package model;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Astar {

    public static void main(String[] args){
//        File input = new File("src/data/sample5.in");
        Scanner in = new Scanner(System.in);
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

        Node root = new Node(initial, findO(initial, o));

        if (in.hasNext()){
            int b_num = in.nextInt();
            for (int i = 1; i <= b_num; i++) {
                int b0 = in.nextInt();
                int index = 0;
                for (int j = 0; j < n * m; j++) {
                    if (initial[j] == b0){
                        index = j;
                        break;
                    }
                }
                String b1 = in.next();
                if (b1.equals("1*2")) {
                    int[] block = new int[2];
                    block[0] = initial[index];
                    block[1] = initial[index + 1];
                    Block b = new Block(block, "1*2");
                    root.addBlock(b);
                }else if (b1.equals("2*1")) {
                    int[] block = new int[2];
                    block[0] = initial[index];
                    block[1] = initial[index + m];
                    Block b = new Block(block, "2*1");
                    root.addBlock(b);
                }else if (b1.equals("2*2")) {
                    int[] block = new int[4];
                    block[0] = initial[index];
                    block[1] = initial[index + 1];
                    block[2] = initial[index + m];
                    block[3] = initial[index + 1 + m];
                    Block b = new Block(block, "2*2");
                    root.addBlock(b);
                }

            }

        }

        Stack<Node> output = solve(root, o, n, m);
        if (output.isEmpty()){
            System.out.println("No");
        }else {
            System.out.println("Yes");
            System.out.println(output.size() - 1);
            int[] aux = output.pop().getNum();
            while(!output.isEmpty()) {
                int[] a = output.pop().getNum();
//                for (int k = 0; k < a.length; k++) {
//                    System.out.print(a[k] +" ");
//                    if ((k + 1) % m == 0){
//                        System.out.println("");
//                    }
//                }
//                System.out.println("");
                out(aux, a, n, m);
                aux = a;
            }
        }
    }

    public static void out(int[] before, int[] now, int n, int m){
        int num = 0;
        int i = 0;
        for (; i < before.length; i++) {
            if (before[i] != now[i] && before[i] != 0){
                num = before[i];
                break;
            }
        }
        if (i % m != 0 && now[i - 1] == num){
            System.out.println(num + "  " + "L");
        }else if ((i + 1) % m != 0 && now[i + 1] == num){
            System.out.println(num + "  " + "R");
        }else if (i / m != 0 && now[i - m] == num){
            System.out.println(num + "  " + "U");
        }else if (i / m != n - 1 && now[i + m] == num){
            System.out.println(num + "  " + "D");
        }
//        else {
//            System.out.println("----------------------wrong--------------------");
//        }
    }

    public static Stack<Node> solve(Node root, int o, int n, int m){
        root.setF(score(root, n, m));
        MinPQ<Node> open = new MinPQ<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.getF() - o2.getF();
            }
        });
        open.insert(root);

//        ArrayList<Node> close = new ArrayList<>();
        HashSet<Node> close = new HashSet<>();
        close.add(root);

        int[] answer = new int[n*m];
        for (int i = 0; i < answer.length - o; i++) {
            answer[i] = i + 1;
        }
        Node an = new Node(answer, findO(answer, o));

        while (!open.isEmpty()){

            Node doing = open.delMin();
//            close.put(doing, doing.hashCode());

            int[] zero = doing.getZero();

            if (doing.getF() == 0){//end//Todo//same(answer, doing.getNum())
                //output stack
//                an.setParent(doing);
                Stack<Node> stack = new Stack<>();
                Node node0 = doing;
                stack.push(node0);
                while(node0.getParent() != null){
                    stack.push(node0.getParent());
                    node0 = node0.getParent();
                }
                return stack;

            }else {

                for (int i = 0; i < zero.length; i++) {

//                    Random r = new Random();
//                    int c = r.nextInt(16) + 1;
//                    if (c <= 4){
//
//                    }else if (c <= 8){
//
//                    }else if (c <= 12){
//
//                    }else {
//
//                    }
                    int[] a = moveL(doing.getNum(), n, m, zero[i], doing.getBlock());
                    if (a != null) {
                        addNode(doing, a, o, n, m, open, close);
                    }
                    a = moveR(doing.getNum(), n, m, zero[i], doing.getBlock());
                    if (a != null){
                        addNode(doing, a, o, n, m, open, close);
                    }
                    a = moveU(doing.getNum(), n, m, zero[i], doing.getBlock());
                    if (a != null){
                        addNode(doing, a, o, n, m, open, close);
                    }
                    a = moveD(doing.getNum(), n, m, zero[i], doing.getBlock());
                    if (a != null){
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

    public static void addNode(Node parent, int[] child, int o, int n, int m, MinPQ<Node> open, HashSet<Node> close){
        Node node = new Node(child, findO(child, o));
        node.setParent(parent);
        node.setBlock(parent.getBlock());
        node.setF(score(node, n, m));
//        node.setG(node.getG() + 1);

        if (!isInClose(close, node)){

//            print(node.getNum());
//            System.out.println(close.size());
            open.insert(node);
            close.add(node);
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

    public static boolean isInClose(HashSet<Node> close, Node node){//can be improved
        return close.contains(node);
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
                    Tn--;
                    Tm = m - 1;
                }

                int Nn = i / m;
                int Nm = i % m;
                score += Math.abs(Nn - Tn) + Math.abs(Nm - Tm);
            }
        }
        return score;// + node.getG()
    }

    public static int[] moveL(int[] initial, int n, int m, int i, ArrayList<Block> blocks){
        if (i % m == 0 || i <= 0) {
            return null;
        }else {
            for (int j = 0; j < blocks.size(); j++) {
                for (int k = 0; k < blocks.get(j).getElement().length; k++) {
                    if (initial[i - 1] == blocks.get(j).getElement()[k]){
                        String b = blocks.get(j).getType();
                        if (b.equals("1*2")){
                            int[] aux = Arrays.copyOfRange(initial, 0, n*m);
                            int tmp = aux[i];
                            aux[i] = aux[i-1];
                            aux[i-1] = aux[i-2];
                            aux[i-2] = tmp;
                            return aux;
                        }else if ((b.equals("2*1"))){
                            if (k % 2 == 0){
                                if (initial[i + m] == 0){
                                    int[] aux = Arrays.copyOfRange(initial, 0, n*m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i-1];
                                    aux[i-1] = tmp;
                                    tmp = aux[i + m];
                                    aux[i + m] = aux[i + m - 1];
                                    aux[i + m - 1] = tmp;
                                    return aux;
                                }else return null;
                            }else {
                                if (initial[i - m] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i - 1];
                                    aux[i - 1] = tmp;
                                    tmp = aux[i - m];
                                    aux[i - m] = aux[i - m - 1];
                                    aux[i - m - 1] = tmp;
                                    return aux;
                                }else return null;
                            }
                        }else if (b.equals("2*2")){
                            if (k == 1){
                                if (initial[i + m] == 0){
                                    int[] aux = Arrays.copyOfRange(initial, 0, n*m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i-1];
                                    aux[i-1] = aux[i-2];
                                    aux[i-2] = tmp;
                                    tmp = aux[i + m];
                                    aux[i + m] = aux[i + m - 1];
                                    aux[i + m - 1] = aux[i + m - 2];
                                    aux[i + m - 2] = tmp;
                                    return aux;
                                }else return null;
                            }else {
                                if (initial[i - m] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n*m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i-1];
                                    aux[i-1] = aux[i-2];
                                    aux[i-2] = tmp;
                                    tmp = aux[i - m];
                                    aux[i - m] = aux[i - m - 1];
                                    aux[i - m - 1] = aux[i - m - 2];
                                    aux[i - m - 2] = tmp;
                                    return aux;
                                }else return null;
                            }
                        }
                    }
                }
            }
            int[] aux = Arrays.copyOfRange(initial, 0, n*m);
            int tmp = aux[i];
            aux[i] = aux[i-1];
            aux[i-1] = tmp;
            return aux;
        }
    }
    public static int[] moveR(int[] initial, int n, int m, int i, ArrayList<Block> blocks){
        if ((i + 1) % m == 0 || i < 0 || i >= n * m - 1) {
            return null;
        }else {
            for (int j = 0; j < blocks.size(); j++) {
                for (int k = 0; k < blocks.get(j).getElement().length; k++) {
                    if (initial[i + 1] == blocks.get(j).getElement()[k]){
                        String b = blocks.get(j).getType();
                        if (b.equals("1*2")){
                            int[] aux = Arrays.copyOfRange(initial, 0, n*m);
                            int tmp = aux[i];
                            aux[i] = aux[i+1];
                            aux[i+1] = aux[i+2];
                            aux[i+2] = tmp;
                            return aux;
                        }else if ((b.equals("2*1"))){
                            if (k % 2 == 0){
                                if (initial[i + m] == 0){
                                    int[] aux = Arrays.copyOfRange(initial, 0, n*m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i+1];
                                    aux[i+1] = tmp;
                                    tmp = aux[i + m];
                                    aux[i + m] = aux[i + m + 1];
                                    aux[i + m + 1] = tmp;
                                    return aux;
                                }else return null;
                            }else {
                                if (initial[i - m] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i + 1];
                                    aux[i + 1] = tmp;
                                    tmp = aux[i - m];
                                    aux[i - m] = aux[i - m + 1];
                                    aux[i - m + 1] = tmp;
                                    return aux;
                                }else return null;
                            }
                        }else if (b.equals("2*2")){
                            if (k == 0){
                                if (initial[i + m] == 0){
                                    int[] aux = Arrays.copyOfRange(initial, 0, n*m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i+1];
                                    aux[i+1] = aux[i+2];
                                    aux[i+2] = tmp;
                                    tmp = aux[i + m];
                                    aux[i + m] = aux[i + m + 1];
                                    aux[i + m + 1] = aux[i + m + 2];
                                    aux[i + m + 2] = tmp;
                                    return aux;
                                }else return null;
                            }else {
                                if (initial[i - m] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n*m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i+1];
                                    aux[i+1] = aux[i+2];
                                    aux[i+2] = tmp;
                                    tmp = aux[i - m];
                                    aux[i - m] = aux[i - m + 1];
                                    aux[i - m + 1] = aux[i - m + 2];
                                    aux[i - m + 2] = tmp;
                                    return aux;
                                }else return null;
                            }
                        }
                    }
                }
            }
            int[] aux = Arrays.copyOfRange(initial, 0, n*m);
            int tmp = aux[i];
            aux[i] = aux[i+1];
            aux[i+1] = tmp;
            return aux;
        }
    }
    public static int[] moveU(int[] initial, int n, int m, int i, ArrayList<Block> blocks){
        if (i / m == 0) {
            return null;
        }else {
            for (int j = 0; j < blocks.size(); j++) {
                for (int k = 0; k < blocks.get(j).getElement().length; k++) {
                    if (initial[i - m] == blocks.get(j).getElement()[k]){
                        String b = blocks.get(j).getType();
                        if (b.equals("1*2")){
                            if (k % 2 == 0) {
                                if (initial[i + 1] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i - m];
                                    aux[i - m] = tmp;
                                    tmp = aux[i + 1];
                                    aux[i + 1] = aux[i - m + 1];
                                    aux[i - m + 1] = tmp;
                                    return aux;
                                }else return null;
                            }else {
                                if (initial[i - 1] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i - m];
                                    aux[i - m] = tmp;
                                    tmp = aux[i - 1];
                                    aux[i - 1] = aux[i - m - 1];
                                    aux[i - m - 1] = tmp;
                                    return aux;
                                }else return null;
                            }
                        }else if ((b.equals("2*1"))){
                            int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                            int tmp = aux[i];
                            aux[i] = aux[i - m];
                            aux[i - m] = aux[i - m - m];
                            aux[i - m - m] = tmp;
                            return aux;
                        }else if (b.equals("2*2")){
                            if (k % 2 == 0){
                                if (initial[i + 1] == 0){
                                    int[] aux = Arrays.copyOfRange(initial, 0, n*m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i - m];
                                    aux[i - m] = aux[i - m - m];
                                    aux[i - m - m] = tmp;
                                    tmp = aux[i + 1];
                                    aux[i + 1] = aux[i - m + 1];
                                    aux[i - m + 1] = aux[i - m - m + 1];
                                    aux[i - m - m + 1] = tmp;
                                    return aux;
                                }else return null;
                            }else {
                                if (initial[i - 1] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n*m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i - m];
                                    aux[i - m] = aux[i - m - m];
                                    aux[i - m - m] = tmp;
                                    tmp = aux[i - 1];
                                    aux[i - 1] = aux[i - m - 1];
                                    aux[i - m - 1] = aux[i - m - m - 1];
                                    aux[i - m - m - 1] = tmp;
                                    return aux;
                                }else return null;
                            }
                        }
                    }
                }
            }
            int[] aux = Arrays.copyOfRange(initial, 0, n*m);
            int tmp = aux[i];
            aux[i] = aux[i-m];
            aux[i-m] = tmp;
            return aux;
        }
    }
    public static int[] moveD(int[] initial, int n, int m, int i, ArrayList<Block> blocks){
        if (i / m == n - 1) {
            return null;
        }else {
            for (int j = 0; j < blocks.size(); j++) {
                for (int k = 0; k < blocks.get(j).getElement().length; k++) {
                    if (initial[i + m] == blocks.get(j).getElement()[k]){
                        String b = blocks.get(j).getType();
                        if (b.equals("1*2")){
                            if (k % 2 == 0) {
                                if (initial[i + 1] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i + m];
                                    aux[i + m] = tmp;
                                    tmp = aux[i + 1];
                                    aux[i + 1] = aux[i + m + 1];
                                    aux[i + m + 1] = tmp;
                                    return aux;
                                }else return null;
                            }else {
                                if (initial[i - 1] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i + m];
                                    aux[i + m] = tmp;
                                    tmp = aux[i - 1];
                                    aux[i - 1] = aux[i + m - 1];
                                    aux[i + m - 1] = tmp;
                                    return aux;
                                }else return null;
                            }
                        }else if ((b.equals("2*1"))){
                            int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                            int tmp = aux[i];
                            aux[i] = aux[i + m];
                            aux[i + m] = aux[i + m + m];
                            aux[i + m + m] = tmp;
                            return aux;
                        }else if (b.equals("2*2")){
                            if (k % 2 == 0){
                                if (initial[i + 1] == 0){
                                    int[] aux = Arrays.copyOfRange(initial, 0, n*m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i + m];
                                    aux[i + m] = aux[i + m + m];
                                    aux[i + m + m] = tmp;
                                    tmp = aux[i + 1];
                                    aux[i + 1] = aux[i + m + 1];
                                    aux[i + m + 1] = aux[i + m + m + 1];
                                    aux[i + m + m + 1] = tmp;
                                    return aux;
                                }else return null;
                            }else {
                                if (initial[i - 1] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n*m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i + m];
                                    aux[i + m] = aux[i + m + m];
                                    aux[i + m + m] = tmp;
                                    tmp = aux[i - 1];
                                    aux[i - 1] = aux[i + m - 1];
                                    aux[i + m - 1] = aux[i + m + m - 1];
                                    aux[i + m + m - 1] = tmp;
                                    return aux;
                                }else return null;
                            }
                        }
                    }
                }
            }
            int[] aux = Arrays.copyOfRange(initial, 0, n*m);
            int tmp = aux[i];
            aux[i] = aux[i+m];
            aux[i+m] = tmp;
            return aux;
        }
    }

//    public static int[] moveL(int[] initial, int n, int m, int i){
//        int[] aux = Arrays.copyOfRange(initial, 0, n*m);
//        int tmp = aux[i];
//        aux[i] = aux[i-1];
//        aux[i-1] = tmp;
//        return aux;
//    }
//    public static int[] moveR(int[] initial, int n, int m, int i){
//        int[] aux = Arrays.copyOfRange(initial, 0, n*m);
//        int tmp = aux[i];
//        aux[i] = aux[i+1];
//        aux[i+1] = tmp;
//        return aux;
//    }
//    public static int[] moveU(int[] initial, int n, int m, int i){
//        int[] aux = Arrays.copyOfRange(initial, 0, n*m);
//        int tmp = aux[i];
//        aux[i] = aux[i-m];
//        aux[i-m] = tmp;
//        return aux;
//    }
//    public static int[] moveD(int[] initial, int n, int m, int i){
//        int[] aux = Arrays.copyOfRange(initial, 0, n*m);
//        int tmp = aux[i];
//        aux[i] = aux[i+m];
//        aux[i+m] = tmp;
//        return aux;
//    }

}