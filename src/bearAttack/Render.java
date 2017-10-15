package bearAttack;

import java.util.ArrayList;
import DataStructures.LList;
import DataStructures.Quadtree;

import java.util.HashSet;
import java.util.Random;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.*;

import java.math.*;
public class Render {
	//Keeps track of all sprites and graphics

	private boolean facingRight = false;
	private boolean crouching = false;

	private Image bearFacingLeft;
	private Image bearFacingRight;
	private Image largeBearFacingLeft;
	private Image tinyBearFacingLeft;
	private Image tinyBearFacingRight;
	private Image largeBearHit;
	private Image bearDyingRight;
	private Image bearDyingLeft;
	private Image tinyBearDyingRight;
	private Image tinyBearDyingLeft;

	private Image newbouldStandingRight;
	private Image newbouldCrouchingRight;
	private Image newbouldStandingLeft;
	private Image newbouldCrouchingLeft;

	private Image noteBullet;

	private Image Ammo1;
	private Image Ammo2;
	private Image Ammo3;
	private Image Ammo4;
	private Image Ammo5;
	private Image Ammo6;
	private Image Reloading;
	private Image Health;
	
	private Image background;
	private Image deathscreen;
	private Image winScreen;
	private Image finalscreen;
	
	private boolean winScreenLoaded;
	private boolean animFinished;
	private long winAnimationStart;

	private LList<Bear> bears;
	private bossBear bossman;
	private Newbould newbould;

	private int MAX_BEARS = 20;
	private int WAVES;

	private String reloadString;

	private boolean finalCountdown;
	private long finaltimestamp;

	int waveCounter = 0;
	private long waveTimer = (long) (System.currentTimeMillis() + 0.0);
	double t;
	int waveDead;

	private Quadtree q;
	private Quadtree bearQuad;

	static HashSet<String> currentlyActiveKeys;
	static LList<Projectile> projectiles = new LList<Projectile>();

	public Render() {
		//Sets up waves and sprites
		newbould = new Newbould(600, 200);
		bears = new LList<Bear>();
		q = new Quadtree(0, new Rectangle(1200,800));
		bearQuad = new Quadtree(0, new Rectangle(1200,800));
		bossman = new bossBear(1250,50,-.5,0);
		finalCountdown = false;
		winScreenLoaded = false;
		animFinished = false;
		waveDead = 1;
		waveTimer = 0;
		WAVES = 3;
	}

	public void loadGraphics() {
		//loads graphics
		background = new Image("/images/background.png");
		deathscreen = new Image("/images/deathscreen.png");
		finalscreen = new Image("/images/finalScreen.png");

		largeBearFacingLeft = new Image("/images/largeBearFacingLeft.png");
		largeBearHit = new Image("/images/largeBearHit.png");
		bearDyingRight = new Image("/images/bearDyingRight.gif");
		bearDyingLeft = new Image("/images/bearDyingLeft.gif");
		bearFacingLeft = new Image("/images/bearFacingLeft.png");
		bearFacingRight = new Image("/images/bearFacingRight.png");
		tinyBearFacingLeft = new Image("/images/tinyBearFacingLeft.png");
		tinyBearFacingRight = new Image("/images/tinyBearFacingRight.png");
		tinyBearDyingLeft = new Image("/images/tinyBearDyingLeft.gif");
		tinyBearDyingRight = new Image("/images/tinyBearDyingRight.gif");

		newbouldStandingRight = new Image("/images/newbouldStandingRight.png");
		newbouldCrouchingRight = new Image("/images/newbouldCrouchingRight.png");
		newbouldStandingLeft = new Image("/images/newbouldStandingLeft.png");
		newbouldCrouchingLeft = new Image("/images/newbouldCrouchingLeft.png");
		noteBullet = new Image("/images/note.png");

		Ammo1 = new Image("/images/Ammo1.png");
		Ammo2 = new Image("/images/Ammo2.png");
		Ammo3 = new Image("/images/Ammo3.png");
		Ammo4 = new Image("/images/Ammo4.png");
		Ammo5 = new Image("/images/Ammo5.png");
		Ammo6 = new Image("/images/Ammo6.png");
		Health = new Image("images/Health.png");
		Reloading = new Image("/images/Reloading.png");

	}

