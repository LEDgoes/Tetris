import java.awt.*;
import java.util.*;
import java.io.*;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.*;


public class TetrisController {
	
	private TetrisPiece currentPiece;
	private Point pieceCorner;
	private TetrisBoard board;
	private boolean isAlive;
	private boolean isPaused;
	//private int score;
	
	private OutputStream out;
	
	private int dropCount;
	public TetrisController()
	{
		board=new TetrisBoard();
		setNewPiece();
		dropCount=5;
		isAlive=true;
		isPaused=false;
		//score=0;
		//out = serialPort.getOutputStream();
		try {
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("COM4");
		    if ( portIdentifier.isCurrentlyOwned() )
		    {
		        System.out.println("Error: Port is currently in use");
		    }
		    else
		    {
		        System.out.println("Connect 1/2");
		        CommPort commPort = portIdentifier.open(this.getClass().getName(),6000);

		        if ( commPort instanceof SerialPort )
		        {
		            System.out.println("Connect 2/2");
		            SerialPort serialPort = (SerialPort) commPort;
		            System.out.println("BaudRate: " + serialPort.getBaudRate());
		            System.out.println("DataBIts: " + serialPort.getDataBits());
		            System.out.println("StopBits: " + serialPort.getStopBits());
		            System.out.println("Parity: " + serialPort.getParity());
		            System.out.println("FlowControl: " + serialPort.getFlowControlMode());
		            serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
		            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
		            System.out.println("BaudRate: " + serialPort.getBaudRate());
		            System.out.println("DataBIts: " + serialPort.getDataBits());
		            System.out.println("StopBits: " + serialPort.getStopBits());
		            System.out.println("Parity: " + serialPort.getParity());
		            System.out.println("FlowControl: " + serialPort.getFlowControlMode());
		           // InputStream in = serialPort.getInputStream();
		            out = serialPort.getOutputStream();
//		            out.write(redBytes);
//		            out.write(greenBytes);
		        }
		    }
			}
			catch (Exception e)
			{
				System.out.print(e.getMessage());
			}
			
	}
	
	public void newGame()
	{
		if (!isAlive)
		{
			board=new TetrisBoard();
			setNewPiece();
			dropCount=5;
			isAlive=true;
			isPaused=false;
		}
	}
	
	public void pause()
	{
		isPaused=!isPaused;
	}
	
