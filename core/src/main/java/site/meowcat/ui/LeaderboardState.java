package site.meowcat.ui;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.*;
import com.simsilica.lemur.style.ElementId;
import site.meowcat.managers.LeaderboardEntry;
import site.meowcat.managers.LeaderboardManager;

import java.util.List;

public class LeaderboardState extends BaseAppState {

    private Container window;
    private SimpleApplication app;

    @Override
    protected void initialize(Application application) {
        this.app = (SimpleApplication) application;

        window = new Container();
        window.addChild(new Label("Leaderboards", new ElementId("title")));

        Container table = window.addChild(new Container());
        table.setInsets(new Insets3f(10, 10, 10, 10));

        List<LeaderboardEntry> entries = LeaderboardManager.getInstance().getEntries();

        if (entries.isEmpty()) {
            table.addChild(new Label("No scores yet!"));
        } else {
            for (int i = 0; i < entries.size(); i++) {
                LeaderboardEntry entry = entries.get(i);
                Container row = table.addChild(new Container());
                row.addChild(new Label((i + 1) + ". " + entry.getName()), 0);
                row.addChild(new Label(String.valueOf(entry.getScore())), 1);
            }
        }

        Button backButton = window.addChild(new Button("Back"));
        backButton.addClickCommands(source -> {
            site.meowcat.managers.AudioManager.getInstance().playSFX("click.ogg");
            getStateManager().attach(new MainMenuState());
            getStateManager().detach(this);
        });
    }

    private void centerWindow() {
        window.setPreferredSize(window.getPreferredSize());
        float x = app.getCamera().getWidth() / 2f;
        float y = app.getCamera().getHeight() / 2f;
        Vector3f size = window.getPreferredSize();
        window.setLocalTranslation(x - size.x / 2f, y + size.y / 2f, 0);
    }

    @Override
    protected void cleanup(Application application) {
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
