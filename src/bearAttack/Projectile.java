package bearAttack;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Projectile {
	private double x;
	private  double y;
	private double v;
	private Rectangle r;
	private Image pjt;

	/**A dangerous weapon forged in the fiery depth of hell
	 * 
	 * @param xpos
	 * @param ypos
	 * @param velocity
	 * @param im
	 */
	Projectile(double xpos, double ypos, int velocity, Image im){
		x = xpos;
		y = ypos;
		v = velocity;
		pjt = im;
		r = new Rectangle(x, y + 11, 8, 5);

	}

	//returns x coordinate
	public double getX(){
		return x;
	}

	//returns y coordinate
	public double getY(){
		return y;
	}

	//moves the projectile position
	public void step(){
		x += v;	}

	//returns image
	public Image getImage(){
		return pjt;
	}

	//draws projectile hitbox
	public void drawHitbox(GraphicsContext gc){
		gc.setStroke(Color.RED);
		gc.strokeRect(x + 5, y , 7, 13);
	}

	//updates and returns rectangle
	public Rectangle getRekt(){
		r.setX(x);
		r.setY(y + 11);
		return r;
	}

}

