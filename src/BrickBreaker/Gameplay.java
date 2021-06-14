package BrickBreaker;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import javax.swing.*;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
	
public static final long serialVersionUID = 2L; // Prevents seriable class warning
//Score
public int score = 0;
public int best;

//Count
public int c = 0;
public int fail = 0;
public int totalBricks = 0;
public int row = 3;
public int level = 1;

//Check
public boolean play = false;
public boolean f = false;
public boolean enterLKey = false;
public boolean move = false;
public boolean gameOver = false;

//Ball Movement & Speed
private Timer timer;
private int delay = 10;
private int playerX = 310;
private int ballposX = 350;
private int ballposY = 530;
//private int ballXdir = -1;
//private int ballYdir = -2;

//we shan't use x velocity or y velocity, instead, we'll use velocity and direction
private int ballvel = 0;
private double balldir = 0;

private MapGenerator map;

public Gameplay() {
	//Brick Creation
	if(f==false)
    map = new MapGenerator(row, 7);
	addKeyListener(this);
	//Component Focus
	setFocusable(true);
	setFocusTraversalKeysEnabled(false);
	//Setting Timer
    timer = new Timer(delay,this);
    timer.start();
}

	public void paint(Graphics g) {
	//Introduction To Game
	if(f==false) {
		move = false;
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		g.setColor(Color.blue);
		g.setFont(new Font("Segoe Print", Font.BOLD, 70));
		g.drawString("Welcome To", 150, 290);
		g.setColor(Color.orange);
		g.setFont(new Font("Candara", Font.BOLD, 90));
		g.drawString("BRICK BREAKER", 35, 390);
		g.setFont(new Font("Candara", Font.BOLD, 35));
		g.setColor(Color.green);
		g.drawString("PRESS [Enter]", 260, 510);
	}
	
	//Introduction To Level With Tips
	if(f==true) {
		move = false;
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//switch!
		switch(level) {
			case 1:
				g.setColor(Color.red);
				g.setFont(new Font("Lucida Console", Font.BOLD, 25));
				g.drawString("Use Left/Right Arrow Keys Or Spacebar To", 25, 450);
				g.drawString("Start The Game . ", 25, 480);
				g.setColor(Color.blue);
				break;
		
			case 2:
				g.setColor(Color.yellow);
				g.setFont(new Font("Lucida Console", Font.BOLD, 25));
				g.drawString("Anticipate The Location Of Ball .", 25, 450);
				g.setColor(Color.green);
				break;
			
			case 3:
				g.setColor(Color.green);
				g.setFont(new Font("Lucida Console", Font.BOLD, 25));
				g.drawString("Don't Panic , Play Patiently . ", 25, 450);
				g.setColor(Color.magenta);
				break;
			
			case 4:
				g.setColor(Color.magenta);
				g.setFont(new Font("Lucida Console", Font.BOLD, 25));
				g.drawString("Challenge Yourself , Try To Beat Your", 25, 450);
				g.drawString("Own High Score .", 25, 480);
				g.setColor(Color.yellow);
				break;
			
			default:
				g.setColor(Color.blue);
				g.setFont(new Font("Lucida Console", Font.BOLD, 25));
				g.drawString("Stay Calm , Concentrate & Win The Game .", 25, 450);
				g.setColor(Color.red);
		}
		
		g.setFont(new Font("Lucida Console", Font.BOLD, 70));
		g.drawString("LEVEL "+level, 210, 300);
		g.setFont(new Font("Candara", Font.BOLD, 35));
		g.drawString("Are You Ready ?", 250, 350);
		g.setFont(new Font("Candara", Font.BOLD, 35));
		if(level==1)
		g.drawString("PLAY [ENTER]", 260, 510);
		else
	    g.drawString("CONTINUE [ENTER]", 225, 510);
		g.setColor(Color.yellow);
		g.drawString("TIPS :", 25, 420);
		g.setFont(new Font("Candara", Font.BOLD, 35));
		
	}
	
	//Background
	if(enterLKey==true) {
		move = true;
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
	}
	
	//Levels
	if(level<=5) {
		switch(level) {
			case 1:
				g.setColor(Color.blue);
				delay--;
				break;
				
			case 2:
				g.setColor(Color.green);
				delay--;
				break;
				
			case 3:
				g.setColor(Color.magenta);
				delay--;
				break;
				
			case 4:
				g.setColor(Color.yellow);
				delay--;
				break;
				
			default:
				g.setColor(Color.red);
				delay--;
		}
		
		g.setFont(new Font("Lucida Console", Font.BOLD, 25));
		g.drawString("LEVEL "+level, 80, 30);
	}
	
	//Drawing Map
	map.color(level);
    map.draw((Graphics2D)g);
	
	//Paddle
    switch(level) {
	    case 1:
	    g.setColor(Color.green);
	    break;
	    
	    case 2:
	    g.setColor(Color.cyan);
	    break;
	    
	    case 3:
		g.setColor(Color.blue);
		break;
		
	    case 4:
	    	g.setColor(Color.orange);
		break;
		
		default:
			g.setColor(Color.red);
    }
    g.fillRect(playerX, 550, 100, 8);
	
	//Ball
	g.setColor(Color.white);
	g.fillOval(ballposX, ballposY, 20, 20);
	
	//Game Over
	if(ballposY > 600 && level<=5) {
		//Stopping Game
		move = false;
		play = false;
		//ballXdir=0;
		//ballYdir=0;
		ballvel = 0;
		balldir = 0;
		gameOver = true;
		
		g.setColor(Color.red);
		g.setFont(new Font("Candara", Font.BOLD, 75));
		g.drawString(" GAME OVER ", 150, 320);
		g.setFont(new Font("Lucida Console", Font.BOLD, 35));
		g.drawString("Scores : "+score, 180, 250);
		g.setFont(new Font("Lucida Console", Font.BOLD, 40));
		g.drawString("BEST : "+best, 240, 370);
		g.setFont(new Font("Lucida Console", Font.BOLD, 45));
		g.drawString("LEVEL "+level+" Failed", 165, 430);
		g.setColor(Color.blue);
		g.setFont(new Font("Candara", Font.BOLD, 35));
		g.drawString("PLAY AGAIN [Enter] ", 208, 510);
		
		//Initializing Elements
		level = 1;
		row = 3;
		delay = 10;
	}
	
	//Scores
		if(gameOver==true && play==true)
		score = 0;
		g.setColor(Color.yellow);
		g.setFont(new Font("Lucida Console", Font.BOLD, 25));
		g.drawString(""+score, 590, 30);
	
	//Won
	if(level>5) {
		//Stopping Game
		move = false;
		play = false;
		//ballXdir=0;
		//ballYdir=0;
		ballvel = 0;
		balldir = 0;
		ballposY=600;
		
		g.setColor(Color.yellow);
		g.setFont(new Font("Candara", Font.BOLD, 90));
		g.drawString(" YOU WON ", 190, 330);
		g.setFont(new Font("Segoe Print", Font.BOLD, 50));
		g.drawString("OMG!!!...", 200, 250);
		g.setColor(Color.blue);
		g.setFont(new Font("Candara", Font.BOLD, 35));
		g.drawString("PLAY AGAIN [Enter]", 205, 510);
		
		//Initialising Elements
		row = 3;
		level = 1;
		delay = 10;
		c = 0;
		f = false;
		enterLKey = false;
		move = false;
		if(play==true) {
			score = 0;
			fail = 0;
			best = 0;
		}
	}
		
	//Dispose Graphics
	g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Starting Timer
		timer.start();
		
		//Bar Intersection
		if(play) {
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 10))) {
				//ballYdir = -ballYdir;
				double xdiff = ballposX - (playerX + 40.0d);
				double ydiff = 550d - ballposY;
				double theta_diff = Math.PI/36;
				
				balldir = Math.atan(ydiff/xdiff);
				
				// atan can return -ve angles, so correct it to reflect going left
				if(balldir < 0)
					balldir += Math.PI;
				
				// prevent ball from getting near perpendicular direction
				if((balldir < 19*theta_diff) && (balldir > 17*theta_diff))
				{
					if(balldir > Math.PI/2)
						balldir = 20*theta_diff;
					else
						balldir = 15*theta_diff;
				}
			}
			
			/*if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX+65, 550, 50, 10)))
			ballYdir = -ballYdir;*/
			
			//Brick Intersection
		A:	for(int i=0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j] > 0) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						//Brick Intersection Consequence
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							fail++;
							score+=57;
							if(score>=9975)
							score=10000; 
							if(fail==1 || score>best)
							best = score;
							if(totalBricks <= 0) {
								row++;
								level++;
								ballposX=350;
								ballposY=530;
								c = 0;
								enterLKey = false;
								play = false;
								f = true;
								
								if(gameOver==true)
								{
									if(fail==1)
									best = score;
									if(score>best)
									best = score;
								}
								
							}
							if( ((ballposX + 19 - ballvel) <= brickRect.x) || ((ballposX + 1 + ballvel) >= (brickRect.x + brickRect.width)) ) {
								//ballXdir = -ballXdir;
								balldir = Math.PI - balldir;
								
							}
							else {
								//ballYdir = -ballYdir;
								balldir = 2*Math.PI - balldir;
							}
							if(balldir >= 2*Math.PI)
								balldir -= 2*Math.PI;
							else if(balldir < 0)
								balldir += 2*Math.PI;
							break A;
						}
					}	
				}
			}
			ballposX+=Math.round(ballvel*Math.cos(balldir));
			ballposY-=Math.round(ballvel*Math.sin(balldir));
			//ballposX+=ballXdir;
			//ballposY+=ballYdir;
			
			/*if(ballposX < 0)
			ballXdir = -ballXdir;
			if(ballposY < 0)
			ballYdir = -ballYdir;
			if(ballposX > 670)
			ballXdir = -ballXdir;*/
			
			if((ballposX < 0) || (ballposX > 670))
				balldir = Math.PI - balldir;
			if(ballposY < 0)
				balldir = 2*Math.PI - balldir;
			if(balldir >= 2*Math.PI)
				balldir -= 2*Math.PI;
			else if(balldir < 0)
				balldir += 2*Math.PI;
		}
        repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(playerX > 570){
				playerX = 585;
			} else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(playerX < 25){
				playerX = 0;
			} else {
				moveLeft();
			}
	    }
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = false;
				delay = 10;
				ballposX=350;
				ballposY=530;
				//ballXdir=-1;
				//ballYdir=-2;
				ballvel = 3;
				balldir = 2.0*Math.PI/3.0;
				playerX = 310;
				totalBricks = row*7;
				map = new MapGenerator(row, 7);
				
				//Game Restarting
				//Scores
				if(gameOver==true && play==false)
				score = 0;
				gameOver=false;
				f = true;
				c++;
				if(c!=1) {
					enterLKey = true;
				}
				repaint();
			}
		}
	}
	public void moveRight() {
		if(move==true) {
			play = true;
			playerX+=25;
		}
	}
	public void moveLeft() {
		if(move==true) {
			play = true;
			playerX-=25;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
