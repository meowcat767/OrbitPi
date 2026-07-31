package site.meowcat;

import com.jme3.system.AppSettings;

public class DesktopLauncher {
    public static void main(String[] args) {
        OrbitPi app = new OrbitPi();
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Orbitπ");
        settings.setFullscreen(false);
        settings.setResolution(1280, 720);

        app.setSettings(settings);
        app.setShowSettings(false); // Settings dialog not supported on mac
        app.start();
    }
}
