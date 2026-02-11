package site.meowcat;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.*;
import com.jme3.scene.*;
import com.jme3.scene.shape.Torus;

public class RingFactory {

    public static void spawnRingCircle(SimpleApplication app, Node parent) {

        float radius = 10f;

        for (int i = 0; i < 10; i++) {

            float angle = FastMath.TWO_PI * i / 10f;

            Vector3f pos = new Vector3f(
                    FastMath.cos(angle) * radius,
                    0,
                    FastMath.sin(angle) * radius
            );

            Torus torus = new Torus(16, 16, 0.2f, 1f);
            Geometry ring = new Geometry("Ring-" + i, torus);

            Material mat = new Material(
                    app.getAssetManager(),
                    "Common/MatDefs/Misc/Unshaded.j3md"
            );
            mat.setColor("Color", ColorRGBA.randomColor());
            ring.setMaterial(mat);

            ring.setLocalTranslation(pos);
            ring.setUserData("digit", i);

            parent.attachChild(ring);
        }
    }
}
