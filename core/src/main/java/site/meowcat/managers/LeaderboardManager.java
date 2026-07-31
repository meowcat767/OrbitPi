package site.meowcat.managers;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderboardManager {
    private static final String FILENAME = "leaderboard.dat";
    private static LeaderboardManager instance;
    private List<LeaderboardEntry> entries;

    private LeaderboardManager() {
        entries = new ArrayList<>();
        load();
    }

    public static LeaderboardManager getInstance() {
        if (instance == null) {
            instance = new LeaderboardManager();
        }
        return instance;
    }

    public List<LeaderboardEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public boolean qualifies(int score) {
        if (entries.size() < 10)
            return true;
        return score > entries.get(entries.size() - 1).getScore();
    }

    public void addEntry(String name, int score) {
        entries.add(new LeaderboardEntry(name, score));
        Collections.sort(entries, Comparator.comparingInt(LeaderboardEntry::getScore).reversed());
        if (entries.size() > 10) {
            entries = new ArrayList<>(entries.subList(0, 10));
        }
        save();
    }

    private void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(entries);
        } catch (IOException e) {
            System.err.println("Error saving leaderboard: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void load() {
        File file = new File(FILENAME);
        if (!file.exists())
            return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            entries = (List<LeaderboardEntry>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading leaderboard: " + e.getMessage());
        }
    }
}
