package com.military.coordination;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Desktop launcher for Military Coordination tactical simulation.
 * Launches the LibGDX application on desktop platforms.
 */
public class DesktopLauncher {

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        // Window configuration for tactical interface
        config.setTitle("Military Coordination - Tower Trust System");
        config.setWindowedMode(1200, 800);
        config.setResizable(true);

        // Performance settings
        config.setIdleFPS(60);
        config.setForegroundFPS(60);
        config.useVsync(true);

        // Launch the game
        Lwjgl3Application app = new Lwjgl3Application(new MilitaryCoordinationGame(), config);
        app.exit();
    }
}
