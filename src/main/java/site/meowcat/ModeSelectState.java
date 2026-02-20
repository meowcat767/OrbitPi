package site.meowcat;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.style.BaseStyles;
import site.meowcat.GameModes.ClassicMode;
import site.meowcat.GameModes.MemoryMode;
import site.meowcat.Interfaces.GameMode;
import site.meowcat.ui.MainMenuState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModeSelectState extends BaseAppState {
    private Container window;
    private static final String PI_DIGITS = "14159265358979323846264338327950288419716939937510π"; // append π here so
                                                                                                   // it can be picked

    private List<BitmapText> rain = new ArrayList<>();
    private List<Float> speeds = new ArrayList<>();
    private BitmapFont font;
    private Random random = new Random();
    private SimpleApplication app;

    @Override
    protected void initialize(Application application) {
        this.app = (SimpleApplication) application;
        window = new Container();
        window.addChild(new Label("Select game mode"));

        // classic
        Button classic = window.addChild(new Button("Classic"));
        classic.addClickCommands(src -> {
            site.meowcat.managers.AudioManager.getInstance().playSFX("click.ogg");
            launchGame(new ClassicMode());
        });

        // memory
        Button memory = window.addChild(new Button("Memory"));
        memory.addClickCommands(src -> {
            site.meowcat.managers.AudioManager.getInstance().playSFX("click.ogg");
            launchGame(new MemoryMode());
        });

        Button back = window.addChild(new Button("Back"));
        back.addClickCommands(src -> {
            site.meowcat.managers.AudioManager.getInstance().playSFX("click.ogg");
            if (getStateManager().getState(ModeSelectState.class) == this) {
                getStateManager().attach(new MainMenuState());
                getStateManager().detach(this);
            }
        });

        int rainCount = 150;
        BitmapFont font = new BitmapFont();
        for (int i = 0; i < rainCount; i++) {
            BitmapText digit = new BitmapText(font);
            digit.setText(randomDigit());
            digit.setSize(24f);

            float x = random.nextFloat() * app.getCamera().getWidth();
            float y = random.nextFloat() * app.getCamera().getHeight();

            digit.setLocalTranslation(x, y, -1); // Behind window
            digit.setColor(new ColorRGBA(0.4f, 0.8f, 1f, 0.6f));

            rain.add(digit);
            speeds.add(50f + random.nextFloat() * 100f);
        }
    }

    private String randomDigit() {
        int index = random.nextInt(PI_DIGITS.length());
        return String.valueOf(PI_DIGITS.charAt(index));
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

    @Override
    public void update(float tpf) {
        float screenHeight = app.getCamera().getHeight();

        for (int i = 0; i < rain.size(); i++) {
            BitmapText digit = rain.get(i);

            digit.move(0, -speeds.get(i) * tpf, 0);

            if (digit.getLocalTranslation().y < 0) {
                float x = random.nextFloat() * app.getCamera().getWidth();
                digit.setLocalTranslation(x, screenHeight, -1);
                digit.setText(randomDigit());
            }
        }
    }
}
