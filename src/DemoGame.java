import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DemoGame extends Game {
    private Random random;
    
    public DemoGame() {
        super();
        this.random = new Random();
        // Reduce the sleep time for faster demonstration
    }
    
    @Override
    protected void handleUserInput() {
        // Do nothing in demo mode - just continue automatically
    }

    @Override
    protected void plantsTakeAction() {
        // Call the parent method to handle existing plant actions
        super.plantsTakeAction();
    }

    @Override
    protected void movePets() {
        // Create a temporary list to avoid concurrent modification
        List<Pet> petsToMove = new ArrayList<>();
        int[][] petPositions = new int[board.getRows()][board.getCols()];

        // Collect all pets and their positions
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                if (board.getPetAt(row, col) != null) {
                    petsToMove.add(board.getPetAt(row, col));
                    petPositions[row][col] = 1; // Mark position
                }
            }
        }

        // Move pets from right to left to avoid position conflicts
        for (int col = board.getCols() - 1; col >= 0; col--) {
            for (int row = 0; row < board.getRows(); row++) {
                Pet pet = board.getPetAt(row, col);
                if (pet != null && pet.isAlive()) {
                    // Decrement move cooldown
                    pet.decrementMoveCooldown();

                    if (pet.canMove()) {
                        // Try to move left (towards column 0)
                        if (col > 0) {
                            // Check if there's a plant in the next position
                            if (board.getPlantAt(row, col - 1) != null) {
                                // Attack the plant instead of moving
                                pet.performAction(board, player);
                            } else {
                                // Move to the left
                                board.setPetAt(row, col - 1, pet);
                                board.setPetAt(row, col, null); // Clear old position
                                pet.setPosition(row, col - 1);
                            }
                        } else {
                            // Pet reached the house (leftmost column)
                            pet.performAction(board, player);
                        }

                        // Reset move cooldown after moving
                        pet.resetMoveCooldown();
                    } else {
                        // Pet can't move this turn, still perform action if on plant
                        if (board.getPlantAt(row, col) != null) {
                            pet.performAction(board, player);
                        }
                    }
                } else if (pet != null && !pet.isAlive()) {
                    // Remove dead pet
                    board.setPetAt(row, col, null);
                }
            }
        }

        // Add new pets more frequently in demo mode (40% chance vs 20% in regular game)
        if (random.nextInt(100) < 40) { // Higher chance to spawn pets in demo mode
            int randomRow = random.nextInt(board.getRows());
            int randomPetType = random.nextInt(3);

            Pet newPet;
            switch (randomPetType) {
                case 0:
                    newPet = new Bunny();
                    break;
                case 1:
                    newPet = new Dog();
                    break;
                case 2:
                    newPet = new Hamster();
                    break;
                default:
                    newPet = new Bunny();
                    break;
            }

            // Place the pet in the rightmost column of the selected row
            if (board.getPetAt(randomRow, board.getCols() - 1) == null) {
                board.setPetAt(randomRow, board.getCols() - 1, newPet);
            }
        }
    }

    // Override the main game loop to add strategic plant placement
    @Override
    protected void displayGameState() {
        super.displayGameState();

        // Try to place strategic plants after displaying the state
        placeStrategicPlants();
    }

    private void placeStrategicPlants() {
        // Strategy 1: Place sunflowers in column 0 for consistent sun production (FIRST!)
        if (random.nextInt(100) < 40) { // 40% chance to prioritize sunflowers first
            placeSunflower();
        }

        // Strategy 2: Place Wall-nut in column 6 (just before Peashooter)
        if (random.nextInt(100) < 15) { // 15% chance to place Wallnut
            placeWallnut();
        }

        // Strategy 3: Place Peashooter where pets are approaching (AFTER sunflowers generate suns!)
        if (random.nextInt(100) < 35) { // 35% chance to place Peashooter after sunflowers
            placePeashooter();
        }
    }

    private void placeSunflower() {
        Sunflower sunflower = new Sunflower();
        if (player.canAfford(sunflower)) {
            // Only place sunflowers in column 0
            for (int row = 0; row < board.getRows(); row++) {
                if (board.getPlantAt(row, 0) == null) {
                    board.setPlantAt(row, 0, sunflower);
                    player.spendSuns(sunflower.getCost());
                    System.out.println("Demo: Placed Sunflower at (" + row + ",0) for sun production");
                    return;
                }
            }
        }
    }

    private void placeWallnut() {
        Wallnut wallnut = new Wallnut();
        if (player.canAfford(wallnut)) {
            // Place Wall-nut in column 6 (just before the Peashooter)
            for (int row = 0; row < board.getRows(); row++) {
                if (board.getPlantAt(row, 6) == null) {
                    board.setPlantAt(row, 6, wallnut);
                    player.spendSuns(wallnut.getCost());
                    System.out.println("Demo: Placed Wallnut at (" + row + ",6) for defense");
                    return;
                }
            }
        }
    }

    private void placePeashooter() {
        Peashooter peashooter = new Peashooter();
        if (player.canAfford(peashooter)) {
            // Find where pets are approaching and place Peashooter to defend
            for (int row = 0; row < board.getRows(); row++) {
                // Look for pets in the area before column 6 (where Wallnut is)
                // Find the rightmost pet in the row
                int rightmostPetCol = -1;
                for (int col = 5; col >= 1; col--) { // Check from column 5 down to 1
                    if (board.getPetAt(row, col) != null) {
                        rightmostPetCol = col;
                        break;
                    }
                }

                // Place Peashooter in the column just before the rightmost pet (or in column 5 if no pets)
                int targetCol = (rightmostPetCol > 1) ? rightmostPetCol - 1 : 5;

                // Make sure the target column is between 1 and 5 (before Wallnut in column 6)
                if (targetCol < 6 && board.getPlantAt(row, targetCol) == null) {
                    board.setPlantAt(row, targetCol, peashooter);
                    player.spendSuns(peashooter.getCost());
                    System.out.println("Demo: Placed Peashooter at (" + row + "," + targetCol + ") to attack approaching pets");
                    return;
                }
            }
        }
    }

    
    public void startDemo() {
        System.out.println("Welcome to Plants vs Pets Demo Mode!");
        System.out.println("Game will run automatically with slower pace for demonstration.");
        System.out.println("The game will place plants automatically to defend against pets.");
        System.out.println("Starting game in 3 seconds...");

        try {
            Thread.sleep(3000); // 3 second delay before starting
        } catch (InterruptedException e) {
            // Handle interruption
        }

        // Main game loop with slower pace for demo
        while (!player.isGameOver() && gameRunning) {
            // Display current state (this will also place strategic plants)
            displayGameState();

            // Generate suns from Sunflowers
            generateSuns();

            // Pets take actions (move and attack)
            movePets();

            // Plants take actions (attack)
            plantsTakeAction();

            // Check win condition
            if (board.hasNoPets() && player.getGameTurn() > 10) { // Increased turns to make it more interesting
                player.setGameOver(true);
                player.setWon(true);
            }

            // Increment turn
            player.incrementTurn();

            // Add a delay to make the game readable in demo mode
            try {
                Thread.sleep(2000); // 2 second delay between turns for slower pace
            } catch (InterruptedException e) {
                // Handle interruption
            }
        }

        // Game over logic
        if (player.hasWon()) {
            System.out.println("\nCongratulations! You defended your garden!");
            System.out.println("You won on turn " + player.getGameTurn());
        } else {
            System.out.println("\nOh no! The pets reached your house!");
            System.out.println("Game Over!");
        }

        System.out.println("Thanks for watching the Plants vs Pets demo!");
    }
    
    public static void main(String[] args) {
        DemoGame demoGame = new DemoGame();
        demoGame.startDemo();
    }
}