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
import site.meowcat.managers.PiManager;
import site.meowcat.player.PlayerControl;

public class GameState extends BaseAppState {

    private SimpleApplication app;
    private Node gameRoot = new Node("GameRoot");
    private PiManager pi = new PiManager();
    private Geometry player;
    private PlayerControl playerControl;
    private float arenaRadius = 10f;

    private void spawnPlayer() {
        var sphere = new Sphere(16,16,0.5f);
        player = new Geometry("player", sphere);
        var mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        player.setMaterial(mat);
        playerControl = new PlayerControl();
        playerControl.setPiManager(pi);
        player.addControl(playerControl);
        playerControl.setupInput(app.getInputManager());
        gameRoot.attachChild(player);
        app.getCamera().setLocation(new Vector3f(0,15,20));
        app.getCamera().lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
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
    public void update(float tpf) {
        // PlayerControl handles its own orbital movement and bouncing.
        // We can remove the old collision and movement logic here.
    }

    @Override
    protected void initialize(Application application) {
        app = (SimpleApplication) application;
        app.getRootNode().attachChild(gameRoot);

        System.out.println("Target digit: " + pi.currentDigit());
        spawnRings();
        spawnPlayer();
    }

    private void spawnRings() {
        RingFactory.spawnRingCircle(app, gameRoot);
    }

    @Override
    protected void cleanup(Application app) {
        gameRoot.removeFromParent();
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }
}
