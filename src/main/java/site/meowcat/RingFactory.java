package site.meowcat;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.*;
import com.jme3.scene.*;
import com.jme3.scene.shape.Torus;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;

public class RingFactory {

    public static void spawnRingCircle(SimpleApplication app, Node parent) {
        BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");

        float radius = 10f;

        for (int i = 0; i < 10; i++) {

            float angle = FastMath.TWO_PI * i / 10f;

            Vector3f pos = new Vector3f(
                    FastMath.cos(angle) * radius,
                    0,
                    FastMath.sin(angle) * radius
            );

            Torus torus = new Torus(16, 16, 0.2f, 1f);
            Geometry ringGeom = new Geometry("RingGeom-" + i, torus);

            Material mat = new Material(
                    app.getAssetManager(),
                    "Common/MatDefs/Misc/Unshaded.j3md"
            );
            mat.setColor("Color", ColorRGBA.randomColor());
            ringGeom.setMaterial(mat);
            
            Node ringNode = new Node("Ring-" + i);
            ringNode.setLocalTranslation(pos);
            ringNode.setUserData("digit", i);
            ringNode.attachChild(ringGeom);

            BitmapText label = new BitmapText(font);
            label.setText(String.valueOf(i));
            label.setSize(1.5f);
            float width = label.getLineWidth();
            float height = label.getLineHeight();
            label.setLocalTranslation(-width / 2, height / 2, 0);
            
            Node labelNode = new Node("LabelNode");
            labelNode.setLocalTranslation(0, 1.5f, 0);
            labelNode.attachChild(label);
            ringNode.attachChild(labelNode);

            parent.attachChild(ringNode);
        }
    }
}
