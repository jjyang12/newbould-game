package bearAttack;


import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class StartAnim {
	//start screens
	private Image titlescreen;
	private Image frame1;
	private Image frame2;
	private Image frame3;
	private Image frame4;
	private Image frame5;
	private Image frame6;
	private Image frame7;
	private Image frame8;

	private int frame;

	public StartAnim(){
		loadGraphics();
		frame = 0;

	}
	public void loadGraphics(){
		titlescreen = new Image("/images/titlescreen.gif");
		frame1 = new Image("/images/frame1.gif");
		frame2 = new Image("/images/frame2.gif");
		frame3 = new Image("/images/frame3.gif");
		frame4 = new Image("/images/frame4.gif");
		frame5 = new Image("/images/frame5.gif");
		frame6 = new Image("/images/frame6.gif");
		frame7 = new Image("/images/frame7.gif");
		frame8 = new Image("/images/frame8.gif");
	}
	public void prepareActionHandlersStartAnimation(Scene theScene){
		//listens for mouse click
		theScene.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				frame++;
			}});
	}

	public boolean showStartAnimation(GraphicsContext gc){
		//displays images (every click continues to next frame)
		switch(frame){
		case 0: gc.drawImage(titlescreen, 0, 0);
		return false;
		case 1: gc.drawImage(frame1, 0, 0);
		return false;
		case 2: gc.drawImage(frame2, 0, 0);
		return false;
		case 3: gc.drawImage(frame3, 0, 0);
		return false;
		case 4:	gc.drawImage(frame4, 0, 0);
		return false;
		case 5: gc.drawImage(frame5, 0, 0);
		return false;
		case 6: gc.drawImage(frame6,0,0);
		return false;
		case 7: gc.drawImage(frame7, 0, 0);
		return false;
		case 8: gc.drawImage(frame8, 0, 0);
		return false;

		default: return true;
		}
	}


}
