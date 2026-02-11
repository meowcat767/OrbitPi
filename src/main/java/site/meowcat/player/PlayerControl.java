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

public class PlayerControl extends AbstractControl implements ActionListener{
    private float angle = 0f;
    private float radius = 0f;
    private float orbitSpeed = 0f; // Speed will be handled by angle increment per bounce
    private float radialVelocity = 20f;
    private float maxRadius = 10f;
    private boolean bouncingOut = true;
    private int currentTargetRing = 0;

    private int lastRingDigit = -1;
    private site.meowcat.managers.PiManager piManager;

    public void setPiManager(site.meowcat.managers.PiManager piManager) {
        this.piManager = piManager;
    }

    public void setupInput(InputManager input){
        input.addMapping("enter", new KeyTrigger(KeyInput.KEY_SPACE));
        input.addListener(this, "enter");
    }

    private void ringHit(int digit) {
        if (piManager == null) return;
        if (digit == piManager.currentDigit()) {
            piManager.advance();
            System.out.println("Correct! Next: " + piManager.currentDigit());
        } else {
            System.out.println("Wrong digit! Expected: " + piManager.currentDigit() + " but got: " + digit);
        }
    }

    @Override
    public void onAction(String name, boolean pressed, float tpf) {
        if (!pressed || !name.equals("enter")) return;

        // Space pressed - check if we are currently "hitting" a ring
        if (!bouncingOut && radius > maxRadius - 1.5f) {
             ringHit(lastRingDigit);
        } else {
            System.out.println("Miss!");
        }
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (spatial == null) return;

        // Radial "bounce"
        if (bouncingOut) {
            radius += radialVelocity * tpf;
            if (radius >= maxRadius) {
                radius = maxRadius;
                bouncingOut = false;
                
                // When we hit the max radius, we should be at a ring.
                // We want to hit EACH ring clockwise.
                // Rings are at angles: TWO_PI * i / 10f;
                // Clockwise means decreasing angle.
                
                angle = FastMath.TWO_PI * (10 - currentTargetRing) / 10f;
                if (angle >= FastMath.TWO_PI) angle -= FastMath.TWO_PI;

                lastRingDigit = currentTargetRing;
                
                // Move to next ring for next bounce
                currentTargetRing = (currentTargetRing + 1) % 10;
            }
        } else {
            radius -= radialVelocity * tpf;
            if (radius <= 0) {
                radius = 0;
                bouncingOut = true;
                
                // Set the angle for the next bounce immediately so it's visible during the flight out
                angle = FastMath.TWO_PI * (10 - currentTargetRing) / 10f;
                if (angle >= FastMath.TWO_PI) angle -= FastMath.TWO_PI;
            }
        }

        float x = FastMath.cos(angle) * radius;
        float z = FastMath.sin(angle) * radius;
        spatial.setLocalTranslation(x, 0, z);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}
}
