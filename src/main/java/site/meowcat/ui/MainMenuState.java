package site.meowcat.ui;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.*;
import com.simsilica.lemur.style.BaseStyles;
import com.simsilica.lemur.style.ElementId;
import com.simsilica.lemur.style.Styles;
import com.simsilica.lemur.style.Attributes;
import site.meowcat.GameState;


public class MainMenuState extends BaseAppState {

    private Container window;
    private SimpleApplication app;

    @Override
    protected void initialize(Application application) {
        this.app = (SimpleApplication) application;
        GuiGlobals.initialize(app);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");
        
        // Setup title style
        Styles styles = GuiGlobals.getInstance().getStyles();
        Attributes titleAttrs = styles.getSelector("title", "glass");
        titleAttrs.set("fontSize", 32f);
        titleAttrs.set("textHAlignment", HAlignment.Center);
        titleAttrs.set("color", new ColorRGBA(0.8f, 0.9f, 1.0f, 0.85f));
        titleAttrs.set("margin", new Insets3f(20, 0, 20, 0));

        window = new Container();
        app.getGuiNode().attachChild(window);
        window.addChild(new Label("OrbitPi", new ElementId("title"))); // glass doesn't allow for "π"
        Button startButton = window.addChild(new Button("Start Game!"));
        startButton.addClickCommands(source -> startGame());
        
        Button settingsButton = window.addChild(new Button("Settings"));
        settingsButton.addClickCommands(source -> System.out.println("Settings not implemented yet"));
        
        Button quitButton = window.addChild(new Button("Quit"));
        quitButton.addClickCommands(source -> app.stop());

        // Add some margin to buttons
        Attributes buttonAttrs = styles.getSelector(Button.ELEMENT_ID, "glass");
        buttonAttrs.set("margin", new Insets3f(10, 20, 10, 20));
        buttonAttrs.set("fontSize", 32f);
        
        centerWindow();
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
        window.removeFromParent();
        getStateManager().attach(new GameState());
        getStateManager().detach(this);
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
