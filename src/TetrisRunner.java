
public class TetrisRunner implements Runnable{
	private boolean threadSuspended;
    private TetrisApplet main;
	
	public TetrisRunner()
    {
        super();
        main=TetrisMain.getMainApplet();
    }
	public void run() 
	{
	   try 
	   {
	      while (true) 
	      {
	    	  if (threadSuspended) {
                  synchronized (this) {
                      while (threadSuspended) {
                          //System.out.println("run(): waiting");
                          wait();
                      }
                  }
              }
	            main.repaint();
	            main.t.sleep(17);    
	      }
	   }
	   catch (InterruptedException e) { }
	}
	public boolean isSuspended() {
		return threadSuspended;
	}  
	public void suspend()
	{
		threadSuspended=true;
	}   
	public void unsuspend()
	{
		threadSuspended=false;
	}
}

