package site.meowcat;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.collision.*;
import site.meowcat.managers.PiManager;
import site.meowcat.player.PlayerControl;

public class GameState extends BaseAppState {

    private SimpleApplication app;
    private Node gameRoot = new Node("GameRoot");
    private PiManager pi = new PiManager();
    private Geometry player;
    private int lastHitDigit = -1;

    private void spawnPlayer() {
        var sphere = new Sphere(16,16,0.5f);
        player = new Geometry("player", sphere);
        var mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        player.setMaterial(mat);
        PlayerControl playerControl = new PlayerControl();
        player.addControl(playerControl);
        playerControl.setupInput(app.getInputManager());
        gameRoot.attachChild(player);
        app.getCamera().setLocation(new Vector3f(0,15,20));
        app.getCamera().lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
    }

    @Override
    public void update(float tpf) {
        CollisionResults results = new CollisionResults();
        boolean hitAny = false;
        for (var ring : gameRoot.getChildren()) {
            if (!(ring instanceof Geometry)) continue;
            if (!ring.getName().startsWith("Ring")) continue;
            results.clear();
            player.collideWith(ring.getWorldBound(), results);
            if(results.size() > 0) {
                int digit = ring.getUserData("digit");
                if (digit != lastHitDigit) {
                    ringHit(digit);
                    lastHitDigit = digit;
                }
                hitAny = true;
                break;
            }
        }
        if (!hitAny) {
            lastHitDigit = -1;
        }
    }

    @Override
    protected void initialize(Application application) {
        app = (SimpleApplication) application;
        app.getRootNode().attachChild(gameRoot);

        System.out.println("Target digit: " + pi.currentDigit());
        spawnPlayer();
        spawnRings();
    }

    private void spawnRings() {
        RingFactory.spawnRingCircle(app, gameRoot);
    }

    public void ringHit(int digit) {
        if (digit == pi.currentDigit()) {
            pi.advance();
            System.out.println("Correct! Next: " + pi.currentDigit());
        } else {
            System.out.println("Wrong digit!");
        }
    }

    @Override protected void cleanup(Application app) {
        gameRoot.removeFromParent();
    }
    @Override protected void onEnable() {}
    @Override protected void onDisable() {}
}
