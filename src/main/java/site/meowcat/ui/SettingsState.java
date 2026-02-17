package site.meowcat.ui;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.*;
import site.meowcat.managers.GameSettings;


public class SettingsState extends BaseAppState {
    private Container window;
    private Slider volume;
    private Slider timing;
    private Checkbox fullscreen;

    @Override
    protected void initialize(Application app) {
        window = new Container();
        window.setLocalTranslation(300, 500, 0);
        window.addChild(new Label("Settings"));

        // vol slider
        window.addChild(new Label("Volume"));
        volume = window.addChild(new Slider());
        volume.getModel().setMinimum(0);
        volume.getModel().setMaximum(1);
        volume.getModel().setValue(GameSettings.volume);

        // hit timing slider
        window.addChild(new Label("Hit Window"));
        timing = window.addChild(new Slider());
        timing.getModel().setMinimum(0.1);
        timing.getModel().setMaximum(1.0);
        timing.getModel().setValue(GameSettings.hitWindow);

        fullscreen = window.addChild(new Checkbox("Fullscreen"));
        fullscreen.setChecked(GameSettings.fullscreen);

        Button back = window.addChild(new Button("Back"));
        back.addClickCommands(source -> {
            saveSettings();
            getStateManager().detach(this);
        });
    }

    private void saveSettings() {
        GameSettings.volume = (float) volume.getModel().getValue();
        GameSettings.hitWindow = (float) timing.getModel().getValue();
        GameSettings.fullscreen = fullscreen.isChecked();
    }

    @Override
    public void update(float tpf) {
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
        ((SimpleApplication) getApplication()).getGuiNode().attachChild(window);
    }

    @Override
    protected void onDisable() {
        window.removeFromParent();
        getStateManager().attach(new MainMenuState());
    }
}
