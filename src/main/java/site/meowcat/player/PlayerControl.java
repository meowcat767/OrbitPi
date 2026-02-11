package site.meowcat.player;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.*;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

public class PlayerControl extends AbstractControl implements ActionListener{
    private Vector3f velocity = new Vector3f();
    private float speed = 10f;

    public void setupInput(InputManager input){
        input.addMapping("left", new KeyTrigger(KeyInput.KEY_A));
        input.addMapping("right", new KeyTrigger(KeyInput.KEY_D));
        input.addMapping("forward", new KeyTrigger(KeyInput.KEY_W));
        input.addMapping("backward", new KeyTrigger(KeyInput.KEY_S));
        input.addListener(this, "left", "right", "forward", "backward");
    }
    @Override
    public void onAction(String name, boolean pressed, float tpf) {
        if(!pressed) return;
        switch (name){
            case "left" -> velocity.x -= speed;
            case "right" -> velocity.x += speed;
            case "forward" -> velocity.z += speed;
            case "backward" -> velocity.z -= speed;
        }
    }

    @Override
    protected void controlUpdate(float tpf) {
        spatial.move(velocity.mult(tpf));
        velocity.multLocal(0.8f);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}
}
