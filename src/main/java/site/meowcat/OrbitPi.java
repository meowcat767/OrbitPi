package site.meowcat;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import site.meowcat.ui.MainMenuState;

/**
 * This is the Main Class of your Game. It should boot up your game and do
 * initial initialisation
 * Move your Logic into AppStates or Controls or other java classes
 */

public class OrbitPi extends SimpleApplication {

    public static void main(String[] args) {
        OrbitPi app = new OrbitPi();
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Orbit \u03C0");
        settings.setFullscreen(false);
        settings.setResolution(1280, 720);

        app.setSettings(settings);
        app.setShowSettings(false); // Settings dialog not supported on mac
        app.start();
    }

    @Override
    public void simpleInitApp() {
        setDisplayStatView(false);
        setDisplayFps(false);
        flyCam.setEnabled(false);

        com.simsilica.lemur.GuiGlobals.initialize(this);
        com.simsilica.lemur.style.BaseStyles.loadGlassStyle();
        com.simsilica.lemur.GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");

        site.meowcat.managers.AudioManager.getInstance().initialize(assetManager, rootNode);
        site.meowcat.managers.AudioManager.getInstance().playBGM("music.ogg");

        stateManager.attach(new MainMenuState());
    }

    @Override
    public void simpleUpdate(float tpf) {
        // this method will be called every game tick and can be used to make updates
    }

    @Override
    public void simpleRender(RenderManager rm) {
        // add render code here (if any)
    }
}
