package site.meowcat.ui;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;

import java.awt.*;

public class AboutAppState extends BaseAppState {
    private Container window;

    @Override
    protected void initialize(Application app) {
        SimpleApplication simpleApp = (SimpleApplication) app;
        window = new Container();
        window.addChild(new Label("About"));
        window.addChild(new Label(""));
        window.addChild(new Label("Memorise pi by hitting the correct digits!"));
        window.addChild(new Label("Full attributions in ATTRIBUTIONS.txt"));
        window.addChild(new Label("Written by Ben House"));
        window.addChild(new Label("Built with jMonkeyEngine, IntelliJ IDEA and <3!"));
        window.addChild(new Label(""));
        Button back  = window.addChild(new Button("Back"));
        back.addClickCommands(source -> {
            getStateManager().attach(new MainMenuState());
            getStateManager().detach(this);
        });
        window.setLocalTranslation(
                simpleApp.getCamera().getWidth() / 2f,
                simpleApp.getCamera().getHeight() / 2f,
                0
        );
        simpleApp.getGuiNode().attachChild(window);
    }

    @Override
    protected void cleanup(Application app) {
        ((SimpleApplication) app).getGuiNode().detachChild(window);
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
