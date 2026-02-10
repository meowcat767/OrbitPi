package site.meowcat.ui;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.*;
import com.simsilica.lemur.style.BaseStyles;

public class MainMenuState extends BaseAppState {

    private Container window;
    private SimpleApplication app;

    @Override
    protected void initialize(Application application) {
        this.app = (SimpleApplication) application;
        GuiGlobals.initialize(app);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");
        window = new Container();
        app.getGuiNode().attachChild(window);
        window.addChild(new Label("Oribt π"));
        window.addChild(new Button("Start Game!"))
                .addClickCommands(source -> startGame());
        window.addChild(new Button("Quit"))
                .addClickCommands(source -> app.stop()); // just kill it
        centerWindow();
    }

    private void centerWindow() {
        float x = app.getCamera().getWidth() / 2f;
        float y = app.getCamera().getHeight() / 2f;
        window.setLocalTranslation(x - 100, y + 100, 0);
    }

    private void startGame() {
        window.removeFromParent();
        getStateManager().detach(this);
        // TODO: write game
        System.out.println("i cannot drink 50 tons of milk");
    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
