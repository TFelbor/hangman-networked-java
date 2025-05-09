package Clients;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class HangmanServer {
    private static final int PORT = 5555;
    private static final String[] WORDS = {"JAVA", "PROGRAMMING", "HANGMAN", "COMPUTER", "ALGORITHM"};
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    private static Map<String, String> currentGames = new ConcurrentHashMap<>();
    private static Map<String, Integer> attemptsLeft = new ConcurrentHashMap<>(); 
    private static Map<String, Set<Character>> guessedLetters = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Hangman Server is running...");
        
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                HangmanThread clientThread = new HangmanThread(clientSocket);
                pool.execute(clientThread);
            } catch (IOException e) {
                System.out.println("Error accepting client connection: " + e.getMessage());
            }
        }
    }

    static class HangmanThread implements Runnable {
        private Socket clientSocket;
        private String sessionID;

        public HangmanThread(Socket socket) {
            this.clientSocket = socket;
            this.sessionID = UUID.randomUUID().toString();
        }

        public void run() {
            try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                // Initialize game
                String word = WORDS[new Random().nextInt(WORDS.length)].toUpperCase();
                currentGames.put(sessionID, word);
                attemptsLeft.put(sessionID, 6);
                guessedLetters.put(sessionID, new HashSet<>());

                // Send initial state
                out.println("SESSION:" + sessionID);
                out.println("WORD:" + maskWord(word, new HashSet<>()));
                out.println("ATTEMPTS_LEFT:6");

                // Handle game loop
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.startsWith("GUESS:")) {
                        handleGuess(inputLine.substring(6).toUpperCase().charAt(0), out);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
            } finally {
                cleanupSession();
            }
        }

        private void handleGuess(char guess, PrintWriter out) {
            String word = currentGames.get(sessionID);
            Set<Character> guessed = guessedLetters.get(sessionID);
            
            if (guessed.contains(guess)) {
                out.println("MESSAGE:You already guessed that letter");
                return;
            }
            
            guessed.add(guess);
            if (word.indexOf(guess) < 0) {
                int attempts = attemptsLeft.get(sessionID) - 1;
                attemptsLeft.put(sessionID, attempts);
                out.println("ATTEMPTS_LEFT:" + attempts);
                
                if (attempts <= 0) {
                    out.println("GAME_OVER:LOSE");
                    out.println("WORD:" + word);
                    return;
                }
            }
            
            String masked = maskWord(word, guessed);
            out.println("WORD:" + masked);
            
            if (!masked.contains("_")) {
                out.println("GAME
