package site.meowcat.Interfaces;

public interface GameMode {
    void onDigitHit(int digit);

    String getHudText(int nextDigit);
}
