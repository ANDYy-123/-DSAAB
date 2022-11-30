import edu.princeton.cs.algs4.Stack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BFS {
    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("src/data/sample2.in");
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

        Stack<int[]> output = solve(initial, o, n, m);
        if (output.isEmpty()){
            System.out.println(-1);
        }else {
            System.out.println(output.size());
            while(!output.isEmpty()) {
                int[] a = output.pop();
                for (int k : a) {
                    System.out.print(k);
                }
                System.out.println("");
            }
        }
    }

    public static Stack<int[]> solve(int[] initial, int o, int n, int m){
        ArrayList<int[]> state = new ArrayList<>();
        state.add(initial);

        ArrayList<Integer> fa = new ArrayList<>();
        fa.add(-1);

        int lo = 0;
        int hi = 1;

        int[] answer = new int[initial.length];
        for (int i = 0; i < answer.length - o; i++) {
            answer[i] = i + 1;
        }

        while(lo < hi){//检索lo到hi-1
            int[] doing = state.get(lo);
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
                    int[] a = moveL(doing, n, m, zero[i]);
                    if (!isRepeated(state, a)) {
                        state.add(a);
                        hi++;
                        fa.add(lo-1);

//                        printInt(a);
                    }
                }
                if (canMoveR(n, m, zero[i])){
                    int[] a = moveR(doing, n, m, zero[i]);
                    if (!isRepeated(state, a)) {
                        state.add(a);
                        hi++;
                        fa.add(lo-1);

//                        printInt(a);
                    }
                }
                if (canMoveU(n, m, zero[i])){
                    int[] a = moveU(doing, n, m, zero[i]);
                    if (!isRepeated(state, a)) {
                        state.add(a);
                        hi++;
                        fa.add(lo-1);

//                        printInt(a);
                    }
                }
                if (canMoveD(n, m, zero[i])){
                    int[] a = moveD(doing, n, m, zero[i]);
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

    public static void printInt(int[] a){
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
            System.out.print(" ");
        }
        System.out.println("");
    }

    public static Stack<int[]> output(ArrayList<int[]> state, ArrayList<Integer> fa){
        Stack<int[]> out = new Stack<>();
        int i = state.size() - 1;
        while(i != -1){
            out.push(state.get(i));
            i = fa.get(i);
        }
        return out;
    }

    public static boolean isRepeated(ArrayList<int[]> state, int[] doing){
        for (int[] ints : state) {
            if (same(ints, doing)) {
                return true;
            }
        }
        return false;
    }

    public static boolean same(int[] initial, int[] answer){
        if (initial.length != answer.length){
            return false;
        }else {
            for (int i = 0; i < initial.length; i++) {
                if (initial[i] != answer[i]){
                    return false;
                }
            }
            return true;
        }
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
