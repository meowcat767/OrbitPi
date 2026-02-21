package site.meowcat.ui;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.ActionListener;
import site.meowcat.managers.ScoreManager;

public class GameOverState extends BaseAppState {

    private BitmapText gameOverText;
    private SimpleApplication simpleApp;

    private final ActionListener listener = (name, isPressed, tpf) -> {
        if (name.equals("Restart") && !isPressed) {
            getStateManager().detach(this);
            getStateManager().attach(new MainMenuState());
        }
    };

    @Override
    protected void initialize(Application app) {

        simpleApp = (SimpleApplication) app;

        gameOverText = new BitmapText(
                simpleApp.getAssetManager().loadFont("Interface/Fonts/Default.fnt")
        );

        gameOverText.setText(
                "GAME OVER\n\nScore: " + ScoreManager.getScore() +
                        "\n\nPress ENTER to return to menu"
        );

        float x = simpleApp.getCamera().getWidth() / 2f - gameOverText.getLineWidth() / 2f;
        float y = simpleApp.getCamera().getHeight() / 2f + gameOverText.getLineHeight();

        gameOverText.setLocalTranslation(x, y, 0);
        simpleApp.getGuiNode().attachChild(gameOverText);

        simpleApp.getInputManager().addMapping("Restart",
                new KeyTrigger(KeyInput.KEY_RETURN));

        simpleApp.getInputManager().addListener(listener, "Restart");
    }

    @Override
    protected void cleanup(Application app) {
        simpleApp.getGuiNode().detachChild(gameOverText);
        simpleApp.getInputManager().deleteMapping("Restart");
        simpleApp.getInputManager().removeListener(listener);
    }

    @Override protected void onEnable() {}
    @Override protected void onDisable() {}
}