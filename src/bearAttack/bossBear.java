package bearAttack;

import javafx.scene.shape.Rectangle;

public class bossBear extends Bear {
	private long hittimestamp;
	private int hp;
	private Rectangle r;

	/** The most evilist of evil bears and the crusher of trombones
	 * 
	 * @param x
	 * @param y
	 * @param xv
	 * @param yv
	 */
	bossBear(double x, double y, double xv, double yv){
		super(x, y, xv, yv);
		r = new Rectangle(x + 108, y + 35, 464,676);
		//HP
		hp = 30;
	}

	//updates and returns big bear rectangle
	@Override
	public Rectangle getRekt(){
		r.setX(x+108);
		r.setY(y+35);
		return r;
	}

	//bear moves forward
	@Override
	public void step(){
		if (getX() > 650){
			super.step();
		}
	}

	//Bear takes a hit
	public void hit(){
		if((System.currentTimeMillis() - hittimestamp)/1000.0 > 0.8){
			hp--;
			hittimestamp = (long) (System.currentTimeMillis() + 0.0);
		}
	}

	//returns hit timestamp
	public long lastHit(){
		return hittimestamp;
	}

	//returns health of bear
	public int health(){
		return hp;
	}

	//returns true if bear is dead
	public boolean isDead(){
		return hp <= 0;
	}
}
