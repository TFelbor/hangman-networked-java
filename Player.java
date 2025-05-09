package Player;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Player {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5555;
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private SceneManager sceneManager;
    
    public Player() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            sceneManager = new SceneManager(this);
            sceneManager.setScene(SceneManager.SceneType.START);
            
            new Thread(this::listenForMessages).start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error connecting to server: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private void listenForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                String finalMessage = message;
                SwingUtilities.invokeLater(() -> 
                    sceneManager.getCurrentScene().handleMessage(finalMessage)
                );
            }
        } catch (IOException e) {
            SwingUtilities.invokeLater(() -> 
                JOptionPane.showMessageDialog(null, "Connection to server lost")
            );
        }
    }
    
    public void sendMessage(String message) {
        out.println(message);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new Player();
        });
    }
}
