package net.snatchlab.investmentfarm;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import net.snatchlab.investmentfarm.InvestmentFarm;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Investment Farm");
		/*config.useVsync(true);
		//// Limits FPS to the refresh rate of the currently active monitor.
		config.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
		//// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
		//// useful for testing performance, but can also be very stressful to some hardware.
		//// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
		config.setWindowedMode(640, 480);
		config.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");*/
		new Lwjgl3Application(new InvestmentFarm(), config);
	}
}
