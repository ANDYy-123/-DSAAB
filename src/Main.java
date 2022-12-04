import Music.Music;
import edu.princeton.cs.algs4.Stack;
import javazoom.jl.decoder.JavaLayerException;
import model.Block;
import model.Node;
import view.ChessboardPoint;
import view.Menu;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static model.Astar.findO;
import static model.Astar.solve;

public class Main {
    public static Color CreateColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        Color color = new Color(r, g, b);
        return color;
    }

    public static int[][] Calculate(int NumBlock, int[][] first, int[] block0, String[] blockStyle) {
        int n = first.length;
        int m = first[0].length;
        int[] initial = new int[n * m];
        int o = 0;
        int k = 0;

        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < first[i].length; j++) {
                if (k < n * m) {
                    initial[k] = first[i][j];
                }
                if (initial[k] == 0) {
                    o++;
                }
                k++;
            }
        }
        Node root = new Node(initial, findO(initial, o));

        for (int i = 1; i <= NumBlock; i++) {
            int b0 = block0[i - 1];
            int index = 0;
            for (int j = 0; j < n * m; j++) {
                if (initial[i] == b0) {
                    index = j;
                    break;
                }
            }
            String b1 = blockStyle[i - 1];
            if (b1.equals("1*2")) {
                int[] block = new int[2];
                block[0] = initial[index];
                block[1] = initial[index + 1];
                Block b = new Block(block, "1*2");
                root.addBlock(b);
            } else if (b1.equals("2*1")) {
                int[] block = new int[2];
                block[0] = initial[index];
                block[1] = initial[index + m];
                Block b = new Block(block, "2*1");
                root.addBlock(b);
            } else if (b1.equals("2*2")) {
                int[] block = new int[4];
                block[0] = initial[index];
                block[1] = initial[index + 1];
                block[2] = initial[index + m];
                block[3] = initial[index + 1 + m];
                Block b = new Block(block, "2*2");
                root.addBlock(b);
            }

        }
        Stack<Node> output = solve(initial, root, o, n, m);
        int[][] answer = new int[output.size()-1][];
        if (output.isEmpty()) {
            System.out.println(-1);
        } else {
            System.out.println(output.size()-1);
            for (int i = 0; i < output.size()-1; i++) {
                answer[i] = output.pop().getNum();
            }

            for (int s = 0; s < answer.length; s++) {
                System.out.print(Arrays.toString(answer[s]) + " ");
                if ((s + 1) % m == 0) {
                    System.out.println();
                }
            }
            System.out.println("____________");
        }
        return answer;
    }

    //直接算出哪几个结块再传进Board就好，每次读档加个判断
    public static void main(String[] args) throws FileNotFoundException, JavaLayerException {
        Scanner input = new Scanner(System.in);
        System.out.println("width:");
        int width = input.nextInt();
        System.out.println("length:");
        int length = input.nextInt();
        System.out.println("HuaRongDao:");
        int[][] firstStatement = new int[width][length];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                firstStatement[i][j] = input.nextInt();
            }
        }
        int NumBlock = input.nextInt();
        int[] block0 = new int[NumBlock];
        ChessboardPoint[] blockLocation = new ChessboardPoint[NumBlock];
        int[][] block = new int[NumBlock][];
        Color[] blockColor = new Color[NumBlock + 1];
        String[] blockStyle = new String[NumBlock];
        for (int i = 0; i < NumBlock; i++) {
            block0[i] = input.nextInt();
            blockStyle[i] = input.next();
        }
        for (int i = 0; i < blockColor.length; i++) {
            blockColor[i] = CreateColor();
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < NumBlock; k++) {
                    if (firstStatement[i][j] == block0[k]) {
                        blockLocation[k] = new ChessboardPoint(i, j);
                    }
                }
            }
        }
        for (int i = 0; i < blockStyle.length; i++) {
            int blockHeight = blockStyle[i].charAt(0) - '0';
            int blockWidth = blockStyle[i].charAt(2) - '0';
            block[i] = new int[blockHeight * blockWidth];
            int counter = 0;
            for (int j = 0; j < blockHeight; j++) {
                for (int k = 0; k < blockWidth; k++) {
                    if (counter < blockHeight * blockWidth) {
                        block[i][counter] = firstStatement[blockLocation[i].getX() + j][blockLocation[i].getY() + k];
                        counter++;
                    }
                }
            }
        }

//        int[][] test=new int[1][];
//        int k = 0;
//        for (int i = 0; i < firstStatement.length; i++) {
//            for (int j = 0; j < firstStatement[i].length; j++) {
//                if (k < firstStatement.length * firstStatement[i].length) {
//                    test[0][k] = firstStatement[i][j];
//                }
//                k++;
//            }
//        }


        SwingUtilities.invokeLater(() -> {
            Menu mainFrame = new Menu(641, 834, firstStatement, blockColor, block, Calculate(NumBlock, firstStatement, block0, blockStyle));
//            Menu mainFrame = new Menu(641, 834, firstStatement, blockColor, block, test);

            mainFrame.setVisible(true);
        });
    }
}
