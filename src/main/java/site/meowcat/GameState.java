package site.meowcat;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import site.meowcat.Interfaces.GameMode;
import site.meowcat.managers.PiManager;
import site.meowcat.player.PlayerControl;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import site.meowcat.ui.MainMenuState;

public class GameState extends BaseAppState {

    private SimpleApplication app;
    private Node gameRoot = new Node("GameRoot");
    private PiManager pi = new PiManager();
    private Geometry player;
    private PlayerControl playerControl;
    private float arenaRadius = 10f;
    private BitmapText digitHud;
    private int lives = 3;
    private BitmapText livesHud;
    private GameMode mode;

    public void startWithMode(GameMode mode) {
        this.mode = mode;
    }

    private void setupHud() {
        BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        digitHud = new BitmapText(font);
        digitHud.setSize(font.getCharSet().getRenderedSize() * 2);
        updateHudText();
        float x = app.getCamera().getWidth() / 2f - digitHud.getLineWidth() / 2f;
        float y = app.getCamera().getHeight() - 20;
        digitHud.setLocalTranslation(x, y, 0);
        app.getGuiNode().attachChild(digitHud);
    }

    private void setupLivesHud() {
        BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        livesHud = new BitmapText(font);
        livesHud.setSize(font.getCharSet().getRenderedSize() * 1.5f);
        updateLivesHud();
        livesHud.setLocalTranslation(20, app.getCamera().getHeight() - 20, 0);
        app.getGuiNode().attachChild(livesHud);
    }

    public void updateLivesHud() {
        if (livesHud != null) {
            livesHud.setText("Lives: " + lives);
        }
    }

    public void applyPenalty() {
        lives--; // It's like C--, but for lives!
        updateLivesHud();
        System.out.println("OOF!  " + lives);
        if (lives <= 0) {
            gameOver();
        }
    }

    public void gameOver() {
        System.out.println("!!! EXPECTED STOP !!!");
        getStateManager().detach(this);
        getStateManager().attach(new MainMenuState());
    }

    public void updateHudText() {
        if (mode != null) {
            digitHud.setText(mode.getHudText(pi.currentDigit()));
        } else {
            digitHud.setText("Next digit: " + pi.currentDigit());
        }
        float x = app.getCamera().getWidth() / 2f - digitHud.getLineWidth() / 2f;
        digitHud.setLocalTranslation(x, digitHud.getLocalTranslation().y, 0);
    }

    private void spawnPlayer() {
        var sphere = new Sphere(16, 16, 0.5f);
        player = new Geometry("player", sphere);
        var mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        player.setMaterial(mat);
        playerControl = new PlayerControl();
        playerControl.setPiManager(pi);
        playerControl.setGameState(this);
        playerControl.setOnHit((digit) -> {
            if (mode != null) {
                mode.onDigitHit(digit);
            }
            if (digit == pi.currentDigit()) {
                pi.advance();
                updateHudText();
                System.out.println("Correct!");
            } else {
                applyPenalty();
            }
        });
        player.addControl(playerControl);
        playerControl.setupInput(app.getInputManager());
        gameRoot.attachChild(player);
        app.getCamera().setLocation(new Vector3f(0, 15, 20));
        app.getCamera().lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
    }

    @Override
    public void update(float tpf) {
        for (Spatial ring : gameRoot.getChildren()) {
            if (ring instanceof Node) {
                Node ringNode = (Node) ring;
                Spatial labelNode = ringNode.getChild("LabelNode");
                if (labelNode != null) {
                    labelNode.lookAt(app.getCamera().getLocation(), Vector3f.UNIT_Y);
                }
            }
        }

    }

    @Override
    protected void initialize(Application application) {
        app = (SimpleApplication) application;
        app.getRootNode().attachChild(gameRoot);

        System.out.println("Target digit: " + pi.currentDigit());
        spawnRings();
        setupHud();
        setupLivesHud();
        spawnPlayer();
    }

    private void spawnRings() {
        RingFactory.spawnRingCircle(app, gameRoot);
    }

    @Override
    protected void cleanup(Application app) {
        if (playerControl != null) {
            playerControl.cleanupInput(app.getInputManager());
        }
        if (digitHud != null)
            digitHud.removeFromParent();
        if (livesHud != null)
            livesHud.removeFromParent();
        gameRoot.removeFromParent();
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }
}
