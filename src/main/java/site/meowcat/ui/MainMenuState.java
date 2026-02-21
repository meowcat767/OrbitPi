package site.meowcat.ui;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.*;
import com.simsilica.lemur.style.BaseStyles;
import com.simsilica.lemur.style.ElementId;
import com.simsilica.lemur.style.Styles;
import com.simsilica.lemur.style.Attributes;
import site.meowcat.GameState;
import site.meowcat.ModeSelectState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainMenuState extends BaseAppState {

    private Container window;
    private SimpleApplication app;
    private static final String PI_DIGITS = "14159265358979323846264338327950288419716939937510π"; // append π here so -
                                                                                                   // upd: cannot be
                                                                                                   // picked because
                                                                                                   // font
                                                                                                   // it can be picked

    private List<BitmapText> rain = new ArrayList<>();
    private List<Float> speeds = new ArrayList<>();
    private BitmapFont font;
    private Random random = new Random();

    @Override
    protected void initialize(Application application) {
        this.app = (SimpleApplication) application;

        // Setup title style
        Styles styles = GuiGlobals.getInstance().getStyles();
        Attributes titleAttrs = styles.getSelector("title", "glass");
        titleAttrs.set("fontSize", 32f);
        titleAttrs.set("textHAlignment", HAlignment.Center);
        titleAttrs.set("color", new ColorRGBA(0.8f, 0.9f, 1.0f, 0.85f));
        titleAttrs.set("margin", new Insets3f(20, 0, 20, 0));

        window = new Container();
        window.addChild(new Label("OrbitPi", new ElementId("title"))); // glass doesn't allow for "π"
        Button startButton = window.addChild(new Button("Start Game!"));
        startButton.addClickCommands(source -> {
            site.meowcat.managers.AudioManager.getInstance().playSFX("click.ogg");
            startGame();
        });

        Button leaderboardButton = window.addChild(new Button("Leaderboards"));
        leaderboardButton.addClickCommands(source -> {
            site.meowcat.managers.AudioManager.getInstance().playSFX("click.ogg");
            getStateManager().attach(new site.meowcat.ui.LeaderboardState());
            getStateManager().detach(this);
        });

        Button quitButton = window.addChild(new Button("Quit"));
        quitButton.addClickCommands(source -> {
            site.meowcat.managers.AudioManager.getInstance().playSFX("click.ogg");
            app.stop();
        });

        // Add some margin to buttons
        Attributes buttonAttrs = styles.getSelector(Button.ELEMENT_ID, "glass");
        buttonAttrs.set("margin", new Insets3f(10, 20, 10, 20));
        buttonAttrs.set("fontSize", 32f);

        font = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");

        int rainCount = 150;

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
        // We need to wait for the preferred size to be calculated or force it
        window.setPreferredSize(window.getPreferredSize());

        float x = app.getCamera().getWidth() / 2f;
        float y = app.getCamera().getHeight() / 2f;
        Vector3f size = window.getPreferredSize();

        // Scale the window to be more visible on high res screens if needed
        // but let's first ensure it's centered
        window.setLocalTranslation(x - size.x / 2f, y + size.y / 2f, 0);
    }

    private void startGame() {
        if (getStateManager().getState(ModeSelectState.class) != null)
            return;
        getStateManager().attach(new ModeSelectState());
        getStateManager().detach(this);
    }

    @Override
    protected void cleanup(Application application) {
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

    @Override
    protected void onEnable() {
        for (BitmapText digit : rain) {
            app.getGuiNode().attachChild(digit);
        }
        app.getGuiNode().attachChild(window);
        centerWindow();
    }

    @Override
    protected void onDisable() {
        window.removeFromParent();
        for (BitmapText digit : rain) {
            digit.removeFromParent();
        }
    }
}
