package site.meowcat.effects;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;

public class DigitRain {

    private static final String PI_DIGITS =
            "1234567890";

    private final BitmapText text;
    private final float speed;

    public DigitRain(BitmapFont font, float x, float y) {
        text = new BitmapText(font);
        text.setText(randomDigit());
        text.setLocalTranslation(x, y, 0);

        text.setColor(new ColorRGBA(0f, 1f, 0f, 1f)); // Matrix green

        speed = 50f + (float)Math.random() * 100f;
    }

    public void update(float tpf, float screenHeight) {
        text.move(0, -speed * tpf, 0);

        if (text.getLocalTranslation().y < 0) {
            text.setLocalTranslation(
                    text.getLocalTranslation().x,
                    screenHeight,
                    0
            );
            text.setText(randomDigit());
        }
    }

    public BitmapText getText() {
        return text;
    }

    private String randomDigit() {
        int index = (int)(Math.random() * PI_DIGITS.length());
        return String.valueOf(PI_DIGITS.charAt(index));
    }
}
