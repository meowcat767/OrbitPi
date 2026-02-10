package site.meowcat.managers;

public class PiManager {
    private final String pi = "314159265358979323846";
    private int index = 0;
    public int currentDigit() {
        return pi.charAt(index) - '0';
    }
    public void advance() {
        index = (index + 1) % pi.length();
    }
    public int getIndex() {
        return index;
    }
}
