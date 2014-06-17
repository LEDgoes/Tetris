
import java.awt.event.KeyEvent;
import java.applet.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.util.ArrayList;

public class Keyboard implements KeyListener {
	
	   public void keyTyped(KeyEvent q)
	   {
		   
	   }
	   public void keyReleased(KeyEvent q)
	   {
//		   int keyCode=q.getKeyCode();
//		   if (keyCode==q.VK_UP)
//		   {
//			   TetrisMain.getMainApplet().getGame().rotatePiece(true);
//		   }
//		   if (keyCode==q.VK_UP)
//		   {
//			   TetrisMain.getMainApplet().getGame().rotatePiece(true);
//		   }
		  
	   }
	   public void keyPressed(KeyEvent q)
	   {
		   int keyCode=q.getKeyCode();
		   if (keyCode==q.VK_UP)
		   {
			   TetrisMain.getMainApplet().getGame().rotatePiece();
		   }
		   if (keyCode==q.VK_LEFT)
		   {
			   TetrisMain.getMainApplet().getGame().shiftPiece(-1);
		   }
		   if (keyCode==q.VK_RIGHT)
		   {
			   TetrisMain.getMainApplet().getGame().shiftPiece(1);
		   }
		   if (keyCode==q.VK_SPACE)
		   {
			   TetrisMain.getMainApplet().getGame().fullDrop();
		   }
		   if (keyCode==q.VK_P)
		   {
			   TetrisMain.getMainApplet().getGame().pause();
		   }
		   if (keyCode==q.VK_5)
		   {
			   TetrisMain.getMainApplet().getGame().printLEDNumbers();
		   }
		   if (keyCode==q.VK_N)
		   {
			   TetrisMain.getMainApplet().getGame().newGame();
		   }
	   }
}
