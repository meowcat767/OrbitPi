package site.meowcat.player;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import site.meowcat.GameState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerControl extends AbstractControl implements ActionListener {
    private static final Logger logger = LogManager.getLogger(PlayerControl.class);
    private float angle = 0f;
    private float radius = 0f;
    private float orbitSpeed = 0f; // Speed will be handled by angle increment per bounce
    private float maxRadius = 10f;
    private boolean bouncingOut = true;
    private int currentTargetRing = 0;
    private float baseSpeed = 1.5f;
    private float speedMultiplier = 0.02f;
    private float angularSpeed;
    private float baseRadialVelocity = 4f;
    private float radialVelocity;
    private float maxRadialVelocity = 14f;

    private int lastRingDigit = -1;
    private site.meowcat.managers.PiManager piManager;
    private site.meowcat.GameState gameState;
    private java.util.function.Consumer<Integer> onHit;

    public int getLastRingDigit() {
        return lastRingDigit;
    }

    public void setPiManager(site.meowcat.managers.PiManager piManager) {
        this.piManager = piManager;
    }

    public void setGameState(site.meowcat.GameState gameState) {
        this.gameState = gameState;
    }

    public void setOnHit(java.util.function.Consumer<Integer> onHit) {
        this.onHit = onHit;
    }

    public void setupInput(InputManager input) {
        input.addMapping("enter", new KeyTrigger(KeyInput.KEY_SPACE));
        input.addListener(this, "enter");
    }

    public void cleanupInput(InputManager input) {
        if (input.hasMapping("enter")) {
            input.deleteMapping("enter");
        }
        input.removeListener(this);
    }

    private void ringHit(int digit) {
        if (piManager == null)
            return;
        if (onHit != null) {
            onHit.accept(digit);
        }
    }

    @Override
    public void onAction(String name, boolean pressed, float tpf) {
        if (!pressed || !name.equals("enter"))
            return;

        // Space pressed - check if we are currently "hitting" a ring
        // Ring radius is 10, torus tube radius is 0.2.
        // We consider a hit if radius is between 9 and 11 (maxRadius is 10)
        if (!bouncingOut && radius > maxRadius - 1.5f) {
            ringHit(lastRingDigit);
        } else {
            System.out.println("Miss! radius: " + radius + " bouncingOut: " + bouncingOut);
            if (gameState != null) {
                gameState.applyPenalty();
            }
        }
    }

    @Override
    protected void controlUpdate(float tpf) {
        int score = site.meowcat.ScoreManager.getScore(); // dialing the kotlin in my area
        radialVelocity = baseRadialVelocity + score * 1.5f;
        radialVelocity = Math.min(radialVelocity, maxRadialVelocity);
        radialVelocity = FastMath.clamp(radialVelocity, 0f, 1f); // anti railgun system
        if (spatial == null)
            return;

        // Radial "bounce"
        if (bouncingOut) {
            radius += radialVelocity * tpf;
            if (radius >= maxRadius) {
                radius = maxRadius;
                bouncingOut = false;

                // When we hit the max radius, we should be at a ring.
                // We want to hit EACH ring clockwise.
                // Rings are at angles: TWO_PI * i / 10f;
                // Clockwise means decreasing index or (10 - index) increasing.

                lastRingDigit = (10 - currentTargetRing) % 10;
            }
        } else {
            radius -= radialVelocity * tpf;
            if (radius <= 0) {
                radius = 0;
                bouncingOut = true;

                // Move to next ring for next bounce
                currentTargetRing = (currentTargetRing + 1) % 10;

                // Set the angle for the next bounce immediately so it's visible during the
                // flight out
                angle = FastMath.TWO_PI * (10 - currentTargetRing) / 10f;
                if (angle >= FastMath.TWO_PI)
                    angle -= FastMath.TWO_PI;
            }
        }

        float x = FastMath.cos(angle) * radius;
        float z = FastMath.sin(angle) * radius;
        spatial.setLocalTranslation(x, 0, z);

        // Update billboard labels to face camera
        // In JME, controls are updated after the spatial's translation is set.
        // But we want to update the rings' labels, not just the player.
        // This is better handled in GameState.update or by a separate BillboardControl.
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
