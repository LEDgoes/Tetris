
public class SqPiece implements TetrisPiece {
	
	private int[][] litPositions;
	private int orient;
	public SqPiece()
	{
		//super(); //[row][column]
		orient=1000;
		litPositions=new int[][] 
                                 {{0,1,1,0},
                                  {0,1,1,0},
                                  {0,0,0,0}};
	}
	public void printPiece()
	{
		for (int i=0;i<litPositions.length;i++)
		{
			for (int j=0;j<litPositions[i].length;j++)
			{
				System.out.print(litPositions[i][j]+" ");
			}
			System.out.println();
		}
	}
	public void rotateForward()
	{
		orient++;
		rotate();
	}
	public void rotateBackward()
	{
		orient--;
		rotate();
	}
	public int[][] getPiece()
	{
		return litPositions;
	}
	
	private void rotate()
	{
		//cricket noises
	}

}