	public void renderGraphics(GraphicsContext gc) {
		//Draws Background and HUD
		
		drawScene(gc);

		//only if the game is running
		if (!newbould.isDead() && !bossman.isDead()){

			//Controls waves and creates bears
			bearWaveController(3);

			//moves bear and draws them
			drawBears(gc);

			//newbould listeners and draws him
			drawNewbould(gc);

			//draws projectiles
			drawProjectiles(gc);

			//detects collisions
			detectCollision(gc);

		}
		if (newbould.isDead()){
			//deathscreen
			gc.drawImage(deathscreen, 0, 130);
		}
		
		if(bossman.isDead()){
			loadWinScreen();
			winScreenLoaded = true;
			bears.clear();
			//draws animation, then restart text
			if ((System.currentTimeMillis() - winAnimationStart)/1000.0 > 6.9)
			{
				//prevents restart while animation is playing
				animFinished = true;
				gc.drawImage(finalscreen,0,0);
			} else {
				gc.drawImage(winScreen, 0, 0);
			}
			drawNewbould(gc);
		}
		

	}
	
	//ensures that win animation plays from the beginning of gif 
	private void loadWinScreen(){
		if (!winScreenLoaded){
		winAnimationStart = (long) (System.currentTimeMillis() + 0.0);
		winScreen = new Image("/images/winScreen.gif");
		}
	}
	
	
	private void detectCollision(GraphicsContext gc) {
		q.clear();
		//Projectile Bear hitbox
		projectiles.moveToStart();
		//O(n)
		for(int i = 0; i < projectiles.length(); i++){
			q.insert(projectiles.getValue().getRekt());
			projectiles.next();
		}
		ArrayList<Rectangle> returnObjects = new ArrayList<Rectangle>();

		//O(n log n) time 
		bears.moveToStart();
		for(int i = 0; i < bears.length(); i++){
			returnObjects.clear();
			q.retrieve(returnObjects, bears.getValue().getRekt());
			for(int o = 0; o < returnObjects.size(); o++){
				if(checkCollision(bears.getValue().getRekt(), returnObjects.get(o))){
					if(bears.getValue() instanceof bossBear){
						bossman.hit();

					} else{
						bears.getValue().setXVel(0);
						bears.getValue().setYVel(0);
						bears.getValue().kill();
					}
				}
			}
			bears.next();
		}


		bearQuad.clear();
		bears.moveToStart();
		//O(n)
		for(int i = 0; i < bears.length(); i++){
			bearQuad.insert(bears.getValue().getRekt());
			bears.next();
		}

		//O(log n)
		//Check Collision for newbould
		ArrayList<Rectangle> bouldHit = new ArrayList<Rectangle>();
		bearQuad.retrieve(bouldHit, newbould.getRekt());
		for(int i = 0; i < bouldHit.size(); i++){
			if(checkCollision(newbould.getRekt(), bouldHit.get(i))){
				newbould.hit();
			}
		}
	}

	private void drawProjectiles(GraphicsContext gc) {
		// Tracks projectiles.  Removes projectiles once they leave the screen
		//O(n) time
		projectiles.moveToStart();
		for(int i = 0; i < projectiles.length(); i++) {
			Projectile p = projectiles.getValue();
			if(p.getX() < 1200 && p.getX() > 0){
				p.step();
				gc.drawImage(noteBullet, p.getX(), p.getY());
				projectiles.next();
			}
			else{
				projectiles.remove();
			}
		}


	}

	private void drawNewbould(GraphicsContext gc) {
		//Newbould moves
		if (currentlyActiveKeys.contains("RIGHT") && newbould.getX() < 1100) {
			facingRight = true;
			newbould.setX(newbould.getX() + 5);
		}

		if (currentlyActiveKeys.contains("LEFT") && newbould.getX() > 0) {
			facingRight = false;
			newbould.setX(newbould.getX() - 5);

		}
		// adds projectiles
		if (currentlyActiveKeys.contains("SPACE")) {
			crouching = true;
		} else {
			crouching = false;
		}
		if (currentlyActiveKeys.contains("UP") && newbould.getY() > 0) {
			newbould.setY(newbould.getY() - 5);
		}

		if (currentlyActiveKeys.contains("DOWN") && newbould.getY() <= 700) {
			newbould.setY(newbould.getY() + 5);
		}

		// draws newbould
		if (facingRight) {
			if (crouching) {
				gc.drawImage(newbouldCrouchingRight, newbould.getX(), newbould.getY());
			} else {
				gc.drawImage(newbouldStandingRight, newbould.getX(), newbould.getY());
			}
		}

		if (!facingRight) {
			if (crouching) {
				gc.drawImage(newbouldCrouchingLeft, newbould.getX() - 120, newbould.getY());
			} else {
				gc.drawImage(newbouldStandingLeft, newbould.getX() - 120, newbould.getY());
			}
		}


	}

