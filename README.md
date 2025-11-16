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
### Quick Start
To run the game, simply execute the run script:
```bash
bash run.sh
```

### Manual Compilation (Alternative)
If you prefer to compile manually:
1. Compile the game: `javac -d bin src/*.java`
2. Run the game: `java -cp bin pvpMain`

### Game Instructions
3. Collect suns to buy plants
4. Place plants strategically to defend against pets
5. Win by surviving for 5+ turns with no pets remaining
6. Lose if pets reach your house (leftmost column)

## Commands
- `place`: Place a new plant on the board
- `info`: Display game help and instructions
- `quit`: Exit the game

=== Turn 3 ===
Suns: 125
  0 1 2 3 4 5 6 7 8
0 . . . . . . . . .
1 . . . . . . . . .
2 . . S . . . . . .
3 . . . . . . . . .
4 . . . . . . . H .

Available Plants:
- Sunflower (Cost: 50)
- Peashooter (Cost: 100)

Commands: 'place', 'info', 'quit'

Enter command: place

Plant options:
1. Sunflower (Cost: 50)
2. Peashooter (Cost: 100)
3. Wallnut (Cost: 50)
4. Splashy Fern (Cost: 150)
Select plant (1-4): 2
Enter row (0-4): 2
Enter column (0-8): 4

Placed Peashooter at (2,4)