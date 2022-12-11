public class randomBoardTest {
    public static void main(String[] args) {
        randomBoard randomboard=new randomBoard(5,5,2);//给定n，m，和结块数b
        randomboard.moveBoard();
        System.out.printf(randomboard.n+" "+randomboard.m);
        System.out.println("");
        for (int i=0;i< randomboard.n* randomboard.m;i++){
            System.out.print(randomboard.node.getNum()[i]);
            if (i==randomboard.n* randomboard.m-1){break;}
            if ((i+1)% randomboard.m==0){
                System.out.println("");
                continue;
            }
            System.out.print(" ");
        }
        System.out.println("");
        System.out.print(randomboard.b);
        for (int i=0;i<randomboard.node.getBlock().size();i++){
             Block block=randomboard.node.getBlock().get(i);
            System.out.println("");
             int[]index=block.getElement();
            System.out.printf(index[0]+" "+block.getType());

        }
        System.out.println("");
        System.out.println(randomboard.runtime);
    }
}
