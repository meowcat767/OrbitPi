# Orbitπ (OrbitPi)

Orbitπ is a fast-paced, rhythmic action game themed around the mathematical constant π (Pi). Originally developed for the UTC Portsmouth "Pi Game Coding Competition," Orbitπ challenges players to accurately select the digits of Pi in order while navigating an orbital environment.

## How to Play

### Objective
The goal is to hit the correct digits of Pi in sequence. You start with the first digit (3) and must progress through the digits of Pi. The game ends when you successfully hit 100 digits or lose all your lives.

### Controls
- **Spacebar**: Select the current digit.
- **Mouse**: Navigate menus.

### Gameplay Mechanics
- **The Player**: You control a white sphere that constantly orbits the center and bounces radially in and out.
- **Selecting Digits**: There are 10 rings (0-9) arranged in a circle. You must press **Space** when your sphere is at its maximum radius (inside a ring) to select that digit.
- **Lives**: You start with **3 lives**.
  - Missing a ring or hitting the wrong digit costs 1 life.
  - The game is over when you reach 0 lives.
- **Difficulty**: As your score increases, the radial velocity of your sphere increases, making the timing more challenging.

## Game Modes

Orbitπ features two distinct ways to test your Pi knowledge:

- **Classic Mode**: The HUD displays the next digit you need to hit. Great for learning the sequence or casual play.
- **Memory Mode**: The HUD only shows the *last* digit you successfully hit. You must remember the next digit in the sequence yourself!

## Features
- **Dynamic Speed**: The game gets faster as you score more points.
- **High Scores**: Track your best performances on the local leaderboard.
- **Audio**: Immersive background music and sound effects.
- **Visuals**: Clean 3D graphics powered by jMonkeyEngine, featuring a "digit rain" effect.

## Technical Stack
- **Engine**: [jMonkeyEngine 3](https://jmonkeyengine.org/) (jME3) for 3D rendering and game logic.
- **UI**: [Lemur](https://github.com/Simsilica/Lemur) library for menus and HUD.
- **Languages**: 
    - **Java**: Main game logic, states, and controls.
    - **Kotlin**: Data management and score tracking.
- **Build System**: Gradle.

## Building and Running

### Prerequisites
- JDK 17 or higher.

### Running the game
You can run the game directly using the Gradle wrapper:
```bash
./gradlew run
```

### Building a distribution
To create a standalone distribution:
```bash
./gradlew distZip
```
The resulting ZIP file in `build/distributions` will contain the game and all necessary libraries.

## Credits
Originally written for the UTC Portsmouth "Pi Game Coding Competition."
Developed by meowcat


