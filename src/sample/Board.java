package sample;

import java.util.Arrays;
import java.util.Random;

public class Board {
    int[][] board;
    int centerX;
    int centerY;

    public Board(int size)
    {
        board = new int[size][size];
    }

    public void fillBoard()
    {
        Random rand = new Random();
        board[0][board.length/2-1]=-1;
        board[0][board.length/2]=-1;
        board[0][board.length/2+1]=-1;
        centerX=2;
        centerY=board.length/2;
        putPixel(centerX,centerY);
        int choose = 0;
        while(centerX< board[0].length - 3)
        {
            boolean correct = true;
            do
            {
                correct=true;
                choose = rand.nextInt(3);
                if(choose == 1)
                {
                    if ((centerY-3 < 1 ? 0 : board[centerX][centerY-3]) == 1 || (centerY-3 <= 0 || centerX -3 < 0 ? 0 : board[centerX-3][centerY-3]) == 1)
                        correct = false;
                }
                else if(choose == 2)
                    if ((centerY+3 > board.length - 1 ? 0 : board[centerX][centerY+3]) == 1 || (centerY+3 > board.length - 1 || centerX -3 < 0  ? 0 : board[centerX-3][centerY+3]) == 1)
                        correct = false;
            }
            while (correct == false);
            fillPixelBoard(choose);
        }
        //Filling the end
        centerX = board[0].length - 2;
        centerY -= 1;
        for(int i=centerX; i < centerX + 2 ; i++) {
            for(int j = centerY; j < centerY + 3; j++) {
                board[i][j] = 2;
            }
        }
        //transpose();
    } // end fillBoard

    public void fillPixelBoard(int choose)
    {
        switch (choose)
        {
            case 0:
                if(centerX+3 > board[0].length-3)
                {
                    centerX = board[0].length-3;
                    break;
                }
                centerX += 3;
                break;
            case 1:
                if(centerY-3<= 0)
                {
                    centerY = 1;
                    break;
                }
                centerY -= 3;
                break;
            case 2:
                if(centerY+3> board.length - 2)
                {
                    centerY = board.length - 2;
                    break;
                }
                centerY += 3;
                break;
        }
        putPixel(centerX,centerY);
    }

    public void transpose() {
        for(int i = 0;i < board.length;i++) {
            for(int j=i+1;j<board[0].length;j++) {
                int temp=board[i][j];
                board[i][j]=board[j][i];
                board[j][i] = temp;
            }
        }
    }
    public void putPixel(int x, int y)
    {
        for(int i=x-1; i<=x+1; i++)
            for(int j=y-1; j<=y+1; j++)
            {
                if(i>=0 && j>=0 && i<board[0].length-2 && j<board.length)
                    board[i][j] = 1;
            }
    }

    public static void main(String[] args) {
        Board board=new Board(200);
        board.fillBoard();
        for(int i=0;i<board.board.length;i++) {
            for(int j=0;j<board.board[0].length;j++) {
                System.out.printf("%2d",board.board[i][j]);
            }
            System.out.println();
        }
    }

    public int get(int x, int y) {
        try {
            return board[x][y];
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            return 0;
        }

    }

    public int getColumns() {
        return board[0].length;
    }

    public int getRows() {
        return board.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(board);
    }
}
