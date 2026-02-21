package site.meowcat.ui;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.*;
import com.simsilica.lemur.style.ElementId;
import site.meowcat.managers.ScoreManager;

public class GameOverState extends BaseAppState {

    private Container window;
    private SimpleApplication app;

    private final ActionListener listener = (name, isPressed, tpf) -> {
        if (name.equals("Restart") && !isPressed) {
            returnToMenu();
        }
    };

    @Override
    protected void initialize(Application application) {
        this.app = (SimpleApplication) application;

        window = new Container();
        window.addChild(new Label("GAME OVER", new ElementId("title")));

        Label scoreLabel = window.addChild(new Label("Score: " + ScoreManager.getScore()));
        scoreLabel.setFontSize(24f);
        scoreLabel.setInsets(new Insets3f(10, 0, 10, 0));

        Button menuButton = window.addChild(new Button("Main Menu"));
        menuButton.addClickCommands(source -> {
            site.meowcat.managers.AudioManager.getInstance().playSFX("click.ogg");
            returnToMenu();
        });

        app.getInputManager().addMapping("Restart", new KeyTrigger(KeyInput.KEY_RETURN));
        app.getInputManager().addListener(listener, "Restart");
    }

    private void centerWindow() {
        window.setPreferredSize(window.getPreferredSize());
        float x = app.getCamera().getWidth() / 2f;
        float y = app.getCamera().getHeight() / 2f;
        Vector3f size = window.getPreferredSize();
        window.setLocalTranslation(x - size.x / 2f, y + size.y / 2f, 0);
    }

    private void returnToMenu() {
        getStateManager().detach(this);
        getStateManager().attach(new MainMenuState());
    }

    @Override
    protected void cleanup(Application application) {
        app.getInputManager().deleteMapping("Restart");
        app.getInputManager().removeListener(listener);
    }

    @Override
    protected void onEnable() {
        app.getGuiNode().attachChild(window);
        centerWindow();
    }

    @Override
    protected void onDisable() {
        window.removeFromParent();
    }
}