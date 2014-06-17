import java.awt.*;

/*import AppletUpdater;
import Circle;
import Game;
import Mouse;*/

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class TetrisApplet extends Applet {

    public static int width, height;
    int i, ticker;
    public Thread t;
    private Image offScreenImage;
    private Graphics offScreenGraphics;
    TetrisRunner ticktock;
   // Mouse mm=new Mouse();
   Keyboard kk=new Keyboard();
   TetrisController oc;
    
    private int mx,my;
    public void incrementTicker()
    {
        ticker++;
    }
    public TetrisController getGame()
    {
    	return oc;
    }
    // Executed when the applet is first created.
    
    public TetrisController getController()
    {
    	return oc;
    }
    public TetrisRunner getMainThread()
    {
    	return ticktock;
    }
    public void init() {
        //addMouseListener(mm);
        //addMouseMotionListener(mm);
        setFocusable(true);
        addKeyListener(kk);
        oc=new TetrisController();
        
        System.out.println("init(): begin");
        width = getSize().width;
        height = getSize().height;
        ticker = 0;
        setBackground(Color.black);
        System.out.println("init(): end");
        i = 0;
        t = null;
        ticktock = new TetrisRunner();
        mx=0;
        my=0;
    }

    // Executed whenever the applet is asked to redraw itself.
    public void paint(Graphics g) {
        /*incrementTicker();
        int contentBarHeight=22;
        if (offScreenImage == null) {

            offScreenImage = (BufferedImage) createImage(1500, 750+contentBarHeight);
        }
        offScreenGraphics = offScreenImage.getGraphics();
        offScreenGraphics.setColor(Color.red);

        offScreenGraphics.clearRect(0, 0, 1500, 750+contentBarHeight);
        //System.out.println(ticker);
        
        //Do stuff
        //cc.draw(offScreenGraphics);
        //pp.draw(offScreenGraphics);
        //End do stuff
        
        g.drawImage(offScreenImage, 0, 0, this);*/
    	
    	if (offScreenImage == null) 
 	   {
            offScreenImage = (BufferedImage) createImage(2000,2000);
        }
        offScreenGraphics = offScreenImage.getGraphics();
        offScreenGraphics.setColor(Color.red);
        offScreenGraphics.clearRect(0, 0, 2000, 2000);
        
        oc.draw(offScreenGraphics);
        
        g.drawImage(offScreenImage, 0, 0, this);
        
    }

    public void update(Graphics g) {
        paint(g);
    }
    
    
    
    public void start() 
    {
 	   if (t == null) 
       {
 		  t = new Thread(ticktock);
          //System.out.println("start(): starting thread");
          ticktock.unsuspend();
          t.start();
 		   
       }
 	  else {
          if (ticktock.isSuspended()) {
              ticktock.unsuspend();
              //System.out.println("start(): notifying thread");
              synchronized (ticktock) {
                  notify();
              }
          }
      }
    }

    // Executed whenever the browser leaves the page containing the applet.
    public void stop() {
        //System.out.println("stop(): begin");
        ticktock.suspend();
    }
}
