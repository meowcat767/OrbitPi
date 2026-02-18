package site.meowcat.managers;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;

import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private static AudioManager instance;
    private AssetManager assetManager;
    private Node rootNode;
    private AudioNode bgm;
    private Map<String, AudioNode> sfxMap = new HashMap<>();
    private float volume = 0.8f;

    private AudioManager() {
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void initialize(AssetManager assetManager, Node rootNode) {
        this.assetManager = assetManager;
        this.rootNode = rootNode;
        this.volume = GameSettings.volume;
    }

    public void playBGM(String name) {
        if (bgm != null) {
            bgm.stop();
            rootNode.detachChild(bgm);
        }
        bgm = new AudioNode(assetManager, "Sounds/" + name, AudioData.DataType.Stream);
        bgm.setLooping(true);
        bgm.setPositional(false);
        bgm.setVolume(volume);
        rootNode.attachChild(bgm);
        bgm.play();
    }

    public void playSFX(String name) {
        AudioNode sfx = sfxMap.get(name);
        if (sfx == null) {
            sfx = new AudioNode(assetManager, "Sounds/" + name, AudioData.DataType.Buffer);
            sfx.setLooping(false);
            sfx.setPositional(false);
            sfxMap.put(name, sfx);
            rootNode.attachChild(sfx);
        }
        sfx.setVolume(volume);
        sfx.playInstance();
    }

    public void setVolume(float volume) {
        this.volume = volume;
        if (bgm != null) {
            bgm.setVolume(volume);
        }
        for (AudioNode sfx : sfxMap.values()) {
            sfx.setVolume(volume);
        }
    }
}