	public void printLEDNumbers()
	{
		//boolean prevPauseState=isPaused;
		//isPaused=true;
		
		int[][] boardArray=board.getBoard();
		int[][] pieceArray=currentPiece.getPiece();
		
		int redLedArray[]=new int[boardArray.length];
		int greenLedArray[]=new int[boardArray.length];
		for (int i=0;i<boardArray.length;i++)
		{
			int ledNum=0;
			for (int j=0;j<boardArray[i].length;j++)
			{
				if (boardArray[i][j]==1)
				{
					ledNum+=Math.pow(2,j);
				}
			}
			redLedArray[i]=ledNum;
		}
		for (int i=0;i<pieceArray.length;i++)
		{
			int bx=pieceCorner.getX()+i; //row
			boolean xInRange=!(bx >= boardArray.length || bx < 0);
			int ledNum=0;
			for (int j=0;j<pieceArray[i].length;j++)
			{
				int by=pieceCorner.getY()+j; //col
				boolean yInRange=!(by < 0 || by >= boardArray[0].length);
				if (!xInRange || !yInRange)
				{
					continue;
				}
				if (pieceArray[i][j]==1)
				{
					ledNum+=Math.pow(2,by);
					if (boardArray[bx][by]==1)
					{
						//greenLedArray[bx]+=Math.pow(2,by);
						redLedArray[bx]-=Math.pow(2,by);
					}
				}
			}
			//System.out.println(bx);
			if (xInRange)
			{
				greenLedArray[bx]=ledNum;
			}
		}
		//System.out.print("red:[");
		/*for (int num:redLedArray)
		{
			System.out.print(num+" ");
		}*/
		//System.out.print("]");
		byte redLedNums[]={(byte)0x80,(byte)0x90,(byte)0xA0,(byte)0xB0};
		byte greenLedNums[]={(byte)0xC0,(byte)0xD0,(byte)0xE0,(byte)0xF0};
		
		//String bytes[]=new String[24];
		byte[] redBytes=new byte[24];
		int index=0;
		for (int i=0;i<redLedArray.length;i++)
		{
			if (i%5==0)
			{
				//bytes[index]=redLedNums[i/5];
				redBytes[index]=redLedNums[i/5];
				index++;
			}
			redBytes[index]=(byte)redLedArray[i];
			index++;
		}
		
		byte[] greenBytes=new byte[24];
		index=0;
		for (int i=0;i<redLedArray.length;i++)
		{
			if (i%5==0)
			{
				//bytes[index]=redLedNums[i/5];
				greenBytes[index]=greenLedNums[i/5];
				index++;
			}
			greenBytes[index]=(byte)greenLedArray[i];
			index++;
		}
		//System.out.print("green:[");
		for (byte num:greenBytes)
		{
			System.out.print(num+" ");
		}
		//System.out.print("]");
		System.out.println();
		//System.out.print("green:[");
		for (byte num:redBytes)
		{
			System.out.print(num+" ");
		}
		//System.out.print("]");
		System.out.println();
		
		try
		{
			out.write(redBytes);
			out.write(greenBytes);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		//isPaused=prevPauseState;
	}
	
	private void setNewPiece()
	{
		currentPiece=getRandomPiece();
		
		int row=0;
		int col=2;
		int[][] pieceArray=currentPiece.getPiece();
		
		//sets corner so piece at the top
		boolean breakk=false;
		for (int i=0;i<pieceArray.length;i++)
		{
			for (int j=0;j<pieceArray[i].length;j++)
			{
				if (pieceArray[i][j]==1)
				{
					row-=i;
					//System.out.println(i+" "+j);
					breakk=true;
					break;
				}
			}
			if (breakk)
			{
				break;
			}
		}
		
		pieceCorner=new Point(row,col);
		//System.out.println(pieceCorner);
		//currentPiece.printPiece();
		
		isAlive=board.canFit(currentPiece.getPiece(), pieceCorner);
	}
	
	private TetrisPiece getRandomPiece()
	{
		TetrisPiece tp;
		Random r=new Random();
		int s=r.nextInt(7);
		
		switch (s)
		{
		case 0:
			tp=new IPiece();
			break;
		case 1:
			tp=new LeftLPiece();
			break;
		case 2:
			tp=new LeftZPiece();
			break;
		case 3:
			tp=new RightLPiece();
			break;
		case 4:
			tp=new RightZPiece();
			break;
		case 5:
			tp=new SqPiece();
			break;
		default:
			tp=new TPiece();
			break;
		}
		 int t=r.nextInt(4);
		 for (int i=0;i<t;i++)
		 {
			 tp.rotateForward();
		 }
		 return tp;
	}
	
	public void draw(Graphics g)
	{
		printLEDNumbers();
		if (isAlive && !isPaused)
		{
			advance();
		}
		else if (isAlive && isPaused)
		{
			g.setColor(Color.red);
			g.drawString("PAUSED", 200, 40);
		}
		else
		{
			g.setColor(Color.red);
			g.drawString("GAME OVER (press 'N' for new game)", 200, 40);
		}
		//board.draw(g);
		//drawCurrentPiece(g);
		g.setColor(Color.red);
		g.drawString("Score: "+Integer.toString(board.getScore()), 200, 475);
	}
	public void drawCurrentPiece(Graphics g)
	{
		int[][] piece=currentPiece.getPiece();
		for (int i=0;i<piece.length;i++)
		{
			for (int j=0;j<piece[i].length;j++)
			{
				int by=pieceCorner.getX()+i; //row
				int bx=pieceCorner.getY()+j; //col
				if (piece[i][j]==1)
				{
					g.setColor(Color.green);
					g.fillRect(10+bx*25, 10+by*25, 25, 25);
					g.setColor(Color.black);
					g.drawRect(10+bx*25, 10+by*25, 25, 25);
				}
			}
		}
	}
	private void advance()
	{
		dropCount++;
		if (dropCount==15)
		{
			Point check=new Point(pieceCorner.getX()+1,pieceCorner.getY());
			boolean isDropGood=board.canFit(currentPiece.getPiece(), check);
			if (!isDropGood)
			{
				board.addPiece(currentPiece.getPiece(),pieceCorner);
				setNewPiece();
			}
			else
			{
				pieceCorner=check;
			}
			dropCount=0;
		}
	}
	
	public void rotatePiece()
	{
		if (!isAlive || isPaused)
		{
			return;
		}
		currentPiece.rotateForward();
		boolean isRotationGood=board.canFit(currentPiece.getPiece(),pieceCorner);
		if (!isRotationGood)
		{
			currentPiece.rotateBackward();
		}
	}
	public void shiftPiece(int i)
	{
		if (!isAlive || isPaused)
		{
			return;
		}
		Point check=new Point(pieceCorner.getX(),pieceCorner.getY()+i);
		boolean isShiftGood=board.canFit(currentPiece.getPiece(), check);
		if (isShiftGood)
		{
			pieceCorner=check;
		}
	}
	public void fullDrop()
	{
		if (!isAlive || isPaused)
		{
			return;
		}
		boolean isDropGood=true;
		while (isDropGood)
		{
			Point check=new Point(pieceCorner.getX()+1,pieceCorner.getY());
			isDropGood=board.canFit(currentPiece.getPiece(), check);
			if (!isDropGood)
			{
				board.addPiece(currentPiece.getPiece(),pieceCorner);
				setNewPiece();
			}
			else
			{
				pieceCorner=check;
			}
		}
		dropCount=0;
	}
}
