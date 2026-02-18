package site.meowcat;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.style.BaseStyles;
import site.meowcat.GameModes.ClassicMode;
import site.meowcat.GameModes.MemoryMode;
import site.meowcat.Interfaces.GameMode;

public class ModeSelectState extends BaseAppState {
    private Container window;

    @Override
    protected void initialize(Application app) {
        window = new Container();
        window.addChild(new Label("Select game mode"));

        // classic
        Button classic = window.addChild(new Button("Classic"));
        classic.addClickCommands(src -> launchGame(new ClassicMode()));

        // memory
        Button memory = window.addChild(new Button("Memory"));
        memory.addClickCommands(src -> launchGame(new MemoryMode()));

        Button back = window.addChild(new Button("Back"));
        back.addClickCommands(src -> {
            if (getStateManager().getState(ModeSelectState.class) == this) {
                getStateManager().detach(this);
            }
        });
    }

    private void centerWindow() {
        window.setPreferredSize(window.getPreferredSize());
        float x = getApplication().getCamera().getWidth() / 2f;
        float y = getApplication().getCamera().getHeight() / 2f;
        com.jme3.math.Vector3f size = window.getPreferredSize();
        window.setLocalTranslation(x - size.x / 2f, y + size.y / 2f, 0);
    }

    private void launchGame(GameMode mode) {
        if (getStateManager().getState(GameState.class) != null)
            return;
        GameState game = new GameState();
        game.startWithMode(mode);
        getStateManager().attach(game);
        getStateManager().detach(this);
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {
        ((SimpleApplication) getApplication()).getGuiNode().attachChild(window);
        centerWindow();
    }

    @Override
    protected void onDisable() {
        window.removeFromParent();
    }
}
