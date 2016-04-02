import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Game extends Basic implements KeyListener {
	private static int difficulty = 0;
	static boolean ai = true;
	static int aiInt = 0;

	// main method
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception alternate){
			
		}
		
		Object[] opts = { "600X500", "700X600", "800X700", "900X800", "Exit" };	
		Object[] aiOpts = {"Human Vs Computer", "Human Vs Human"};//message box will ask for for the user to select difficulty
		 difficulty = JOptionPane.showOptionDialog(null, "Welcome to the Surya and Sean's Tron Game! \n You can select a resolution below \n \n \n", //this will be displayed in the box
				"Tron", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,		//title of the message box
				null, opts, opts);
		 if(difficulty != 4){
			 aiInt = JOptionPane.showOptionDialog(null, "Select Game Mode \n \n \n", //this will be displayed in the box
						"Tron", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,		//title of the message box
						null, aiOpts, aiOpts);
			 if(aiInt == 0)
				 ai = true;
			 else
				 ai = false;
		 }
		if (difficulty == 0) {		//if difficulty easy is selected then
			difficulty = 2;
			sleep = 62;			//length of break until loop restarts (in milliseconds)
			grid = true;			//the grid will be visible to guide the user's movements
			x = 600;
			y = 500;
			new Game().run();		//starts game initiation
			
		} else if (difficulty == 1) {		//if difficulty medium is selected then
			difficulty = 2;
			sleep = 59;			//length of break until loop restarts (in milliseconds)
			grid = true;			//the grid will be visible to guide the user's movements
			x = 700;
			y = 600;
			new Game().run();		//starts game initiation
			
		} else if (difficulty == 2) {		//if difficulty hard is selected then
			difficulty = 2;
			sleep = 53;			//length of break until loop restarts (in milliseconds)
			grid = true;			//the grid will be absent to increase difficulty
			x = 800;
			y = 700;
			new Game().run();		//starts game initiation
			
		} else if (difficulty == 3) {		//if difficulty extreme is selected then
			difficulty = 2;
			sleep = 48;			//length of break until loop restarts (in milliseconds)
			grid = true;			//the grid will be absent to increase difficulty
			x = 900;
			y = 800;
			new Game().run();		//starts game initiation
			
		} else {
			System.exit(0);		//exit game
		}
		
		if(replay){
			main(null);
		}
	}

	ArrayList<Rectangle> p1;
	ArrayList<Rectangle> p2;
	Rectangle food;		//declaring the food an area in coordinate space
	boolean up, down, left, right, p1Eaten, p1DirChanged, w, a, s, d, p2Eaten, p2DirChanged;		//Declaring booleans variables

	static boolean grid;		//Declaring grid variable
	int score;		//declaring score variable

	
	public void initializer() {		// initializer method
		super.initializer();
		gameFrame.addKeyListener(this);
		removeCursor();
		
		p1 = new ArrayList<Rectangle>();		//snake's Initial shape size
		p1.add(new Rectangle(x/4, y/2, 10, 10));
		p1.add(new Rectangle(x/4+1, y/2, 10, 10));
		p1.add(new Rectangle(-10, -10, 10, 10));
		
		p2 = new ArrayList<Rectangle>();		//snake's Initial shape size
		p2.add(new Rectangle(x - x/4, y/2, 10, 10));
		p2.add(new Rectangle((x - x/4)+1, y/2, 10, 10));
		p2.add(new Rectangle(-10, -10, 10, 10));

		left = true;
		d = true;
		p1Eaten = true;
		p2Eaten = true;
		score = 0;
	}


	public void update() {		// updates the game
		
		p1DirChanged = false;
		p2DirChanged = false;
		if(ai){
		int count = 0;
		for(int i = 0; i < p1.size(); i++){
			if(p1.get(i).getLocation().getY() == p2.get(0).getLocation().getY() + 20 && p1.get(i).getLocation().getX() == p2.get(0).getLocation().getX() && (up || down)){
				count++;
			}
			if(p1.get(i).getLocation().getY() == p2.get(0).getLocation().getY() - 20 && p1.get(i).getLocation().getX() == p2.get(0).getLocation().getX()  && (up || down)){
				count++;
			}	
			if(0 >= p2.get(0).getLocation().getY() - 20 && (up || down)){
				count++;
				//System.out.println("Top");
			}
			if(gameFrame.getSize().getHeight() <= p2.get(0).getLocation().getY() + 20 && (up || down)){
				count++;
				//System.out.println("Bottom");
			}
			if(p2.get(i).getLocation().getY() == p2.get(0).getLocation().getY() + 20 && p2.get(i).getLocation().getX() == p2.get(0).getLocation().getX() && (down)){
				count++;
			}
			if(p2.get(i).getLocation().getY() == p2.get(0).getLocation().getY() - 20 && p2.get(i).getLocation().getX() == p2.get(0).getLocation().getX()  && (up)){
				count++;
			}
		}
		//System.out.print(count);
		//System.out.println("  X");
		if(count > 0){
			int rightCount = 0;
			int leftCount = 0;
			for(int i = 0; i < p1.size(); i++){
				if(p2.get(i).getLocation().getX() == p2.get(0).getLocation().getX() - 20 && p2.get(i).getLocation().getY() == p2.get(0).getLocation().getY() && (up || down)){
					rightCount++;
					//System.out.println( "Self Right Count "+rightCount);
				}
				if(p1.get(i).getLocation().getX() == p2.get(0).getLocation().getX() - 20 && p1.get(i).getLocation().getY() == p2.get(0).getLocation().getY() && (up || down)){
					rightCount++;
					//System.out.println( "Other Right Count "+rightCount);
				}
				if(p2.get(i).getLocation().getX() == p2.get(0).getLocation().getX() + 20 && p2.get(i).getLocation().getY() == p2.get(0).getLocation().getY() && (up || down)){
					leftCount++;
					//System.out.println("Self Left Count "+leftCount);
				}
				if(p1.get(i).getLocation().getX() == p2.get(0).getLocation().getX() + 20 && p1.get(i).getLocation().getY() == p2.get(0).getLocation().getY() && (up || down)){
					leftCount++;
					//System.out.println("Other Left Count "+leftCount);
				}
			}
			if(0 >= p2.get(0).getLocation().getX() - 20 || rightCount > 0){
				if(!left && !p2DirChanged){
					p2DirChanged = true;		//the direction is to be changed
					up = false;			//all directions are false except right
					down = false;
					left = false;
					right = true;
					//System.out.println("right");
					}
			}else if(gameFrame.getSize().getWidth() <= p2.get(0).getLocation().getX() + 20 || leftCount > 0){
				if(!right && !p2DirChanged){
					p2DirChanged = true;		//the direction is to be changed
					up = false;			//all directions are false except left
					down = false;
					left = true;
					right = false;
					//System.out.println("left");
				}
			}else{
			if(Math.random() < 0.5){
				if(!right && !p2DirChanged){
					p2DirChanged = true;		//the direction is to be changed
					up = false;			//all directions are false except left
					down = false;
					left = true;
					right = false;
					//System.out.println("left");
				}
			}else{
				if(!left && !p2DirChanged){
					p2DirChanged = true;		//the direction is to be changed
					up = false;			//all directions are false except right
					down = false;
					left = false;
					right = true;
					//System.out.println("right");
				}
			}
		}
			count = 0;
			rightCount = 0;
			leftCount = 0;
		}
		
		count = 0;
		for(int i = 0; i < p1.size(); i++){
			if(p1.get(i).getLocation().getX() == p2.get(0).getLocation().getX() + 20 && p1.get(i).getLocation().getY() == p2.get(0).getLocation().getY() && (right || left)){
				count++;
			}
			if(p1.get(i).getLocation().getX() == p2.get(0).getLocation().getX() - 20 && p1.get(i).getLocation().getY() == p2.get(0).getLocation().getY()  && (right || left)){
				count++;
			}	
			if(0 >= p2.get(0).getLocation().getX() - 20 && (right || left)){
				count++;
			//	System.out.println("leftbounds");
			}
			if(gameFrame.getSize().getWidth() <= p2.get(0).getLocation().getX() + 20 && (right || left)){
				count++;
			//	System.out.println("RightBounds");
			}
			if(p2.get(i).getLocation().getX() == p2.get(0).getLocation().getX() + 20 && p2.get(i).getLocation().getY() == p2.get(0).getLocation().getY() && right){
				count++;
			}
			if(p2.get(i).getLocation().getX() == p2.get(0).getLocation().getX() - 20 && p2.get(i).getLocation().getY() == p2.get(0).getLocation().getY()  && left){
				count++;
			}
		}
		//System.out.print(count);
		//System.out.println("  Y");
		if(count > 0){
			int downCount = 0;
			int upCount = 0;
			for(int i = 0; i < p1.size(); i++){
				if(p2.get(i).getLocation().getY() == p2.get(0).getLocation().getY() - 20 && p2.get(i).getLocation().getX() == p2.get(0).getLocation().getX() && (left || right)){
					downCount++;
					//System.out.println("Self Down Count "+downCount);
				}
				if(p1.get(i).getLocation().getY() == p2.get(0).getLocation().getY() - 20 && p1.get(i).getLocation().getX() == p2.get(0).getLocation().getX() && (left || right)){
					downCount++;
					//System.out.println("Other Down Count "+downCount);
				}
				if(p2.get(i).getLocation().getY() == p2.get(0).getLocation().getY() + 20 && p2.get(i).getLocation().getX() == p2.get(0).getLocation().getX() && (left || right)){
					upCount++;
					//System.out.println("Self Up Count "+upCount);
				}
				if(p1.get(i).getLocation().getY() == p2.get(0).getLocation().getY() + 20 && p1.get(i).getLocation().getX() == p2.get(0).getLocation().getX() && (left || right)){
					upCount++;
					//System.out.println("Other Up Count "+upCount);
				}
			}
			if(0 >= p2.get(0).getLocation().getY() - 20 || downCount > 0){
				if(!up && !p2DirChanged){
					p2DirChanged = true;		//the direction is to be changed
					up = false;			//all directions are false except right
					down = true;
					left = false;
					right = false;
					//System.out.println("down");
					}
			}else if(gameFrame.getSize().getHeight() <= p2.get(0).getLocation().getY() + 20 || upCount > 0){
				if(!down && !p2DirChanged){
					p2DirChanged = true;		//the direction is to be changed
					up = true;			//all directions are false except left
					down = false;
					left = false;
					right = false;
					//System.out.println("up");
				}
			}else{
			
				if(Math.random() < 0.5){
					if(!down && !p2DirChanged){
						p2DirChanged = true;		//the direction is to be changed
						up = true;			//all directions are false except left
						down = false;
						left = false;
						right = false;
						//System.out.println("up");
					}
				}else{
					if(!up && !p2DirChanged){
						p2DirChanged = true;		//the direction is to be changed
						up = false;			//all directions are false except right
						down = true;
						left = false;
						right = false;
						//System.out.println("down");
					}
				}
			}
			count = 0;
			downCount = 0;
			upCount = 0;
		}
	}


		// Collision between p1 and itself
		for (int i = 0; i < p1.size(); i++) {
			for (int j = i + 1; j < p1.size(); j++) {
				if (p1.get(i).getLocation().x == p1.get(j).getLocation().x
						&& p1.get(i).getLocation().y == p1.get(j)
				.getLocation().y) {
					stop();		//exits out of the loop if the snake ate itself
					gameFrame.setVisible(false);
					JOptionPane.showMessageDialog(null, "Player 2 Wins",		//displays how much the user scored is a message box
							"Tron", JOptionPane.PLAIN_MESSAGE);
					replay = true;		//replays the game
				}
			}
		}
		
		// Collision between p2 and itself
		for (int i = 0; i < p2.size(); i++) {
			for (int j = i + 1; j < p2.size(); j++) {
				if (p2.get(i).getLocation().x == p2.get(j).getLocation().x
						&& p2.get(i).getLocation().y == p2.get(j)
				.getLocation().y) {
					System.out.println("Self");
					stop();		//exits out of the loop if the snake ate itself
					gameFrame.setVisible(false);
					JOptionPane.showMessageDialog(null, "Player 1 Wins",		//displays how much the user scored is a message box
							"Tron", JOptionPane.PLAIN_MESSAGE);
					replay = true;		//replays the game
				}
			}
		}
		
		// Collision between p1 and p2
		for (int i = 0; i < p2.size(); i++){
			if (p1.get(0).getLocation().x == p2.get(i).getLocation().x
					&& p1.get(0).getLocation().y == p2.get(i)
			.getLocation().y) {	
				System.out.println("Together");
				stop();		//exits out of the loop if the snake ate itself
				gameFrame.setVisible(false);
				JOptionPane.showMessageDialog(null, "Player 2 Wins",		//displays how much the user scored is a message box
						"Tron", JOptionPane.PLAIN_MESSAGE);
				replay = true;		//replays the game
			}
		}
		// Collision between p2 and p1
		for (int i = 0; i < p1.size(); i++){
			if (p2.get(0).getLocation().x == p1.get(i).getLocation().x
					&& p2.get(0).getLocation().y == p1.get(i)
			.getLocation().y) {	
				System.out.println("Together");
				stop();		//exits out of the loop if the snake ate itself
				//gameFrame.setVisible(false);
				JOptionPane.showMessageDialog(null, "Player 1 Wins",		//displays how much the user scored is a message box
						"Tron", JOptionPane.PLAIN_MESSAGE);
				replay = true;		//replays the game
			}
		}
		
		// movement for p1
		for (int i = p1.size() - 1; i >= 0; i--) {
			
			if (w) {		//if up arrow was clicked
				try {		//checks if the snake is not already moving in the upwards direction
					p1.get(i).setLocation(p1.get(i - 1).x,
							p1.get(i - 1).y);
				} catch (Exception alternate ) {		//if not, then it will change the direction of the snake to move upwards
					if (p1.get(i).getLocation().y > 0) {
						p1.get(i).setLocation(p1.get(i).x,
								p1.get(i).y - 10);
					} else if ( (difficulty == 0) || (difficulty == 1)) {		// if difficulty is set to easy or medium then	
						p1.get(i).setLocation(p1.get(i).x,
								gameFrame.getHeight() - 10);		// snake will pop up in the other side of the frame, if hard or extreme, then you die
					}

					p1.add(new Rectangle(-10, -10, 10, 10));
				}
				
			} else if (s) {		//if down arrow was clicked
				try {			//checks if the snake is not already moving in the downwards direction
					p1.get(i).setLocation(p1.get(i - 1).x,
							p1.get(i - 1).y);
				} catch (Exception alternate) {		//if not, then it will change the direction of the snake to move downwards
					if (p1.get(i).getLocation().y < gameFrame.getHeight() - 10) {
						p1.get(i).setLocation(p1.get(i).x,	
								p1.get(i).y + 10);
					} else if ( (difficulty == 0) || (difficulty == 1)) {		// if difficulty is set to easy or medium then		
						p1.get(i).setLocation(p1.get(i).x, 0);		// snake will pop up in the other side of the frame, if hard or extreme, then you die
					}

					p1.add(new Rectangle(-10, -10, 10, 10));
				}
				
			} else if (a) {		//if left arrow was clicked
				try {			//checks if the snake is not already moving in the left direction
					p1.get(i).setLocation(p1.get(i - 1).x,
							p1.get(i - 1).y);
				} catch (Exception alternate) {		//if not, then it will change the direction of the snake to move left
					if (p1.get(i).getLocation().x > 0) {
						p1.get(i).setLocation(p1.get(i).x - 10,
								p1.get(i).y);
					} else if ( (difficulty == 0) || (difficulty == 1)) {	// if difficulty is set to easy or medium then			
						p1.get(i).setLocation(gameFrame.getWidth() - 10,
								p1.get(i).y);		// snake will pop up in the other side of the frame, if hard or extreme, then you die
					}

					p1.add(new Rectangle(-10, -10, 10, 10));
				}
				
			} else if (d) {		//if right arrow was clicked
				try {			//checks if the snake is not already moving in the right direction
					p1.get(i).setLocation(p1.get(i - 1).x,
							p1.get(i - 1).y);
				} catch (Exception alternate) {		//if not, then it will change the direction of the snake to move right
					if (p1.get(i).getLocation().x < gameFrame.getWidth() - 10) {
						p1.get(i).setLocation(p1.get(i).x + 10,
								p1.get(i).y);
					} else if ( (difficulty == 0) || (difficulty == 1)) {	// if difficulty is set to easy or medium then	
						p1.get(i).setLocation(0, p1.get(i).y);		// snake will pop up in the other side of the frame, if hard or extreme, then you die
					}
					p1.add(new Rectangle(-10, -10, 10, 10));
				}
			}
		}
		
		
		// movement for p2
		for (int i = p2.size() - 1; i >= 0; i--) {
			
			if (up) {		//if up arrow was clicked
				try {		//checks if the snake is not already moving in the upwards direction
					p2.get(i).setLocation(p2.get(i - 1).x,
							p2.get(i - 1).y);
				} catch (Exception alternate ) {		//if not, then it will change the direction of the snake to move upwards
					if (p2.get(i).getLocation().y > 0) {
						p2.get(i).setLocation(p2.get(i).x,
								p2.get(i).y - 10);
					} else if ( (difficulty == 0) || (difficulty == 1)) {		// if difficulty is set to easy or medium then	
						p2.get(i).setLocation(p2.get(i).x,
								gameFrame.getHeight() - 10);		// snake will pop up in the other side of the frame, if hard or extreme, then you die
					}

					p2.add(new Rectangle(-10, -10, 10, 10));
				}
				
			} else if (down) {		//if down arrow was clicked
				try {			//checks if the snake is not already moving in the downwards direction
					p2.get(i).setLocation(p2.get(i - 1).x,
							p2.get(i - 1).y);
				} catch (Exception alternate) {		//if not, then it will change the direction of the snake to move downwards
					if (p2.get(i).getLocation().y < gameFrame.getHeight() - 10) {
						p2.get(i).setLocation(p2.get(i).x,	
								p2.get(i).y + 10);
					} else if ( (difficulty == 0) || (difficulty == 1)) {		// if difficulty is set to easy or medium then		
						p2.get(i).setLocation(p2.get(i).x, 0);		// snake will pop up in the other side of the frame, if hard or extreme, then you die
					}

					p2.add(new Rectangle(-10, -10, 10, 10));
				}
				
			} else if (left) {		//if left arrow was clicked
				try {			//checks if the snake is not already moving in the left direction
					p2.get(i).setLocation(p2.get(i - 1).x,
							p2.get(i - 1).y);
				} catch (Exception alternate) {		//if not, then it will change the direction of the snake to move left
					if (p2.get(i).getLocation().x > 0) {
						p2.get(i).setLocation(p2.get(i).x - 10,
								p2.get(i).y);
					} else if ( (difficulty == 0) || (difficulty == 1)) {	// if difficulty is set to easy or medium then			
						p2.get(i).setLocation(gameFrame.getWidth() - 10,
								p2.get(i).y);		// snake will pop up in the other side of the frame, if hard or extreme, then you die
					}

					p2.add(new Rectangle(-10, -10, 10, 10));
				}
				
			} else if (right) {		//if right arrow was clicked
				try {			//checks if the snake is not already moving in the right direction
					p2.get(i).setLocation(p2.get(i - 1).x,
							p2.get(i - 1).y);
				} catch (Exception alternate) {		//if not, then it will change the direction of the snake to move right
					if (p2.get(i).getLocation().x < gameFrame.getWidth() - 10) {
						p2.get(i).setLocation(p2.get(i).x + 10,
								p2.get(i).y);
					} else if ( (difficulty == 0) || (difficulty == 1)) {	// if difficulty is set to easy or medium then	
						p2.get(i).setLocation(0, p2.get(i).y);		// snake will pop up in the other side of the frame, if hard or extreme, then you die
					}
					p2.add(new Rectangle(-10, -10, 10, 10));
				}
			}
		}
	}


	// draw method
	public void draw(Graphics2D graphics) {	//setting up the graphics for the game
		
		graphics.setColor(Color.BLACK);		//the background color of the frame
		graphics.fillRect(0, 0, gameFrame.getWidth(), gameFrame.getHeight());		//so the color fills the entire background of the frame

		graphics.setColor(Color.GRAY);		//color of the grid
		if(grid){		//uses loops to draw the lines of the grid
			for (int x = 0; x < gameFrame.getWidth() / 10; x++) {
				for (int y = 0; y < gameFrame.getHeight() / 10; y++) {
					graphics.drawRect(x * 10, y * 10, 10, 10);
				}
			}
		}
		
		if (!p1Eaten) {
			graphics.fill(food);		//jumps to the food method
		}
		
		graphics.setColor(Color.BLUE);		//color of words and snake
		for (int i = p1.size() - 1; i >= 0; i--) {
			graphics.fill(p1.get(i));		//white color is filled into the snake pieces
		}
		graphics.setColor(Color.RED);	
		for (int i = p2.size() - 1; i >= 0; i--) {
			graphics.fill(p2.get(i));		//white color is filled into the snake pieces
		}
		graphics.setColor(Color.WHITE);	
		graphics.setFont(new Font("Dialog", Font.PLAIN, 16));
		graphics.drawString("Score: " + score, 2, 17);		//sets the location and displays the score on the frame
		graphics.drawString("Press Escape to quit", 450, 17);		//sets the location and displays the keys to exit the game
		if(!up && !down && !left && !right){	//checks as long as any directional keys aren't pressed
			graphics.drawString("Press Up, down, left or right to start game", 130, 17);	//sets the location and displays the directions of the game
		}
	}
	
	public void keyPressed(KeyEvent alternate) {	// Key listeners
		if(!ai){
		if (alternate.getKeyCode() == KeyEvent.VK_ESCAPE) {		//if escape on the keyboard is pressed then
			stop();		//quite the game
		}	
		
			if (alternate.getKeyCode() == KeyEvent.VK_UP && !down && !p2DirChanged) {	//if up key is pressed then
			p2DirChanged = true;		//the direction is to be changed
			up = true;			//all directions are false except up
			down = false;
			left = false;
			right = false;
			
		} else if (alternate.getKeyCode() == KeyEvent.VK_DOWN && !up && !p2DirChanged) {	//if down key is pressed then
			p2DirChanged = true;		//the direction is to be changed
			up = false;			//all directions are false except down
			down = true;
			left = false;
			right = false;
			
		} else if (alternate.getKeyCode() == KeyEvent.VK_LEFT && !right && !p2DirChanged) {	//if left key is pressed then
			p2DirChanged = true;		//the direction is to be changed
			up = false;			//all directions are false except left
			down = false;
			left = true;
			right = false;
			
		} else if (alternate.getKeyCode() == KeyEvent.VK_RIGHT && !left && !p2DirChanged) {	//if right key is pressed then
			p2DirChanged = true;		//the direction is to be changed
			up = false;			//all directions are false except right
			down = false;
			left = false;
			right = true;
		}
		}
		
		if (alternate.getKeyCode() == KeyEvent.VK_W && !s && !p1DirChanged) {	//if up key is pressed then
			p1DirChanged = true;		//the direction is to be changed
			w = true;			//all directions are false except up
			s = false;
			a = false;
			d = false;
			
		} else if (alternate.getKeyCode() == KeyEvent.VK_S && !w && !p1DirChanged) {	//if down key is pressed then
			p1DirChanged = true;		//the direction is to be changed
			w = false;			//all directions are false except down
			s = true;
			a = false;
			d = false;
			
		} else if (alternate.getKeyCode() == KeyEvent.VK_A && !d && !p1DirChanged) {	//if left key is pressed then
			p1DirChanged = true;		//the direction is to be changed
			w = false;			//all directions are false except left
			s = false;
			a = true;
			d = false;
			
		} else if (alternate.getKeyCode() == KeyEvent.VK_D && !a && !p1DirChanged) {	//if right key is pressed then
			p1DirChanged = true;		//the direction is to be changed
			w = false;			//all directions are false except right
			s = false;
			a = false;
			d = true;
		}
		
	}

	public void keyReleased(KeyEvent alternate) {
	}

	public void keyTyped(KeyEvent alternate) {
	}
}