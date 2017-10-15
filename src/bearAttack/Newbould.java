package bearAttack;

import javafx.scene.shape.Rectangle;

public class Newbould {
    private int ammo;
    private long reloadtimestamp;
    private long hittimestamp;
    private Rectangle r;
    private int hp;
    
    private double x;
    private double y;

    /**The protagonist of this epic story
     * 
     * @param myX
     * @param myY
     */
    Newbould(double myX, double myY){
    	x = myX;
    	y = myY;
    	r = new Rectangle(x + 5, y, 65,255); 
    	ammo = 6;
    	reloadtimestamp = (long) (System.currentTimeMillis() + 0.0);
    	hittimestamp = (long) (System.currentTimeMillis() + 0.0);
    	hp = 10;
    }
    
    //returns x pos
    public double getX(){
    	return x;
    }
    
    //returns y pos
    public double getY(){
    	return y;
    }
    
    //sets x pos
    public void setX(double newx){
    	x = newx;
    }
    
    //sets y pos
    public void setY(double newy){
    	y = newy;
    }
    
    //updates and returns rectangle
    public Rectangle getRekt(){
    	r.setX(x + 5);
    	r.setY(y);
    	return r;
    }
    
    //reloads his trombone (ammo goes to six and there is a timer)
    public void reload(){
    	ammo = 6;
    	reloadtimestamp = (long) (System.currentTimeMillis() + 0.0);
    }
    
    //returns last reload time
    public long reloadTime(){
    	return reloadtimestamp;
    }
    
    //newbould shoots
    public void shoot(){
    	if(ammo > 1){
    		ammo--;
    	}
    	else{
    		reload();
    	}
    }
    
    //returns newboulds ammo
    public int ammoCount(){
    	return ammo;
    }
    
    //newbould is hit (can only be hit every 0.5s)
    public void hit(){
    	if((System.currentTimeMillis() - hittimestamp)/1000.0 > 0.5){
 
    			hp--;
    	
    		hittimestamp = (long) (System.currentTimeMillis() + 0.0);
    	}
    }
    
    //last time newbould was hit
    public long lastHit(){
    	return hittimestamp;
    }
    
    //returns newboulds health
    public int health(){
    	return hp;
    }
    
    //returns true if newbould is dead
    public boolean isDead(){
    	return hp <= 0;
    }
}
