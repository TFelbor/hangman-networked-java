# CPSC329 : Software Development Final Project - Hangman Game

A networked Hangman game with client-server architecture implementing the classic word guessing game with visual hangman drawing.

## Features
- **Server Components**:
  - Multiplayer capable server
  - Random word selection
  - Game session management
  - Win/lose condition tracking

- **Client Components**:
  - Graphical user interface
  - Visual hangman drawing
  - Real-time game state updates
  - Multiple scene navigation

## System Requirements
- Java 8 or higher
- Maven (for building)

## Installation & Running
1. **Compile the project**:
```bash
javac -d bin src/Clients/*.java src/Player/*.java
```
2. **Start the server (in separate terminal)**:
```bash
java -cp bin Clients.HangmanServer
```
3. **Start a client:**
```bash
java -cp bin Player.Player
```

## Game Rules
1. The server selects a random word
2. Player guesses letters one at a time
3. Correct letters reveal their positions
4. Incorrect guesses reduce remaining attempts
5. Six incorrect guesses result in a loss
6. Guessing all letters wins the game

## Project Structure
```code
src/
├── Clients/
│   ├── Account.java       - User account management
│   ├── HangmanServer.java - Main server class
│   └── HangmanThread.java - Client connection handler
│
└── Player/
    ├── ButtonBar.java     - Common button panel
    ├── ChooserScene.java  - Word chooser interface
    ├── ConnectScene.java  - Connection setup
    ├── GuesserScene.java  - Main game interface
    ├── HintsScene.java    - Game hints
    ├── OutputTable.java   - Score display
    ├── Player.java        - Main client class
    ├── SceneBasic.java    - Base scene class
    ├── SceneManager.java  - Scene navigation
    ├── SettingsScene.java - Game settings
    ├── StartScene.java    - Main menu
    └── UserInput.java     - Input handling
```

## Protocol Specification
Client and server communicate using these messages:
```code
Message Format	   |      Description
-------------------+-------------------------------------------
SESSION:<id>       |      Session initialization
-------------------+-------------------------------------------
WORD:<masked>      |      Current word state (e.g., "J _ V _")
-------------------+-------------------------------------------
ATTEMPTS_LEFT:<n>  |      Remaining guess attempts
-------------------+-------------------------------------------
GUESS:<letter>     |      Client's letter guess
-------------------+-------------------------------------------
GAME_OVER:<result> |      Win/Lose notification
-------------------+-------------------------------------------
MESSAGE:<text>	   |      Information message
-------------------+-------------------------------------------
```

## Possible Enhancements
- User account system with score tracking
- Word categories and difficulty levels
- Multiplayer competition mode
- Enhanced graphics and animations
- Mobile client version
- 
