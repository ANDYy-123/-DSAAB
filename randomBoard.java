import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class randomBoard {
    public Node node;
    public int n;
    public int m;
    public int o;
    public int b;
    public int runtime = 0;
    public Random random = new Random();

    public randomBoard(int n, int m, int b) {
        this.n = n;
        this.m = m;
        this.b = b;
        initial();
    }


    public void initial() {
        this.o = random.nextInt(1, 3);
        int k = 0;
        int[] num = new int[n * m];
        for (int i = 1; i <= n * m - o; i++) {
            num[k] = i;
            k++;
        }
        //this.node.setNum(num);
        int[] zero = new int[o];
        int s = 0;
        while (k < n * m) {
            zero[s] = k;
            k++;
            s++;
        }
        //this.node.setZero(zero);
        ArrayList<Block> blocks = new ArrayList<>();
        int z = 1;
        ArrayList<Integer> list = new ArrayList<>();//判断结块有没有区域相连，以排除非法结块
        while (z <= b) {
            int a1 = random.nextInt(1, 3);
            int a2 = random.nextInt(1, 3);
            if (a1 == 1 && a2 == 1) {
                a2 = 2;
            }
            String a = a1 + "*" + a2;
            int index;
            int[] element = new int[0];

            if (a1 == 1 && a2 == 2) {
                while (true) {
                    element = new int[2];
                    int x = random.nextInt(1, m);
                    int y = random.nextInt(1, n);
                    index = (y - 1) * m + x;

                    if (!list.contains(index) && !list.contains(index + 1)) {
                        element[0] = index;
                        element[1] = index + 1;
                        list.add(index);
                        list.add(index + 1);
                        break;
                    }
                }
            } else if (a1 == 2 && a2 == 1) {
                while (true) {
                    element = new int[2];
                    int x = random.nextInt(1, m + 1);
                    int y = random.nextInt(1, n);
                    if (y == n - 1) {
                        int c = random.nextInt(1, m - 1);
                        index = (n - 2) * m + c;
                        if (!list.contains(index) && !list.contains(index + m)) {
                            element[0] = index;
                            element[1] = index + m;
                            list.add(index);
                            list.add(index + m);
                            break;
                        }
                    } else {
                        index = (y - 1) * m + x;
                        if (!list.contains(index) && !list.contains(index + m)) {
                            element[0] = index;
                            element[1] = index + m;
                            list.add(index);
                            list.add(index + m);
                            break;
                        }
                    }
                }
            } else if (a1 == 2 && a2 == 2) {
                while (true) {
                    element = new int[4];
                    int x = random.nextInt(1, n - 1);
                    int y = random.nextInt(1, m);
                    index = (y - 1) * m + x;
                    if (!list.contains(index) && !list.contains(index + m) && !list.contains(index + 1) && !list.contains(index + m + 1)) {
                        element[0] = index;
                        element[1] = index + 1;
                        element[2] = index + m;
                        element[3] = index + m + 1;
                        list.add(index);
                        list.add(index + 1);
                        list.add(index + m);
                        list.add(index + m + 1);
                        break;
                    }
                }
            }
            blocks.add(new Block(element, a));
            z++;
        }
//        node.setBlock(blocks);
        node = new Node(num, zero, blocks);
    }

    public void moveBoard() {

        while (runtime <= 600) {

                int r = random.nextInt(1, 5);
                if (r == 1 ) {
                    if (moveL(node.getNum(), n, m, node.getZero()[0], node.getBlock()) != null) {
                        node.setNum(moveL(node.getNum(), n, m, node.getZero()[0], node.getBlock()));
                        node.setZero(findO(node.getNum(), o));

                        runtime += 1;

                    }
                }
                if (r == 2 ) {
                    if (moveR(node.getNum(), n, m, node.getZero()[0], node.getBlock()) != null) {
                        node.setNum(moveR(node.getNum(), n, m, node.getZero()[0], node.getBlock()));
                        node.setZero(findO(node.getNum(), o));

                        runtime += 1;

                    }
                }
                if (r == 3 ) {
                    if (moveU(node.getNum(), n, m, node.getZero()[0], node.getBlock()) != null) {
                        node.setNum(moveU(node.getNum(), n, m, node.getZero()[0], node.getBlock()));
                        node.setZero(findO(node.getNum(), o));

                        runtime += 1;

                    }
                }
                if (r == 4 ) {
                    if (moveD(node.getNum(), n, m, node.getZero()[0], node.getBlock()) != null) {
                        node.setNum(moveD(node.getNum(), n, m, node.getZero()[0], node.getBlock()));
                        node.setZero(findO(node.getNum(), o));

                        runtime += 1;

                    }
                }

            }
            if (o==2){
                int r = random.nextInt(1, 5);
                if (r == 1 ) {
                    if (moveL(node.getNum(), n, m, node.getZero()[1], node.getBlock()) != null) {
                        node.setNum(moveL(node.getNum(), n, m, node.getZero()[1], node.getBlock()));
                        node.setZero(findO(node.getNum(), o));

                        runtime += 1;

                    }
                }
                if (r == 2 ) {
                    if (moveR(node.getNum(), n, m, node.getZero()[1], node.getBlock()) != null) {
                        node.setNum(moveR(node.getNum(), n, m, node.getZero()[1], node.getBlock()));
                        node.setZero(findO(node.getNum(), o));

                        runtime += 1;

                    }
                }
                if (r == 3 ) {
                    if (moveU(node.getNum(), n, m, node.getZero()[1], node.getBlock()) != null) {
                        node.setNum(moveU(node.getNum(), n, m, node.getZero()[1], node.getBlock()));
                        node.setZero(findO(node.getNum(), o));

                        runtime += 1;

                    }
                }
                if (r == 4 ) {
                    if (moveD(node.getNum(), n, m, node.getZero()[1], node.getBlock()) != null) {
                        node.setNum(moveD(node.getNum(), n, m, node.getZero()[1], node.getBlock()));
                        node.setZero(findO(node.getNum(), o));

                        runtime += 1;
                    }
                }

        }
    }

    public static int[] moveL(int[] initial, int n, int m, int i, ArrayList<Block> blocks) {
        if (i % m == 0 || i <= 0) {
            return null;
        } else {
            for (int j = 0; j < blocks.size(); j++) {
                for (int k = 0; k < blocks.get(j).getElement().length; k++) {
                    if (initial[i - 1] == blocks.get(j).getElement()[k]) {
                        String b = blocks.get(j).getType();
                        if (b.equals("1*2")) {
                            int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                            int tmp = aux[i];
                            aux[i] = aux[i - 1];
                            aux[i - 1] = aux[i - 2];
                            aux[i - 2] = tmp;
                            return aux;
                        } else if ((b.equals("2*1"))) {
                            if (k % 2 == 0) {
                                if (initial[i + m] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i - 1];
                                    aux[i - 1] = tmp;
                                    tmp = aux[i + m];
                                    aux[i + m] = aux[i + m - 1];
                                    aux[i + m - 1] = tmp;
                                    return aux;
                                } else return null;
                            } else {
                                if (initial[i - m] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i - 1];
                                    aux[i - 1] = tmp;
                                    tmp = aux[i - m];
                                    aux[i - m] = aux[i - m - 1];
                                    aux[i - m - 1] = tmp;
                                    return aux;
                                } else return null;
                            }
                        } else if (b.equals("2*2")) {
                            if (k == 1) {
                                if (initial[i + m] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i - 1];
                                    aux[i - 1] = aux[i - 2];
                                    aux[i - 2] = tmp;
                                    tmp = aux[i + m];
                                    aux[i + m] = aux[i + m - 1];
                                    aux[i + m - 1] = aux[i + m - 2];
                                    aux[i + m - 2] = tmp;
                                    return aux;
                                } else return null;
                            } else {
                                if (initial[i - m] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i - 1];
                                    aux[i - 1] = aux[i - 2];
                                    aux[i - 2] = tmp;
                                    tmp = aux[i - m];
                                    aux[i - m] = aux[i - m - 1];
                                    aux[i - m - 1] = aux[i - m - 2];
                                    aux[i - m - 2] = tmp;
                                    return aux;
                                } else return null;
                            }
                        }
                    }
                }
            }
            int[] aux = Arrays.copyOfRange(initial, 0, n * m);
            int tmp = aux[i];
            aux[i] = aux[i - 1];
            aux[i - 1] = tmp;
            return aux;
        }
    }

    public static int[] moveR(int[] initial, int n, int m, int i, ArrayList<Block> blocks) {
        if ((i + 1) % m == 0 || i < 0 || i >= n * m - 1) {
            return null;
        } else {
            for (int j = 0; j < blocks.size(); j++) {
                for (int k = 0; k < blocks.get(j).getElement().length; k++) {
                    if (initial[i + 1] == blocks.get(j).getElement()[k]) {
                        String b = blocks.get(j).getType();
                        if (b.equals("1*2")) {
                            int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                            int tmp = aux[i];
                            aux[i] = aux[i + 1];
                            aux[i + 1] = aux[i + 2];
                            aux[i + 2] = tmp;
                            return aux;
                        } else if ((b.equals("2*1"))) {
                            if (k % 2 == 0) {
                                if (initial[i + m] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i + 1];
                                    aux[i + 1] = tmp;
                                    tmp = aux[i + m];
                                    aux[i + m] = aux[i + m + 1];
                                    aux[i + m + 1] = tmp;
                                    return aux;
                                } else return null;
                            } else {
                                if (initial[i - m] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i + 1];
                                    aux[i + 1] = tmp;
                                    tmp = aux[i - m];
                                    aux[i - m] = aux[i - m + 1];
                                    aux[i - m + 1] = tmp;
                                    return aux;
                                } else return null;
                            }
                        } else if (b.equals("2*2")) {
                            if (k == 0) {
                                if (initial[i + m] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i + 1];
                                    aux[i + 1] = aux[i + 2];
                                    aux[i + 2] = tmp;
                                    tmp = aux[i + m];
                                    aux[i + m] = aux[i + m + 1];
                                    aux[i + m + 1] = aux[i + m + 2];
                                    aux[i + m + 2] = tmp;
                                    return aux;
                                } else return null;
                            } else {
                                if (initial[i - m] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i + 1];
                                    aux[i + 1] = aux[i + 2];
                                    aux[i + 2] = tmp;
                                    tmp = aux[i - m];
                                    aux[i - m] = aux[i - m + 1];
                                    aux[i - m + 1] = aux[i - m + 2];
                                    aux[i - m + 2] = tmp;
                                    return aux;
                                } else return null;
                            }
                        }
                    }
                }
            }
            int[] aux = Arrays.copyOfRange(initial, 0, n * m);
            int tmp = aux[i];
            aux[i] = aux[i + 1];
            aux[i + 1] = tmp;
            return aux;
        }
    }

    public static int[] moveU(int[] initial, int n, int m, int i, ArrayList<Block> blocks) {
        if (i / m == 0) {
            return null;
        } else {
            for (int j = 0; j < blocks.size(); j++) {
                for (int k = 0; k < blocks.get(j).getElement().length; k++) {
                    if (initial[i - m] == blocks.get(j).getElement()[k]) {
                        String b = blocks.get(j).getType();
                        if (b.equals("1*2")) {
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
                                } else return null;
                            } else {
                                if (initial[i - 1] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i - m];
                                    aux[i - m] = tmp;
                                    tmp = aux[i - 1];
                                    aux[i - 1] = aux[i - m - 1];
                                    aux[i - m - 1] = tmp;
                                    return aux;
                                } else return null;
                            }
                        } else if ((b.equals("2*1"))) {
                            int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                            int tmp = aux[i];
                            aux[i] = aux[i - m];
                            aux[i - m] = aux[i - m - m];
                            aux[i - m - m] = tmp;
                            return aux;
                        } else if (b.equals("2*2")) {
                            if (k % 2 == 0) {
                                if (initial[i + 1] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i - m];
                                    aux[i - m] = aux[i - m - m];
                                    aux[i - m - m] = tmp;
                                    tmp = aux[i + 1];
                                    aux[i + 1] = aux[i - m + 1];
                                    aux[i - m + 1] = aux[i - m - m + 1];
                                    aux[i - m - m + 1] = tmp;
                                    return aux;
                                } else return null;
                            } else {
                                if (initial[i - 1] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i - m];
                                    aux[i - m] = aux[i - m - m];
                                    aux[i - m - m] = tmp;
                                    tmp = aux[i - 1];
                                    aux[i - 1] = aux[i - m - 1];
                                    aux[i - m - 1] = aux[i - m - m - 1];
                                    aux[i - m - m - 1] = tmp;
                                    return aux;
                                } else return null;
                            }
                        }
                    }
                }
            }
            int[] aux = Arrays.copyOfRange(initial, 0, n * m);
            int tmp = aux[i];
            aux[i] = aux[i - m];
            aux[i - m] = tmp;
            return aux;
        }
    }

    public static int[] moveD(int[] initial, int n, int m, int i, ArrayList<Block> blocks) {
        if (i / m == n - 1) {
            return null;
        } else {
            for (int j = 0; j < blocks.size(); j++) {
                for (int k = 0; k < blocks.get(j).getElement().length; k++) {
                    if (initial[i + m] == blocks.get(j).getElement()[k]) {
                        String b = blocks.get(j).getType();
                        if (b.equals("1*2")) {
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
                                } else return null;
                            } else {
                                if (initial[i - 1] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i + m];
                                    aux[i + m] = tmp;
                                    tmp = aux[i - 1];
                                    aux[i - 1] = aux[i + m - 1];
                                    aux[i + m - 1] = tmp;
                                    return aux;
                                } else return null;
                            }
                        } else if ((b.equals("2*1"))) {
                            int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                            int tmp = aux[i];
                            aux[i] = aux[i + m];
                            aux[i + m] = aux[i + m + m];
                            aux[i + m + m] = tmp;
                            return aux;
                        } else if (b.equals("2*2")) {
                            if (k % 2 == 0) {
                                if (initial[i + 1] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i + m];
                                    aux[i + m] = aux[i + m + m];
                                    aux[i + m + m] = tmp;
                                    tmp = aux[i + 1];
                                    aux[i + 1] = aux[i + m + 1];
                                    aux[i + m + 1] = aux[i + m + m + 1];
                                    aux[i + m + m + 1] = tmp;
                                    return aux;
                                } else return null;
                            } else {
                                if (initial[i - 1] == 0) {
                                    int[] aux = Arrays.copyOfRange(initial, 0, n * m);
                                    int tmp = aux[i];
                                    aux[i] = aux[i + m];
                                    aux[i + m] = aux[i + m + m];
                                    aux[i + m + m] = tmp;
                                    tmp = aux[i - 1];
                                    aux[i - 1] = aux[i + m - 1];
                                    aux[i + m - 1] = aux[i + m + m - 1];
                                    aux[i + m + m - 1] = tmp;
                                    return aux;
                                } else return null;
                            }
                        }
                    }
                }
            }
            int[] aux = Arrays.copyOfRange(initial, 0, n * m);
            int tmp = aux[i];
            aux[i] = aux[i + m];
            aux[i + m] = tmp;
            return aux;
        }
    }

    public static int[] findO(int[] doing, int o) {
        int[] zero = new int[o];
        int j = 0;
        for (int i = 0; i < doing.length; i++) {
            if (doing[i] == 0) {
                zero[j] = i;
                j++;
            }
        }
        return zero;
    }
}
