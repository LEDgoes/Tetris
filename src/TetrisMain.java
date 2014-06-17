

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class TetrisMain
{
    private static TetrisApplet foo;
   
    
   public static final int height=700;
   public static final int width=1100;
   //public static final int height=790;
   //public static final int width=1440;
    
   public static void main(String[] args) {
       //... Create an initialize the applet.
       foo = new TetrisApplet();
       foo.init();
       foo.start();

       JFrame window = new JFrame("Tetris!");
       final int contentBarHeight=22;
       window.setPreferredSize(new Dimension(width, height+contentBarHeight));
       window.setResizable(false);
       //window.setUndecorated(true);
       window.setContentPane(foo);
       window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       window.pack();
       window.setVisible(true);
   }
   
   public static TetrisApplet getMainApplet()
   {
       return foo;
   }

}

