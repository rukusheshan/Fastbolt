package in.lotusilab.fastbolt.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import in.lotusilab.fastbolt.InitValues;
import in.lotusilab.fastbolt.Fastbolt;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = InitValues.SCREEN_TITLE;
		config.width = InitValues.SCREEN_WIDTH;
		config.height = InitValues.SCREEN_HEIGHT;

		new LwjglApplication(new Fastbolt(), config);
	}
}
