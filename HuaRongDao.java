import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class HuaRongDao implements Comparable{
    private int length;//列数
    private int wide;//行数
    private int[] num = new int[length * wide];//棋盘
    private int evaluation;                //估计函数f(n)：从起始状态到目标的最小估计值
    private int depth;                    //d(n)：当前的深度，即走到当前状态的步骤
    private int misposition;            //启发函数 h(n)：到目标的最小估计(记录和目标状态有多少个数不同)
    private HuaRongDao parent;            //当前状态的父状态
    private ArrayList<HuaRongDao> answer = new ArrayList<HuaRongDao>();    //保存最终路径

//    public HuaRongDao(int length, int wide, int[] num) {
//        this.length = length;
//        this.wide = wide;
//        this.num = num;
//    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setWide(int wide) {
        this.wide = wide;
    }

    public void setNum(int[] num) {
        this.num = num;
    }

    public int[] getNum() {
        return num;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public int getMisposition() {
        return misposition;
    }

    public void setMisposition(int misposition) {
        this.misposition = misposition;
    }

    public HuaRongDao getParent() {
        return parent;
    }

    public void setParent(HuaRongDao parent) {
        this.parent = parent;
    }

    //判断是否为目标状态
    public boolean isTarget(HuaRongDao target) {
        return Arrays.equals(getNum(), target.getNum());
    }

    //求估计函数f(n) = g(n)+h(n)
    public void init(HuaRongDao target) {
        int temp = 0;
        for (int i = 0; i < wide*length; i++) {
            if (num[i] != target.getNum()[i])
                temp++;            //记录当前节点与目标节点差异的度量
        }
        this.setMisposition(temp);
        if (this.getParent() == null) {
            this.setDepth(0);    //初始化步数（深度）
        } else {
            this.depth = this.parent.getDepth() + 1;//记录步数
        }
        this.setEvaluation(this.getDepth() + this.getMisposition());//返回当前状态的估计值
    }

    //用逆序数判断是否有解
    public boolean isSolvable(HuaRongDao target) {
        int reverse = 0;
        for (int i = 0; i < wide*length; i++) {
            for (int j = 0; j < i; j++) {//遇到0跳过
                if (num[j] > num[i] && num[j] != 0 && num[i] != 0)
                    reverse++;
                if (target.getNum()[j] > target.getNum()[i] && target.getNum()[j] != 0 && target.getNum()[i] != 0)
                    reverse++;
            }
        }
        return reverse % 2 == 0;
    }

    @Override
    public int compareTo(Object o) {
        HuaRongDao c = (HuaRongDao) o;
        return this.evaluation - c.getEvaluation();//默认排序为f(n)由小到大排序
    }

    //获取空格位置
    public int getZeroPosition() {
        int position = -1;
        for (int i = 0; i < wide*length; i++) {
            if (this.num[i] == 0) {
                position = i;
            }
        }
        return position;
    }

    //判断是否有重复
    public int isContains(ArrayList<HuaRongDao> open) {
        for (int i = 0; i < open.size(); i++) {
            if (Arrays.equals(open.get(i).getNum(), getNum())) {
                return i;
            }
        }
        return -1;
    }

    //判断能否上移，看0点是否在第一行
    public boolean isMoveUp() {
        int position = getZeroPosition();
        return position > length;
    }

    //判断能否下移，看0点是否在最后一行
    public boolean isMoveDown() {
        int position = getZeroPosition();
        return position < wide*length-length;
    }

    //判断能否能否左移，看0点是否在第一列
    public boolean isMoveLeft() {
        int position = getZeroPosition();
        return position % length != 0;
    }

    //判断能否右移，看0点是否在最后一列
    public boolean isMoveRight() {
        int position = getZeroPosition();
        return (position) % length != length-1;
    }

    //w上s下a左d右
    public HuaRongDao moveUp(char move) {
        HuaRongDao temp = new HuaRongDao();
        int[] tempnum = num.clone();
        temp.setNum(tempnum);
        temp.setWide(wide);
        temp.setLength(length);
        int position = getZeroPosition();    //0的位置
        int p = 0;                            //与0换位置的位置
        switch (move) {
            case 'w':
                p = position - length;
                temp.getNum()[position] = num[p];
                break;
            case 's':
                p = position + length;
                temp.getNum()[position] = num[p];
                break;
            case 'a':
                p = position - 1;
                temp.getNum()[position] = num[p];
                break;
            case 'd':
                p = position + 1;
                temp.getNum()[position] = num[p];
                break;
        }
        temp.getNum()[p] = 0;
        return temp;
    }

    /**
     * 按照3*3格式输出
     */
    public void print() {
        for (int i = 0; i < wide*length; i++) {
            if (i % length == length-1) {
                System.out.println(this.num[i]);
            } else {
                System.out.print(this.num[i] + "  ");
            }
        }
    }

    /**
     * 将最终答案路径保存下来并输出
     */
    public void printRoute() {
        HuaRongDao temp = null;
        int count = 0;
        temp = this;
        System.out.println("move start");
        while (temp != null) {
            answer.add(temp);
            temp = temp.getParent();
            count++;
        }
        for (int i = answer.size() - 1; i >= 0; i--) {
            answer.get(i).print();
            System.out.println(" ");
        }
        System.out.println("the smallest move times:" + (count - 1));
    }

    /**
     * @param open   open表
     * @param close  close表
     * @param parent 父状态
     * @param target 目标状态
     */
    public void operation(ArrayList<HuaRongDao> open, ArrayList<HuaRongDao> close, HuaRongDao parent, HuaRongDao target) {
        if (this.isContains(close) == -1) {//如果不在close表中
            int position = this.isContains(open);//获取在open表中的位置
            if (position == -1) {//如果也不在open表中
                this.parent = parent;//指明它的父状态
                this.init(target);//计算它的估计值
                open.add(this);//把它添加进open表
            } else {//如果它在open表中
                if (this.getDepth() < open.get(position).getDepth()) {//跟已存在的状态作比较，如果它的步数较少则是较优解
                    open.remove(position);//把已经存在的相同状态替换掉
                    this.parent = parent;
                    this.init(target);
                    open.add(this);
                }
            }
        }
    }

//    @SuppressWarnings("unchecked")
    public static void main(String args[]) {
        //定义open表
        ArrayList<HuaRongDao> open = new ArrayList<HuaRongDao>();
        ArrayList<HuaRongDao> close = new ArrayList<HuaRongDao>();

        Scanner s = new Scanner(System.in);
        System.out.println("length:");
        int l=s.nextInt();
        System.out.println("wide:");
        int w=s.nextInt();
        int stnum[] = new int[l*w];
        int target[]=new int[l*w];
        System.out.println("start:");
        for (int i = 0; i < l*w; i++) {
            stnum[i] = s.nextInt();
        }
        System.out.println("start suan");
        s.close();
        for (int k=0;k<l*w;k++){
            if (k<l*w-1){
                target[k]=k+1;
            }else {
                target[k]=0;
            }
        }
        HuaRongDao start = new HuaRongDao();
        start.setNum(stnum);
        start.setLength(l);
        start.setWide(w);
        HuaRongDao fTarget = new HuaRongDao();
        fTarget.setNum(target);
        fTarget.setLength(l);
        fTarget.setWide(w);

        if (start.isSolvable(fTarget)) {
            //初始化初始状态
            start.init(fTarget);
            open.add(start);
            while (!open.isEmpty()) {
                Collections.sort(open);            //按照evaluation的值排序
                HuaRongDao best = open.get(0);    //从open表中取出最小估值的状态并移出open表
                open.remove(0);
                close.add(best);

                if (best.isTarget(fTarget)) {
                    //输出
                    best.printRoute();
                    break;
                }

                char move;
                //由best状态进行扩展并加入到open表中
                //0的位置上移之后状态不在close和open中设定best为其父状态，并初始化f(n)估值函数
                if (best.isMoveUp()) {//可以上移的话
                    move = 'w';//上移标记
                    HuaRongDao up = best.moveUp(move);//best的一个子状态
                    up.operation(open, close, best, fTarget);
                }
                //0的位置下移之后状态不在close和open中设定best为其父状态，并初始化f(n)估值函数
                if (best.isMoveDown()) {
                    move = 's';
                    HuaRongDao down = best.moveUp(move);
                    down.operation(open, close, best, fTarget);
                }
                //0的位置左移之后状态不在close和open中设定best为其父状态，并初始化f(n)估值函数
                if (best.isMoveLeft()) {
                    move = 'a';
                    HuaRongDao left = best.moveUp(move);
                    left.operation(open, close, best, fTarget);
                }
                //0的位置右移之后状态不在close和open中设定best为其父状态，并初始化f(n)估值函数
                if (best.isMoveRight()) {
                    move = 'd';
                    HuaRongDao right = best.moveUp(move);
                    right.operation(open, close, best, fTarget);
                }

            }
        } else {
            System.out.println("目标状态不可达");
        }
    }

}
