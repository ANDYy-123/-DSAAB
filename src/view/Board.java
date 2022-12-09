package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JFrame {
    private JPanel jPanel;
    private JButton left;
    private JButton right;
    static JLabel j = new JLabel();
    int[][] first;
    int[][] block;
    Color[] blockColor;
    int[][] Calculate;
    int cnt = 0;
    boolean last = false;


    public Board(int[][] first, int[][] block, Color[] blockColor, int[][] Calculate) {
        addGif();
        windows();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        this.setVisible(true);
        this.block = block;
        this.blockColor = blockColor;
        this.first = first;
        this.Calculate = Calculate;
        addPart(first);
        addPicture();

        System.out.println(Calculate.length);
    }

    public void upset() {
        jPanel.removeAll();
        int[][] upset = new int[first.length][first[0].length];
        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < first[i].length; j++) {
                upset[i][j] = Calculate[cnt][i * first[i].length + j];
            }
        }

        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < first[i].length; j++) {
                JButton button = new JButton(String.valueOf(upset[i][j]));
                button.setBounds((360 / first[i].length) * j, (360 / first.length) * i, (360 / first[i].length), (360 / first.length));
                if (upset[i][j] != 0) {
                    button.setBackground(blockColor[InBlockLocation(upset[i][j])]);
                } else {
                    button.setBackground(Color.white);
                }

                jPanel.add(button);
            }
        }

        this.add(jPanel);
        jPanel.repaint();
    }

    public void windows() {
        this.setTitle("Java数字华容道");
        this.setSize(960, 565);
        this.setDefaultCloseOperation(3);
        this.setLayout(null);
//        this.setLocationRelativeTo(null);
        setLocation(450,300);
        this.setAlwaysOnTop(true);
    }

    public int InBlockLocation(int chess) {
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                if (block[i][j] == chess) {
                    return i + 1;
                }
            }
        }
        return 0;
    }


    public void addPart(int[][] first) {
        //棋盘
        jPanel = new JPanel();
        jPanel.setBounds(150, 114, 360, 360);
        jPanel.setLayout(null);
        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < first[i].length; j++) {
                JButton button = new JButton(String.valueOf(first[i][j]));
                button.setBounds((360 / first[i].length) * j, (360 / first.length) * i, (360 / first[i].length), (360 / first.length));
                if (first[i][j] != 0) {
                    button.setBackground(blockColor[InBlockLocation(first[i][j])]);
                } else {
                    button.setBackground(Color.white);
                }

                jPanel.add(button);
            }
        }
        this.add(jPanel);

        //左按钮
        left = new JButton(new ImageIcon("picture/zuo.png"));
        left.setBounds(650, 247, 57, 57);
        left.addActionListener(e -> {
            cnt--;
            if (cnt < 0) {
                cnt = 0;
            }
            upset();
        });
        this.add(left);
        //右按钮
        right = new JButton(new ImageIcon("picture/you.png"));
        right.setBounds(813, 247, 57, 57);
        right.addActionListener(e -> {
            cnt++;
            if (cnt >= Calculate.length - 1) {
                cnt = Calculate.length - 1;
                if (!last) {
                    SwingUtilities.invokeLater(() -> {
                        laught mainFrame = new laught(240, 240);
                        mainFrame.setVisible(true);
                    });
                }
//                if (last) {
//                    JOptionPane.showMessageDialog(null, "已经结束咧！", "FBI WARNING", JOptionPane.INFORMATION_MESSAGE);
//                }
                last = true;

            }
            upset();
        });
        this.add(right);


    }

    private void addPicture() {
        ImageIcon picture;
        picture = new ImageIcon("./picture/蛮羊.png");
        j = new JLabel(picture);
        j.setBounds(0, 0, 960, 565);
        add(j);
    }

    private void addGif() {
        MyJPanel m = new MyJPanel();
        m.setBounds(813, 150, 35, 35);
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.repaint();
            }
        });
        timer.start();
        add(m);
    }
}

class MyJPanel extends JPanel {
    int i = 0;
    ImageIcon[] gif = new ImageIcon[22];

    public void paint(Graphics g) {
        for (int i = 0; i < 22; i++) {
            int j = i + 1;
            gif[i] = new ImageIcon("./picture/全部GIF图片帧/" + j + ".png");
//            gif[i]=new ImageIcon("picture/全部GIF图片帧/1.png");
        }
        super.paint(g);
        g.drawImage(gif[i % 22].getImage(), 0, 0, this);
        i++;
    }

}
