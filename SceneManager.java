package Player;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    public enum SceneType {
        START, CHOOSER, GUESSER, CONNECT, SETTINGS, HINTS
    }

    private final Player player;
    private final JFrame frame;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final Map<SceneType, SceneBasic> scenes;
    private SceneType currentScene;

    public SceneManager(Player player) {
        this.player = player;
        this.scenes = new HashMap<>();
        
        // Initialize frame
        frame = new JFrame("Hangman Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setMinimumSize(new Dimension(400, 400));
        
        // Set up card layout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        frame.add(cardPanel);
        
        // Create all scenes
        createScenes();
        
        // Show frame
        frame.setVisible(true);
    }

    private void createScenes() {
        scenes.put(SceneType.START, new StartScene(this));
        scenes.put(SceneType.CHOOSER, new ChooserScene(this));
        scenes.put(SceneType.GUESSER, new GuesserScene(this));
        scenes.put(SceneType.CONNECT, new ConnectScene(this));
        scenes.put(SceneType.SETTINGS, new SettingsScene(this));
        scenes.put(SceneType.HINTS, new HintsScene(this));

        // Add all scenes to card panel
        for (Map.Entry<SceneType, SceneBasic> entry : scenes.entrySet()) {
            cardPanel.add(entry.getValue(), entry.getKey().name());
        }
    }

    public void setScene(SceneType sceneType) {
        this.currentScene = sceneType;
        cardLayout.show(cardPanel, sceneType.name());
        scenes.get(sceneType).onSceneDisplayed();
        frame.revalidate();
    }

    public SceneBasic getCurrentScene() {
        return scenes.get(currentScene);
    }

    public void sendMessage(String message) {
        player.sendMessage(message);
    }
}