	private void drawBears(GraphicsContext gc) {
		bears.moveToStart();
		while(bears.getValue() != null){
			if(bears.getValue() instanceof bossBear){
				gc.drawImage(largeBearFacingLeft, bossman.getX(), bossman.getY());
				bossman.step();
			}else{
				bears.getValue().step();
				bears.getValue().rebound();

				//Checks which graphics to load for each bear
				//O(n)
				if (bears.getValue().getX() > newbould.getX()) {
					if(bears.getValue().isDead()){
						if (bears.getValue() instanceof bulletBear){
							gc.drawImage(tinyBearDyingLeft, bears.getValue().getX(), bears.getValue().getY());
						} else{
							gc.drawImage(bearDyingLeft, bears.getValue().getX(), bears.getValue().getY());
						}
					}else{
						if (bears.getValue() instanceof bulletBear){
							gc.drawImage(tinyBearFacingLeft, bears.getValue().getX(), bears.getValue().getY());
						} else{
							gc.drawImage(bearFacingLeft, bears.getValue().getX(), bears.getValue().getY());
						}
					}
				} else {
					if(bears.getValue().isDead()){
						if (bears.getValue() instanceof bulletBear){
							gc.drawImage(tinyBearDyingRight, bears.getValue().getX(), bears.getValue().getY());
						} else{
							gc.drawImage(bearDyingRight, bears.getValue().getX(), bears.getValue().getY());
						}
					}else{
						if (bears.getValue() instanceof bulletBear){
							gc.drawImage(tinyBearFacingRight, bears.getValue().getX(), bears.getValue().getY());
						} else {
							gc.drawImage(bearFacingRight, bears.getValue().getX(), bears.getValue().getY());
						}
					}
				}
			}
			//Removes Dead Bears
			if(bears.getValue().isDead() && (System.currentTimeMillis() - bears.getValue().whenDead())/1000.0 > 0.8){
				bears.remove();
			}
			bears.next();
		}


	}

	public void bearWaveController(int n) {
		//Makes Bear Waves
		if(waveCounter <= WAVES){
			if(waveDead == 1 && (System.currentTimeMillis() - waveTimer)/1000 > 2){
				waveCounter++;
				if(waveCounter <= WAVES){
					if(waveCounter == 1){
						bearWave(1);
					}
					else{
						bearWave((waveCounter-1) * 5);
					}
				}
				waveDead = 0;
			}
			if(bears.length() == 0 && waveDead == 0){
				waveTimer = (long) (System.currentTimeMillis() + 0.0);
				waveDead++;
			}
		}else{
			//Final Boss Level
			if(finalCountdown == false){
				initiateFinalCountdown();
				finalCountdown = true;
			}
			if((System.currentTimeMillis() - finaltimestamp)/1000.0 > 2){
				bearShot(1);
				//bearWave(2);
				finaltimestamp = (long) (System.currentTimeMillis() + 0.0);
			}

		}
	}

	public boolean checkCollision(Rectangle a, Rectangle b){
		//checks if the rectangles touch
		if(a.localToScene(a.getBoundsInLocal()).intersects(b.localToScene(b.getBoundsInLocal()))){
			return true;
		}
		return false;
	}

