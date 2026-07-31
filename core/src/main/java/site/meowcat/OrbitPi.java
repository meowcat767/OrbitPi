package site.meowcat;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import site.meowcat.ui.MainMenuState;

/**
 * This is the Main Class of your Game. It should boot up your game and do
 * initial initialisation
 * Move your Logic into AppStates or Controls or other java classes
 */

public class OrbitPi extends SimpleApplication {

    @Override
    public void simpleInitApp() {
        setDisplayStatView(false);
        setDisplayFps(false);
        flyCam.setEnabled(false);

        com.simsilica.lemur.GuiGlobals.initialize(this);
        com.simsilica.lemur.style.BaseStyles.loadGlassStyle();
        com.simsilica.lemur.GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");

        site.meowcat.managers.AudioManager.getInstance().initialize(assetManager, rootNode);
        site.meowcat.managers.AudioManager.getInstance().playRandomBGM();

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
