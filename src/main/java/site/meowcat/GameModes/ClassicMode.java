package site.meowcat.GameModes;

import site.meowcat.Interfaces.GameMode;

public class ClassicMode implements GameMode {
    @Override
    public void onDigitHit(int digit) {

    }

    @Override
    public String getHudText(int nextDigit) {
        return "Next digit: " + nextDigit;
    }
}
