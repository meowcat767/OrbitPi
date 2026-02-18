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
    GuiGlobals.initialize(app);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");
        window = new Container();
        ((SimpleApplication) app).getGuiNode().attachChild(window);
        window.setLocalTranslation(300, 500, 0);
        window.addChild(new Label("Select game mode"));

        // classic
        Button classic = window.addChild(new Button("Classic"));
        classic.addClickCommands(src -> launchGame(new ClassicMode()));

        // memory
        Button memory = window.addChild(new Button("Memory"));
        memory.addClickCommands(src -> launchGame(new MemoryMode()));

        Button back = window.addChild(new Button("Back"));
        back.addClickCommands(src -> getStateManager().detach(this));
    }

    private void launchGame(GameMode mode) {
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

    }

    @Override
    protected void onDisable() {

    }
}
