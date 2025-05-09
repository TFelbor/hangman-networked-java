package Clients;
//Created by Will
//This program is uses by every thread to run the server client relationship
import Player.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class HangmanThread extends Thread {

	public static Socket guesser;//socket from guesser
	public static Socket chooser;//socket from chooser
	private Account guesserAccount = new Account("");//a guesser account
	private Account chooserAccount = new Account("");//a chooser account
	public static BufferedReader chooserIncoming;//buffered reader to read server
	public static PrintWriter chooserOutgoing;//printwriter to communciate to server
	public static BufferedReader guesserIncoming;//buffered reader to read server
	public static PrintWriter guesserOutgoing;//printwriter to communicate to server
	private String lineFromChooser;//string from chooser
	private String lineFromGuesser;//string from guesser
	private String hints[] = new String[3]; //array of hints
	private String realWord; //a word for the game
	private int guessCount = 5;
	private String guess = "THISISSTUPID"; //a character guess from guesser
	private ArrayList<String> displayWord = new ArrayList<String>(); //a word that the guesser sees
	

	public HangmanThread(Socket guesser, Socket chooser) {
		HangmanThread.chooser = chooser;
		HangmanThread.guesser = guesser;
	}

	public void run() {
		
		try {
			System.out.print(chooser.getLocalPort());
			chooserIncoming = new BufferedReader(new InputStreamReader(chooser.getInputStream()));
			chooserOutgoing = new PrintWriter(chooser.getOutputStream());
			guesserIncoming = new BufferedReader(new InputStreamReader(guesser.getInputStream()));
			guesserOutgoing = new PrintWriter(guesser.getOutputStream());
			getHints(chooserIncoming);
			String lineFromGuesser = guesserIncoming.readLine();
			guesserAccount.setUsername(lineFromGuesser);
			sendDisplay(realWord, displayWord, guess); 
			boolean choice = false;
			while (!choice) {
				lineFromGuesser = guesserIncoming.readLine();
				if (lineFromGuesser.equals("SEND_GUESS")) {
					guess = guesserIncoming.readLine();
					System.out.println("Received this guess   " + guess);
					sendDisplay(realWord, displayWord, guess);
					
				}
				else if (lineFromGuesser.equals("SEND_HINTS")) {
					sendHints();
				}
				
				
			}

		} catch (Exception e) {
			System.out.println("Sorry, the server has shut down.");
			System.out.println("Error:  " + e);
			return;
		}

	}
	
	//gets the hints from the chooser
	public void getHints(BufferedReader chooserIncoming) {
		try {
			String lineFromChooser = chooserIncoming.readLine();
			chooserAccount.setUsername(lineFromChooser);
			System.out.println("This is the choosers name: " + lineFromChooser);
			lineFromChooser = chooserIncoming.readLine();
			hints[0] = lineFromChooser;
			System.out.println("This is the choosers Hint1: " + lineFromChooser);
			lineFromChooser = chooserIncoming.readLine();
			hints[1] = lineFromChooser;
			System.out.println("This is the choosers Hint2: " + lineFromChooser);
			lineFromChooser = chooserIncoming.readLine();
			hints[2] = lineFromChooser;
			System.out.println("This is the choosers Hint3: " + lineFromChooser);
			lineFromChooser = chooserIncoming.readLine();
			realWord = lineFromChooser;
			System.out.println("This is the choosers Word: " + lineFromChooser);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//a method to set and send the desplay word to guesser
	public void sendDisplay(String realWord, ArrayList<String> displayWord, String guess) {
		if (guess == "THISISSTUPID") {
			int temp = realWord.length(); //get length of real word
			for (int i = 0; i < temp; i ++) {
				displayWord.add(i, "_");
			}
			guesserOutgoing.println("You have " + guessCount + " guess" + displayWord.toString());
			System.out.println("Sending this display back:" + displayWord.toString());
			guesserOutgoing.flush();
		}
		else {
			guessCount--;
			for (int i = 0; i < realWord.length(); i ++) {
				if (guess.equals(realWord.substring(i,i + 1))) {
					displayWord.set(i, guess);
					guessCount++;
				}
			}
			guesserOutgoing.println("You have " + guessCount + " guess" + displayWord.toString());
			System.out.println("Sending this display back:" + displayWord.toString());
			guesserOutgoing.flush();
		}
	}
	
	//a method to send the hints to the client
	public void sendHints() {
		guesserOutgoing.println(hints[0]);
		guesserOutgoing.println(hints[1]);
		guesserOutgoing.println(hints[2]);
		guesserOutgoing.flush();


	}
	
	
}
