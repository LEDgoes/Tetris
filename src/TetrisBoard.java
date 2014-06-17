import java.awt.*;

public class TetrisBoard {

	private int[][] board;
	private int score;
	//private TetrisController tc;
	public TetrisBoard()
	{
		board=new int[20][7]; //row,col
		score=0;
	}
	
	public int[][] getBoard()
	{
		return board;
	}
	
	public boolean canFit(int[][] piece,Point corner)
	{
		for (int i=0;i<piece.length;i++)
		{
			for (int j=0;j<piece[i].length;j++)
			{
				int bx=corner.getX()+i; //row
				int by=corner.getY()+j; //col
				boolean xInRange=!(bx >= board.length || bx < 0);
				boolean yInRange=!(by < 0 || by >= board[0].length);
				if (!xInRange || !yInRange)
				{
					if (piece[i][j]==1)
					{
						return false;
					}
					continue;
				}
				if (piece[i][j]==1 && board[bx][by]==1)
				{
					//check if pieces will overlap
					return false;
				}
			}
		}
		return true;
	}
	
	public void addPiece(int[][] piece,Point corner)
	{
		score+=6;
		for (int i=0;i<piece.length;i++)
		{
			for (int j=0;j<piece[i].length;j++)
			{
				int bx=corner.getX()+i; //row
				int by=corner.getY()+j; //col
				boolean xInRange=!(bx >= board.length || bx < 0);
				boolean yInRange=!(by < 0 || by >= board[0].length);
				if (!xInRange || !yInRange)
				{
					continue;
				}
				if (piece[i][j]==1)
				{
					board[bx][by]=1;
				}
			}
		}
		clearRows();
	}
	
	private void clearRows()
	{
		for (int i=0;i<board.length;i++)
		{
			boolean canRowClear=true;
			for (int j=0;j<board[0].length;j++)
			{
				if (board[i][j]==0)
				{
					canRowClear=false;
				}
			}
			if (canRowClear)
			{
				score+=10;
				//clear row
				for (int k=0;k<board[0].length;k++)
				{
					board[i][k]=0;
				}
				
				//shift other rows down
				for (int k=i-1;k>=0;k--)
				{
					for (int m=0;m<board[0].length;m++)
					{
						board[k+1][m]=board[k][m];
					}
				}
				
				//clear top row
				for (int k=0;k<board[0].length;k++)
				{
					board[0][k]=0;
				}
			}
		}
	}
	
	public void draw(Graphics g)
	{
		int xpos=10;
		int ypos=10;
		for (int i=0;i<board.length;i++)
		{
			for (int j=0;j<board[0].length;j++)
			{
				if (board[i][j]==1)
				{
					g.setColor(Color.red);
				}
				else
				{
					g.setColor(Color.gray);
				}
				g.fillRect(ypos, xpos, 25, 25);
				g.setColor(Color.black);
				g.drawRect(ypos, xpos, 25, 25);
				
				ypos+=25;
			}
			ypos=10;
			xpos+=25;
		}
	}
	
	public int getScore()
	{
		return score;
	}
	
}
