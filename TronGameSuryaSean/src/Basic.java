import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.*;

public abstract class Basic {
	
	boolean running;		//declaring variables
	static boolean replay;
	JFrame gameFrame;
	protected static int x;
	protected static int y;
	

	public void stop(){		//stop method
		running = false;	// games stop running
	}
	

	public void run(){		//call initializer and gameloop
		
		try{
			initializer();	//jumps to public void initializer to setup the JFrame
			gameLoop();		//jumps to public void gameLoop to start the game
		}finally{
			if(!replay){	//if the user doesn't want to replay the program then
				System.exit(0);		//it exits the program
			}
		}
	}
	
	public void initializer(){
		
		gameFrame = new JFrame();	//creates a new JFrame 
		
		gameFrame.setUndecorated(true);		//so we can "decorate" the frame
		gameFrame.setIgnoreRepaint(true);	//repaints set by the windows programs will be ignored
		gameFrame.setResizable(false);	//the user can not resize the frame
		
		gameFrame.setSize(x, y);		//size of the JFrame
		gameFrame.setLocationRelativeTo(null);	/** search what this does **/
		
		gameFrame.setFont(new Font("Arial", Font.PLAIN, 24));	//sets the font and size of the words that are to be displayed
		gameFrame.setBackground(Color.BLACK);	//Color of the background
		gameFrame.setForeground(Color.WHITE);	
		gameFrame.setVisible(true);		//so we can see the JFrame
		
		gameFrame.createBufferStrategy(2);	//a new strategy for multi-buffering is set on this component
		replay = false;
		running = true;
	}
	
	static long sleep;		//the time break
	
	
	public void gameLoop(){	//main game loop
		
		while(running){		//while the game is still going
			Graphics2D graphics = (Graphics2D) gameFrame.getBufferStrategy().getDrawGraphics();
			draw(graphics);		//to initiate the code to draw the grid
			graphics.dispose();
			this.update();		//restarts the loop of the game, checks if the snake has eaten the food, itself and if any arrow was pressed for movement
			
			if(gameFrame != null){
				BufferStrategy s = gameFrame.getBufferStrategy();
				if(!s.contentsLost()){
					s.show();
				}
			}
			
			try{
				Thread.sleep(sleep);
			}catch(Exception alternate){}
		}
	}
	

	public abstract void update();	//update game
	
	
	public abstract void draw(Graphics2D graphics);	//draws to the screen
	

	public void removeCursor(){		//this method makes the cursor invisible when brought onto the frame of the game
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		gameFrame.setCursor(blankCursor);
	}
}