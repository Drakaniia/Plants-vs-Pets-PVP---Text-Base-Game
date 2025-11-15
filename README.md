# Plants vs Pets (PVP) - Text Base Game

## Description
Plants vs Pets is a text-based strategy game inspired by Plants vs Zombies. In this game, players defend their garden from pesky pets by strategically placing different types of plants with unique abilities. The game features turn-based combat, resource management, and strategic decision-making.

## Game Features
- **Turn-based Strategy**: Plan your defenses each turn as pets advance toward your house
- **Resource Management**: Collect suns from Sunflowers to purchase more plants
- **Multiple Plant Types**: Each plant has unique abilities and costs
  - Sunflower: Produces suns for currency
  - Peashooter: Basic attacker that shoots pets in its row
  - Wallnut: High-health defensive plant
  - Splashy Fern: Area-of-effect attacker
- **Multiple Pet Types**: Various pets with different stats and speeds
  - Bunny: Fast but weak
  - Dog: Strong and tough
  - Hamster: Very fast but weak
- **Grid-based Combat**: 5x9 grid where the battle takes place

## Game Objective
Stop pets from reaching your house (leftmost column) by strategically placing plants that can attack or block the advancing pets.

## System Requirements
- Java 8 or higher
- Command line interface

## How to Play
1. Compile the game: `javac -d bin src/*.java`
2. Run the game: `java -cp bin pvpMain`
3. Collect suns to buy plants
4. Place plants strategically to defend against pets
5. Win by surviving for 5+ turns with no pets remaining
6. Lose if pets reach your house (leftmost column)

## Commands
- `place`: Place a new plant on the board
- `info`: Display game help and instructions
- `quit`: Exit the game

## File Structure
- `Plant.java`: Abstract base class for all plants
- `Sunflower.java`: Sunflower plant implementation
- `Peashooter.java`: Peashooter plant implementation
- `Wallnut.java`: Wallnut plant implementation
- `SplashyFern.java`: Splashy Fern plant implementation
- `Pet.java`: Abstract base class for all pets
- `Bunny.java`: Bunny pet implementation
- `Dog.java`: Dog pet implementation
- `Hamster.java`: Hamster pet implementation
- `Player.java`: Manages player resources and state
- `GameBoard.java`: Manages the game grid
- `Game.java`: Main game logic and loop
- `pvpMain.java`: Entry point for the application