package bearAttack;

import java.io.File;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import DataStructures.*;

public class RunProgram extends Application {

	private double t;
	private Scene theScene;
	private GraphicsContext gc;
	private double width;
	private double height;
	private Render rendGraph;
	private StartAnim openingcreds;
	private LList<MediaPlayer> jukebox;
	private boolean testBoolean = true;
	private boolean queFinalStage = false;
	
	//Launches program
	public static void main(String[] args) {
		launch(args);
	}
	
	//Starts Program
	@Override
	public void start(Stage theStage) {
		//Sets up window
		width = 1200;
		height = 800;
		
		Group root = new Group();
		theStage.setTitle("Newbould's Revenge");
		theScene = new Scene(root);
		theStage.setScene(theScene);
		theStage.setResizable(false);
		
		Canvas canvas = new Canvas(width, height);
		root.getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();
		
				
		
		//opening
		openingcreds = new StartAnim();
		openingcreds.prepareActionHandlersStartAnimation(theScene);
		
		//Loads sprites
		rendGraph = new Render();
		rendGraph.loadGraphics();
		
		//Music
		jukebox = new LList<MediaPlayer>();
		jukebox.append(new MediaPlayer(new Media(new File("res/music/Les Miserables - LookDown.mp3").toURI().toString())));
		jukebox.append(new MediaPlayer(new Media(new File("res/music/Anticipation.mp3").toURI().toString())));
		jukebox.append(new MediaPlayer(new Media(new File("res/music/Heartache-Toby Fox.mp3").toURI().toString())));
		jukebox.append(new MediaPlayer(new Media(new File("res/music/BATH.mp3").toURI().toString())));
		jukebox.append(new MediaPlayer(new Media(new File("res/music/Megalovania.mp3").toURI().toString())));
		jukebox.append(new MediaPlayer(new Media(new File("res/music/Requiem.mp3").toURI().toString())));

		
		nextSong(jukebox.getValue(), jukebox);
		
		
		//Animation loop that really runs the program and tracks all sprites
		//final long startNanoTime = System.nanoTime();
		AnimationTimer render = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				//t = (currentNanoTime - startNanoTime)/100000000.0;
				gc.clearRect(0, 0, 1200, 800);
				//Opening Animation
				if(	openingcreds.showStartAnimation(gc)){
					musicController();
					//Actual Game
					rendGraph.renderGraphics(gc);
				}
			}
		};
		//Starts AnimationTimer
		render.start();
		
		theStage.show();

	}
	
	public void musicController(){
		//Handles Music
		if(testBoolean){
			jukebox.getValue().stop();
			jukebox.next();
			nextSong(jukebox.getValue(), jukebox);
			testBoolean = false;
			rendGraph.prepareActionHandlersNewbould(theScene);
		}
		if(!queFinalStage && rendGraph.finalLevel()){
			jukebox.getValue().stop();
			jukebox.moveToPos(jukebox.length()-1);
			nextSong(jukebox.getValue(), jukebox);
			queFinalStage = true;
		}
		if(queFinalStage && !rendGraph.finalLevel()){
			jukebox.getValue().stop();
			jukebox.moveToStart();
			jukebox.next();
			nextSong(jukebox.getValue(), jukebox);
			queFinalStage = false;
		}
	}
	
	public void nextSong(MediaPlayer m, LList<MediaPlayer> list){
		//Plays next song
		m.seek(Duration.ZERO);
		m.play();
		m.setOnEndOfMedia(new Runnable(){
			public void run(){
				list.next();
				if(list.getValue() == null){
					list.moveToStart();
					list.next();
				}
				nextSong(list.getValue(), list);
			}
		});
	}
	

}
