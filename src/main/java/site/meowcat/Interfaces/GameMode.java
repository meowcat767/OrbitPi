package site.meowcat.Interfaces;

public interface GameMode {
    void onCorrectDigit(int digit);
    String getHudText(int nextDigit);
}
