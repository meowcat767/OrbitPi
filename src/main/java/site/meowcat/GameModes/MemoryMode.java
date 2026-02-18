package site.meowcat.GameModes;

import site.meowcat.Interfaces.GameMode;

public class MemoryMode implements GameMode {
    private int lastDigit = -1;

    @Override
    public void onDigitHit(int digit) {
        lastDigit = digit;
    }

    @Override
    public String getHudText(int nextDigit) {
        if (lastDigit == -1) {
            return "Enter " + nextDigit;
        }
        return "Last digit: " + lastDigit;
    }
}