	public void drawScene(GraphicsContext gc){
		//Draws HUD
		gc.drawImage(background, 0, 0,1200,800);

		//gc.fillRect(bossman.getX(), bossman.getY(), 200, 800);

		gc.setFill(Color.BLACK);
		gc.fillText("Wave: "+ waveCounter, 520, 60);
		gc.setFont(new Font("Comic Sans MS", 40));
		gc.setFill(Color.RED);

		gc.drawImage(Health, 20, 60);
		gc.fillRect(108, 69, newbould.health()/10.0 *103, 26);


		if((System.currentTimeMillis() - newbould.reloadTime())/1000 > 1){
			switch(newbould.ammoCount()){
			case 1: gc.drawImage(Ammo1, 20,20);
			break;
			case 2: gc.drawImage(Ammo2, 20,20);
			break;
			case 3: gc.drawImage(Ammo3, 20,20);
			break;
			case 4: gc.drawImage(Ammo4, 20,20);
			break;
			case 5: gc.drawImage(Ammo5, 20,20);
			break;
			case 6: gc.drawImage(Ammo6, 20,20);
			break;
			}
		}else{
			gc.drawImage(Reloading, 20, 20);
		}

	}
	//Creates Bears
	public void initializeBearArray() {

		Random rand = new Random();
		bears.moveToStart();
		for (int i = 0; i < 10; i++) {
			if (i % 2 == 0) {
				bears.append(new Bear(0, rand.nextInt(700) + 1, rand.nextInt(5) + 1, rand.nextInt(3) + 1));
			} else {
				bears.append(new Bear(1100, rand.nextInt(700) + 1, rand.nextInt(10) - 5, rand.nextInt(6) - 3));
			}
		}
	}

	public void bearWave(int wave){
		if(bears.length() >= MAX_BEARS){
			return;
		}
		Random rand = new Random();
		for (int i = 0; i < wave; i++){
			if(i%2 == 0){
				bears.append(new Bear(0, rand.nextInt(700) + 1, (rand.nextInt(5) + 1) + 0.1 * wave, (rand.nextInt(3) + 1)+ 0.1 * wave));
			}else{
				bears.append(new Bear(1100, rand.nextInt(700) + 1, (rand.nextInt(5) + 1)+ 0.1 * wave, (rand.nextInt(3) + 1)+ 0.1 * wave));
			}
		}
	}

	public void bearShot(int n){
		if(bears.length() >= MAX_BEARS){
			return;
		}
		Random rand = new Random();
		double randX = rand.nextInt(216) + 173;
		double vectorLen = Math.sqrt(Math.pow(newbould.getX() - bossman.getX(), 2) + Math.pow(bossman.getY() - bossman.getY(),2));
		bears.append(new bulletBear(bossman.getX() + randX, bossman.getY() + 353, (newbould.getX() - bossman.getX())/vectorLen * 3, (newbould.getY() - bossman.getY()-220)/vectorLen * 3));
		bears.append(new bulletBear(bossman.getX() + 172, bossman.getY() + 211, (newbould.getX() - bossman.getX())/vectorLen * 3, (newbould.getY() - bossman.getY()-220)/vectorLen * 3));
	}
	public void initiateFinalCountdown(){
		//Sets up final boss
		//System.out.println("This is the final countdown...");
		finaltimestamp = (long) (System.currentTimeMillis() +0.0);
		//background = finalStage;
		bears.append(bossman);
	}

	public void reset(){
		//resets board
		newbould = new Newbould(600, 200);
		bears = new LList<Bear>();
		q = new Quadtree(0, new Rectangle(1200,800));
		bearQuad = new Quadtree(0, new Rectangle(1200,800));
		bossman = new bossBear(1250,50,-.5,0);
		finalCountdown = false;
		winScreenLoaded = false;
		animFinished = false;
		waveDead = 1;
		waveTimer = 0;
		waveCounter = 0;
		background = new Image("/images/background.png");
	}


	//Listeners
	public void prepareActionHandlersNewbould(Scene theScene) {
		currentlyActiveKeys = new HashSet<String>();
		theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				currentlyActiveKeys.add(event.getCode().toString());
			}
		});
		theScene.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				if(newbould.isDead() || (animFinished && bossman.isDead())){
					reset();
				}
			}});
		theScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().toString() == "SPACE" && (System.currentTimeMillis() - newbould.reloadTime())/1000 > 1) {

					newbould.shoot();
					reloadString = "Ammo: "+ newbould.ammoCount();
					if (facingRight) {
						projectiles.append(new Projectile(newbould.getX() + 120, newbould.getY() + 10, 10, noteBullet));
					} else {
						projectiles.append(new Projectile(newbould.getX() - 60, newbould.getY() + 10, -10, noteBullet));
					}

				}
				currentlyActiveKeys.remove(event.getCode().toString());
			}
		});
	}
	public boolean finalLevel(){
		return finalCountdown;
	}

}
