import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BFSS {
    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("src/data/sample.in");
        Scanner in = new Scanner(input);
        int n = in.nextInt();
        int m = in.nextInt();
        String initial = "";
        int o = 0;
        for (int i = 0; i < n * m; i++) {
            switch (in.nextInt()) {
                case 0 -> {
                    o++;
                    initial = initial.concat("*");
                }
                case 1 -> initial = initial.concat("a");
                case 2 -> initial = initial.concat("b");
                case 3 -> initial = initial.concat("c");
                case 4 -> initial = initial.concat("d");
                case 5 -> initial = initial.concat("e");
                case 6 -> initial = initial.concat("f");
                case 7 -> initial = initial.concat("g");
                case 8 -> initial = initial.concat("h");
                case 9 -> initial = initial.concat("i");
                case 10 -> initial = initial.concat("j");
                case 11 -> initial = initial.concat("k");
                case 12 -> initial = initial.concat("l");
                case 13 -> initial = initial.concat("m");
                case 14 -> initial = initial.concat("n");
                case 15 -> initial = initial.concat("o");
                case 16 -> initial = initial.concat("p");
                case 17 -> initial = initial.concat("q");
                case 18 -> initial = initial.concat("r");
                case 19 -> initial = initial.concat("s");
                case 20 -> initial = initial.concat("t");
                case 21 -> initial = initial.concat("u");
                case 22 -> initial = initial.concat("v");
                case 23 -> initial = initial.concat("w");
                case 24 -> initial = initial.concat("x");
                case 25 -> initial = initial.concat("y");
                case 26 -> initial = initial.concat("z");
            }
        }

        Stack<String> output = solve(initial, o, n, m);
        Queue<int[]> b0 = new Queue<>();
        if (output.isEmpty()){
            System.out.println(-1);
        }else {
            System.out.println(output.size());
            while(!output.isEmpty()) {
                String[] a = output.pop().split("");
                int[] b = new int[a.length];
                for (int i = 0; i < a.length; i++) {
                    switch (a[i]) {
                        case "a" -> b[i] = 1;
                        case "b" -> b[i] = 2;
                        case "c" -> b[i] = 3;
                        case "d" -> b[i] = 4;
                        case "e" -> b[i] = 5;
                        case "f" -> b[i] = 6;
                        case "g" -> b[i] = 7;
                        case "h" -> b[i] = 8;
                        case "i" -> b[i] = 9;
                        case "j" -> b[i] = 10;
                        case "k" -> b[i] = 11;
                        case "l" -> b[i] = 12;
                        case "m" -> b[i] = 13;
                        case "n" -> b[i] = 14;
                        case "o" -> b[i] = 15;
                        case "p" -> b[i] = 16;
                        case "q" -> b[i] = 17;
                        case "r" -> b[i] = 18;
                        case "s" -> b[i] = 19;
                        case "t" -> b[i] = 20;
                        case "u" -> b[i] = 21;
                        case "v" -> b[i] = 22;
                        case "w" -> b[i] = 23;
                        case "x" -> b[i] = 24;
                        case "y" -> b[i] = 25;
                        case "z" -> b[i] = 26;
                        case "*" -> b[i] = 0;
                    }
                }
                b0.enqueue(b);
            }

            while(!b0.isEmpty()){
                int[] b1 = b0.dequeue();
                for (int i = 0; i < b1.length; i++) {
                    System.out.print(b1[i]);
                    System.out.print(" ");
                }
                System.out.println("");
            }
        }
    }

    public static Stack<String> solve(String initial, int o, int n, int m){
        ArrayList<String> state = new ArrayList<>();
        state.add(initial);

        ArrayList<Integer> fa = new ArrayList<>();
        fa.add(-1);

        int lo = 0;
        int hi = 1;

        String answer = "";
        for (int i = 0; i < n*m - o; i++) {
            answer = answer.concat(Character.toString(i + 97));
        }
        for (int i = 0; i < o; i++) {
            answer = answer.concat("*");
        }

        while(lo < hi){//检索lo到hi-1
            String doing = state.get(lo);
            lo++;
            System.out.println(state.size());

            int[] zero = findO(doing, o);

            if (same(doing, answer)){//终止状态
                //输出含有步骤的STACK
                state.add(answer);
                fa.add(lo-1);
                return output(state, fa);
            }

            for (int i = 0; i < zero.length; i++) {
                if (canMoveL(n, m, zero[i])){
                    //判断是否重复
                    String a = moveL(doing, n, m, zero[i]);
                    if (!isRepeated(state, a)) {
                        state.add(a);
                        hi++;
                        fa.add(lo-1);

//                        printInt(a);
                    }
                }
                if (canMoveR(n, m, zero[i])){
                    String a = moveR(doing, n, m, zero[i]);
                    if (!isRepeated(state, a)) {
                        state.add(a);
                        hi++;
                        fa.add(lo-1);

//                        printInt(a);
                    }
                }
                if (canMoveU(n, m, zero[i])){
                    String a = moveU(doing, n, m, zero[i]);
                    if (!isRepeated(state, a)) {
                        state.add(a);
                        hi++;
                        fa.add(lo-1);

//                        printInt(a);
                    }
                }
                if (canMoveD(n, m, zero[i])){
                    String a = moveD(doing, n, m, zero[i]);
                    if (!isRepeated(state, a)) {
                        state.add(a);
                        hi++;
                        fa.add(lo-1);

//                        printInt(a);
                    }
                }

            }

        }
        return new Stack<>();
    }

    public static int[] findO(String doing, int o){
        int[] zero = new int[o];
        int j = 0;
        for (int i = 0; i < doing.length(); i++) {
            if (doing.charAt(i) == '*'){
                zero[j] = i;
                j++;
            }
        }
        return zero;
    }

    public static void printInt(int[] a){
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
            System.out.print(" ");
        }
        System.out.println("");
    }

    public static Stack<String> output(ArrayList<String> state, ArrayList<Integer> fa){
        Stack<String> out = new Stack<>();
        int i = state.size() - 1;
        while(i != -1){
            out.push(state.get(i));
            i = fa.get(i);
        }
        return out;
    }

    public static boolean isRepeated(ArrayList<String> state, String doing){
        for (String ints : state) {
            if (same(ints, doing)) {
                return true;
            }
        }
        return false;
    }

    public static boolean same(String initial, String answer){
        return initial.equals(answer);
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

    public static String moveL(String initial, int n, int m, int i){
        String[] aux = initial.split("");
        String tmp = aux[i];
        aux[i] = aux[i-1];
        aux[i-1] = tmp;
        String a = "";
        for (int j = 0; j < aux.length; j++) {
            a = a.concat(aux[j]);
        }
        return a;
    }
    public static String moveR(String initial, int n, int m, int i){
        String[] aux = initial.split("");
        String tmp = aux[i];
        aux[i] = aux[i+1];
        aux[i+1] = tmp;
        String a = "";
        for (int j = 0; j < aux.length; j++) {
            a = a.concat(aux[j]);
        }
        return a;
    }
    public static String moveU(String initial, int n, int m, int i){
        String[] aux = initial.split("");
        String tmp = aux[i];
        aux[i] = aux[i-m];
        aux[i-m] = tmp;
        String a = "";
        for (int j = 0; j < aux.length; j++) {
            a = a.concat(aux[j]);
        }
        return a;
    }
    public static String moveD(String initial, int n, int m, int i){
        String[] aux = initial.split("");
        String tmp = aux[i];
        aux[i] = aux[i+m];
        aux[i+m] = tmp;
        String a = "";
        for (int j = 0; j < aux.length; j++) {
            a = a.concat(aux[j]);
        }
        return a;
    }

}

