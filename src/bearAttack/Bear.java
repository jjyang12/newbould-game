package bearAttack;

import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
public class Bear {
	protected double x;
	protected double y;
	protected double xvel;
	protected double yvel;
	private boolean dead;
	private Rectangle r;
	private long timeOfDeath;
	protected boolean isboss;
	
	/**creates an evil bear that's only goal is to destroy Newbould.
	 */
	Bear(double xpos, double ypos, double xv, double yv)
	{
		x = xpos;
		y = ypos;
		xvel = xv;
		yvel = yv;
		dead = false;
		r = new Rectangle(x + 16, y + 10, 68, 93);
		timeOfDeath = -1;
	}
	

	//x value of bear
	public double getX(){
		return x;
	}
	
	//y value of bear
	public double getY(){
		return y;
	}
	
	//changes the x value
	public void setX(double newX){
		x = newX;
	}
	
	public void setY(double newY){
		y = newY;
	}
	
	//changes the y velocity
	public void setXVel(double newX){
		xvel = newX;
	}
	
	//gets the velocity
	public double getXVel(){
		return xvel;
	}
	
	//changes y velocity
	public void setYVel(double newY){
		yvel = newY;
	}
	
	//get the y velocity
	public double getYVel(){
		return yvel;
	}
	
	//bear moves
	public void step(){
		x += xvel;	
		y += yvel;
	}
	
	//what happens if a bear touches a corner
	public void rebound(){
		if (xvel > 0 && x > 1100){
			xvel = -1*xvel;
		}
		if (xvel < 0 && x < 0)
		{
			xvel = -1*xvel;
		}
		if (yvel > 0 && y > 692){
			yvel = -1*yvel;
		}
		if (yvel < 0 && y < 0){
			yvel = -1*yvel; 
		}
		
	}
	
	//kills the bear (can only happen once)
	public void kill(){
		if(!dead){
			timeOfDeath = (long) (System.currentTimeMillis() + 0.0);
			dead = true;
		}
	}
	
	//returns true if bear is dead
	public boolean isDead(){
		return dead;
	}
	
	//returns time of death
	public long whenDead(){
		return timeOfDeath;
	}
	
	//updates and returns rectangle
	public Rectangle getRekt(){
	    r.setX(x + 16);
	    r.setY(y + 10);
	    return r;
	}
}
