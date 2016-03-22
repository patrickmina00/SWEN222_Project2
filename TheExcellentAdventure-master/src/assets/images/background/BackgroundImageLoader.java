package assets.images.background;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BackgroundImageLoader {

	private static Image background;

	static {
		try {
			background = ImageIO.read(BackgroundImageLoader.class.getResource("background.png"));
		} catch (IOException e) {
			System.err.println("Background loading encountered an error:" + e.getLocalizedMessage());
		}
	}

	public static Image getBackgroundImage(){
		return background;
	}
	
}
